package com.demo.apps.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.demo.apps.data.TextProperties

class TextListViewModel : ViewModel() {
    // Using mutableStateListOf to observe changes in the list
     val textPropertiesList = mutableStateListOf<TextProperties>()

     val textPropertiesModel = MutableLiveData<Pair<Int,TextProperties>>()

    fun addTextProperties(textProperties: TextProperties) {
        textPropertiesList.add(textProperties)
    }

    fun removeTextProperties(textProperties: TextProperties) {
        textPropertiesList.remove(textProperties)
    }

    // You can also add methods to update or modify specific TextProperties if needed
}
