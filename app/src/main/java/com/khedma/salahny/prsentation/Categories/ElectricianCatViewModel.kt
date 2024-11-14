import android.util.Log
import com.khedma.salahny.data.Worker
import com.khedma.salahny.prsentation.Categories.BaseWorkerViewModel

class ElectricianCatViewModel : BaseWorkerViewModel() {
    override suspend fun getWorkerByProfession(): List<Worker> {
        val response = apiService.getAllWorkers()
        Log.i("Response", response.toString())
        return response.values.filter { it.profession == "Electrician" && it.isAvailable }
    }
}