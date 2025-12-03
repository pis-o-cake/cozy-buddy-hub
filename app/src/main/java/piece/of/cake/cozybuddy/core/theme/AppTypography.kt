package piece.of.cake.cozybuddy.core.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import piece.of.cake.cozybuddy.R

object AppTypography {
    val pretendard = FontFamily(
        Font(R.font.pretendard_bold, FontWeight.Bold)
    )

    val jua = FontFamily(
        Font(R.font.bm_jua_bold, FontWeight.Bold)
    )

    val default = pretendard

    fun fromOption(option: FontOption): FontFamily {
        return when (option) {
            FontOption.PRETENDARD -> pretendard
            FontOption.JUA -> jua
        }
    }
}

enum class FontOption(val key: String, val displayName: String) {
    PRETENDARD("pretendard", "Pretendard"),
    JUA("jua", "Jua");

    companion object {
        val DEFAULT = JUA
        fun fromKey(key: String) = entries.find { it.key == key } ?: DEFAULT
    }
}
