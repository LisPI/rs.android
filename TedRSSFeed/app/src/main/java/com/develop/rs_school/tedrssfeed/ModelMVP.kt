package com.develop.rs_school.tedrssfeed

import android.content.Context
import org.json.JSONObject
import kotlin.random.Random

interface ModelMVP {
    fun getItemsList(): String
    fun getItem(appContext : Context): String
}

class ModelJSON : ModelMVP {
    override fun getItemsList(): String {
        TODO("Not yet implemented")
    }

    //TODO CONTEXT ???????????????????????????????????????????
    override fun getItem(appContext : Context):String {
        //TODO add parser. It is just for fun
        val jsonString = appContext.assets.open("data.json").bufferedReader().use { it.readText() }
        val json = JSONObject(jsonString)
        val itemsArray = json.getJSONObject("channel").getJSONArray("item")
        return itemsArray.getJSONObject(Random.nextInt(100)).getString("title")
    }
}

class ModelXML : ModelMVP{
    override fun getItemsList(): String {
        TODO("Not yet implemented")
    }

    override fun getItem(appContext: Context): String {
        TODO("Not yet implemented")
    }
}