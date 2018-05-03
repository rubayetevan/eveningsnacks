package eveningsnacks.bdjobs.com.eveningsnacks.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import eveningsnacks.bdjobs.com.eveningsnacks.API.Order
import eveningsnacks.bdjobs.com.eveningsnacks.Communication.Communicator
import eveningsnacks.bdjobs.com.eveningsnacks.R
import kotlinx.android.synthetic.main.fragment_order_summary.*
import java.util.*
import kotlin.collections.ArrayList

class OrderSummaryFragment : Fragment() {

    var rootView: View? = null
    var communicator: Communicator? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_order_summary, container, false)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        communicator = activity as Communicator

    }

    override fun onResume() {
        super.onResume()
        communicator?.getOrderSummary()
    }

    fun showSummary(orders: List<Order>) {
        try {
            menuLL.visibility = View.VISIBLE
            notFoundView.visibility = View.GONE
            val menus: ArrayList<String> = ArrayList()

            for (order in orders!!) {
                menus.add(order.menu)
            }
            val printed = HashSet<String>()
            for (s in menus) {
                if (printed.add(s)) {
                    when {
                        printed.size == 1 -> item1TV.text = s + ": " + Collections.frequency(menus, s)
                        printed.size == 2 -> item2TV.text = s + ": " + Collections.frequency(menus, s)
                        printed.size == 3 -> item3TV.text = s + ": " + Collections.frequency(menus, s)
                        printed.size == 4 -> item4TV.text = s + ": " + Collections.frequency(menus, s)
                    }
                }
            }

            totalOrderTV.text="Total order: ${orders.size}"
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun showNoOrderAnimation() {
        try {
            menuLL.visibility = View.GONE
            notFoundView.visibility = View.VISIBLE
            notFoundView.setAnimation(R.raw.not_found)
            notFoundView.playAnimation()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}