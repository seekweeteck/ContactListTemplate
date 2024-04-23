package my.edu.tarc.contactlist202401

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import my.edu.tarc.contactlist202401.databinding.ActivityMainBinding
import my.edu.tarc.contactlist202401.ui.contact_list.ContactViewModel
import my.edu.tarc.mycontact.ui.contact_list.Contact

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    //TODO 3: Declare the Activity View Model
    private lateinit var contactViewModel: ContactViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        //TODO 4: Initialise View Model
        contactViewModel = ViewModelProvider(this).get(ContactViewModel::class.java)


        binding.fab.setOnClickListener { view ->
/*            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()*/
            navController.navigate(R.id.action_nav_contact_list_to_nav_add_contact2)
        }

        navController.addOnDestinationChangedListener{controller, destination, arguments->
            when(destination.id){
                R.id.nav_add_contact, R.id.nav_profile ->{
                    binding.fab.visibility = View.INVISIBLE
                }
                else ->
                    binding.fab.visibility = View.VISIBLE
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.action_profile -> {
                findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.action_nav_contact_list_to_nav_profile)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    /*companion object{
        val contactList = ArrayList<Contact>()
    }*/
}