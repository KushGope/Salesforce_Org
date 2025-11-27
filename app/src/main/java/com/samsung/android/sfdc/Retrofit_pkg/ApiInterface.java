package com.samsung.android.sfdc.Retrofit_pkg;

import com.samsung.android.sfdc.Api_pkg.AuthResponse;
import com.samsung.android.sfdc.BuildConfig;
import com.samsung.android.sfdc.Case_pkg.Case;
import com.samsung.android.sfdc.Case_pkg.CaseResponse;
import com.samsung.android.sfdc.Comment_pkg.CommentResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {
    String BASE_URL = BuildConfig.BASE_URL;

    @POST("services/oauth2/token")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    Call<AuthResponse> getBearerToken(
            @Field("grant_type") String grant_type,
            @Field("client_id") String client_id,
            @Field("client_secret") String client_secret,
            @Field("username") String username,
            @Field("password") String password
    );

    @GET("services/data/v61.0/query")
    Call<CaseResponse> getCaseList(@Header("Authorization") String BearerToken, @Query("q") String query);

    @GET("services/data/v61.0/sobjects/Case")
    Call<Case> getCase(@Header("Authorization") String BearerToken);

    @GET("services/data/v62.0/query")
    Call<CommentResponse> getCommentList(@Header("Authorization") String BearerToken, @Query("q") String query);

}
