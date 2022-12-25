package com.kuvandikov.english.presentation.ui.fragments.main.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuvandikov.english.data.local.db.daos.WordsDao
import com.kuvandikov.english.presentation.ui.model.Word
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SavedViewModel @Inject constructor(
    private val dao: WordsDao,
) : ViewModel() {

    private val _noFoundTextVisibility = MutableStateFlow<Boolean>(false)
    val noFoundTextVisibility: StateFlow<Boolean> = _noFoundTextVisibility

    private val _list = MutableStateFlow<MutableList<Word>>(mutableListOf())
    val list: StateFlow<MutableList<Word>> = _list



    fun initData(){
        viewModelScope.launch(Dispatchers.IO) {
            val result = dao.getSaved().map {
                Word(it.id ?: 0, it.word ?: "", it.description,it.isFavourite)
            }
            _list.value = result.toMutableList()

            _noFoundTextVisibility.value = _list.value.isEmpty()
        }
    }

    fun removeSaved(word: Word) {
        viewModelScope.launch(Dispatchers.IO) {
            _list.value = _list.value.filter { it.id != word.id }.toMutableList()
            dao.save(word.copy(isFavourite = !word.isFavourite).toEntity())
            _noFoundTextVisibility.value = _list.value.isEmpty()
        }
    }


}