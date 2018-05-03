package eveningsnacks.bdjobs.com.eveningsnacks.API

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("getuserlist.asp")
    fun getUserList(): Call<UserListModel>

    @GET("viewsnacks.asp")
    fun getOrderList(): Call<OrderListModel>

    @GET("getweekno.asp")
    fun getWeekNo(): Call<WeekNoModel>

    @GET("getmenu.asp")
    fun getMenu(): Call<MenuModel>

    @GET("viewtodayorder.asp")
    fun getMyOrder(@Query("userid") userId: String): Call<MyOrderModel>

    @GET("ordersnacks.asp")
    fun makeOrder(@Query("userid") userId: String, @Query("uname") userName: String, @Query("menu") menu: String, @Query("corder") orderByUserId: String): Call<OrderModel>

    @GET("deleteorder.asp")
    fun deleteOrder(@Query("userid") userId: String): Call<OrderModel>

    companion object Factory {
        private const val baseUrl = "http://172.16.9.250/testprojects/snacks/"
        fun create(): ApiService {
            val retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}