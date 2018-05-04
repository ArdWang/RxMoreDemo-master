# 详细的MVP+Retrofit2+Rxjava2+RxLifecycle2.0的项目流程开发

**本次详细的讲解MVP+Retrofit2+Rxjava2+RxLifecycle2.0项目流程，包含了怎么去使用基本的方法去调用服务器一个登录的接口，以及包的创建流程，本文章都为基础知识。基本能使大家看懂。**



-------------------

## 导入依赖

 
App bulid.gradle
```
apply plugin: 'com.android.application'

android {
    compileSdkVersion 26
    defaultConfig {
        applicationId "rx.com.rxmore"
        minSdkVersion 19
        targetSdkVersion 26
        versionCode 1
        versionName "1.0"
        testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }
    }
}

dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation 'com.android.support:appcompat-v7:26.1.0'
    implementation 'com.android.support.constraint:constraint-layout:1.1.0'
    testImplementation 'junit:junit:4.12'
    androidTestImplementation 'com.android.support.test:runner:1.0.1'
    androidTestImplementation 'com.android.support.test.espresso:espresso-core:3.0.1'


    //retrofit2+rxjava2+rxlifecycle2 网络请求框架
    compile "io.reactivex.rxjava2:rxjava:2.1.3"
    compile "io.reactivex.rxjava2:rxandroid:2.0.1"
    compile "com.squareup.retrofit2:retrofit:2.3.0"
    compile "com.squareup.retrofit2:converter-gson:2.3.0"
    compile "com.squareup.retrofit2:adapter-rxjava2:2.3.0"
    compile "com.jakewharton.rxrelay2:rxrelay:2.0.0"
    compile "com.trello.rxlifecycle2:rxlifecycle-android:2.2.1"
    compile "com.trello.rxlifecycle2:rxlifecycle-components:2.2.1"

    //okhttp3
    compile "com.squareup.okhttp3:okhttp:3.10.0"
    compile "com.squareup.okhttp3:logging-interceptor:3.9.0"

}

```

## 包名创建

> 详细的请看[Github][2]代码

你也可以自己创建相应的包名，项目中只使用了Presenter+View结构 省去了model层

详细的请参考[Github][2].中的项目  

### Base包里面的内容

**common包**

里面包含了 BaseConstant 类 存放服务器的地址

```
public class BaseConstant {
    //本地服务器地址 必须要带/结尾 不然请求就会报错
    public static final String SERVER_ADDRESS = "http://192.168.0.100:8080/";
}
```
自定义MainApplication 集成与Application 主要的作用是获取全局的 Context

```
public class MainApplication extends Application{

    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    //获取全局变量的Context
    public static Context getContext(){
        return context;
    }
}
```
###Data包
包含了 net包 用户配置 Retrofit2的工厂类 包含 网络拦截器 Okhttp3 创建以及Retrofit实例化，Okhttp3的日志拦截 详细注解看以下代码

```
public class RetrofitFactory {

    private Retrofit retrofit;

    //通用拦截器
    private Interceptor interceptor;

    //单列模式
    private static class SingletonHolder{
        private static final RetrofitFactory INSTANCE = new RetrofitFactory();
    }

    public RetrofitFactory(){
        interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                        //返回参数
                        .addHeader("Content_Type","application/json")
                        //添加编码格式
                        .addHeader("charset","UTF-8")
                        //token 可以将存为传入到服务器的token
                        .addHeader("token", "1")
                        .build();
                return chain.proceed(request);
            }
        };


        //Retrofit实例化
        retrofit =new Retrofit.Builder()
                .baseUrl(BaseConstant.SERVER_ADDRESS)
                //与Gson结合
                .addConverterFactory(GsonConverterFactory.create())
                //与Rxjava结合
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //OKHttpClient初始化
                .client(initClient())
                .build();
    }

    /**
       OKHttp创建
    */
    private OkHttpClient initClient(){
        return new OkHttpClient.Builder()
                //日志拦截器
                .addInterceptor(initLogInterceptor())
                //处理头部拦截器
                .addInterceptor(interceptor)
                //连接时间 10秒
                .connectTimeout(20, TimeUnit.SECONDS)
                //超时时间 10秒
                .readTimeout(20, TimeUnit.SECONDS)
                .build();
    }

    /**
        日志拦截器
     */
    private HttpLoggingInterceptor initLogInterceptor(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        //日志级别 Body级别
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }


    /**
     * 获取RetrofitServiceManager
     * @return
     */
    public static RetrofitFactory getInstance(){
        return RetrofitFactory.SingletonHolder.INSTANCE;
    }

    /**
     * 获取对应的Service
     * @param service Service 的 class
     * @param <T>
     * @return
     */
    public <T> T  create(Class<T> service){
        return retrofit.create(service);
    }
    
}

```
protocol 包是存放 BaseResp 服务器返回回来的json数据处理 里面包含了一个泛型的类，可以根据需要自定义相应的获取数据Model类

```
/**
    服务器返回回来的数据 统一处理
    比如我的服务器返回回来的数据为 {"code":"200","message":"登录成功"，"data":{"username":"123456",...}}
    象这种json就需要另外定义一个Base基类来处理Json数据
    T为任意对象  了解泛型
 */
public class BaseResp<T> {

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    private String message;
    private T data;
}

```
BaseRepository包 用来配置 Rxjava2 里面Observable运行线程配置以及Rxlifecycer注入

```
/**
 * 配置Rxjava2 里面Observable运行线程配置以及Rxlifecycer注入
 */
public class BaseRepository {
    //Activity
    protected <T> Observable<T> observeat(Observable<T> observable,LifecycleProvider<ActivityEvent> lifecycleProvider){
        return observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .compose(lifecycleProvider.<T>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread()); //指定在主线程中
    }

    //Fragment
    protected <T> Observable<T> observefg(Observable<T> observable, LifecycleProvider<FragmentEvent> lifecycleProvider){
        return observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .compose(lifecycleProvider.<T>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread()); //指定在主线程中
    }
}

```
其他的包为常用的MVP 里面配置 Base基类操作 详细看代码

###Rx的包 此包是把Rxjava做了简单的封装

第一个  BaseException 基本错误处理类 获取服务器返回回来的错误处理

```
public class BaseException extends Throwable{
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BaseException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private String code;
    private String message;
}

```
第二个 BaseFunction 公共数据类型的转换 服务器返回200是表示成功 0表示失败
把服务器返回回来BaseResp<T>转为需要的Observable<T>类型。
目的就是把服务器返回数据的model类型直接转为自己所需要的model类型。

```

/*
    公共数据类型转换
 */
public class BaseFunction<T> implements Function<BaseResp<T>,Observable<T>> {
    @Override
    public Observable<T> apply(BaseResp<T> t) throws Exception {
        if(!t.getCode().equals("200")){
            return Observable.error(new BaseException(t.getCode(),t.getMessage()));
        }
        return Observable.just(t.getData());
    }
}
```
BaseFunctionBoolean类型同上面的意思一样 只不过转换为一个Boolean类型

```
/**
 * 通用类型转换封装 可以强转T类型变为 true或者false
 */

public class BaseFunctionBoolean<T> implements Function<BaseResp<T>,Observable<Boolean>> {
    @Override
    public Observable<Boolean> apply(BaseResp<T> t) throws Exception {
        if (!t.getCode().equals("200")){
            return Observable.error(new BaseException(t.getCode(), t.getMessage()));
        }
        return Observable.just(true);
    }
}
```
BaseObserver  此类里面主要处理 数据加载完成后 需要把 progressLoading 对话框隐藏掉 还有请求错误的时候返回到操作界面的错误显示。以及实现了(观察者)Observer接口来处理相应的事件

```
public class BaseObserver<T> implements Observer<T> {

    private BaseView baseView;

    public BaseObserver(BaseView v){
        baseView = v;
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onError(Throwable e) {
        baseView.hideLoading();
        if(e instanceof BaseException){
            baseView.onError(e.getMessage());
        }
    }

    @Override
    public void onComplete() {
        baseView.hideLoading();
    }
}
```

###User包

首先data包里面 有接口地址
 

```
/**
   服务器采用ResultFul 风格 域名+接口地址
 */

public interface UserApi {
    @POST("/user/getUser")
    Observable<BaseResp<User>> getUser(@Body GetUserReq req);
}
```

GetUserReq 类用于传入服务器所需要的接口参数

```
public class GetUserReq {
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPushid() {
        return pushid;
    }

    public void setPushid(String pushid) {
        this.pushid = pushid;
    }

    private String phone;

    //构造方法
    public GetUserReq(String phone, String password, String pushid) {
        this.phone = phone;
        this.password = password;
        this.pushid = pushid;
    }

    private String password;
    private String pushid;
}

```
UserRepository 此类需要继承与 BaseRepository 主要是注意 此数据转换的时候使用的是 flatMap进行数据转换，可以将任意类型数据相互转换 比如把 T 类型 转为 List<T>类型 等等。

```
public class UserRepository extends BaseRepository{

    public Observable<User> getUser(String phone, String password, String pushid,LifecycleProvider<ActivityEvent> lifeProvider){
        return observeat(RetrofitFactory.getInstance()
                .create(UserApi.class)
                .getUser(new GetUserReq(phone,password,pushid)),lifeProvider)
                .flatMap(new BaseFunction());

    }

}
```

UserPresenter类里面主要用于 网络数据交互处理

```
public class UserPresenter extends BasePresenter<UserView>{

    private UserRepository userRepository;

    public void getUser(String phone, String password, String pushid,
                        LifecycleProvider<ActivityEvent> lifeProvider){

        userRepository = new UserRepository();

        if(!checkNetWork()){
            return;
        }

        mView.showLoading();

        userRepository.getUser(phone,password,pushid,lifeProvider).subscribe(new BaseObserver<User>(mView){
            @Override
            public void onNext(User user) {
                mView.onGetUserResult(user);
            }
        });


    }
}

```

详细代码请看github 源码分析。
