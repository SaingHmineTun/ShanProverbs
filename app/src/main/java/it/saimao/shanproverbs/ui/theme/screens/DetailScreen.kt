package it.saimao.shanproverbs.ui.theme.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import it.saimao.shanproverbs.R
import it.saimao.shanproverbs.data.Jsons
import it.saimao.shanproverbs.ui.theme.Namkhone
import it.saimao.shanproverbs.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    proverbKey: String, viewModel: ShanProverbViewModel, navigateBack: () -> Unit
) {
    val proverbList by viewModel.detailStateFlow.collectAsState()
    val searchText by viewModel.searchText.collectAsState()
    val searching by viewModel.searching.collectAsState()

    val clipboardManager = LocalClipboardManager.current

    fun copyToClipboard(text: String) {
        clipboardManager.setText(AnnotatedString(text))
        viewModel.showToast();
    }


    LaunchedEffect(Unit) {
        Log.d("Kham", "key - $proverbKey")
        viewModel.resetSearchText()
        viewModel.sortedProverbs(proverbKey)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { navigateBack.invoke() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Go back")
                    }
                    Text(
                        text = "${stringResource(id = R.string.shan_proverb)} - $proverbKey",
                        style = Typography.headlineSmall,
                        modifier = Modifier
                            .weight(1F)
                            .wrapContentSize(Alignment.CenterStart)
                            .padding(20.dp)
                    )
                }
            })
        }) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .padding(paddingValues),
        ) {
            OutlinedTextField(value = searchText,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                placeholder = {
                    Text(
                        text = "Searching..."
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear Filtering Text",
                        modifier = Modifier.clickable { viewModel.updateSearchText("") })
                },
                singleLine = true,
                shape = CutCornerShape(8.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                ),
                onValueChange = {
                    viewModel.updateSearchText(it)
                })


            Spacer(modifier = Modifier.height(8.dp))

            if (searching) {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }
            } else {
                LazyColumn(content = {
                    itemsIndexed(proverbList) { index, item ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp),
                            onClick = {
                                copyToClipboard(item.proverb)
                            },
                            shape = CutCornerShape(8.dp)
                        ) {
                            Text(
                                text = "${index + 1}. ${item.proverb}",
                                modifier = Modifier.padding(16.dp),
                                style = Typography.titleSmall
                            )
                        }
                    }
                })
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun DetailScreenPreview() {
    val proverbs = Jsons.getJsonData(LocalContext.current).allProverbs[0]
    DetailScreen(proverbKey = proverbs.key, viewModel = viewModel(), navigateBack = {})
}
