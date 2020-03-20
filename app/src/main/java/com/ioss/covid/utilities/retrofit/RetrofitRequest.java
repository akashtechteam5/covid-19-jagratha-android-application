package com.ioss.covid.utilities.retrofit;

import com.ioss.covid.utilities.Constants;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitRequest {


    private static Retrofit retrofit;
    private static Retrofit retrofitGoogle;

    private static final String BASE_URL = Constants.URL +"app/";
//    private static final String WEBVIEW_URL = "http://teamioss.in/covid/login/";
    private static final String GOOGLE_BASE_URL = "https://maps.googleapis.com/";

    public static Retrofit getRetrofitInstance() {

        if (retrofit == null) {

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS);

            httpClient.addInterceptor(logging);
            httpClient.addInterceptor(new Interceptor() {
                        @Override
                        public okhttp3.Response intercept(Chain chain) throws IOException {
                            Request request = chain.request();
                            okhttp3.Response response = chain.proceed(request);
                            // todo deal with the issues the way you need to

                            if (response.code() == 403) {
                            }

                            return response;
                        }
                    });

             retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

        }

        return retrofit;
    }

/*
    public static Retrofit getWebRetrofitInstance() {


            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS);

            httpClient.addInterceptor(logging);
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    okhttp3.Response response = chain.proceed(request);
                    // todo deal with the issues the way you need to

                    if (response.code() == 403) {
                    }

                    return response;
                }
            });

        Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(WEBVIEW_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

        return retrofit;
    }
*/


    public static Retrofit getGoogleRetrofitInstance() {

        if (retrofitGoogle == null) {

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(logging);

            retrofitGoogle = new Retrofit.Builder()
                    .baseUrl(GOOGLE_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }

        return retrofitGoogle;
    }
}