package eveningsnacks.bdjobs.com.eveningsnacks.API

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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

    @GET("getmenu.asp")
    fun getMenu(): Call<MenuModel>

    @GET("viewtodayorder.asp")
    fun getMyOrder(@Query("userid") userId: String): Call<MyOrderModel>

    @GET("ordersnacks.asp")
    fun makeOrder(@Query("userid") userId: String, @Query("uname") userName: String, @Query("menu") menu: String, @Query("corder") orderByUserId: String): Call<OrderModel>

    @GET("deleteorder.asp")
    fun deleteOrder(@Query("userid") userId: String): Call<OrderModel>

    companion object Factory {

        private const val baseUrl = "http://172.16.9.235/snacks/"

        @Volatile
        private var retrofit: Retrofit? = null

        @Synchronized
        fun create(): ApiService {

            retrofit ?: synchronized(this) {
                retrofit = buildRetrofit()
            }

            return retrofit?.create(ApiService::class.java)!!
        }

        private fun buildRetrofit(): Retrofit {

            val gson = GsonBuilder()
                    .setLenient()
                    .create()

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val okHttpClient = OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()

            return Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
        }

    }
}