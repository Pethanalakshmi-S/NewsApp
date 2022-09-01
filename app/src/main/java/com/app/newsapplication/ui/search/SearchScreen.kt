package com.app.newsapplication.ui.search

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.app.newsapplication.data.localdata.NewsData
import com.app.newsapplication.data.model.NewsDataDetails
import com.app.newsapplication.noRippleClickable
import com.app.newsapplication.ui.home.NewsListStateManagment
import com.app.newsapplication.ui.navigation.Screen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach


@ExperimentalCoilApi
@Composable
fun SearchScreen(
    state: NewsListStateManagment.State,
    effectFlow: Flow<NewsListStateManagment.Effect>?,
    navController: NavController,
    viewModel: SearchViewModel
){
    val scaffoldState: ScaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = effectFlow, block = {
        effectFlow?.onEach { effect ->
            if (effect is NewsListStateManagment.Effect.DataWasLoaded)
                scaffoldState.snackbarHostState.showSnackbar(
                    message = "News data are loaded.",
                    duration = SnackbarDuration.Short
                )
        }?.collect()
    })

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            SearchBar(navController, viewModel)
        },
    ) {
        Box {
           NewsList(newsData = state.newsList, navController)
            if (state.isLoading)
               LoadingBar()
        }
    }
}

@Composable
private fun SearchBar(navController: NavController, viewModel: SearchViewModel) {
    var textState = remember{ mutableStateOf("")}
    Row() {
        TextField(
            value = textState.value,
            onValueChange = {
                textState.value = it
                viewModel.loadSearchedNews(it.trim())
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = "Search News")}
        )
    }
}


@Composable
fun NewsList(
    newsData: List<NewsData>,
    navController: NavController
) {
    LazyColumn(
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        items(newsData) { news ->
            NewsListRow(item = news, itemShouldExpand = true, navController = navController)
        }
    }
}

@Composable
fun NewsListRow(
    item: NewsData,
    itemShouldExpand: Boolean = false,
    iconTransformationBuilder: ImageRequest.Builder.() -> Unit = { },
    navController: NavController
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            .clickable {
                val url = NewsDataDetails(item.url)
                navController.currentBackStackEntry?.savedStateHandle?.set("news", url)
                navController.navigate(Screen.NewsDetail.route)
            }
    ) {
        var expanded by rememberSaveable { mutableStateOf(false) }
        Row(modifier = Modifier.animateContentSize()) {
            Box(modifier = Modifier.align(alignment = Alignment.CenterVertically)) {
                NewsThumbnail(item.imageUrl.toString(), iconTransformationBuilder)
            }
            NewsDetails(
                item = item,
                expandedLines = if (expanded) 10 else 2,
                modifier = Modifier
                    .padding(
                        start = 10.dp,
                        end = 8.dp,
                        top = 10.dp,
                        bottom = 10.dp
                    )
                    .fillMaxWidth(0.80f)
                    .align(Alignment.CenterVertically)
            )
            if (itemShouldExpand)
                Box(
                    modifier = Modifier
                        .align(if (expanded) Alignment.Bottom else Alignment.CenterVertically)
                        .noRippleClickable { expanded = !expanded }
                ) {
                    ExpandableContentIcon(expanded)
                }
        }
    }
}

@Composable
private fun ExpandableContentIcon(expanded: Boolean) {
    Icon(
        imageVector = if (expanded)
            Icons.Filled.KeyboardArrowUp
        else
            Icons.Filled.KeyboardArrowDown,
        contentDescription = "Expand row icon",
        modifier = Modifier
            .padding(all = 16.dp).size(20.dp)
    )
}

@Composable
fun NewsDetails(
    item: NewsData?,
    expandedLines: Int,
    modifier: Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = item?.title.toString().trim(),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.subtitle1,
            // maxLines = 2,
            //overflow = TextOverflow.Ellipsis
        )
        if (item?.content?.trim()?.isNotEmpty() == true)
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    text = item.content.toString().trim(),
                    textAlign = TextAlign.Start,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.caption,
                    maxLines = expandedLines
                )
            }
    }
}

@Composable
fun NewsThumbnail(
    thumbnailUrl: String,
    iconTransformationBuilder: ImageRequest.Builder.() -> Unit
) {
    Image(
        painter = rememberImagePainter(
            data = thumbnailUrl,
            builder = iconTransformationBuilder
        ),
        modifier = Modifier
            .size(120.dp, 120.dp)
            .padding(start = 10.dp, top = 10.dp, bottom = 10.dp),
        contentDescription = null,
        contentScale = ContentScale.FillBounds
    )
}

@Composable
fun LoadingBar() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}

