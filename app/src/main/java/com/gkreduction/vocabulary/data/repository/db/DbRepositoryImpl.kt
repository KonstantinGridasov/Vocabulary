package com.gkreduction.vocabulary.data.repository.db

import com.gkreduction.vocabulary.data.db.dao.IdiomDao
import com.gkreduction.vocabulary.data.db.dao.IrVerbDao
import com.gkreduction.vocabulary.data.db.dao.WordDao
import com.gkreduction.vocabulary.data.db.entity.IdiomDb
import com.gkreduction.vocabulary.data.db.entity.IrVerbDb
import com.gkreduction.vocabulary.data.db.entity.WordDb
import com.gkreduction.vocabulary.data.utils.mappers.mapToDb
import com.gkreduction.vocabulary.data.utils.mappers.toBase
import com.gkreduction.vocabulary.presentation.entity.BaseWord
import com.gkreduction.vocabulary.presentation.entity.Settings

class DbRepositoryImpl(
    var idiomDao: IdiomDao,
    var wordDao: WordDao,
    var irVerbDao: IrVerbDao
) : DbRepository {

    override suspend fun getFullDb(): List<BaseWord> {
        val result = ArrayList<BaseWord>()
        result.addAll(getWords())
        result.addAll(getIrregularVerb())
        result.addAll(getIdioms())
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

    override suspend fun getBaseWordExam(settings: Settings): BaseWord? {
        return when (getListSettings(settings).random()) {
            0 -> getRandomWord()
            1 -> getRandomIrVerb()
            2 -> getRandomIdiom()
            else -> getRandomWord()

        }
    }

    override suspend fun getWords(): List<BaseWord> {
        val result = ArrayList<BaseWord>()

        wordDao.getRandomListWords()
            ?.run {
                forEach {
                    result.add(toBase(it))
                }
            }

        return result
    }

    override suspend fun getIrregularVerb(): List<BaseWord> {
        val result = ArrayList<BaseWord>()

        irVerbDao.getRandomListIrVerbs()
            ?.run {
                forEach {
                    result.add(toBase(it))
                }
            }
        return result
    }

    override suspend fun getIdioms(): List<BaseWord> {
        val result = ArrayList<BaseWord>()
        idiomDao.getRandomListIdioms()
            ?.run {
                forEach {
                    result.add(toBase(it))
                }
            }
        return result
    }

    private fun getListSettings(settings: Settings): ArrayList<Int> {
        val list = ArrayList<Int>()
        if (settings.isWord)
            list.add(0)
        if (settings.isIrVerbs)
            list.add(1)
        if (settings.isIdiom)
            list.add(2)

        if (list.isEmpty())
            list.add(0)
        return list
    }

    private suspend fun getRandomIdiom(): BaseWord? {
        return idiomDao.getRandomIdiom()?.let {
            toBase(it)
        }
    }

    private suspend fun getRandomWord(): BaseWord? {
        return wordDao.getRandomWord()?.let {
            toBase(it)
        }
    }

    private suspend fun getRandomIrVerb(): BaseWord? {
        return irVerbDao.getRandomIrVerb()?.let {
            toBase(it)
        }
    }
}


