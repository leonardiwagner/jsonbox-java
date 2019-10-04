package io.jsonbox;

import java.io.IOException;
import java.util.Map;

import okhttp3.*;

public class Http {
  public static final String url = "https://jsonbox.io/";
  private static final OkHttpClient client = new OkHttpClient();
  private static final MediaType mediaType = MediaType.parse("application/json");

  public String request(String id, String method, Map<String,String> parameters, String json) throws IOException {
    HttpUrl.Builder urlBuilder = this.createBuilder(id, parameters);
    RequestBody body = this.createBody(json);      
    Request.Builder requestBuilder = this.createRequestBuilder(method, json, urlBuilder, body);
    
    Response response = client.newCall(requestBuilder.build()).execute();

    return response.body().string();
  }

  private HttpUrl.Builder createBuilder(String id, Map<String,String> parameters) {
    HttpUrl.Builder urlBuilder = HttpUrl.parse(url + id)
      .newBuilder();

    if(parameters.size() > 0) {
      for(Map.Entry<String, String> param : parameters.entrySet()) {
        urlBuilder.addQueryParameter(param.getKey(),param.getValue());
      }
    }

    return urlBuilder;
  }

  private RequestBody createBody(String json) {
    boolean hasJsonBody = json != null && json.length() > 0;

    RequestBody body = null;
    if(hasJsonBody) {
      body = RequestBody.create(mediaType, json);
    }

    return body;
  }

  private Request.Builder createRequestBuilder(String method, String json, HttpUrl.Builder urlBuilder, RequestBody body) {
    Request.Builder requestBuilder = new okhttp3.Request.Builder()
            .url(urlBuilder.build())
            .method(method, body)
            .addHeader("cache-control", "no-cache");
    
    boolean hasJsonBody = json != null && json.length() > 0;
    if(hasJsonBody) {
      requestBuilder.addHeader("content-type", "application/json");
    }

    return requestBuilder;
  }
}