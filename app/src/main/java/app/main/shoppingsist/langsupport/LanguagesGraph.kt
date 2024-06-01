package app.main.shoppingsist.langsupport

enum class Languages {
    EN, RU, AM
}
class LanguagesGraph {
    private var language: Languages = Languages.EN

    fun setLanguage(lang: String) {
        language = when (lang) {
            "en" -> Languages.EN
            "ru" -> Languages.RU
            "am" -> Languages.AM
            else -> {
                Languages.EN
            }
        }
    }

    fun getLanguage(): Languages {
        return language
    }

}