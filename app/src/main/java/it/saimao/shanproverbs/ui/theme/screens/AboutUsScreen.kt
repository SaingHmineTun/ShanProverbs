package it.saimao.shanproverbs.ui.theme.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import it.saimao.shanproverbs.R
import it.saimao.shanproverbs.ui.theme.Typography

@Composable
fun AboutUsScreen(onNavigateBack: () -> Unit) {
    LocalContext.current
    Column {
        TitleBar(onNavigateBack)
        Image(painter = painterResource(id = R.drawable.tmklogo), modifier = Modifier.fillMaxWidth(), contentScale = ContentScale.Crop, contentDescription = "TMK Logo")
        ContactUsList(modifier = Modifier.padding(horizontal = 8.dp))
    }
}

@Composable
fun ContactUsList(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Column(modifier) {
        ListItem(R.drawable.ic_gmail, "Email", "tmk.muse@gmail.com") {
            val to = "tmk.muse@gmail.com"
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
            intent.setType("message/rfc822")
            startActivity(context, Intent.createChooser(intent, "Choose an Email client :"), null)


        }
        Spacer(
            modifier = Modifier
                .height(8.dp)
                .fillMaxWidth(),
        )

        ListItem(R.drawable.ic_facebook, "Facebook", "ထုင်ႉမၢဝ်းၶမ်း") {

            val intent = try {
                Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/100377671433172"))
            } catch (e: Exception) {
                Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/100377671433172"))
            }
            startActivity(context, intent, null)

            }
        Spacer(
            modifier = Modifier
                .height(8.dp)
                .fillMaxWidth(),
        )

        ListItem(R.drawable.ic_github, "Github", "Get Source Code") {

            startActivity(
                context,
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://github.com/SaingHmineTun/ShanProverbs")
                ),
                null
            )
        }
        Spacer(
            modifier = Modifier
                .height(8.dp)
                .fillMaxWidth(),
        )

        ListItem(R.drawable.ic_playstore, "Rate this app on Play Store", "") {

            startActivity(
                context,
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=it.saimao.shanproverbs")
                ), null
            )
        }
    }

}

@Composable
fun TitleBar(onNavigateBack: () -> Unit) {


    Row(Modifier.fillMaxWidth()) {

        IconButton(
            onClick = { onNavigateBack() },
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = 20.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "About us",
                Modifier.size(32.dp)
            )
        }
        Text(
            text = stringResource(id = R.string.about_us),
            style = Typography.headlineSmall,
            modifier = Modifier
                .weight(1F)
                .padding(20.dp)
        )
    }

}

@Composable
fun ListItem(icon: Int, title: String, subTitle: String, onAction: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onAction.invoke() },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(16.dp)

        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = "Email",
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = if (subTitle.isNotEmpty()) "$title : " else title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = subTitle, style = MaterialTheme.typography.titleSmall)
        }
    }

}

@Preview(showSystemUi = true)
@Composable
fun AboutUsScreenPreview() {
    AboutUsScreen(onNavigateBack = {})
}