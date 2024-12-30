package com.example.exerciseapp.data

class UserRepository(private val userDao: UserDao) {
    suspend fun insertUser(user: User) = userDao.insertUser(user)
    suspend fun getUser() = userDao.getUser()
    suspend fun getLastUser() = userDao.getLastUser()
}
