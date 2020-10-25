package miworkgroup.safefix

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SafeFixDatabaseHelper internal constructor(context: Context?) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE MONEY (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "CATEGORY TEXT, "
                    + "IMAGE_RESOURCE_ID INTEGER, " +
                    "DATA DATETIME DEFAULT CURRENT_TIMESTAMP, " + "MONEY TEXT);"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    companion object {
        private const val DB_NAME = "safefix"
        private const val DB_VERSION = 1
    }
}