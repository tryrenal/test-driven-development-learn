package com.renaldysabdo.testdrivendevelopment.ui.fragment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.renaldysabdo.testdrivendevelopment.R
import com.renaldysabdo.testdrivendevelopment.getOrAwaitValue
import com.renaldysabdo.testdrivendevelopment.launchFragmentInHiltContainer
import com.renaldysabdo.testdrivendevelopment.ui.ShoppingViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@ExperimentalCoroutinesApi
@HiltAndroidTest
@MediumTest
class AddShoppingItemFragmentTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

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
}