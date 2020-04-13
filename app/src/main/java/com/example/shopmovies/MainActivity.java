package com.example.shopmovies;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;

public class MainActivity extends AppCompatActivity implements GetUserInfo.OnTaskCompleted {
    private UserInfo userInfo = new UserInfo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        trustEveryone();

        findViewById(R.id.navigation_movies).setOnClickListener((v) -> updateFragment(new MovieFragment()));
        findViewById(R.id.navigation_profile).setOnClickListener((v) -> updateFragment(new ProfileFragment(userInfo)));
        findViewById(R.id.navigation_logout).setOnClickListener((v) -> {
            LoginManager.getInstance().logOut();
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .requestProfile()
                    .build();
            GoogleSignIn.getClient(this, gso).signOut();
            startActivity(new Intent(this, LoginActivity.class));
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isFBLoggedIn = (accessToken != null && !accessToken.isExpired()) ;
        if (isFBLoggedIn) {
            new GetUserInfo(this).execute(accessToken.getToken());
            updateFragment(new MovieFragment());
            return;
        }

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            this.userInfo.setEmail(account.getEmail());
            this.userInfo.setName(account.getDisplayName());
            this.userInfo.setImage(account.getPhotoUrl().toString());
            this.userInfo.setBirthday("N/A");
            updateFragment(new MovieFragment());
            return;
        }

        LoginManager.getInstance().logOut();
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void updateFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onGetUserCompleted(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    private void trustEveryone() {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[]{new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType) {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }}, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(
                    context.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
