package com.example.geogame.ui;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;
import retrofit2.Call;
import retrofit2.Response;

public class RetrofitAPITests {
    @Test
    public void testLogin() {
        LoginInfo loginInfo = new LoginInfo("testuser", "password", "");
        Call<LoginInfo> call = RetrofitClient.getInstance().getMyApi().login(loginInfo);
        try {
            Response<LoginInfo> response = call.execute();
            LoginInfo responseInfo = response.body();
            assert responseInfo != null;
            System.out.println("SID: " + responseInfo.getSID());
            assertNotEquals("No SID received from backend! The password may be incorrect.",responseInfo.getSID(), "");
        } catch (Exception ex) {
            ex.printStackTrace();
            fail();
        }
    }

    @Test
    public void testUserData() {
        String SID = "";
        LoginInfo loginInfo = new LoginInfo("testuser", "password", "");
        Call<LoginInfo> call = RetrofitClient.getInstance().getMyApi().login(loginInfo);
        try {
            Response<LoginInfo> response = call.execute();
            LoginInfo responseInfo = response.body();
            assert responseInfo != null;
            System.out.println("SID: " + responseInfo.getSID());
            SID = responseInfo.getSID();
            assertNotEquals("No SID received from backend! The password may be incorrect.",responseInfo.getSID(), "");
        } catch (Exception ex) {
            ex.printStackTrace();
            fail();
        }

        UserData userData = new UserData("testuser", SID, "","","","");
        Call<UserData> call2 = RetrofitClient.getInstance().getMyApi().getUserData(userData);
        try {
            Response<UserData> response = call2.execute();
            UserData responseInfo = response.body();
            assert responseInfo != null;
            assertEquals("Ranking response does not match expected data from the database", responseInfo.rank(), "1001");
            assertEquals("Games played response does not match expected data from the database", responseInfo.gamesPlayed(), "0");
            assertEquals("Average score response does not match expected data from the database", responseInfo.averageScore(), "0");
            assertEquals("Accuracy response does not match expected data from the database", responseInfo.accuracy(), "0");

        } catch (Exception ex) {
            ex.printStackTrace();
            fail();
        }
    }

    @Test
    public void testUserDataWrong() {
        String SID = "";
        LoginInfo loginInfo = new LoginInfo("testuser", "password", "");
        Call<LoginInfo> call = RetrofitClient.getInstance().getMyApi().login(loginInfo);
        try {
            Response<LoginInfo> response = call.execute();
            LoginInfo responseInfo = response.body();
            assert responseInfo != null;
            System.out.println("SID: " + responseInfo.getSID());
            SID = responseInfo.getSID();
            assertNotEquals("No SID received from backend! The password may be incorrect.",responseInfo.getSID(), "");
        } catch (Exception ex) {
            ex.printStackTrace();
            fail();
        }

        UserData userData = new UserData("testuser", SID, "","","","");
        Call<UserData> call2 = RetrofitClient.getInstance().getMyApi().getUserData(userData);
        try {
            Response<UserData> response = call2.execute();
            UserData responseInfo = response.body();
            assert responseInfo != null;
            assertNotEquals("Impossible ranking response matches unexpected data from the database", responseInfo.rank(), "10000000");
            assertNotEquals("Impossible games played response matches unexpected data from the database", responseInfo.gamesPlayed(), "10000000");
            assertNotEquals("Impossible average score response matches unexpected data from the database", responseInfo.averageScore(), "10000000");
            assertNotEquals("Impossible accuracy response matches unexpected data from the database", responseInfo.accuracy(), "10000000");

        } catch (Exception ex) {
            ex.printStackTrace();
            fail();
        }
    }

    @Test
    public void testLeaderboard() {
        GenericResponse result = new GenericResponse("");
        Call<GenericResponse> call = RetrofitClient.getInstance().getMyApi().num_users(result);
        int leaderboardSize = 100;

        try
        {
            Response<GenericResponse> response = call.execute();
            GenericResponse responseInfo = response.body();
            assert responseInfo != null;
            if(Integer.parseInt(responseInfo.result()) < 100){
                leaderboardSize = Integer.parseInt(responseInfo.result());
            };
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            fail();
        }

        for (int i = 1; i <= leaderboardSize; i++){
            UserData userData = new UserData("", "", String.valueOf(i),"","","");
            Call<UserData> call2 = RetrofitClient.getInstance().getMyApi().leaderboardRequest(userData);
            try
            {
                Response<UserData> response = call2.execute();
                UserData responseInfo = response.body();
                assert responseInfo != null;

                System.out.println(responseInfo.name());

                LeaderboardData.addItem(responseInfo.name());
                LeaderboardData.addItem(String.valueOf(i));
                LeaderboardData.addItem(responseInfo.gamesPlayed());
                LeaderboardData.addItem(responseInfo.averageScore());
                LeaderboardData.addItem(responseInfo.accuracy());
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                fail();
            }
        }

        for(String obj : LeaderboardData.getDataArray()) {
            assertNotNull("A null object was found in the leaderboard array!", obj);
        }
    }


}