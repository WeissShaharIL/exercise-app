package com.example.exerciseapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exerciseapp.data.entities.User
import com.example.exerciseapp.data.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _allUserRecords = MutableLiveData<List<User>>()
    val allUserRecords: LiveData<List<User>> = _allUserRecords

    init {
        fetchLastUser() // Fetch data when ViewModel is created
    }

    private fun fetchLastUser() {
        _isLoading.value = true // Start loading
        viewModelScope.launch {
            _user.value = repository.getLastUser()
            _isLoading.value = false // Stop loading
        }
    }

    fun fetchAllUsers() {
        _isLoading.value = true
        viewModelScope.launch {
            _allUserRecords.value = repository.getAllUsers()
            _isLoading.value = false

        }
    }

    fun saveUser(height: Float, weight: Float) {
        viewModelScope.launch {
            repository.insertUser(User(weight = weight, height = height))
            fetchLastUser() // Refresh data after saving
        }
    }
}