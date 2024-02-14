package it.saimao.shanproverbs.ui.theme.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import it.saimao.shanproverbs.R
import it.saimao.shanproverbs.data.AllProverbs
import it.saimao.shanproverbs.data.Jsons
import it.saimao.shanproverbs.data.Proverbs
import it.saimao.shanproverbs.shan_tools.ShanWordSorting

@Composable
fun HomeScreen(listOfKeys: List<String>, onDetailItemClick: (String) -> Unit) {

    Column {
        Text(
            text = stringResource(id = R.string.shan_proverb),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center)
                .padding(20.dp)
        )

        LazyVerticalGrid(columns = GridCells.Adaptive(150.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            content = {
                items(listOfKeys.sortedWith(ShanWordSorting())) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onDetailItemClick.invoke(it)
                            },
                    ) {
                        Text(
                            text = "${stringResource(id = R.string.shan_proverb)} - $it",
                            modifier = Modifier.padding(vertical = 32.dp, horizontal = 16.dp)
                        )
                    }
                }
            })
    }

}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(Jsons.getJsonData(LocalContext.current).allProverbs.map { it.key }, {})
}