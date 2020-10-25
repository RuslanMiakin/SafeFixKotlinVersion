package miworkgroup.safefix
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var drawerToggle : ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var titles : Array<String>
    private lateinit var drawerList : ListView
    private var currentPosition = 0

    inner class DrawerItemClickListener : AdapterView.OnItemClickListener{
        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            selectItem(position)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        titles = resources.getStringArray(R.array.titles)
        drawerList = findViewById(R.id.drawer)
        drawerLayout = findViewById(R.id.drawer_layout)
        drawerList.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_activated_1, titles
        )
        drawerList.onItemClickListener = DrawerItemClickListener()
        drawerToggle = object : ActionBarDrawerToggle(
            this, drawerLayout,
            R.string.open_drawer, R.string.close_drawer
        ) {
            override fun onDrawerClosed(view: View) {
                super.onDrawerClosed(view)
                invalidateOptionsMenu()
            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
            }
        }
        drawerLayout.addDrawerListener(drawerToggle)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)

        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt("position")
            setActionBarTitle(currentPosition)
        } else {
            selectItem(0)
        }

        supportFragmentManager.addOnBackStackChangedListener {
            val fragMan = supportFragmentManager
            val fragment = fragMan.findFragmentByTag("visible_fragment")
            if (fragment is FragmentTop) {
                currentPosition = 0
            }
            if (fragment is FragmentVisibleRecicleFull) {
                currentPosition = 1
            }
            if (fragment is FragmentVisibleSettings) {
                currentPosition = 2
            }
            setActionBarTitle(currentPosition)
            drawerList.setItemChecked(currentPosition, true)
        }
        if (savedInstanceState == null) {
            selectItem(0)
        }
    }
    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        drawerToggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        drawerToggle.onConfigurationChanged(newConfig)
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 2) {
            moveTaskToBack(false)
        } else {
            super.onBackPressed()
        }
    }

    private fun selectItem(position: Int) {
        currentPosition = position
        val fragment: Fragment
        when (position) {
            1 -> {
                val bundl = Bundle()
                bundl.putString("cat", "no")
                fragment = FragmentVisibleRecicleFull()
                fragment.arguments = bundl
            }
            2 -> fragment = FragmentVisibleSettings()
            else -> fragment = FragmentVisible1()
        }
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.content_frame, fragment, "visible_fragment")
        ft.addToBackStack(null)
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        ft.commit()
        setActionBarTitle(position)
        drawerLayout.closeDrawer(drawerList)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("position", currentPosition)
    }

    private fun setActionBarTitle(position: Int) {
        val title: String = if (position == 0) {
            resources.getString(R.string.app_name)
        } else {
            titles[position]
        }
        Objects.requireNonNull(supportActionBar)?.title = title
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (drawerToggle.onOptionsItemSelected(item)) {
            true
        } else when (item.itemId) {
            R.id.action_create_order -> {
                selectItem(1)
                true
            }
            R.id.action_settings -> {
                val intent = Intent(this, ActivityVisibleAddCategory::class.java)
                intent.putExtra(ActivityVisibleAddCategory.EXTRA_MONEY, "")
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}}