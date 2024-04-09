package my.edu.tarc.contactlist202401.ui.contact_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import my.edu.tarc.contactlist202401.MainActivity
import my.edu.tarc.contactlist202401.databinding.FragmentContactListBinding
import my.edu.tarc.mycontact.ContactAdapter
import my.edu.tarc.mycontact.ui.contact_list.Contact

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ContactListFragment : Fragment() {

    private var _binding: FragmentContactListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

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
        adapter.setContact(MainActivity.contactList)
        binding.recycleListView.adapter = adapter
        Toast.makeText(requireContext(), "Record Count:" + MainActivity.contactList.size, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}