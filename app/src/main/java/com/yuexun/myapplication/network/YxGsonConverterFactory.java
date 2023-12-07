package com.yuexun.myapplication.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import timber.log.Timber;

public class YxGsonConverterFactory extends Converter.Factory {
    /**
     * Create an instance using a default {@link Gson} instance for conversion. Encoding to JSON and
     * decoding from JSON (when no charset is specified by a header) will use UTF-8.
     */
    public static YxGsonConverterFactory create() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        return create(gson);
    }

    /**
     * Create an instance using {@code gson} for conversion. Encoding to JSON and
     * decoding from JSON (when no charset is specified by a header) will use UTF-8.
     */
    @SuppressWarnings("ConstantConditions") // Guarding public API nullability.
    public static YxGsonConverterFactory create(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        return new YxGsonConverterFactory(gson);
    }

    private final Gson gson;

    private YxGsonConverterFactory(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {

        boolean isList = false;
        boolean isBase = false;
        try {

            ParameterizedType t = (ParameterizedType) type;
            isList = t.getRawType().toString().contains("java.util.List");
            isBase = t.getRawType().toString().endsWith("com.yuexunit.retrofit.converter.BaseResponse");
        } catch (Exception e) {
            Timber.i("传入参数类型不是list");
        }
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new YxGsonResponseBodyConverter<>(gson, adapter, isList, isBase);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                          Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {

        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));

        return new YxGsonRequestBodyConverter<>(gson, adapter);
    }
}
