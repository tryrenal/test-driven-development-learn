package com.renaldysabdo.testdrivendevelopment.ui.fragment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.FragmentFactory
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.renaldysabdo.testdrivendevelopment.R
import com.renaldysabdo.testdrivendevelopment.adapter.ImageAdapter
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
class ImagePickFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var fragmentFactory: DefFragmentFactory

    @Before
    fun setup(){
        hiltRule.inject()
    }

    @Test
    fun clickImage_popBackStackAndSetImageUrl(){
        val navController = mock(NavController::class.java)

        val imageUrl = "test url image"

        val testViewModel = ShoppingViewModel(FakeShopRepositoryAndroidTestImpl())

        launchFragmentInHiltContainer<ImagePickFragment>(fragmentFactory = fragmentFactory) {
            Navigation.setViewNavController(requireView(), navController)
            imageAdapter.images = listOf(imageUrl)
            shopViewModel = testViewModel
        }

        onView(withId(R.id.rvImages)).perform(
                RecyclerViewActions.actionOnItemAtPosition<ImageAdapter.ImageViewHolder>(
                        0, click()
                )
        )

        verify(navController).popBackStack()
        assertThat(testViewModel.curImage.getOrAwaitValue()).isEqualTo(imageUrl)
    }
}