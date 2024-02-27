package it.saimao.shanproverbs.ui.theme.screens

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import it.saimao.shanproverbs.R
import it.saimao.shanproverbs.data.AllProverbs
import it.saimao.shanproverbs.data.Jsons
import it.saimao.shanproverbs.data.Proverbs
import it.saimao.shanproverbs.shan_tools.ShanWordSorting
import it.saimao.shanproverbs.ui.theme.Namkhone
import it.saimao.shanproverbs.ui.theme.Typography
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun HomeScreen(
    viewModel: ShanProverbViewModel,
    listOfKeys: List<String> = viewModel.allProverbKeys,
    onDetailItemClick: (String) -> Unit
) {

    Column {
        Text(
            text = stringResource(id = R.string.shan_proverb),
            style = Typography.headlineSmall,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center)
                .padding(20.dp)
        )
        LazyVerticalGrid(
            modifier = Modifier.weight(1F),
            columns = GridCells.Adaptive(150.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            content = {
                items(listOfKeys.sortedWith(ShanWordSorting()), key = { it }) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onDetailItemClick.invoke(it)
                            },
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "${stringResource(id = R.string.shan_proverb)} - $it",
                            modifier = Modifier
                                .padding(vertical = 32.dp, horizontal = 16.dp)
                                .align(Alignment.CenterHorizontally),
                            style = Typography.titleSmall
                        )
                    }
                }
            })
        Spacer(modifier = Modifier.height(24.dp))
        RandomProverb(onInterval = { viewModel.getRandomProverb() })
    }
}

@Composable
fun RandomProverb(onInterval: () -> String) {

    var randomProverb by rememberSaveable { mutableStateOf("") }
    var isPlaying by rememberSaveable {
        mutableStateOf(true)
    }

    val backgroundColor by animateColorAsState(
        targetValue = if (isPlaying) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.tertiaryContainer,
        label = "Random Proverb Background Color"
    )

    LaunchedEffect(isPlaying) {
        withContext(Dispatchers.Default) {
            while (isPlaying) {
                randomProverb = onInterval()
                delay(10_000) // Wait for 1 second (1000 milliseconds)
            }
        }
    }
    Text(
        text = randomProverb,
        color = if (isPlaying) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onTertiaryContainer,
        style = Typography.titleSmall,
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(16.dp)
            .wrapContentSize(Alignment.Center)
            .clickable {
                isPlaying = !isPlaying
            }
    )
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        viewModel = viewModel(),
        Jsons.getJsonData(LocalContext.current).allProverbs.map { it.key }
    ) {}
}