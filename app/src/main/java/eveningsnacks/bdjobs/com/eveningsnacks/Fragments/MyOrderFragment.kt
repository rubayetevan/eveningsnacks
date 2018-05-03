package eveningsnacks.bdjobs.com.eveningsnacks.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import eveningsnacks.bdjobs.com.eveningsnacks.API.MenuModel
import eveningsnacks.bdjobs.com.eveningsnacks.API.MyOrderModel
import eveningsnacks.bdjobs.com.eveningsnacks.Communication.Communicator
import eveningsnacks.bdjobs.com.eveningsnacks.R
import eveningsnacks.bdjobs.com.eveningsnacks.Session.SessionManager
import eveningsnacks.bdjobs.com.eveningsnacks.Session.SessionManagerProperties
import kotlinx.android.synthetic.main.fragment_myorder.*
import org.jetbrains.anko.noButton
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.yesButton

class MyOrderFragment : Fragment() {

    private var rootView: View? = null
    private var communicator: Communicator? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_myorder, container, false)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        communicator = activity as Communicator
    }

    override fun onResume() {
        super.onResume()
        try {
            orderPrBar.visibility = View.VISIBLE
            menuLL.visibility = View.GONE
            communicator?.getMyOrder()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun showOrderLayout(myOrderModel: MyOrderModel) {
        try {
            orderPrBar.visibility = View.GONE
            menuLL.visibility = View.VISIBLE
            if (myOrderModel.messageType == "0") {
                val menuModel = communicator?.providedMenu()!!
                makeOrderLL.visibility = View.VISIBLE
                myOrderLL.visibility = View.GONE

                val alternateMenus = menuModel?.alternateMenu?.split("/".toRegex())?.dropLastWhile({ it.isEmpty() })?.toTypedArray()
                val alternateMenu1 = alternateMenus!![0].trim({ it <= ' ' })
                val alternateMenu2 = alternateMenus!![1].trim({ it <= ' ' })
                val alternateMenu3 = alternateMenus!![2].trim({ it <= ' ' })
                radioGroup.clearCheck()
                radioButton1.text = menuModel?.mainMenu!!
                radioButton2.text = alternateMenu1
                radioButton3.text = alternateMenu2
                radioButton4.text = alternateMenu3

                orderBTN.onClick {
                    val checkedRadioButtonId = radioGroup.checkedRadioButtonId
                    var menuItem: String? = null
                    when (checkedRadioButtonId) {
                        R.id.radioButton1 -> menuItem = menuModel?.mainMenu!!
                        R.id.radioButton2 -> menuItem = alternateMenu1
                        R.id.radioButton3 -> menuItem = alternateMenu2
                        R.id.radioButton4 -> menuItem = alternateMenu3
                    }
                    if (menuItem != null) {
                        communicator?.makeOrder(menuItem)
                    } else {
                        toast("Please select a menu!")
                    }

                }


            } else if (myOrderModel.messageType == "1") {
                makeOrderLL.visibility = View.GONE
                myOrderLL.visibility = View.VISIBLE
                myOrderTV.text = "My today's order is  ${myOrderModel.menu}"
                val sessionManager = SessionManager(activity!!)
                val userId = sessionManager?.getUserDetails()?.get(SessionManagerProperties.KEY_USER_ID.toString())!!
                deleteOrderBTN.onClick {
                    alert( "Are you sure to delete your order?") {
                        yesButton { communicator?.deleteOrder(userId, MyOrderFragment::class.java.simpleName) }
                        noButton {}
                    }.show()

                }

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}