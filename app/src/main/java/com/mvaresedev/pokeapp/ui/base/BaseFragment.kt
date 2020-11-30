package com.mvaresedev.pokeapp.ui.base

import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.layout_loading.*


abstract class BaseFragment : Fragment() {

    protected fun handleLoading(show: Boolean) {
        loading_container?.isVisible = show
    }
}