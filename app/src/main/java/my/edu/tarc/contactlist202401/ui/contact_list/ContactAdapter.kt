package my.edu.tarc.mycontact

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import my.edu.tarc.contactlist202401.databinding.RecordBinding
import my.edu.tarc.mycontact.ui.contact_list.Contact

class ContactAdapter() : RecyclerView.Adapter<ContactAdapter.ViewHolder>(){
    private var dataSet = emptyList<Contact>()
    private var onClickListener: OnClickListener? = null
    class ViewHolder(val binding: RecordBinding): RecyclerView.ViewHolder(binding.root)

    internal fun setContact(contact: List<Contact>){
        dataSet = contact
        notifyDataSetChanged()
    }

    fun setOnClickListener(listener: OnClickListener){
        this.onClickListener = listener
    }

    interface OnClickListener{
        fun onItemClick(item: Contact)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val binding = RecordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataSet!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = dataSet?.get(position)
        holder.binding.textViewName.text = contact!!.name
        holder.binding.textViewEmail.text = contact.email
        holder.binding.textViewPhone.text = contact.phone

        holder.itemView.setOnClickListener {
            onClickListener?.onItemClick(contact)
        }
    }
}

