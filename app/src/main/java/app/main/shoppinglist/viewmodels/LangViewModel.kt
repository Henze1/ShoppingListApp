package app.main.shoppinglist.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import app.main.shoppinglist.langsupport.English
import app.main.shoppinglist.langsupport.Language

class LangViewModel: ViewModel() {
    var lang by mutableStateOf<Language>(English())
}