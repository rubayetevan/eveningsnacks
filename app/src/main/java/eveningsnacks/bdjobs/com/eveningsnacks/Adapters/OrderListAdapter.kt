package eveningsnacks.bdjobs.com.eveningsnacks.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import eveningsnacks.bdjobs.com.eveningsnacks.API.OrderListModel
import eveningsnacks.bdjobs.com.eveningsnacks.Communication.Communicator
import eveningsnacks.bdjobs.com.eveningsnacks.R
import org.jetbrains.anko.sdk25.coroutines.onClick


class OrderListAdapter(private val orderListModel: OrderListModel, private val context: Context) : RecyclerView.Adapter<OrderListAdapter.ViewHolder>() {
    var communicator: Communicator? = null

    init {
        communicator = context as Communicator
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //To change body of created functions use File | Settings | File Templates.
        val name = orderListModel.orders[position].uname
        val menu = orderListModel.orders[position].menu
        val orderedby = orderListModel.orders[position].orderedby

        holder.nameTV.text = name
        holder.menuTV.text = menu

        holder.orderByTV.text = if (orderedby == "NULL") "Self" else orderedby

        holder.deleteBTN.onClick {
            communicator?.deleteOrder(orderListModel.orders[position].userid, OrderListAdapter::class.java.simpleName)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //To change body of created functions use File | Settings | File Templates.
        val v = LayoutInflater.from(context).inflate(R.layout.item_order_list, parent, false)
        return ViewHolder(v)

    }

    override fun getItemCount(): Int {
        //To change body of created functions use File | Settings | File Templates.
        return orderListModel.orders.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val nameTV = itemView.findViewById<TextView>(R.id.nameTV)
        val menuTV = itemView.findViewById<TextView>(R.id.menuTV)
        val deleteBTN = itemView.findViewById<Button>(R.id.deleteBTN)
        val orderByTV = itemView.findViewById<TextView>(R.id.orderByTV)


    }
}