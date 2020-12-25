package com.renaldysabdo.testdrivendevelopment.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.renaldysabdo.testdrivendevelopment.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
//untuk memberitahu junit bahwa test yang dilakukan adalah instrument test (emulator)
@RunWith(AndroidJUnit4::class)
//optional untuk menambahkan ini (small, medium, large)
@SmallTest
class ShoppingDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database : ShoppingItemDatabase
    private lateinit var dao : ShoppingDao

    //akan dieksekusi sebelum semua test dieksekusi
    @Before
    fun setup(){
        //inMemoryDatabase = membuat database untuk menyimpan data hanya pada test case
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ShoppingItemDatabase::class.java
        ).allowMainThreadQueries()
            .build()
        dao = database.shoppingDao()
    }

    //akan dieksekusi setelah semua test dieksekusi
    @After
    fun tearDown(){
        database.close()
    }

    //runBlockingTest = untuk mendelay dan membuat hasil berjalan pada satu thread
    @Test
    fun insertShoppingItem() = runBlockingTest {
        val shoppingItem = ShoppingItem(
            "name", 1, 1f, "url", id = 1
        )

        dao.insertShoppingItem(shoppingItem)

        //getOrAwaitValue digunakan untuk menunggu live data sampai mengembalikan hasil
        val allObserveItem = dao.observeAllShoppingItems().getOrAwaitValue()

        assertThat(allObserveItem).contains(shoppingItem)
    }

    @Test
    fun deleteShoppingItem() = runBlockingTest {
        val shoppingItem = ShoppingItem(
            "name", 1, 1f, "url", id = 1
        )
        dao.insertShoppingItem(shoppingItem)

        dao.deleteShoppingItem(shoppingItem)
        val allObserveItem = dao.observeAllShoppingItems().getOrAwaitValue()
        assertThat(allObserveItem).doesNotContain(shoppingItem)
    }

    @Test
    fun observeTotalPrice() = runBlockingTest {
        val shoppingItem1 = ShoppingItem("alpha", 1, 9f, "url", id = 1)
        val shoppingItem2 = ShoppingItem("beta", 20, 1000f, "url", id = 2)
        val shoppingItem3 = ShoppingItem("charlie", 0, 4000f, "url", id = 3)

        dao.insertShoppingItem(shoppingItem1)
        dao.insertShoppingItem(shoppingItem2)
        dao.insertShoppingItem(shoppingItem3)

        val total = dao.observeTotalPrice().getOrAwaitValue()

        assertThat(total).isEqualTo(1 * 9f + 20 * 1000f)

    }
}