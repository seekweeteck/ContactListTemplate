package my.edu.tarc.mycontact.ui.contact_list

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class ContactViewModel(application: Application): AndroidViewModel(application) {
    val contactList = ArrayList<Contact>()

    fun insert(contact: Contact){
        contactList.add(contact)
    }

}