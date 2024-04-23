package my.edu.tarc.contactlist202401.ui.contact_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import my.edu.tarc.contactlist202401.databinding.FragmentContactListBinding
import my.edu.tarc.mycontact.ContactAdapter
import my.edu.tarc.mycontact.ui.contact_list.Contact

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ContactListFragment : Fragment(), ContactAdapter.OnClickListener {

    private var _binding: FragmentContactListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    //TODO: Declare View Model
    private val contactViewModel: ContactViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentContactListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ContactAdapter()
        adapter.setOnClickListener(this)

        contactViewModel.contactList.observe(viewLifecycleOwner){
            if(it.isEmpty()){
                Toast.makeText(context, "No record", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(context, "Record number " + it.size, Toast.LENGTH_SHORT).show()
            }
            adapter.setContact(it)
        }
        binding.recycleListView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(item: Contact) {
        Toast.makeText(requireActivity(), "Contact name ${item.name}", Toast.LENGTH_SHORT).show()
    }
}