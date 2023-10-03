package com.example.healthytaste.data.fistsdish

import com.example.healthytaste.data.fistsdish.local.FirstDishLocalImpl
import com.example.healthytaste.data.fistsdish.remote.FirstDishRemoteImpl
import com.example.healthytaste.domain.FirstDishRepository
import com.example.healthytaste.model.First
import com.example.healthytaste.model.FirstDishResponse

class FirstDishDataImpl(
    private val firstDishLocalImpl:FirstDishLocalImpl,
    private val firstDishRemoteImpl:FirstDishRemoteImpl
) : FirstDishRepository {
    override suspend fun getFirstDish(forceRemote: Boolean): List<First> {

        val chachedFirstDishList = firstDishLocalImpl.getFirstDish()

        if (chachedFirstDishList.isNotEmpty() && !forceRemote){
            return chachedFirstDishList
        }else{
            val remoteFirstDishList = firstDishRemoteImpl.getFirstDish()
            saveFirstDish(remoteFirstDishList)
            return remoteFirstDishList
        }
    }

    override suspend fun getOneFirstDish(firstDishId: Int): First {
        return firstDishRemoteImpl.getOneFirstDish(firstDishId)
    }

    override fun saveFirstDish(firstDish: List<First>) {
        firstDishLocalImpl.saveFirstDish(firstDish)
    }



}