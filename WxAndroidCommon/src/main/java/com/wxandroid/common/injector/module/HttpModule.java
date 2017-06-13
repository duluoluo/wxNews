package com.wxandroid.common.injector.module;

import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.wxandroid.common.CommonApplication;
import com.wxandroid.common.http.GlobeHttpHandler;
import com.wxandroid.common.http.RequestIntercept;
import com.wxandroid.common.utils.Constants;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.wxandroid.common.http.GlobeHttpHandler.globeHttpHandler;

/**
 * Created by wenxin
 */
@Module
public class HttpModule {

    private GlobeHttpHandler mHandler;

    @Singleton
    @Provides
    Retrofit provideRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, Constants.BASEURL);
    }

    @Singleton
    @Provides
    Retrofit.Builder provideRetrofitBuilder() {
        return new Retrofit.Builder();
    }

    @Singleton
    @Provides
    OkHttpClient.Builder provideOkHttpBuilder() {
        return new OkHttpClient.Builder();
    }

    @Singleton
    @Provides
    OkHttpClient provideClient(OkHttpClient.Builder builder, Interceptor intercept, Cache cache) {
        //设置超时
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.writeTimeout(10, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        builder.addNetworkInterceptor(intercept);
        builder.cache(cache);
        //https支持
//        try {
//            setCertificates(builder,
//                    CommonApplication.getContext().getAssets().open(".crt"));

//        builder.hostnameVerifier(new HostnameVerifier() {
//            public boolean verify(String hostname, SSLSession session) {
//                return true;
//            }
//        });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return builder.build();
    }

    protected Retrofit createRetrofit(Retrofit.Builder builder, OkHttpClient client, String url) {
        return builder
                .baseUrl(url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd hh:mm:ss")
                        .create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }


    @Singleton
    @Provides
    Cache provideCache(CommonApplication myApplication) {
        //设置缓存
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        File cacheDir = myApplication.getCacheDir();
        File httpCacheDirectory = new File(cacheDir, "OkHttpCache");
        return new Cache(httpCacheDirectory, cacheSize);
    }

    @Singleton
    @Provides
    Interceptor provideIntercept(RequestIntercept intercept) {
        return intercept;//打印请求信息的拦截器
    }


    @Singleton
    @Provides
    GlobeHttpHandler provideGlobeHttpHandler() {
        return mHandler == null ? globeHttpHandler : mHandler;
    }


    /**
     * 配置https证书
     *
     * @param builder
     * @param certificates
     */
    public void setCertificates(OkHttpClient.Builder builder, InputStream... certificates) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            for (InputStream certificate : certificates) {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));

                try {
                    if (certificate != null)
                        certificate.close();
                } catch (IOException e) {
                }
            }

            SSLContext sslContext = SSLContext.getInstance("TLS");

            TrustManagerFactory trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());

            trustManagerFactory.init(keyStore);
            sslContext.init
                    (
                            null,
                            trustManagerFactory.getTrustManagers(),
                            new SecureRandom()
                    );
            builder.sslSocketFactory(sslContext.getSocketFactory());


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
