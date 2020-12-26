package com.renaldysabdo.testdrivendevelopment.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.renaldysabdo.testdrivendevelopment.MainCoroutineRule
import com.renaldysabdo.testdrivendevelopment.getOrAwaitValueTest
import com.renaldysabdo.testdrivendevelopment.other.Constanta
import com.renaldysabdo.testdrivendevelopment.other.Status
import com.renaldysabdo.testdrivendevelopment.repositories.FakeShopRepositoryImp
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class ShoppingViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel : ShoppingViewModel

    @Before
    fun setup(){
        viewModel = ShoppingViewModel(FakeShopRepositoryImp())
    }

    //create shopping item model - viewModel

    @Test
    fun `insert with empty field, return false`(){
        viewModel.createShoppingItem("name", "", "3.0")

        val value = viewModel.shoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert with long name, return false`(){

        //membuat string dengan konstanta panjang maksimul dari nama ditambah 1
        val name = buildString {
             for (i in 1..Constanta.MAX_NAME_LENGTH + 1){
                 append(1)
             }
        }

        viewModel.createShoppingItem(name, "5", "4.0")

        val value = viewModel.shoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert with long price, return false`(){
        val price = buildString {
            for (i in 1..Constanta.MAX_PRICE_LENGTH + 1){
                append(1)
            }
        }
        viewModel.createShoppingItem("name", "1" , price)
        val value = viewModel.shoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert with long amount,return false`(){

        viewModel.createShoppingItem("name", "99999999999999", "4.0")

        val value = viewModel.shoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert with valid, return true`(){
        viewModel.createShoppingItem("name", "1", "1.0")
        val value = viewModel.shoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
    }

    //search image - viewModel

    @Test
    fun `search image with empty field, return false`(){
        viewModel.searchImageQuery("")

        val value = viewModel.images.getOrAwaitValueTest()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `search image valid`(){
        viewModel.searchImageQuery("banana")

        val value = viewModel.images.getOrAwaitValueTest()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
    }

    //set current image - viewModel

    @Test
    fun `set current image with empty, return empty`(){
        viewModel.setCurImageUrl("")

        val value = viewModel.curImage.getOrAwaitValueTest()
        assertThat(value).isEmpty()
    }

    @Test
    fun `set current image with url image, return url`(){
        val urlImage = "https://www.google.com/url?sa=i&url=https%3A%2F%2Fsproutsocial.com%2Finsights%2Fsocial-media-image-sizes-guide%2F&psig=AOvVaw1foxMWlWTlGFRovwhWZ6s5&ust=1609049254776000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCND829396u0CFQAAAAAdAAAAABAD"
        viewModel.setCurImageUrl(urlImage)

        val value = viewModel.curImage.getOrAwaitValueTest()

        assertThat(value).isEqualTo(urlImage)
    }
}