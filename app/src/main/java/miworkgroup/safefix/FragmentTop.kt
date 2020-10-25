package miworkgroup.safefix

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate
import java.text.SimpleDateFormat
import java.util.*


class FragmentTop : Fragment(), View.OnClickListener {
    private lateinit var button0: Button ; private lateinit var button00: Button
    private lateinit var button1: Button ; private lateinit var button2: Button
    private lateinit var button3: Button ; private lateinit var button4: Button
    private lateinit var button5: Button ; lateinit var button6: Button
    private lateinit var button7: Button ; private lateinit var button8: Button
    private lateinit var button9: Button ; private lateinit var button_ok: Button
    private lateinit var button_delite: Button ; private lateinit var textView454: TextView
    private lateinit var getedit: String ; private lateinit  var chart: BarChart
    private lateinit var SafefixDatabaseHelper: SQLiteOpenHelper ; private lateinit var db: SQLiteDatabase
    private lateinit var entries: ArrayList<BarEntry> ; private lateinit var labels: ArrayList<String>
    private lateinit var data: BarData ; private lateinit var dataset: BarDataSet
    private lateinit var MoneyRecic: ArrayList<String> ; private lateinit  var ImageRecic: ArrayList<Int>
    private lateinit var idArray: ArrayList<Int> ; private lateinit var CategoryRecic: ArrayList<String>
    private lateinit  var DATERecic: ArrayList<String> ; private lateinit var ListMoney: ArrayList<Float>
    private lateinit var CategoryAlert: Array<String?> ; private lateinit var summaRash: TextView


    @SuppressLint("CutPasteId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        inflater.inflate(R.layout.fragment_top, container, false)
        entries = ArrayList()
        labels = ArrayList()
        MoneyRecic = ArrayList()
        ImageRecic = ArrayList()
        CategoryRecic = ArrayList()
        DATERecic = ArrayList()
        ListMoney = ArrayList()
        idArray = ArrayList()
        VisualTask().execute()
        button1 = view!!.findViewById(R.id.button2)
        button1.setOnClickListener(this)
        button2 = view!!.findViewById(R.id.button5)
        button3 = view!!.findViewById(R.id.button6)
        button3.setOnClickListener(this)
        button4 = view!!.findViewById(R.id.button7)
        button4.setOnClickListener(this)
        button5 = view!!.findViewById(R.id.button8)
        button5.setOnClickListener(this)
        button6 = view!!.findViewById(R.id.button9)
        button6.setOnClickListener(this)
        button7 = view!!.findViewById(R.id.button10)
        button7.setOnClickListener(this)
        button8 = view!!.findViewById(R.id.button11)
        button8.setOnClickListener(this)
        button9 = view!!.findViewById(R.id.button12)
        button9.setOnClickListener(this)
        button0 = view!!.findViewById(R.id.button13)
        button0.setOnClickListener(this)
        button00 = view!!.findViewById(R.id.button14)
        button00.setOnClickListener(this)
        button_ok = view!!.findViewById(R.id.button3)
        button_ok.setOnClickListener(this)
        button_delite = view!!.findViewById(R.id.button2)
        button_delite.setOnClickListener(this)
        textView454 = view!!.findViewById(R.id.textView3)
        textView454.append("0")
        getedit = textView454.text.toString()
        summaRash = view!!.findViewById(R.id.textView)
        return view
    }

    @SuppressLint("StaticFieldLeak")
    private inner class VisualTask :
        AsyncTask<Boolean?, Void?, Boolean>() {
        @SuppressLint("SetTextI18n")

        public override fun doInBackground(vararg params: Boolean?): Boolean? {
            entries.clear()
            labels.clear()
            ListMoney.clear()
            CategoryRecic.clear()
            DATERecic.clear()
            MoneyRecic.clear()
            ImageRecic.clear()
            idArray.clear()

            try {
                SafefixDatabaseHelper = SafeFixDatabaseHelper(view!!.context)
                db = SafefixDatabaseHelper.readableDatabase
                val cursorGrafic = db.query(
                    "MONEY", arrayOf("CATEGORY", "SUM(MONEY) AS MONEY"),
                    null, null,
                    "CATEGORY", null, null
                )
                CategoryAlert = arrayOfNulls(cursorGrafic.count)
                while (cursorGrafic.moveToNext()) {
                    var Money: Float
                    var Category: String
                    Money = cursorGrafic.getFloat(cursorGrafic.getColumnIndex("MONEY"))
                    Category = cursorGrafic.getString(cursorGrafic.getColumnIndex("CATEGORY"))
                    ListMoney!!.add(Money)
                    labels!!.add(Category)
                }
                cursorGrafic.close()
                val cursorRecicle = db.query(
                    "MONEY", arrayOf("_id", "CATEGORY", "IMAGE_RESOURCE_ID", "MONEY", "DATA"),
                    null, null,
                    null, null, "DATA DESC"
                )
                while (cursorRecicle.moveToNext()) {
                    var Money: String = cursorRecicle.getString(cursorRecicle.getColumnIndex("MONEY"))
                    var ImageRes1: Int = cursorRecicle.getInt(cursorRecicle.getColumnIndex("IMAGE_RESOURCE_ID"))
                    var Category: String = cursorRecicle.getString(cursorRecicle.getColumnIndex("CATEGORY"))
                    var Data: String = cursorRecicle.getString(cursorRecicle.getColumnIndex("DATA"))
                    var id: Int = cursorRecicle.getInt(cursorRecicle.getColumnIndex("_id"))
                    if (CategoryRecic.size < 10) {
                        CategoryRecic.add(Category)
                        DATERecic.add(Data)
                        MoneyRecic.add(Money)
                        ImageRecic.add(ImageRes1)
                        idArray.add(id)
                    }
                }
                cursorRecicle.close()
                val cursorSum = db.query(
                    "MONEY", arrayOf("SUM(MONEY) AS MONEY"),
                    null, null, null, null, null
                )
                if (cursorSum.moveToLast()) {
                    val sum = cursorSum.getLong(cursorSum.getColumnIndex("MONEY"))
                    summaRash.setText("" + sum)
                }
                cursorSum.close()
                db.close()
            } catch (e: SQLiteException) {
                val toast =
                    Toast.makeText(view!!.context, "Database unavailable", Toast.LENGTH_SHORT)
                toast.show()
            }

            for ((j, i) in ListMoney.withIndex()) {
                entries.add(BarEntry(i, j))
            }
            for (i in labels.indices) {
                CategoryAlert[i] = labels[i]
            }
            return true
        }

        override fun onPostExecute(result: Boolean) {
            dataset = BarDataSet(entries, "")
            chart = view!!.findViewById(R.id.chart)
            data = BarData(labels, dataset)
            chart.setData(data)
            chart.setDescription("")
            dataset.setColors(ColorTemplate.COLORFUL_COLORS)
            chart.animateY(500)
            chart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
                override fun onValueSelected(entry: Entry, i: Int, highlight: Highlight) {
                    val categoryGraf = CategoryAlert[entry.xIndex]
                    val bundl = Bundle()
                    bundl.putString("cat", categoryGraf)
                    val fragment: Fragment = FragmentVisibleRecicleFull()
                    fragment.arguments = bundl
                    val ft = fragmentManager!!.beginTransaction()
                    ft.replace(R.id.content_frame, fragment)
                    ft.addToBackStack(null)
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    ft.commit()
                }

                override fun onNothingSelected() {}
            })

            val Recycler: RecyclerView = view!!.findViewById(R.id.pizza_recycler)
            val adapter = CaptionedImagesAdapter(
                MoneyRecic,
                ImageRecic,
                CategoryRecic,
                DATERecic,
                idArray
            )
            Recycler.adapter = adapter
            val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            Recycler.layoutManager = layoutManager
        }
    }

    //Диалоговое окно
    private fun openDialog() {
        var IntFav = 0
        val MoneyIntent: String = textView454.text.toString()
        textView454.text = "0"
        getedit = "0"
        val builder = AlertDialog.Builder(view!!.context, AlertDialog.THEME_HOLO_DARK)
        builder.setTitle(R.string.category_inter)
        builder.setPositiveButton(R.string.dialog_exit,
            DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })
        builder.setNeutralButton(R.string.category_dialog_add,
            DialogInterface.OnClickListener { dialog, id ->
                val intent = Intent(view!!.context, ActivityVisibleAddCategory::class.java)
                intent.putExtra(ActivityVisibleAddCategory.EXTRA_MONEY, MoneyIntent)
                startActivity(intent)
            })
        builder.setItems(
            CategoryAlert
        ) { dialog, which ->
            try {
                if (Exists(CategoryAlert[which])) {
                    db = SafefixDatabaseHelper!!.readableDatabase
                    val cFav = db.query(
                        "MONEY", arrayOf("IMAGE_RESOURCE_ID", "CATEGORY"),
                        "CATEGORY =?", arrayOf(CategoryAlert[which]),
                        "CATEGORY", null, null
                    )
                    if (cFav.moveToFirst()) {

                        IntFav = cFav.getInt(cFav.getColumnIndex("IMAGE_RESOURCE_ID"))
                    }
                    cFav.close()
                }
                insert(db, CategoryAlert[which], IntFav, MoneyIntent)
                db.close()
            } catch (e: SQLiteException) {
                val toast = Toast.makeText(view!!.context, "Database Error", Toast.LENGTH_SHORT)
                toast.show()
            }
            VisualTask().execute()
        }
        val dialog = builder.create()
        dialog.show()
        val nbutton = dialog.getButton(DialogInterface.BUTTON_NEUTRAL)
        nbutton.setTextColor(Color.WHITE)
        val pbutton = dialog.getButton(DialogInterface.BUTTON_POSITIVE)
        pbutton.setTextColor(Color.WHITE)
    }

    //Проверка наличия строки
    private fun Exists(searchItem: String?): Boolean {
        SafefixDatabaseHelper = SafeFixDatabaseHelper(view!!.context)
        db = SafefixDatabaseHelper!!.readableDatabase
        val columns = arrayOf("CATEGORY")
        val selection = "CATEGORY" + " =?"
        val selectionArgs = arrayOf(searchItem)
        val limit = "1"
        val cursor = db.query("MONEY", columns, selection, selectionArgs, null, null, null, limit)
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }

    //Удаление символов в строке
    private fun removeLastChar(s: String?): String? {
        return if (s == null || s.isEmpty()) null else s.substring(0, s.length - 1)
    }

    //Обработка нажатий на клавиатуре
    override fun onClick(v: View) {
        when (v.id) {
            R.id.button2 -> {
                textView454.text = getedit
                textView454.text = removeLastChar(getedit)
                if (textView454.text.length <= 0) {
                    textView454.text = "0"
                }
                getedit = textView454.text.toString()
            }
            R.id.button3 -> if (getedit == "0") {
                val toast: Toast =
                    Toast.makeText(view!!.context, R.string.summ_add, Toast.LENGTH_SHORT)
                toast.show()
            } else {
                openDialog()
                getedit = "0"
            }
            R.id.button4 -> {
                if (getedit == "0") {
                    textView454.text = ""
                }
                if (textView454.text.length < 10) {
                    textView454.append(button1!!.text)
                    getedit = textView454.text.toString()
                }
            }
            R.id.button5 -> {
                if (getedit == "0") {
                    textView454.text = ""
                }
                if (textView454.text.length < 10) {
                    textView454.append(button2.text)
                    getedit = textView454.text.toString()
                }
            }
            R.id.button6 -> {
                if (getedit == "0") {
                    textView454.text = ""
                }
                if (textView454.text.length < 10) {
                    textView454.append(button3.text)
                    getedit = textView454.text.toString()
                }
            }
            R.id.button7 -> {
                if (getedit == "0") {
                    textView454.text = ""
                }
                if (textView454.text.length < 10) {
                    textView454.append(button4.text)
                    getedit = textView454.text.toString()
                }
            }
            R.id.button8 -> {
                if (getedit == "0") {
                    textView454.text = ""
                }
                if (textView454.text.length < 10) {
                    textView454.append(button5.text)
                    getedit = textView454.text.toString()
                }
            }
            R.id.button9 -> {
                if (getedit == "0") {
                    textView454.text = ""
                }
                if (textView454.text.length < 10) {
                    textView454.append(button6.text)
                    getedit = textView454.text.toString()
                }
            }
            R.id.button10 -> {
                if (getedit == "0") {
                    textView454.text = ""
                }
                if (textView454.text.length < 10) {
                    textView454.append(button7.text)
                    getedit = textView454.text.toString()
                }
            }
            R.id.button11 -> {
                if (getedit == "0") {
                    textView454.text = ""
                }
                if (textView454.text.length < 10) {
                    textView454.append(button8.text)
                    getedit = textView454.text.toString()
                }
            }
            R.id.button12 -> {
                if (getedit == "0") {
                    textView454.text = ""
                }
                if (textView454.text.length < 10) {
                    textView454.append(button9.text)
                    getedit = textView454.text.toString()
                }
            }
            R.id.button13 -> if (getedit != "0") {
                textView454.append(button0.text)
                getedit = textView454.text.toString()
            }
            R.id.button14 -> {
                val sub = ","
                if (!getedit.contains(sub) && textView454.text.length > 0) {
                    textView454.append(button00.text)
                    getedit = textView454.text.toString()
                }
            }
        }
    }

    companion object {
        private fun insert(db: SQLiteDatabase?, Category: String?, resourceId: Int, Money: String) {
            @SuppressLint("SimpleDateFormat") val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val strDate = sdf.format(Date())
            val drinkValues = ContentValues()
            drinkValues.put("CATEGORY", Category)
            drinkValues.put("IMAGE_RESOURCE_ID", resourceId)
            drinkValues.put("DATA", strDate)
            drinkValues.put("MONEY", Money)
            db!!.insert("MONEY", null, drinkValues)
        }
    }
}
