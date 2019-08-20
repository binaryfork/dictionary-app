package com.bf.dict.ui.favorites


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bf.dict.R
import com.bf.dict.TranslateApplication
import com.bf.dict.di.MainModule
import com.bf.dict.domain.repository.Translation
import com.bf.dict.ui.base.BaseFragment
import com.bf.dict.ui.favorites.adapter.TranslationAdapter
import com.bf.dict.ui.main.MainActivity
import com.jakewharton.rxbinding2.widget.RxSearchView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.favorites_fragment.*
import javax.inject.Inject


class FavoritesFragment : BaseFragment(), FavoritesView {

    var adapter = TranslationAdapter()
    lateinit var viewDisposable: Disposable

    @Inject
    lateinit var presenter: FavoritesPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TranslateApplication.get(requireContext())
                .applicationComponent
                .mainComponent(MainModule())
                .inject(this)
    }

    private fun setupSearchViewMenu() {
        favoriteAppBar.inflateMenu(R.menu.search_menu)
        val searchItem = favoriteAppBar.menu.findItem(R.id.search)
        val searchView = searchItem.actionView as SearchView
        viewDisposable = RxSearchView.queryTextChanges(searchView).observeOn(AndroidSchedulers.mainThread()).subscribe({
            presenter.searhFavorite(it.toString())
        }, {
            Log.e("Favorite", "RxSearchView " + it.message)
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.favorites_fragment, container, false)
    }

    override fun onDestroyView() {
        viewDisposable.dispose()
        presenter.unbindView()
        super.onDestroyView()
    }

    override fun renderView(screenModel: FavoritesScreenModel) {
        adapter.translations = screenModel.translations
        adapter.notifyDataSetChanged()
        favoritesEmptyView.visibility = if (screenModel.translations.isEmpty())
            View.VISIBLE else View.GONE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.bindView(this)

        val layoutManager = LinearLayoutManager(context)
        favoritesRecyclerView.layoutManager = layoutManager
        adapter.onItemClick = presenter::clickOnTranslation
        adapter.onDeleteClick = presenter::deleteTranslation

        favoritesRecyclerView.adapter = adapter

        setupSearchViewMenu()
    }

    fun onTabSelected() {
        presenter.loadTranslations()
    }

    override fun openTranslation(translation: Translation) {
        val activity = activity as MainActivity? ?: return
        activity.openMainFragment(translation)
    }
}
