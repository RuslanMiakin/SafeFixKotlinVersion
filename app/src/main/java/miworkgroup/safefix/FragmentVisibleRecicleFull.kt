package miworkgroup.safefix

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class FragmentVisibleRecicleFull : Fragment() {
    lateinit var date: String ; lateinit var SafefixDatabaseHelper: SQLiteOpenHelper
    lateinit var db: SQLiteDatabase ; lateinit var cursor: Cursor
    lateinit var CategoryArray: ArrayList<String> ; lateinit var ImageArray: ArrayList<Int>
    lateinit var Money: ArrayList<String> ; lateinit var DATA: ArrayList<String>
    lateinit var idArray: ArrayList<Int> ; lateinit var MoneyLong: ArrayList<Float>
    lateinit var spinner: Spinner ; lateinit var v: View
    lateinit var bundle: Bundle ; lateinit var select: String
    lateinit var datePickerDialog: DatePickerDialog ; lateinit var calend: Button
    lateinit var summ: TextView
    var yearn = 0 ; var dayn = 0 ; var mountn = 0
    lateinit var adapter: CaptionedImagesAdapter
    lateinit var recycler: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v= inflater.inflate(R.layout.fragment_recicler_full, container, false)
        calend = view!!.findViewById(R.id.button_calen)
        summ = view!!.findViewById(R.id.text_add_sum)
        spinner = view!!.findViewById(R.id.spinner)
        bundle = arguments
        assert(bundle != null)
        select = bundle!!.getString("cat")
        assert(select != null)
        if (select == "no") {
            VisualSort1().execute()
        } else {
            VisualSortBarChart().execute()
        }
        calend.setOnClickListener(View.OnClickListener { view ->
            if (view.id == R.id.button_calen) {
                VisualCalk()
            }
        })
        spinner.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> {
                    }
                    1 -> {
                        VisualSortSpiner().execute("DATA DESC")
                        summ()
                    }
                    2 -> {
                        VisualSortSpiner().execute("CATEGORY")
                        summ()
                    }
                    3 -> {
                        VisualSortSpiner().execute("CAST(MONEY AS INTEGER) DESC")
                        summ()
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })
        return view
    }

    @SuppressLint("SetTextI18n")
    private fun summ() {
        var ss = 0f
        for (s in MoneyLong!!) {
            ss = s + ss
        }
        summ!!.text = "" + ss
    }

    @SuppressLint("StaticFieldLeak")
    private inner class VisualSortSpiner :
        AsyncTask<String?, Void?, Boolean?>() {
        override fun doInBackground(vararg start: String?): Boolean? {
            try {
                SafefixDatabaseHelper = SafeFixDatabaseHelper(view!!.context)
                db = SafefixDatabaseHelper.getReadableDatabase()
                cursor = db.query(
                    "MONEY", arrayOf("_id", "IMAGE_RESOURCE_ID", "CATEGORY", "DATA", "MONEY"),
                    null, null,
                    null, null, start[0]
                )
                CategoryArray = ArrayList()
                ImageArray = ArrayList()
                Money = ArrayList()
                DATA = ArrayList()
                idArray = ArrayList()
                MoneyLong = ArrayList()
                while (cursor.moveToNext()) {
                    val cursorCategory = cursor.getString(cursor.getColumnIndex("CATEGORY"))
                    val cursorImage = cursor.getInt(cursor.getColumnIndex("IMAGE_RESOURCE_ID"))
                    val cursorId = cursor.getInt(cursor.getColumnIndex("_id"))
                    val cursorMoney = cursor.getString(cursor.getColumnIndex("MONEY"))
                    val cursorDATA = cursor.getString(cursor.getColumnIndex("DATA"))
                    val money = cursor.getFloat(cursor.getColumnIndex("MONEY"))
                    idArray.add(cursorId)
                    CategoryArray.add(cursorCategory)
                    ImageArray.add(cursorImage)
                    Money.add(cursorMoney)
                    DATA.add(cursorDATA)
                    MoneyLong.add(money)
                }
                cursor.close()
                db.close()
            } catch (e: SQLiteException) {
                val toast =
                    Toast.makeText(view!!.context, "Database unavailable", Toast.LENGTH_SHORT)
                toast.show()
            }
            return true
        }

        override fun onPostExecute(result: Boolean) {
            adapter.setData(Money, ImageArray, CategoryArray, DATA, idArray)
            val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            recycler!!.layoutManager = layoutManager
        }
    }

    @SuppressLint("StaticFieldLeak")
    private inner class VisualSortBarChart :
        AsyncTask<String?, Void?, Boolean>() {
        override fun doInBackground(vararg start: String?): Boolean {
            try {
                SafefixDatabaseHelper = SafeFixDatabaseHelper(view!!.context)
                db = SafefixDatabaseHelper.getReadableDatabase()
                cursor = db.query(
                    "MONEY", arrayOf("_id", "IMAGE_RESOURCE_ID", "CATEGORY", "DATA", "MONEY"),
                    "CATEGORY =?", arrayOf(select),
                    null, null, null
                )
                CategoryArray = ArrayList()
                ImageArray = ArrayList()
                Money = ArrayList()
                DATA = ArrayList()
                idArray = ArrayList()
                MoneyLong = ArrayList()
                while (cursor.moveToNext()) {
                    val cursorCategory = cursor.getString(cursor.getColumnIndex("CATEGORY"))
                    val cursorImage = cursor.getInt(cursor.getColumnIndex("IMAGE_RESOURCE_ID"))
                    val cursorMoney = cursor.getString(cursor.getColumnIndex("MONEY"))
                    val cursorDATA = cursor.getString(cursor.getColumnIndex("DATA"))
                    val cursorId = cursor.getInt(cursor.getColumnIndex("_id"))
                    val money = cursor.getFloat(cursor.getColumnIndex("MONEY"))
                    idArray.add(cursorId)
                    CategoryArray.add(cursorCategory)
                    ImageArray.add(cursorImage)
                    Money.add(cursorMoney)
                    DATA.add(cursorDATA)
                    MoneyLong.add(money)
                }
                cursor.close()
                db.close()
            } catch (e: SQLiteException) {
                val toast =
                    Toast.makeText(view!!.context, "Database unavailable", Toast.LENGTH_SHORT)
                toast.show()
            }
            return true
        }

        override fun onPostExecute(result: Boolean) {
            summ()
            visualRecicler()
        }
    }

    @SuppressLint("StaticFieldLeak")
    private inner class VisualSort1 :
        AsyncTask<String?, Void?, Boolean>() {
        override fun doInBackground(vararg start: String?): Boolean {
            try {
                SafefixDatabaseHelper = SafeFixDatabaseHelper(view!!.context)
                db = SafefixDatabaseHelper.getReadableDatabase()
                cursor = db.query(
                    "MONEY", arrayOf("_id", "IMAGE_RESOURCE_ID", "CATEGORY", "DATA", "MONEY"),
                    null, null,
                    null, null, "DATA DESC"
                )
                CategoryArray = ArrayList()
                ImageArray = ArrayList()
                Money = ArrayList()
                DATA = ArrayList()
                idArray = ArrayList()
                MoneyLong = ArrayList()
                while (cursor.moveToNext()) {
                    val cursorCategory = cursor.getString(cursor.getColumnIndex("CATEGORY"))
                    val cursorImage = cursor.getInt(cursor.getColumnIndex("IMAGE_RESOURCE_ID"))
                    val cursorMoney = cursor.getString(cursor.getColumnIndex("MONEY"))
                    val cursorDATA = cursor.getString(cursor.getColumnIndex("DATA"))
                    val cursorId = cursor.getInt(cursor.getColumnIndex("_id"))
                    val money = cursor.getFloat(cursor.getColumnIndex("MONEY"))
                    idArray.add(cursorId)
                    CategoryArray.add(cursorCategory)
                    ImageArray.add(cursorImage)
                    Money.add(cursorMoney)
                    DATA.add(cursorDATA)
                    MoneyLong.add(money)
                }
                cursor.close()
                db.close()
            } catch (e: SQLiteException) {
                val toast =
                    Toast.makeText(view!!.context, "Database unavailable", Toast.LENGTH_SHORT)
                toast.show()
            }
            return true
        }

        override fun onPostExecute(result: Boolean) {
            summ()
            visualRecicler()
        }
    }

    @SuppressLint("StaticFieldLeak")
    private inner class VisualSortCalk :
        AsyncTask<String?, Void?, Boolean>() {
        @SuppressLint("Recycle")
        override fun doInBackground(vararg start: String?): Boolean {
            date = yearn.toString() + "-" + (mountn + 1) + "-" + dayn
            Log.d(ContentValues.TAG, date)
            try {
                SafefixDatabaseHelper = SafeFixDatabaseHelper(context)
                db = SafefixDatabaseHelper.getReadableDatabase()
                cursor = db.query(
                    "MONEY", arrayOf("_id", "IMAGE_RESOURCE_ID", "CATEGORY", "DATA", "MONEY"),
                    "DATA BETWEEN ? AND ?", arrayOf(
                        "$date 00:00:00", "$date 23:59:59"
                    ), null, null, "MONEY DESC", null
                )
                CategoryArray = ArrayList()
                ImageArray = ArrayList()
                Money = ArrayList()
                DATA = ArrayList()
                idArray = ArrayList()
                MoneyLong = ArrayList()
                while (cursor.moveToNext()) {
                    val cursorCategory = cursor.getString(cursor.getColumnIndex("CATEGORY"))
                    val cursorImage = cursor.getInt(cursor.getColumnIndex("IMAGE_RESOURCE_ID"))
                    val cursorMoney = cursor.getString(cursor.getColumnIndex("MONEY"))
                    val cursorDATA = cursor.getString(cursor.getColumnIndex("DATA"))
                    val cursorId = cursor.getInt(cursor.getColumnIndex("_id"))
                    val money = cursor.getFloat(cursor.getColumnIndex("MONEY"))
                    idArray!!.add(cursorId)
                    CategoryArray!!.add(cursorCategory)
                    ImageArray!!.add(cursorImage)
                    Money!!.add(cursorMoney)
                    DATA!!.add(cursorDATA)
                    MoneyLong!!.add(money)
                }
                cursor.close()
                db.close()
            } catch (e: SQLiteException) {
            }
            return true
        }

        override fun onPostExecute(result: Boolean) {
            adapter.setData(Money, ImageArray, CategoryArray, DATA, idArray)
            val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            recycler!!.layoutManager = layoutManager
        }
    }

    fun VisualCalk() {
        val calendar = Calendar.getInstance()
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH]
        val dayOfMonth = calendar[Calendar.DAY_OF_MONTH]
        datePickerDialog = DatePickerDialog(
            view!!.context, AlertDialog.THEME_HOLO_DARK,
            { datePicker, year, month, day ->
                yearn = year
                mountn = month
                dayn = day
                VisualSortCalk().execute()
            }, year, month, dayOfMonth
        )
        datePickerDialog!!.show()
    }

    private fun visualRecicler() {
        recycler = view!!.findViewById(R.id.recycler_body)
        adapter = CaptionedImagesAdapter(
            view!!.context, Money, ImageArray,
            CategoryArray!!, DATA, idArray
        )
        recycler.adapter = adapter
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recycler.layoutManager = layoutManager
        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(adapter))
        itemTouchHelper.attachToRecyclerView(recycler)
    }
}