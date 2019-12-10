package net.mbiztech.webtoapp.network;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {


    @Headers({"Content-Type: application/json"})
    @POST("api/v1/saveImei.php")
    Call<String> sendImeiToDb(@Query("user_id") int user_id, @Query("imei") String imei);
}
