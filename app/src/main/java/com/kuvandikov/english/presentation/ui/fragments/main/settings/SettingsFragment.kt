package com.kuvandikov.english.presentation.ui.fragments.main.settings

import android.content.Intent
import android.net.Uri
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kuvandikov.english.BuildConfig
import com.kuvandikov.english.R
import com.kuvandikov.english.databinding.FragmentSettingsBinding
import com.kuvandikov.english.presentation.base.BaseFragment


class SettingsFragment : BaseFragment(R.layout.fragment_settings) {

    private val binding by viewBinding(FragmentSettingsBinding::bind)


    override fun initialize() {
        val appVersion = "${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})"
        binding.appVersion.text = getString(R.string.app_version, appVersion)
    }

    override fun setupListeners() {

        binding.rateTheApp.setOnClickListener {
            try {
                val playStoreIntent = Intent(Intent.ACTION_VIEW)
                playStoreIntent.data =
                    Uri.parse("https://play.google.com/store/apps/details?id=uz.loving.math_battle")
                startActivity(playStoreIntent)
            } catch (e: Exception) {

            }
        }

        binding.feedback.setOnClickListener {
            // https://t.me/ChatRussianUzbekDictionaryBot
            try {
                val telegramIntent = Intent(Intent.ACTION_VIEW)
                telegramIntent.data = Uri.parse("https://t.me/ChatRussianUzbekDictionaryBot")
                startActivity(telegramIntent)
            } catch (e: Exception) {

            }
        }

        binding.shareToFriends.setOnClickListener {
            try {
                val sendIntent = Intent()
                sendIntent.action = Intent.ACTION_SEND
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "https://play.google.com/store/apps/details?id=uz.loving.math_battle")
                sendIntent.type = "text/plain"
                startActivity(sendIntent)
            } catch (e: Exception) {

            }

        }
    }

}