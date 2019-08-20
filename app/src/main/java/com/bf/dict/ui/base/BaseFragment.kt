package com.bf.dict.ui.base

import android.widget.Toast

import androidx.fragment.app.Fragment

import com.bf.dict.R


open class BaseFragment : Fragment(), BaseView {

    override fun showError(errorMessage: String) {
        Toast.makeText(context, errorMessage,
                Toast.LENGTH_SHORT).show()
    }

    override fun showInternetConnectionError() {
        Toast.makeText(context, resources.getString(R.string.error_description_no_internet),
                Toast.LENGTH_SHORT).show()
    }

}
