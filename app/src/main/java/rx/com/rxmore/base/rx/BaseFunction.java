package rx.com.rxmore.base.rx;



import io.reactivex.functions.Function;
import rx.com.rxmore.base.data.protocol.BaseResp;

/**
    公共数据类型转换
 */
public class BaseFunction<T> implements Function<BaseResp<T>,T> {
    @Override
    public T apply(BaseResp<T> t) throws Exception {
        if(!t.getCode().equals("200")){
            new BaseException(t.getCode(),t.getMessage());
        }
        return t.getData();
    }
}

