package com.example.healthytaste.data.dish

import com.example.healthytaste.data.dish.local.DishLocalImpl
import com.example.healthytaste.data.dish.remote.DishRemoteImpl
import com.example.healthytaste.domain.DishRepository
import com.example.healthytaste.model.DessertDish
import com.example.healthytaste.model.First
import com.example.healthytaste.model.SecondDish

class DishDataImpl(
    private val dishLocalImpl: DishLocalImpl,
    private val dishRemoteImpl: DishRemoteImpl
) : DishRepository {
    override suspend fun getFirstDish(forceRemote: Boolean): List<First> {

        val chachedFirstDishList = dishLocalImpl.getFirstDish()

        if (chachedFirstDishList.isNotEmpty() && !forceRemote) {
            return chachedFirstDishList
        } else {
            val remoteFirstDishList = dishRemoteImpl.getFirstDish()
            saveFirstDish(remoteFirstDishList)
            return remoteFirstDishList
        }
    }

    override suspend fun getOneFirstDish(firstDishId: Int): First {
        return dishRemoteImpl.getOneFirstDish(firstDishId)
    }

    override suspend fun getSecondDish(forceRemote: Boolean): List<SecondDish> {
        val chachedSecondDishList = dishLocalImpl.getSecondDish()

        if (chachedSecondDishList.isNotEmpty() && !forceRemote) {
            return chachedSecondDishList
        } else {
            val remoteSecondDishList = dishRemoteImpl.getSecondDish()
            saveSecondDish(remoteSecondDishList)
            return remoteSecondDishList
        }
    }

    override suspend fun getSecondDetail(secondDishId: Int): SecondDish {
        return dishRemoteImpl.getSecondDishDetail(secondDishId)
    }

    override suspend fun getDessertDish(forceRemote: Boolean): List<DessertDish> {
        val chachedDessertDishList = dishLocalImpl.getDessertDish()

        if (chachedDessertDishList.isNotEmpty() && !forceRemote) {
            return chachedDessertDishList
        } else {
            val remoteDessertDishList = dishRemoteImpl.getDessertDish()
            saveDessertDish(remoteDessertDishList)
            return remoteDessertDishList
        }
    }

    override suspend fun getDessertDetail(dessertDishId: Int): DessertDish {
        return dishRemoteImpl.getDessertDishDetail(dessertDishId)

    }

    override fun saveFirstDish(firstDish: List<First>) {
        dishLocalImpl.saveFirstDish(firstDish)
    }

    override fun saveSecondDish(secondDish: List<SecondDish>) {
        dishLocalImpl.saveSecondDish(secondDish)
    }

    override fun saveDessertDish(dessertDish: List<DessertDish>) {
        dishLocalImpl.saveDessertDish(dessertDish)
    }


}