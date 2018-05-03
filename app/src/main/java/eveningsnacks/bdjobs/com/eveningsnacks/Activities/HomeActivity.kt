package eveningsnacks.bdjobs.com.eveningsnacks.Activities

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import kotlinx.android.synthetic.main.activity_home.*
import android.support.v4.view.ViewPager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import eveningsnacks.bdjobs.com.eveningsnacks.API.*
import eveningsnacks.bdjobs.com.eveningsnacks.Adapters.OrderListAdapter
import eveningsnacks.bdjobs.com.eveningsnacks.R
import eveningsnacks.bdjobs.com.eveningsnacks.Session.SessionManager
import eveningsnacks.bdjobs.com.eveningsnacks.Adapters.ViewPagerAdapter
import eveningsnacks.bdjobs.com.eveningsnacks.Communication.Communicator
import eveningsnacks.bdjobs.com.eveningsnacks.Fragments.*
import eveningsnacks.bdjobs.com.eveningsnacks.Session.SessionManagerProperties
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.google.firebase.analytics.FirebaseAnalytics
import org.jetbrains.anko.longToast


class HomeActivity : AppCompatActivity(), Communicator {


    private val TAG = HomeActivity::class.java.simpleName

    private var prevMenuItem: MenuItem? = null
    private var mGoogleApiClient: GoogleApiClient? = null
    private var mGoogleSignInClient: GoogleSignInClient? = null
    private var sessionManager: SessionManager? = null

    private val todaysMenuFragment = TodaysMenuFragment()
    private val myOrderFragment = MyOrderFragment()
    private val allOrdersFragment = AllOrdersFragment()
    private val orderSummaryFragment = OrderSummaryFragment()
    private val colleagueOrderFragment = ColleagueOrderFragment()

    private var menuModel: MenuModel? = null

    var userID: String? = null
    var userName: String? = null

    private var mFirebaseAnalytics: FirebaseAnalytics? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initialization()
        onClickListeners()
        loadUserDependency()
    }

    private fun loadUserDependency() {
        when {
            sessionManager?.getUserDetails()?.get(SessionManagerProperties.KEY_USER_ID.toString()) == "QhoTkvnq2eTdPiXwXZy4dCmLw5l1" -> {

            }
            sessionManager?.getUserDetails()?.get(SessionManagerProperties.KEY_USER_ID.toString()) == "56mbpmXT0aOsn5JaY3XD7i5NSY62" -> {

            }
            sessionManager?.getUserDetails()?.get(SessionManagerProperties.KEY_USER_ID.toString()) == "OBOCangppAOW7F82vzaE3lcxRSk1" -> {

            }
            sessionManager?.getUserDetails()?.get(SessionManagerProperties.KEY_USER_ID.toString()) == "w1rNJRaA4MdJ0X2Sz6CUj48zMKT2" -> {

            }
            sessionManager?.getUserDetails()?.get(SessionManagerProperties.KEY_USER_ID.toString()) == "uqVVrStVaJc67YLlE8h5MnmlDY53" -> {

            }

            else -> {
                bottomNavigation.menu.removeItem(R.id.allOrders)
                bottomNavigation.menu.removeItem(R.id.orderSummary)
            }
        }
    }

    private fun onClickListeners() {
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.getItemId()) {
                R.id.todaysMenu -> myViewPager.currentItem = 0
                R.id.myOrder -> myViewPager.currentItem = 1
                R.id.clgOrder -> myViewPager.currentItem = 2
                R.id.allOrders -> myViewPager.currentItem = 3
                R.id.orderSummary -> myViewPager.currentItem = 4
            }
            false
        };

        myViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                if (prevMenuItem != null) {
                    prevMenuItem!!.isChecked = false
                } else {
                    bottomNavigation.menu.getItem(0).isChecked = false
                }
                Log.d(TAG, "onPageSelected: $position")
                bottomNavigation.menu.getItem(position).isChecked = true
                prevMenuItem = bottomNavigation.menu.getItem(position)

            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }

    private fun initialization() {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        sessionManager = SessionManager(applicationContext)
        userID = sessionManager?.getUserDetails()?.get(SessionManagerProperties.KEY_USER_ID.toString())!!
        userName = sessionManager?.getUserDetails()?.get(SessionManagerProperties.KEY_NAME.toString())!!

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        mGoogleApiClient = GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        disableShiftMode(bottomNavigation)

        setupViewPager(myViewPager)

    }


    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(todaysMenuFragment) //0
        adapter.addFragment(myOrderFragment)  //1
        adapter.addFragment(colleagueOrderFragment)//2
        adapter.addFragment(allOrdersFragment) //3
        adapter.addFragment(orderSummaryFragment) //4
        viewPager.adapter = adapter
        viewPager.setPageTransformer(false, FadePageTransformer())


    }

    private class FadePageTransformer : ViewPager.PageTransformer {
        override fun transformPage(view: View, position: Float) {
            if (position <= -1.0F || position >= 1.0F) {
                view.translationX = view.width * position
                view.alpha = 0.0F
            } else if (position == 0.0F) {
                view.translationX = view.width * position
                view.alpha = 1.0F
            } else {
                // position is between -1.0F & 0.0F OR 0.0F & 1.0F
                view.translationX = view.width * -position
                view.alpha = 1.0F - Math.abs(position)
            }
        }
    }

    @SuppressLint("RestrictedApi")
    private fun disableShiftMode(view: BottomNavigationView) {
        val menuView = view.getChildAt(0) as BottomNavigationMenuView
        try {
            val shiftingMode = menuView.javaClass.getDeclaredField("mShiftingMode")
            shiftingMode.isAccessible = true
            shiftingMode.setBoolean(menuView, false)
            shiftingMode.isAccessible = false
            for (i in 0 until menuView.childCount) {
                val item = menuView.getChildAt(i) as BottomNavigationItemView
                item.setShiftingMode(false)
                // set once again checked value, so view will be updated
                item.setChecked(item.itemData.isChecked)
            }
        } catch (e: NoSuchFieldException) {
            Log.e(TAG, "Unable to get shift mode field", e)
        } catch (e: IllegalAccessException) {
            Log.e(TAG, "Unable to change value of shift mode", e)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.signOut) {
            signOut()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun signOut() {
        mGoogleSignInClient?.signOut()
                ?.addOnCompleteListener(this, OnCompleteListener<Void> {
                    FirebaseAuth.getInstance().signOut()
                    sessionManager?.logoutUser()
                    startActivity(intentFor<LoginActivity>())
                    finish()
                })
    }

    override fun makeOrder(menu: String) {
        ApiService.create().makeOrder(userID!!, userName!!, menu, userID!!).enqueue(object : Callback<OrderModel> {
            override fun onFailure(call: Call<OrderModel>?, t: Throwable?) {
                Log.d(TAG, t.toString())
            }

            override fun onResponse(call: Call<OrderModel>?, response: Response<OrderModel>?) {
                try {
                    if (response?.body()?.messageType == "1") {
                        toast("Order placed successfully!")
                        val params = Bundle()
                        params.putString("menu", menu);
                        params.putString("user", userName);
                        mFirebaseAnalytics?.logEvent("ordered_items", params);
                        getMyOrder()
                    } else {
                        longToast("You can not place order today! Please contact with Polin vai for further assistance.")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

        })
    }

    override fun makeOrderForColleague(menu: String, colleagueName: String, colleagueUserID: String) {
        ApiService.create().makeOrder(colleagueUserID!!, colleagueName!!, menu, userID!!).enqueue(object : Callback<OrderModel> {
            override fun onFailure(call: Call<OrderModel>?, t: Throwable?) {
                Log.d(TAG, t.toString())
            }

            override fun onResponse(call: Call<OrderModel>?, response: Response<OrderModel>?) {
                try {
                    if (response?.body()?.messageType == "1") {
                        toast("Order placed successfully!")
                        val params = Bundle()
                        params.putString("menu", menu)
                        params.putString("user", colleagueName);
                        mFirebaseAnalytics?.logEvent("ordered_items", params);
                        colleagueOrderFragment.clearData()
                    } else {
                        longToast("You can not place order today! Please contact with Polin vai for further assistance.")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

        })
    }

    override fun deleteOrder(userID: String, pageName: String) {
        ApiService.create().deleteOrder(userID).enqueue(object : Callback<OrderModel> {
            override fun onFailure(call: Call<OrderModel>?, t: Throwable?) {
                Log.d(TAG, t.toString())
            }

            override fun onResponse(call: Call<OrderModel>?, response: Response<OrderModel>?) {
                try {
                    if (response?.body()?.messageType == "1") {
                        toast("Order has been deleted!")
                        if (pageName == MyOrderFragment::class.java.simpleName) {
                            getMyOrder()
                        } else if (pageName == OrderListAdapter::class.java.simpleName) {
                            getTotalOrderList()
                        }
                    } else {
                        longToast("You can not delete order today! Please contact with Polin vai for further assistance.")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

        })

    }

    override fun getOrderSummary() {
        ApiService.create().getOrderList().enqueue(object : Callback<OrderListModel> {
            override fun onFailure(call: Call<OrderListModel>?, t: Throwable?) {
                Log.d(TAG, t.toString())
            }

            override fun onResponse(call: Call<OrderListModel>?, response: Response<OrderListModel>?) {
                try {
                    Log.d(TAG, response?.body()?.orders?.size?.toString()!!)
                    if (response?.body()?.orders!![0].messageType == "1") {
                        orderSummaryFragment.showSummary(response?.body()?.orders!!)
                    } else {
                        // toast("No orders found!")
                        orderSummaryFragment.showNoOrderAnimation()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        })
    }

    override fun getTotalOrderList() {
        allOrdersFragment.showProgressbar()
        ApiService.create().getOrderList().enqueue(object : Callback<OrderListModel> {
            override fun onFailure(call: Call<OrderListModel>?, t: Throwable?) {
                Log.d(TAG, t.toString())
                allOrdersFragment.hideProgressbar()
            }

            override fun onResponse(call: Call<OrderListModel>?, response: Response<OrderListModel>?) {
                try {
                    Log.d(TAG, response?.body()?.orders?.size?.toString()!!)
                    allOrdersFragment.hideProgressbar()

                    if (response?.body()?.orders!![0].messageType == "1") {
                        allOrdersFragment.showOrderList(response?.body()!!)
                    } else {
                        // toast("No orders found!")
                        allOrdersFragment.showNoOrderAnimation()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        })
    }

    override fun getColleagueList() {
        ApiService.create().getUserList().enqueue(object : Callback<UserListModel> {
            override fun onFailure(call: Call<UserListModel>?, t: Throwable?) {
                Log.d(TAG, t.toString())
            }

            override fun onResponse(call: Call<UserListModel>?, response: Response<UserListModel>?) {
                try {
                    colleagueOrderFragment.setColleagueList(response?.body()?.users!!)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        })

    }

    override fun getTodaysMenu() {
        ApiService.create().getMenu().enqueue(object : Callback<MenuModel> {
            override fun onFailure(call: Call<MenuModel>?, t: Throwable?) {
                Log.d(TAG, t.toString())
            }

            override fun onResponse(call: Call<MenuModel>?, response: Response<MenuModel>?) {
                try {
                    menuModel = response?.body()!!
                    todaysMenuFragment.showMenu(menuModel!!)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        })


    }


    override fun providedMenu(): MenuModel {
        return menuModel!!
    }

    override fun getMyOrder() {

        Log.d(TAG, userID)
        ApiService.create().getMyOrder(userID!!).enqueue(object : Callback<MyOrderModel> {
            override fun onFailure(call: Call<MyOrderModel>?, t: Throwable?) {
                Log.d(TAG, t.toString())
            }

            override fun onResponse(call: Call<MyOrderModel>?, response: Response<MyOrderModel>?) {
                try {
                    myOrderFragment.showOrderLayout(response?.body()!!)
                    Log.d(TAG, response?.body()?.messageType!!)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        })

    }


}
