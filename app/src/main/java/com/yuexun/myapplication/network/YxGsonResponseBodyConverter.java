package com.yuexun.myapplication.network;

import static java.nio.charset.StandardCharsets.UTF_8;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.yuexun.myapplication.app.entity.BaseResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Converter;

public class YxGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;
    private final Boolean isList;
    private final Boolean isBase;

    YxGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter, boolean isList, boolean isBase) {
        this.gson = gson;
        this.adapter = adapter;
        this.isList = isList;
        this.isBase = isBase;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();

        BaseResponse httpStatus = gson.fromJson(response, BaseResponse.class);
        if (httpStatus.getStatus() != 200 && httpStatus.getStatus() != 0) {
            throw new ApiException(httpStatus.getStatus(), httpStatus.getMessage());

        }
        MediaType contentType = value.contentType();
        Charset charset = contentType != null ? contentType.charset(UTF_8) : UTF_8;
        InputStream inputStream = new ByteArrayInputStream(response.getBytes());
        Reader reader = new InputStreamReader(inputStream, charset);
        JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();
        JsonArray array = jsonObject.getAsJsonArray("datas");
        if (array == null) {
            array = jsonObject.getAsJsonArray("data");
        }
        if (!isBase) {

            if (array != null && array.size() > 0) {
                if (array.size() == 1 && !isList) {
                    JsonObject t = array.get(0).getAsJsonObject();
                    inputStream = new ByteArrayInputStream(t.toString().getBytes());
                } else {
                    inputStream = new ByteArrayInputStream(array.toString().getBytes());
                }
                reader = new InputStreamReader(inputStream, charset);
            } else {
                inputStream = new ByteArrayInputStream(array.toString().getBytes());
                reader = new InputStreamReader(inputStream, charset);
            }
        }
        JsonReader jsonReader = gson.newJsonReader(reader);
        try {
            return adapter.read(jsonReader);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return null;
        } finally {
            value.close();
        }
    }
}
