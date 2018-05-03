package eveningsnacks.bdjobs.com.eveningsnacks.Communication

import eveningsnacks.bdjobs.com.eveningsnacks.API.MenuModel

interface Communicator {

    fun makeOrder(menu:String)
    fun makeOrderForColleague(menu:String,colleagueName: String,colleagueUserID:String)
    fun deleteOrder(userID:String,pageName:String)
    fun getOrderSummary()
    fun getTotalOrderList()
    fun getTodaysMenu()
    fun getColleagueList()
    fun providedMenu():MenuModel
    fun getMyOrder()

}