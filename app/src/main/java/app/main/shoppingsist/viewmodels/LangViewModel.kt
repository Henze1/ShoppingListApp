package app.main.shoppingsist.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import app.main.shoppingsist.langsupport.English
import app.main.shoppingsist.langsupport.Language

class LangViewModel: ViewModel() {
    var lang by mutableStateOf<Language>(English())
}