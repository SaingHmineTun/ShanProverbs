package it.saimao.shanproverbs.ui.theme.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
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
import it.saimao.shanproverbs.data.Jsons
import it.saimao.shanproverbs.data.Proverb
import it.saimao.shanproverbs.data.Proverbs
import it.saimao.shanproverbs.shan_tools.ShanWordSorting
import it.saimao.shanproverbs.shan_tools.ShanWordSortingProverb

@Composable
fun DetailScreen(proverbs: Proverbs) {

    val listOfProverb: List<Proverb> = proverbs.proverbs.sortedWith(ShanWordSortingProverb())

    Column(modifier = Modifier.padding(horizontal = 8.dp)) {

        Text(
            text = "${stringResource(id = R.string.shan_proverb)} - ${proverbs.key}",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center)
                .padding(20.dp)
        )
        LazyColumn(content = {
            items(listOfProverb) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                ) {
                    Text(text = it.proverb, modifier = Modifier.padding(16.dp))
                }
            }
        })
    }
}

@Preview(showSystemUi = true)
@Composable
fun DetailScreenPreview() {
    DetailScreen(proverbs = Jsons.getJsonData(LocalContext.current).allProverbs[0])
}
