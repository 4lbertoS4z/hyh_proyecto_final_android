package com.example.healthytaste.presentation.viewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthytaste.domain.usecase.secondishusecase.GetSecondDishDetailUseCase
import com.example.healthytaste.domain.usecase.secondishusecase.GetSecondDishUseCase
import com.example.healthytaste.model.ResourceState
import com.example.healthytaste.model.SecondDish
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

typealias SecondDishListState = ResourceState<List<SecondDish>>
typealias SecondDishDetailState = ResourceState<SecondDish>

class SecondDishViewModel(
    private val secondDishUseCase: GetSecondDishUseCase,
    private val secondDishDetailUseCase: GetSecondDishDetailUseCase
) : ViewModel() {

    private val secondDishMutableLiveData = MutableLiveData<SecondDishListState>()
    private val secondDishDetailMutableLiveData = MutableLiveData<SecondDishDetailState>()


    fun getSecondDishLiveData(): LiveData<SecondDishListState> {
        return secondDishMutableLiveData
    }

    fun getSecondDishDetailLiveData(): LiveData<SecondDishDetailState> {
        return secondDishDetailMutableLiveData
    }

    fun fetchSecondDishList() {
        secondDishMutableLiveData.value = ResourceState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = secondDishUseCase.execute(forceRemote = true)

                withContext(Dispatchers.Main) {
                    secondDishMutableLiveData.value = ResourceState.Success(data)
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    secondDishMutableLiveData.value =
                        ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }

    fun fetchSecondDishDetail(secondDishId: Int) {
        secondDishDetailMutableLiveData.value = ResourceState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = secondDishDetailUseCase.execute(secondDishId)

                withContext(Dispatchers.Main) {
                    secondDishDetailMutableLiveData.value = ResourceState.Success(data)
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    secondDishDetailMutableLiveData.value =
                        ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }

}