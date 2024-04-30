package my.edu.tarc.contactlist202401

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.google.firebase.Firebase
import com.google.firebase.database.database
import my.edu.tarc.contactlist202401.database.MyWebDB
import my.edu.tarc.contactlist202401.databinding.ActivityMainBinding
import my.edu.tarc.contactlist202401.ui.contact_list.ContactViewModel
import my.edu.tarc.mycontact.ui.contact_list.Contact
import org.json.JSONArray
import org.json.JSONObject

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
            R.id.action_upload -> {
                uploadContact()
                true
            }
            R.id.action_download ->{
                downloadContact("https://raw.githubusercontent.com/seekweeteck/contact_json/main/contact.json")
                true
            }
            R.id.action_settings ->{
                true
            }
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

    fun uploadContact(){
        val sharedPreferences = getSharedPreferences("profile_pref", Context.MODE_PRIVATE)
        val userID = sharedPreferences.getString("phone", "")

        if(!userID.isNullOrEmpty()){
            val database = Firebase.database.reference

            for(contact in contactViewModel.contactList.value!!.listIterator()){
                database.child("user").child(userID).child(contact.phone).setValue(contact)
            }
            Toast.makeText(this, "Contact uploaded", Toast.LENGTH_SHORT).show()
        }
    }

    private fun downloadContact(url: String) {
        //TODO: Insert Internet Permission into the Manifest file

        //Display progress bar
        binding.progressBar.visibility = View.VISIBLE

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                // Process the JSON
                try{
                    if(response != null){
                        val strResponse = response.toString()
                        val jsonResponse  = JSONObject(strResponse)
                        val jsonArray: JSONArray = jsonResponse.getJSONArray("records")
                        val size: Int = jsonArray.length()
                        for(i in 0..size-1){
                            var jsonUser: JSONObject = jsonArray.getJSONObject(i)
                            var contact = Contact(jsonUser.getString("name"),
                                jsonUser.getString("email"),
                                jsonUser.getString("phone"))

                            contactViewModel.addContact(contact)
                        }
                        Toast.makeText(applicationContext, "Record found :$size", Toast.LENGTH_LONG).show()
                        binding.progressBar.visibility = View.GONE

                    }
                }catch (e:Exception){
                    Log.d("Main", "Response: %s".format(e.message.toString()))
                    Toast.makeText(applicationContext, "Error loading record", Toast.LENGTH_LONG).show()
                    binding.progressBar.visibility = View.GONE
                }
            },
            { error ->
                Log.d("Main", "Response: %s".format(error.message.toString()))
                binding.progressBar.visibility = View.GONE
            }
        )

        //Volley request policy, only one time request
        jsonObjectRequest.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
            0, //no retry
            1f
        )

        // Access the RequestQueue through your singleton class.
        MyWebDB.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }


    /*companion object{
        val contactList = ArrayList<Contact>()
    }*/
}