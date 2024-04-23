package my.edu.tarc.contactlist202401.ui.add_contact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import my.edu.tarc.contactlist202401.R
import my.edu.tarc.contactlist202401.databinding.FragmentAddContactBinding
import my.edu.tarc.mycontact.ui.contact_list.Contact
import my.edu.tarc.contactlist202401.ui.contact_list.ContactViewModel

class AddContactFragment : Fragment(), MenuProvider {
    private var _binding: FragmentAddContactBinding? = null
    //TODO: Insert View Model
    private val myViewModel: ContactViewModel by activityViewModels()


    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        binding.buttonSave.setOnClickListener {
            val newContact = Contact(binding.editTextName.text.toString(), binding.editTextEmailAddress.text.toString(), binding.editTextPhone.text.toString())
            //MainActivity.contactList.add(newContact)
            myViewModel.addContact(newContact)
            Toast.makeText(requireContext(), "Contact Saved", Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.findItem(R.id.action_settings).setVisible(false)
        menu.findItem(R.id.action_profile).setVisible(false)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when(menuItem.itemId){
            android.R.id.home->{
                findNavController().navigateUp()
            }
        }
        return true
    }
}