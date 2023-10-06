package com.example.healthytaste.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthytaste.domain.usecase.dessertdishusecase.GetDessertDishDetailUseCase
import com.example.healthytaste.domain.usecase.dessertdishusecase.GetDessertDishUseCase
import com.example.healthytaste.domain.usecase.firstdishusecase.GetFirstDishDetailUseCase
import com.example.healthytaste.domain.usecase.firstdishusecase.GetFirstDishUseCase
import com.example.healthytaste.model.DessertDish
import com.example.healthytaste.model.ResourceState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

typealias DessertDishListState = ResourceState<List<DessertDish>>
typealias DessertDishDetailState = ResourceState<DessertDish>

class DessertDishViewModel(
    private val dessertDishUseCase: GetDessertDishUseCase,
    private val dessertDishDetailUseCase: GetDessertDishDetailUseCase
) : ViewModel() {

    private val dessertDishMutableLiveData = MutableLiveData<DessertDishListState>()
    private val dessertDishDetailMutableLiveData = MutableLiveData<DessertDishDetailState>()


    fun getDessertDishLiveData(): LiveData<DessertDishListState> {
        return dessertDishMutableLiveData
    }
    fun getDessertDishDetailLiveData(): LiveData<DessertDishDetailState> {
        return dessertDishDetailMutableLiveData
    }

    fun fetchDessertDishList() {
        dessertDishMutableLiveData.value = ResourceState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = dessertDishUseCase.execute(forceRemote = true)

                withContext(Dispatchers.Main) {
                    dessertDishMutableLiveData.value = ResourceState.Success(data)
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    dessertDishMutableLiveData.value =
                        ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }

    fun fetchDessertDishDetail(dessertDishId: Int) {
        dessertDishDetailMutableLiveData.value = ResourceState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = dessertDishDetailUseCase.execute(dessertDishId)

                withContext(Dispatchers.Main) {
                    dessertDishDetailMutableLiveData.value = ResourceState.Success(data)
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    dessertDishDetailMutableLiveData.value =
                        ResourceState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }

}