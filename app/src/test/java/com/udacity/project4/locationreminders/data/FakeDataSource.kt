package com.udacity.project4.locationreminders.data
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.data.dto.Result

//Use FakeDataSource that acts as a test double to the LocalDataSource
class FakeDataSource(
    var reminders: MutableList<ReminderDTO>? = mutableListOf()) :
    ReminderDataSource {

    private var returnError = false
    // Create a fake data source to act as a double to the real data source
    fun returnError(value:Boolean){
        returnError = value
    }


    override suspend fun getReminders(): Result<List<ReminderDTO>> {
        // that confirm the correct behavior when the reminders list for some reason can't be loaded
        return if(returnError){
            Result.Error("reminders were unable to get retrieved")
        } else {
            Result.Success(ArrayList(reminders))
        }
    }

    override suspend fun saveReminder(reminder: ReminderDTO) {
        reminders?.add(reminder)
    }

    // that confirm the correct behavior when the reminders list for some reason can't be loaded
    override suspend fun getReminder(id: String): Result<ReminderDTO> {
        if(returnError){
            return Result.Error("reminders were unable to get retrieved")
        }
        // Return the reminder with the id
        val reminder = reminders?.find {
            it.id == id
        }
        return if (reminder!=null){
            Result.Success(reminder)
        } else{
            Result.Error("reminders were unable to get retrieved")
        }
    }

    override suspend fun deleteById(id: String) {
        val remind = reminders?.find {
            it.id == id
        }
        reminders?.remove(remind)
    }

    override suspend fun deleteAllReminders() {
        // Delete all the reminders
        reminders?.clear()
    }


}