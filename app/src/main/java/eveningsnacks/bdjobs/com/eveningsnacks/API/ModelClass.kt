package eveningsnacks.bdjobs.com.eveningsnacks.API
import com.google.gson.annotations.SerializedName



data class WeekNoModel(
		@SerializedName("messageType") val messageType: String,
		@SerializedName("weekno") val weekno: String
)


data class OrderListModel(
		@SerializedName("orders") val orders: List<Order>
)

data class Order(
		@SerializedName("uname") val uname: String,
		@SerializedName("menu") val menu: String,
		@SerializedName("userid") val userid: String,
		@SerializedName("messageType") val messageType: String,
		@SerializedName("orderedby") val orderedby: String
)
data class UserListModel(
		@SerializedName("users") val users: List<User>
)

data class User(
		@SerializedName("uname") val uname: String,
		@SerializedName("gid") val gid: String,
		@SerializedName("messageType") val messageType: String
)


data class MenuModel(
		@SerializedName("messageType") val messageType: String,
		@SerializedName("mainMenu") val mainMenu: String,
		@SerializedName("AlternateMenu") val alternateMenu: String
)


data class MyOrderModel(
		@SerializedName("messageType") val messageType: String,
		@SerializedName("menu") val menu: String
)


data class OrderModel(
		@SerializedName("messageType") val messageType: String
)