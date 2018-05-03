package eveningsnacks.bdjobs.com.eveningsnacks.Adapters

import android.support.v4.app.FragmentManager
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter


class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
    private val mFragmentList: ArrayList<Fragment>? = ArrayList()

    override fun getItem(position: Int): Fragment {
        return mFragmentList?.get(position)!!
    }

    override fun getCount(): Int {
        return mFragmentList?.size!!
    }

    fun addFragment(fragment: Fragment) {
        mFragmentList?.add(fragment)
    }

}