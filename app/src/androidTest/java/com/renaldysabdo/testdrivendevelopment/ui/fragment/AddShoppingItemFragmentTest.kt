package com.renaldysabdo.testdrivendevelopment.ui.fragment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.renaldysabdo.testdrivendevelopment.R
import com.renaldysabdo.testdrivendevelopment.data.local.ShoppingItem
import com.renaldysabdo.testdrivendevelopment.getOrAwaitValue
import com.renaldysabdo.testdrivendevelopment.launchFragmentInHiltContainer
import com.renaldysabdo.testdrivendevelopment.repositories.FakeShopRepositoryAndroidTestImpl
import com.renaldysabdo.testdrivendevelopment.ui.DefFragmentFactory
import com.renaldysabdo.testdrivendevelopment.ui.ShoppingViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltAndroidTest
@MediumTest
class AddShoppingItemFragmentTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory: DefFragmentFactory

    @Before
    fun setup(){
        hiltRule.inject()
    }

    @Test
    fun pressBackButton_navigateToShoppingFragment(){
        val navController = mock(NavController::class.java)
        var viewModel : ShoppingViewModel? = null

        launchFragmentInHiltContainer<AddShoppingItemFragment> {
            Navigation.setViewNavController(requireView(), navController)
            viewModel = this.shopViewModel
            this.shopViewModel.setCurImageUrl("image url")
        }

        pressBack()
        verify(navController).popBackStack()

        val curImage = viewModel?.curImage?.getOrAwaitValue()
        assertThat(curImage).isEmpty()
    }

    @Test
    fun pressImageButton_navigateToImagePickFragment(){
        val navController = mock(NavController::class.java)

        launchFragmentInHiltContainer<AddShoppingItemFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.ivShoppingImage)).perform(click())

        verify(navController).navigate(
            AddShoppingItemFragmentDirections.actionAddShoppingItemFragmentToImagePickFragment()
        )
    }

    @Test
    fun clickInsertIntoDB_shoppingItemInserted(){
        val testViewModel = ShoppingViewModel(FakeShopRepositoryAndroidTestImpl())

        launchFragmentInHiltContainer<AddShoppingItemFragment>(fragmentFactory = fragmentFactory) {
            shopViewModel = testViewModel
        }

        onView(withId(R.id.etShoppingItemName)).perform(replaceText("name"))
        onView(withId(R.id.etShoppingItemAmount)).perform(replaceText("5"))
        onView(withId(R.id.etShoppingItemPrice)).perform(replaceText("5.0"))
        onView(withId(R.id.btnAddShoppingItem)).perform(click())

        assertThat(testViewModel.shoppingItems.getOrAwaitValue())
            .contains(ShoppingItem("name", 5, 5.0f, ""))
    }
}