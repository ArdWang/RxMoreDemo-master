package rx.com.rxmore.base.data.net;


import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.com.rxmore.base.common.BaseConstant;


/**
 * Retrofit工厂配置
 */
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
