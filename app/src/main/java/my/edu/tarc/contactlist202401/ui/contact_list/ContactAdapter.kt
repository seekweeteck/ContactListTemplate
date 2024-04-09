package my.edu.tarc.mycontact

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import my.edu.tarc.contactlist202401.databinding.RecordBinding
import my.edu.tarc.mycontact.ui.contact_list.Contact

class ContactAdapter() : RecyclerView.Adapter<ContactAdapter.ViewHolder>(){

    //private val dataSet: List<Contact>
    private var dataSet = emptyList<Contact>()

    /*class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewName: TextView = view.findViewById(R.id.textViewName)
        val textViewEmail: TextView = view.findViewById(R.id.textViewEmail)
        val textViewPhone: TextView = view.findViewById(R.id.textViewPhone)

        init {
            // Define click listener for the ViewHolder's View.
            view.setOnClickListener {
            }
        }
    }*/

    class ViewHolder(val binding: RecordBinding): RecyclerView.ViewHolder(binding.root)

    internal fun setContact(contact: List<Contact>){
        dataSet = contact
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val binding = RecordBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = dataSet[position]
        holder.binding.textViewName.text = contact.name
        holder.binding.textViewEmail.text = contact.email
        holder.binding.textViewPhone.text = contact.phone
    }
}

