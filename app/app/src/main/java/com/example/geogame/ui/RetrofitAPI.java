package com.example.geogame.ui;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface RetrofitAPI {
    //String BASE_URL = "http://76.10.4.218:5000";
    String BASE_URL = "http://192.168.50.22:5000";

    @POST("/login")
    Call<LoginInfo> login(@Body LoginInfo loginInfo);

    @POST("/logout")
    Call<LoginInfo> logout(@Body LoginInfo loginInfo);

    @POST("/reset_password")
    Call<LoginInfo> resetPassword(@Body LoginInfo loginInfo);

    @POST("create_account")
    Call<LoginInfo> createAccount(@Body LoginInfo loginInfo);

    @POST("/user_data")
    Call <UserData> getUserData(@Body UserData userData);

    @POST("/update_user_data")
    Call <UserData> updateUserData(@Body UserData userData);

    @POST("/leaderboard_request")
    Call <UserData> leaderboardRequest(@Body UserData userData);

    @PUT("/user_data")
    Call <UserData> putUserData(@Body UserData userData);

    @POST("/num_users")
    Call <GenericResponse> num_users(@Body GenericResponse response);

    @POST("/num_locations")
    Call <GenericResponse> num_locations(@Body GenericResponse response);

    @POST("/get_random_game")
    Call <GameInfo> getRandomGame(@Body GameInfo response);

    @POST("create_game")
    Call<GameInfo> createGame(@Body GameInfo gameInfo);
}
