package com.example.exerciseapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exerciseapp.data.User
import com.example.exerciseapp.data.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> = _user

    init {
        fetchLastUser() // Fetch data when ViewModel is created
    }

    private fun fetchLastUser() {
        viewModelScope.launch {
            _user.value = repository.getLastUser()
        }
    }

    fun saveUser(height: Float, weight: Float) {
        viewModelScope.launch {
            repository.insertUser(User(weight = weight, height = height))
            fetchLastUser() // Refresh data after saving
        }
    }
}
