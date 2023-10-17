package com.example.healthytaste.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthytaste.domain.usecase.firstdishusecase.GetFirstDishDetailUseCase
import com.example.healthytaste.domain.usecase.firstdishusecase.GetFirstDishUseCase
import com.example.healthytaste.model.First
import com.example.healthytaste.model.ResourceState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

typealias FirstDishListState = ResourceState<List<First>>
typealias FirstDishDetailState = ResourceState<First>

class FirstDishViewModel(
    private val firstDishUseCase: GetFirstDishUseCase,
    private val firstDishDetailUseCase: GetFirstDishDetailUseCase
) : ViewModel() {

    private val firstDishMutableLiveData = MutableLiveData<FirstDishListState>()
    private val firstDishDetailMutableLiveData = MutableLiveData<FirstDishDetailState>()


    fun getFirstDishLiveData(): LiveData<FirstDishListState> {
        return firstDishMutableLiveData
    }

    fun getFirstOneDishLiveData(): LiveData<FirstDishDetailState> {
        return firstDishDetailMutableLiveData
    }

    fun fetchFirstDishList() {
        firstDishMutableLiveData.value = ResourceState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = firstDishUseCase.execute(forceRemote = true)

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

    fun fetchFirstDishDetail(firstDishId: Int) {
        firstDishDetailMutableLiveData.value = ResourceState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = firstDishDetailUseCase.execute(firstDishId)

                withContext(Dispatchers.Main) {
                    firstDishDetailMutableLiveData.value = ResourceState.Success(data)
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    firstDishDetailMutableLiveData.value =
                        ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }

}