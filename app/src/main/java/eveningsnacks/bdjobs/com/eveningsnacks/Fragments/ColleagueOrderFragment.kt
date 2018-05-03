package eveningsnacks.bdjobs.com.eveningsnacks.Fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import eveningsnacks.bdjobs.com.eveningsnacks.API.User
import eveningsnacks.bdjobs.com.eveningsnacks.Communication.Communicator
import eveningsnacks.bdjobs.com.eveningsnacks.R
import kotlinx.android.synthetic.main.fragment_colleague_order.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.toast

class ColleagueOrderFragment : Fragment() {

    private var rootView: View? = null
    private var communicator: Communicator? = null
    private var selectedColleague: User? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_colleague_order, container, false)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        communicator = activity as Communicator


    }

    override fun onResume() {
        super.onResume()
        try {
            selectedColleague = null
            colleagueNameTIET.text.clear()
            orderPrBar.visibility=View.VISIBLE
            menuLL.visibility=View.GONE
            communicator?.getColleagueList()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun setColleagueList(colleagues: List<User>) {
        try {
            val userNames = arrayOfNulls<String>(colleagues.size)
            for ((i, colleague) in colleagues.withIndex()) {
                userNames[i] = colleague.uname
            }

            colleagueNameTIET.onClick {
                val builder = AlertDialog.Builder(activity)
                builder.setTitle("Please Select Colleague's Name")
                        .setItems(userNames,
                                { dialog, which ->
                                    selectedColleague = User(colleagues[which].uname, colleagues[which].gid, "1")
                                    colleagueNameTIET.setText(selectedColleague?.uname)
                                })
                val dialog = builder.create()
                dialog.show()
            }

            val menuModel = communicator?.providedMenu()!!

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

                if(selectedColleague!=null) {

                    val checkedRadioButtonId = radioGroup.checkedRadioButtonId
                    var menuItem: String? = null
                    when (checkedRadioButtonId) {
                        R.id.radioButton1 -> menuItem = menuModel?.mainMenu!!
                        R.id.radioButton2 -> menuItem = alternateMenu1
                        R.id.radioButton3 -> menuItem = alternateMenu2
                        R.id.radioButton4 -> menuItem = alternateMenu3
                    }
                    if (menuItem != null) {
                        communicator?.makeOrderForColleague(menuItem,selectedColleague?.uname!!,selectedColleague?.gid!!)
                    } else {
                        toast("Please select a menu!")
                    }
                }else{
                    toast("Please select a colleague name!")
                }

            }
            orderPrBar.visibility=View.GONE
            menuLL.visibility=View.VISIBLE
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun clearData(){
        try {
            selectedColleague = null
            colleagueNameTIET.text.clear()
            radioGroup.clearCheck()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}