package com.renaldysabdo.testdrivendevelopment.ui.fragment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.renaldysabdo.testdrivendevelopment.R
import com.renaldysabdo.testdrivendevelopment.adapter.ShoppingItemAdapter
import com.renaldysabdo.testdrivendevelopment.data.local.ShoppingItem
import com.renaldysabdo.testdrivendevelopment.getOrAwaitValue
import com.renaldysabdo.testdrivendevelopment.launchFragmentInHiltContainer
import com.renaldysabdo.testdrivendevelopment.ui.DefFragmentFactoryTest
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
class ShoppingFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var fragmentFactoryTest : DefFragmentFactoryTest

    @Before
    fun setup(){
        hiltRule.inject()
    }

    @Test
    fun clickAddShoppingItemButton_navigationToAddShoppingItemFragment(){
        //object memiliki semua fungsi dalam class navController tanpa perlu implemntasi - mock
        val navController = mock(NavController::class.java)

        launchFragmentInHiltContainer<ShoppingFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.fabAddShoppingItem)).perform(click())

        verify(navController).navigate(
            ShoppingFragmentDirections.actionShoppingFragmentToAddShoppingItemFragment()
        )
    }

    @Test
    fun swipeShoppingItem_deleteItem(){
        val shoppingItem = ShoppingItem("name", 1, 1f, "", id = 1)
        var testViewModel: ShoppingViewModel? = null

        launchFragmentInHiltContainer<ShoppingFragment>(fragmentFactory = fragmentFactoryTest) {
            testViewModel = shopViewModel
            shopViewModel?.insertShoppingItemToDb(shoppingItem)
        }

        onView(withId(R.id.rvShoppingItems)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ShoppingItemAdapter.ShoppingItemViewHolder>(
                0, swipeLeft()
            )
        )

        assertThat(testViewModel?.shoppingItems?.getOrAwaitValue()).isEmpty()
    }
}