package com.mukesh.mvvmarchitecturetutorialkotlin.ui.home.profile

import androidx.lifecycle.ViewModel
import com.mukesh.mvvmarchitecturetutorialkotlin.data.repositories.UserRepository

class ProfileViewModel(userRepository: UserRepository) : ViewModel() {

    val user = userRepository.getUserDetails()

}
