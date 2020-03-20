package com.ioss.covid.utilities.retrofit;

import com.ioss.covid.model.ViewMembersModel.ViewUserModel;
import com.ioss.covid.model.chcModel.CHCModel;
import com.ioss.covid.model.districtModel.DistrictModel;
import com.ioss.covid.model.loginModel.LoginModel;
import com.ioss.covid.model.panchayathModel.PanchayathModel;
import com.ioss.covid.model.registerModel.RegisterModel;
import com.ioss.covid.model.searchMemberModel.SearchUserModel;
import com.ioss.covid.model.stateModel.StateModel;
import com.ioss.covid.model.userDetailsModel.UserDetailsModel;
import com.ioss.covid.model.vulnerabilityModel.VulnerabilityModel;
import com.ioss.covid.utilities.Constants;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface ApiRequest {

    @POST("auth/main_access")
    @Headers("api-key:"+ Constants.STATIC_KEY)
    @FormUrlEncoded
    Call<LoginModel> loginRRT(@FieldMap HashMap<String, String> params);

    @POST("auth/user_access")
    @Headers("api-key:"+ Constants.STATIC_KEY)
    @FormUrlEncoded
    Call<LoginModel> loginUser(@FieldMap HashMap<String, String> params);

    @POST("register/states")
    @Headers("api-key:"+ Constants.STATIC_KEY)
    @FormUrlEncoded
    Call<StateModel> getState(@FieldMap HashMap<String, String> params);

    @POST("register/districts")
    @Headers("api-key:"+ Constants.STATIC_KEY)
    @FormUrlEncoded
    Call<DistrictModel> getDistrict(@FieldMap HashMap<String, String> params);

    @POST("register/panchayaths")
    @Headers("api-key:"+ Constants.STATIC_KEY)
    @FormUrlEncoded
    Call<PanchayathModel> getPanchayaths(@FieldMap HashMap<String, String> params);

    @POST("register/chcs")
    @Headers("api-key:"+ Constants.STATIC_KEY)
    @FormUrlEncoded
    Call<CHCModel> getCHC(@FieldMap HashMap<String, String> params);

    @POST("register/register")
    @Headers("api-key:"+ Constants.STATIC_KEY)
    @FormUrlEncoded
    Call<RegisterModel> registrationUser(@FieldMap HashMap<String, String> params);

    @POST("register/register")
    @Headers("api-key:"+ Constants.STATIC_KEY)
    @FormUrlEncoded
    Call<RegisterModel> registrationRRT(@Header("access-token") String token,@FieldMap HashMap<String, String> params);

    @GET("home/user_detail_report")
    @Headers("api-key:"+ Constants.STATIC_KEY)
    Call<ViewUserModel> viewMembers(@Header("access-token") String token, @QueryMap HashMap<String, String> params);

    @GET("home/search_user")
    @Headers("api-key:"+ Constants.STATIC_KEY)
    Call<SearchUserModel> search_user(@Header("access-token") String token, @QueryMap HashMap<String, String> params);


    @GET("home/get_user_details")
    @Headers("api-key:"+ Constants.STATIC_KEY)
    Call<UserDetailsModel> get_user_details(@Header("access-token") String token, @QueryMap HashMap<String, String> params);

    @POST("home/update_user_symptoms")
    @Headers("api-key:"+ Constants.STATIC_KEY)
    @FormUrlEncoded
    Call<UserDetailsModel> update_user_symptoms(@Header("access-token") String token,@FieldMap HashMap<String, String> params);

    @POST("register/add_user")
    @Headers("api-key:"+ Constants.STATIC_KEY)
    @FormUrlEncoded
    Call<SearchUserModel> add_user(@Header("access-token") String token,@FieldMap HashMap<String, String> params);

    @GET("register/vulnerabilities")
    @Headers("api-key:"+ Constants.STATIC_KEY)
    Call<VulnerabilityModel> vulnerabilities();

 /*   @POST("Token?xn_pretty=true")
    @FormUrlEncoded
    Call<SplashModel> test(@Header("Authorization") String token, @FieldMap HashMap<String, String> params);


    @GET("Login/app_info?user_type=customer")
    @Headers("Auth:"+ Constants.STATIC_KEY)
    Call<SplashModel> appInfo();

    @POST("Login/check_mobile")
    @Headers("Auth:"+ Constants.STATIC_KEY)
    @FormUrlEncoded
    Call<LoginModel> sendOtp(@FieldMap HashMap<String, String> params);

    @POST("Login/verify_otp")
    @Headers("Auth:"+ Constants.STATIC_KEY)
    @FormUrlEncoded
    Call<OtpModel> verifyOtp(@FieldMap HashMap<String, String> params);

    @Multipart
    @POST("Register/customer_register")
    @Headers("Auth:"+ Constants.STATIC_KEY)
    Call<RegisterModel> register(@Part MultipartBody.Part file, @PartMap() HashMap<String, RequestBody> params);

    @Multipart
    @POST("Register/customer_register")
    @Headers("Auth:"+ Constants.STATIC_KEY)
    Call<RegisterModel> register(@PartMap() HashMap<String, RequestBody> params);

    @POST("Customer/update_fcm")
    @FormUrlEncoded
    Call<JsonObject> updateFcm(@Header("Auth") String token, @FieldMap HashMap<String, String> params);

    @GET("Customer/product_list")
    Call<ProductListModel> getProductList(@Header("Auth") String token, @QueryMap HashMap<String, String> params);

    @GET("/maps/api/geocode/json?sensor=false")
    Call<JsonObject> getAddress(@Query("latlng") String latlng, @Query("key") String key);

    @GET("/maps/api/geocode/json")
    Call<JsonObject> getLatLng(@Query("place_id") String placeId, @Query("key") String key);

    @GET("Customer/get_customerdetails")
    Call<ProfileModel> getProfileDetails(@Header("Auth") String token, @QueryMap HashMap<String, String> params);

    @Multipart
    @POST("Customer/update_customer")
    Call<ProfileUpdateModel> updateProfile(@Header("Auth") String token, @Part MultipartBody.Part file, @PartMap() HashMap<String, RequestBody> params);

    @Multipart
    @POST("Customer/update_customer")
    Call<ProfileUpdateModel> updateProfile(@Header("Auth") String token, @PartMap() HashMap<String, RequestBody> params);

    @GET("Customer/emirates_list")
    Call<EmiratesListModel> getEmiratesList(@Header("Auth") String token, @QueryMap HashMap<String, String> params);

    @POST("Customer/insert_order_request")
    @FormUrlEncoded
    Call<RequestModel> requestProduct(@Header("Auth") String token, @FieldMap HashMap<String, String> params);

    @POST("Customer/call_vendors")
    @FormUrlEncoded
    Call<CallRenderModel> callVendors(@Header("Auth") String token, @FieldMap HashMap<String, String> params);

    @GET("Customer/pending_requests")
    Call<PendingReqModel> getPendingRequests(@Header("Auth") String token, @QueryMap HashMap<String, String> params);

    @GET("Customer/active_requests")
    Call<ActiveReqestModel> getActiveRequests(@Header("Auth") String token, @QueryMap HashMap<String, String> params);

    @GET("Customer/order_history")
    Call<OrderHistoryModel> getOrderHistory(@Header("Auth") String token, @QueryMap HashMap<String, String> params);

    @GET("Customer/track_order")
    Call<TrackOrderModel> trackOrder(@Header("Auth") String token, @QueryMap HashMap<String, String> params);

    @POST("Customer/confirm_order")
    @FormUrlEncoded
    Call<SimpleRespModel> confirmOrder(@Header("Auth") String token, @FieldMap HashMap<String, String> params);

    @POST("Customer/cancel_order")
    @FormUrlEncoded
    Call<SimpleRespModel> cancelOrder(@Header("Auth") String token, @FieldMap HashMap<String, String> params);

    @POST("Customer/rate_order")
    @FormUrlEncoded
    Call<SimpleRespModel> rateOrder(@Header("Auth") String token, @FieldMap HashMap<String, String> params);

    @GET("Customer/last_addresses")
    Call<RecentLocModel> getRecentLocations(@Header("Auth") String token, @QueryMap HashMap<String, String> params);

    @GET("Customer/nearest_vendors")
    Call<NearestModel> nearestVendors(@Header("Auth") String token, @QueryMap HashMap<String, String> params);

    @GET("Customer/cancelled_orders")
    Call<CancelledReqModel> getCancelledOrders(@Header("Auth") String token, @QueryMap HashMap<String, String> params);

    @GET("Customer/transaction_history")
    Call<TransactionHistoryModel> getTransactionHistory(@Header("Auth") String token, @QueryMap HashMap<String, String> params);

    @POST("Customer/change_mobile_otp_send")
    @FormUrlEncoded
    Call<LoginModel> changeMobileOtpSend(@Header("Auth") String token, @FieldMap HashMap<String, String> params);

    @POST("Customer/change_mobile_otp_verify")
    @FormUrlEncoded
    Call<OtpModel> changeMobileOtpVerify(@Header("Auth") String token, @FieldMap HashMap<String, String> params);*/
}
