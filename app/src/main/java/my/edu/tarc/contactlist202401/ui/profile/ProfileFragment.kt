package my.edu.tarc.contactlist202401.ui.profile

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import my.edu.tarc.contactlist202401.R
import my.edu.tarc.contactlist202401.databinding.FragmentProfileBinding
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.OutputStream

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ProfileFragment : Fragment(), MenuProvider {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()){ uri: Uri? ->
        if(uri != null){
            binding.imageViewProfile.setImageURI(uri)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        readProfilePicture()

        binding.imageViewProfile.setOnClickListener {
            getContent.launch("image/*")
        }

        binding.buttonSaveProfile.setOnClickListener {
            saveProfilePicture()
        }

        Log.d("Profile Fragment", "onCreate")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.d("Profile Fragment", "onDestroy")
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

    private fun saveProfilePicture(){
        val filename = "profile.png"
        val file = File(this.context?.filesDir, filename)

        val bd = binding.imageViewProfile.drawable as BitmapDrawable
        val bitmap = bd.bitmap
        val outputStream: OutputStream

        try{
            outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 50, outputStream)
            outputStream.flush()
            outputStream.close()
        }catch (e: FileNotFoundException){
            e.printStackTrace()
        }
    }

    private fun readProfilePicture(){
        val filename = "profile.png"
        val file = File(this.context?.filesDir, filename)

        try{
            if(file.exists()){
                val bitmap = BitmapFactory.decodeFile(file.absolutePath)
                binding.imageViewProfile.setImageBitmap(bitmap)
            }
        }catch (e: FileNotFoundException){
            e.printStackTrace()
        }
    }
}