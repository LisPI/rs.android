package com.develop.rs_school.tedrssfeed

import android.content.Context

interface PresenterMVP{
    fun getItemButtonClicked(context: Context)
}

class Presenter(val model: ModelMVP, val view: ViewMVP) : PresenterMVP{
    override fun getItemButtonClicked(context: Context) {
        view.showItem(model.getItem(context))
    }
}