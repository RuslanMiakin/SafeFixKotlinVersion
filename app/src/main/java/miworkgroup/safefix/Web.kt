package miworkgroup.safefix

import android.content.Intent
import android.database.sqlite.SQLiteException
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.Serializable


class Web : AppCompatActivity(), View.OnClickListener {
    lateinit var builder: StringBuilder ; lateinit var builder2: StringBuilder
    lateinit var webview: WebView ; lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        webview = findViewById(R.id.web)
        button = findViewById(R.id.button15)
        button.setOnClickListener(this)
        try {
            val SafefixDatabaseHelper = SafeFixDatabaseHelper(this)
            val db = SafefixDatabaseHelper.readableDatabase
            val cursor = db.query(
                "MONEY", arrayOf("CATEGORY", "DATA", "MONEY"),
                null, null,
                null, null, null
            )
            builder = StringBuilder()
            builder2 = StringBuilder()
            builder.append("<html><body><h1>Данные</h1><table>")
            while (cursor.moveToNext()) {
                builder.append("<tr><td>")
                val Data = cursor.getString(cursor.getColumnIndex("DATA"))
                builder.append(Data).append("  ")
                builder2.append(Data).append("  ").append("\n")
                builder.append("</td><td>")
                val Categ = cursor.getString(cursor.getColumnIndex("CATEGORY"))
                builder.append(Categ).append("  ")
                builder.append("</td><td>")
                builder2.append(Categ).append("  ").append("\n")
                val Money = cursor.getString(cursor.getColumnIndex("MONEY"))
                builder.append(Money).append(" ")
                builder.append("</td></tr>")
                builder2.append(Money).append(" ").append("\n")
            }
            cursor.close()
            db.close()
        } catch (e: SQLiteException) {
            val toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT)
            toast.show()
        }
        webview.loadData(builder.toString(), "text/html", "UTF-8")
    }

    override fun onClick(v: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, builder2 as Serializable?)
        startActivity(intent)
    }
}