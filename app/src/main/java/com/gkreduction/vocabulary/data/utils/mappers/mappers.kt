package com.gkreduction.vocabulary.data.utils.mappers

import com.gkreduction.vocabulary.data.db.entity.IdiomDb
import com.gkreduction.vocabulary.data.db.entity.IrVerbDb
import com.gkreduction.vocabulary.data.db.entity.WordDb
import com.gkreduction.vocabulary.presentation.entity.BaseWord

fun toBase(db: IdiomDb): BaseWord {
    return BaseWord.Idiom(text = db.text, translate = db.translate)
}

fun toBase(db: IrVerbDb): BaseWord {
    return BaseWord.IrVerb(text = db.text, form1 = db.form1, form2 = db.form2, form3 = db.form3)
}

fun toBase(db: WordDb): BaseWord {
    return BaseWord.Word(text = db.text, translate = db.translate)
}


fun mapToDb(idiom: BaseWord.Idiom): IdiomDb {
    return IdiomDb(text = idiom.text, translate = idiom.translate)
}

fun mapToDb(word: BaseWord.Word): WordDb {
    return WordDb(text = word.text, translate = word.translate)
}

fun mapToDb(irVerb: BaseWord.IrVerb): IrVerbDb {
    return IrVerbDb(
        text = irVerb.text,
        form1 = irVerb.form1,
        form2 = irVerb.form2,
        form3 = irVerb.form3
    )
}