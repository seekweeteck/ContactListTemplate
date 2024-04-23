package my.edu.tarc.mycontactlist.database

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import my.edu.tarc.mycontact.ui.contact_list.Contact

@Dao
interface ContactDao {
    @Query("SELECT * FROM contact ORDER BY name ASC")
    fun getAllContact(): LiveData<List<Contact>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(contact: Contact)

    @Delete
    suspend fun delete(contact: Contact)

    @Update
    suspend fun update(contact: Contact)
}