package eveningsnacks.bdjobs.com.eveningsnacks.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import eveningsnacks.bdjobs.com.eveningsnacks.API.OrderListModel
import eveningsnacks.bdjobs.com.eveningsnacks.Adapters.OrderListAdapter
import eveningsnacks.bdjobs.com.eveningsnacks.Communication.Communicator
import eveningsnacks.bdjobs.com.eveningsnacks.R
import kotlinx.android.synthetic.main.fragment_allorders.*


class AllOrdersFragment : Fragment() {

    private var rootView:View?=null
    private var communicator:Communicator?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_allorders, container, false)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        communicator = activity as Communicator
    }

    override fun onResume() {
        super.onResume()
        communicator?.getTotalOrderList()
    }

    fun showOrderList(orderListModel: OrderListModel){
        try {
            orderListRV.visibility=View.VISIBLE
            notFoundView.visibility=View.GONE
            val orderListAdapter = OrderListAdapter(orderListModel,activity!!)
            orderListRV.layoutManager = LinearLayoutManager(activity!!, LinearLayout.VERTICAL, false)
            orderListRV.adapter = orderListAdapter
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun showProgressbar(){
        try {
            notFoundView.visibility=View.GONE
            orderListRV.visibility=View.GONE
            orderPrBar.visibility=View.VISIBLE
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun hideProgressbar(){
        try {
            orderPrBar.visibility=View.GONE
            notFoundView.visibility=View.GONE
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun showNoOrderAnimation(){
        try {
            notFoundView.visibility=View.VISIBLE
            notFoundView.setAnimation(R.raw.not_found)
            notFoundView.playAnimation()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}