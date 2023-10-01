package com.example.healthytaste.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthytaste.domain.usecase.firstdishusecase.GetFirstDishUseCase
import com.example.healthytaste.model.First
import com.example.healthytaste.model.ResourceState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

typealias FirstDishListState = ResourceState<List<First>>
typealias FirstDishDetailState = ResourceState<First>

class FirstDishViewModel(private val firstDishUseCase: GetFirstDishUseCase) : ViewModel() {

    private val firstDishMutableLiveData = MutableLiveData<FirstDishListState>()


    fun getFirstDishLiveData(): LiveData<FirstDishListState> {
        return firstDishMutableLiveData
    }


    fun fetchFirstDishList() {
        firstDishMutableLiveData.value = ResourceState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = firstDishUseCase.execute()

                withContext(Dispatchers.Main) {
                    firstDishMutableLiveData.value = ResourceState.Success(data)
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    firstDishMutableLiveData.value =
                        ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }
}