package com.example.wong.testing.rest;

/**
 * Created by Wong on 4/1/17.
 */

import android.content.Context;

import com.example.wong.testing.R;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {
    public static final String BASE_URL = "https://www.kimhau.dev/task_manager/v1/";
    private static Retrofit retrofit = null;
    private static final String TAG = "client";
    public static final String HOST = "www.kimhau.dev";
    public static final String PUBLIC_KEY_HASH = "sha256/mgRQrkNH6zuS4sBcAuDxMhilrgFmUPsvlJ5xd1pe6Cs=";
    private static Context context;

    public Client(Context con) {
        super();
        this.context = con;
    }



   /* public static Retrofit getClient() throws CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        clientBuilder.addInterceptor(loggingInterceptor);

        CertificatePinner certificatePinner = new CertificatePinner.Builder()
                .add(HOST, PUBLIC_KEY_HASH)
                .build();
        final OkHttpClient client = clientBuilder.certificatePinner(certificatePinner).build();


        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }

        return retrofit;
    }*/

    public static Retrofit createAdapter(Context context) {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
       // OkHttpClient okHttpClient = new OkHttpClient();

        // loading CAs from an InputStream
        CertificateFactory cf = null;
        try {
            cf = CertificateFactory.getInstance("X.509");
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        InputStream cert = context.getResources().openRawResource(R.raw.testing);
        Certificate ca = null;
        try {
            ca = cf.generateCertificate(cert);
        } catch (CertificateException e) {
            e.printStackTrace();
        } finally {
            try {
                cert.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // creating a KeyStore containing our trusted CAs
        String keyStoreType = KeyStore.getDefaultType();
        KeyStore keyStore = null;
        try {
            keyStore = KeyStore.getInstance(keyStoreType);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        if (keyStore != null) {
            try {
                keyStore.load(null, null);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (CertificateException e) {
                e.printStackTrace();
            }
        }
        if (ca != null) try {
            keyStore.setCertificateEntry("ca", ca);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }

        // creating a TrustManager that trusts the CAs in our KeyStore
        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = null;
        try {
            tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            tmf.init(keyStore);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }

        // creating an SSLSocketFactory that uses our TrustManager
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            sslContext.init(null, tmf.getTrustManagers(), null);
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        clientBuilder.sslSocketFactory(sslContext.getSocketFactory());
        final OkHttpClient okHttpClient =  clientBuilder.build();

        // creating a RestAdapter using the custom client
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }


}
