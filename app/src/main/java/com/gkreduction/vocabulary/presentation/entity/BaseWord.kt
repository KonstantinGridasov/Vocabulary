package com.gkreduction.vocabulary.presentation.entity

sealed class BaseWord {
    data class Idiom(var text: String, var translate: String) : BaseWord()
    data class Word(var text: String, var translate: String) : BaseWord()

    data class IrVerb(
        var text: String, var form1: String,
        var form2: String, var form3: String
    ) : BaseWord()
}