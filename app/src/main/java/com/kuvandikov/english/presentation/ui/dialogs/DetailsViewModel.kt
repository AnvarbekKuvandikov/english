package com.kuvandikov.english.presentation.ui.dialogs

import android.media.MediaPlayer
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kuvandikov.english.R
import com.kuvandikov.english.data.local.db.daos.WordsDao
import com.kuvandikov.english.databinding.FragmentDetailsBinding
import com.kuvandikov.english.presentation.base.BaseFragment
import com.kuvandikov.english.presentation.ui.fragments.main.home.HomeViewModel
import com.kuvandikov.english.presentation.ui.model.Word
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject



@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val dao: WordsDao,
) : ViewModel() {

    private val mediaPlayer = MediaPlayer()



    fun playAudio(id: Long, path: String) {

        viewModelScope.launch(Dispatchers.IO) {
            mediaPlayer.reset()
            val word = dao.getById(id)
            val audioByteArray = word.audio ?: return@launch

            try {
                val audioFile = File(path)

                val fileOutputStream = FileOutputStream(audioFile)
                /*val fileInputStream = FileOutputStream(audioFile)*/
                fileOutputStream.write(audioByteArray)
                fileOutputStream.close()
                mediaPlayer.setDataSource(path)
                mediaPlayer.prepare()
                mediaPlayer.start()
                Log.d("TAG", "playAudio: ")

            } catch (e: IOException) {
                Log.d("TAG", "playAudio: ${e.message}")
            }
        }
    }

}