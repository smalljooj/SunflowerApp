package br.edu.ifpb.sunflower.datastore

import kotlinx.serialization.Serializable

@Serializable
data class AppSettings(
    val language: Language = Language.PORTUGUESE,
    val firstUse: Boolean = true
)

enum class Language {
    PORTUGUESE, ENGLISH
}