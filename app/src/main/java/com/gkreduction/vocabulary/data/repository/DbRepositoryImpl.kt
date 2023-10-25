package com.gkreduction.vocabulary.data.repository

import com.gkreduction.vocabulary.data.db.dao.IdiomDao
import com.gkreduction.vocabulary.data.db.dao.IrVerbDao
import com.gkreduction.vocabulary.data.db.dao.WordDao
import com.gkreduction.vocabulary.data.db.entity.IdiomDb
import com.gkreduction.vocabulary.data.db.entity.IrVerbDb
import com.gkreduction.vocabulary.data.db.entity.WordDb
import com.gkreduction.vocabulary.data.utils.mappers.mapToDb
import com.gkreduction.vocabulary.data.utils.mappers.toBase
import com.gkreduction.vocabulary.presentation.entity.BaseWord

class DbRepositoryImpl(
    var idiomDao: IdiomDao,
    var wordDao: WordDao,
    var irVerbDao: IrVerbDao
) : DbRepository {

    override suspend fun getWords(): List<BaseWord> {
        val result = ArrayList<BaseWord>()
        idiomDao.getRandomIdiom()
            ?.run {
                forEach {
                    result.add(toBase(it))
                }
            }
        return result
    }

    override suspend fun saveWords(list: List<BaseWord>) {
        val idioms = ArrayList<IdiomDb>()
        val words = ArrayList<WordDb>()
        val irVerbs = ArrayList<IrVerbDb>()

        list.forEach {
            when (it) {
                is BaseWord.Idiom -> idioms.add(mapToDb(it))
                is BaseWord.Word -> words.add(mapToDb(it))
                is BaseWord.IrVerb -> irVerbs.add(mapToDb(it))
            }
        }

        idiomDao.saveIdioms(idioms)
        wordDao.saveWords(words)
        irVerbDao.saveIrVerbs(irVerbs)

    }

    override suspend fun saveWord(item: BaseWord) {
        when (item) {
            is BaseWord.Idiom -> idiomDao.saveIdiom(mapToDb(item))
            is BaseWord.Word -> wordDao.saveWord(mapToDb(item))
            is BaseWord.IrVerb -> irVerbDao.saveIrVerb(mapToDb(item))
        }
    }
}


