package io.jsonbox;

import java.io.IOException;
import java.util.Map;

import okhttp3.*;

public class Http {
  public final String url = "https://jsonbox.io";
  private final OkHttpClient client = new OkHttpClient();
  private final MediaType mediaType = MediaType.parse("application/json");

  public String request(String storageId, String recordId, String method, Map<String,String> parameters, String json) throws IOException {
    HttpUrl.Builder urlBuilder = this.createBuilder(storageId, recordId, parameters);
    RequestBody body = this.createBody(json);      
    Request.Builder requestBuilder = this.createRequestBuilder(method, json, urlBuilder, body);
    
    Response response = client.newCall(requestBuilder.build()).execute();

    return response.body().string();
  }

  private HttpUrl.Builder createBuilder(String storageId, String recordId, Map<String,String> parameters) {
    String requestUrl = this.url;
    if(storageId != null && storageId.length() > 0){
      requestUrl += "/" + storageId;

      if(recordId != null && recordId.length() > 0) {
        requestUrl += "/" + recordId;
      }
    }
    
    HttpUrl.Builder urlBuilder = HttpUrl.parse(requestUrl)
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