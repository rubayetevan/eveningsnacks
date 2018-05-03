package eveningsnacks.bdjobs.com.eveningsnacks.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import eveningsnacks.bdjobs.com.eveningsnacks.API.MenuModel
import eveningsnacks.bdjobs.com.eveningsnacks.Communication.Communicator
import eveningsnacks.bdjobs.com.eveningsnacks.CustomViews.CircleTransform
import eveningsnacks.bdjobs.com.eveningsnacks.R
import eveningsnacks.bdjobs.com.eveningsnacks.Session.SessionManager
import eveningsnacks.bdjobs.com.eveningsnacks.Session.SessionManagerProperties
import kotlinx.android.synthetic.main.fragment_todays_menu.*

class TodaysMenuFragment:Fragment() {

    private var rootView:View?=null
    private var communicator:Communicator?=null
    private var sessionManager:SessionManager?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_todays_menu, container, false)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        communicator = activity as Communicator

    }

    override fun onResume() {
        super.onResume()
        try {
            sessionManager = SessionManager(activity!!)
            val picUrl = sessionManager?.getUserDetails()?.get(SessionManagerProperties.KEY_PHOTO_URL.toString())!!
            val name = sessionManager?.getUserDetails()?.get(SessionManagerProperties.KEY_NAME.toString())!!
            communicator?.getTodaysMenu()
            nameTV.text=name
            Picasso.get().load(picUrl).transform(CircleTransform()).into(profilePicIMGV)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun showMenu(menuModel: MenuModel){

        try {
            mainMenuTV.text = "Main menu: ${menuModel.mainMenu}"
            alternateMenuTV.text = "Alternate menu: ${menuModel.alternateMenu}"
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}