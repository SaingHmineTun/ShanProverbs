package it.saimao.shanproverbs.ui.theme.screens

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import it.saimao.shanproverbs.ShanProverbApplication
import it.saimao.shanproverbs.data.AllProverbs
import it.saimao.shanproverbs.data.Jsons
import it.saimao.shanproverbs.data.Proverb
import it.saimao.shanproverbs.shan_tools.ShanWordSorting
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShanProverbViewModel(private val application: Application) : ViewModel() {

    private val allProverbs: AllProverbs = Jsons.getJsonData(application.applicationContext)
    private val shanWordSorting: ShanWordSorting<Proverb> = ShanWordSorting()
    val allProverbKeys: List<String> = allProverbs.allProverbs.map { it.key }

    private var _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()
    fun updateSearchText(filterString: String) {
        _searchText.value = filterString
    }

    private var _searching = MutableStateFlow(false)
    var searching: StateFlow<Boolean> = _searching.asStateFlow()

    private val _detailStateFlow: MutableStateFlow<List<Proverb>> = MutableStateFlow(emptyList())

    @OptIn(FlowPreview::class)
    val detailStateFlow = _searchText
        .debounce(500L)
        .combine(_detailStateFlow) { text, proverbs ->
            if (text.isEmpty()) {
                proverbs
            } else {
                proverbs.filter { it.proverb.contains(text) }.sortedWith(shanWordSorting)
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _detailStateFlow.value)

    fun sortedProverbs(proverbKey: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _searching.update {
                    true
                }
                _detailStateFlow
                    .update {
                        allProverbs.allProverbs.first { it.key == proverbKey }.proverbs.sortedWith(
                            shanWordSorting
                        )
                    }
                _searching.update { false }
            }
        }

    }

    fun resetSearchText() {
        _searchText.update { "" }
    }

    fun showToast() {
        Toast.makeText(
            application.applicationContext,
            "Copied Shan Proverb Success!",
            Toast.LENGTH_LONG
        ).show()
    }

    fun getRandomProverb(): String {
        return allProverbs.allProverbs.random().proverbs.random().proverb
    }

    companion object {
        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as ShanProverbApplication
                ShanProverbViewModel(application = application)
            }
        }
    }

}