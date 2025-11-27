package com.samsung.android.sfdc.Api_pkg;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.samsung.android.sfdc.BuildConfig;
import com.samsung.android.sfdc.R;
import com.samsung.android.sfdc.Retrofit_pkg.ApiInterface;
import com.samsung.android.sfdc.Retrofit_pkg.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Auth extends AppCompatActivity {
    TextView text;
    Button startAuth;
    static String AuthToken = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_layout);
        setTitle("Authentication");

        startAuth = findViewById(R.id.getAuthToken);
        text = findViewById(R.id.resultHolder);

        //Todo
        // Create Auth request object and pass it to the function
        AuthRequest request = new AuthRequest();

        request.setGrant_type("password");
        request.setClient_id(BuildConfig.Client_id);
        request.setClient_secret(BuildConfig.Client_secret);
        request.setUsername(BuildConfig.Username);
        request.setPassword(BuildConfig.Password);

        startAuth.setOnClickListener(v -> getAuthToken(request));
    }

    void getAuthToken(AuthRequest request) {
        ApiInterface apiInterface = RetrofitClient.getInstance().getJsonApi(); //get Api instance
        Call<AuthResponse> call = apiInterface.getBearerToken(request.grant_type, request.client_id, request.client_secret, request.username, request.password);
        call.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if (response.isSuccessful()) {
                    AuthResponse post = response.body();
                    AuthToken = post.getAccess_token();
                    System.out.println(AuthToken);
                    text.setText(AuthToken);
                } else {
                    // Handle error
                    System.out.println(response.errorBody());
                    text.setText(response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    public static String getAuthToken() {
        return AuthToken;
    }

}
