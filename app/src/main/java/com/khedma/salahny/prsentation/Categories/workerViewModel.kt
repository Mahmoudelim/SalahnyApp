package com.khedma.salahny.prsentation.Categories

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import com.khedma.salahny.data.Worker

class WorkerViewModel : ViewModel() {
    private val _selectedWorker = MutableLiveData<Worker?>()
    val selectedWorker: LiveData<Worker?> get() = _selectedWorker

    fun selectWorker(worker: Worker) {
        _selectedWorker.value = worker
        Log.i("sesworker",worker.toString())
    }
}
