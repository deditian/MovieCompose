package com.dika.moviecompose.ui.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.dika.moviecompose.R
import com.dika.moviecompose.util.formatDate
import com.dika.moviecompose.util.textMax
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.tian.core.cons.IMG_URL
import com.tian.core.model.Item
import com.tian.core.model.TvShow
import com.dika.moviecompose.ui.network.ApiResultHandler
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeChild
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch



@Composable
fun HomeScreen(viewModel: HomeViewModel) {

    LaunchedEffect(Unit) {
        viewModel.getMovieNowPlaying()
        viewModel.getTvPopular()
    }

    Scaffold(
        topBar = {
            ToolbarMe(
                cityName = "Jakarta",
                onLogoClick = {
                    println("App logo clicked!")
                },
                onSearchClick = {
                    println("Search icon clicked!")
                }
            )
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            color = MaterialTheme.colorScheme.background
        ) {
            HomeUI(viewModel)
        }

    }


}

@Composable
fun HomeUI(viewModel: HomeViewModel){

    val images = listOf(
        ImageData(R.drawable.ic_star_popular, "Image 1", 4.5),
        ImageData(R.drawable.ic_star_popular, "Image 2", 3.8),
        ImageData(R.drawable.ic_star_popular, "Image 3", 4.2),
        ImageData(R.drawable.ic_star_popular, "Image 4", 5.0),
        ImageData(R.drawable.ic_star_popular, "Image 5", 3.6)
    )

    val items = listOf(
        Item("Item 1", R.drawable.ic_image, 4.5),
        Item("Item 2", R.drawable.ic_image, 3.8),
        Item("Item 3", R.drawable.ic_image, 4.2),
        Item("Item 4", R.drawable.ic_image, 5.0),
        Item("Item 5", R.drawable.ic_image, 3.6)
    )


    val sampleItems = listOf(
        ItemMenu(
            imageUrl = R.drawable.ic_movie,
            title = "Movies"
        ),
        ItemMenu(
            imageUrl = R.drawable.ic_food,
            title = "m.food"
        ),
        ItemMenu(
            imageUrl = R.drawable.ic_cinema,
            title = "Cinema"
        ),
        ItemMenu(
            imageUrl = R.drawable.ic_booking,
            title = "Private\nBooking"
        )
    )


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState())
    ) {

        Spacer(modifier = Modifier.height(5.dp))

        Text(text = "Afternoon, Deditian",
            fontSize = 16.sp,
             style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
             modifier = Modifier.padding(horizontal = 16.dp))

        Spacer(modifier = Modifier.height(16.dp))


        MenuIcon(sampleItems)




        AutoImageSlider(
            viewModel = viewModel,
            onImageClick = { clickedImageIndex ->
                println("Image clicked: Index $clickedImageIndex")
            }
        )



        Spacer(modifier = Modifier.height(16.dp))

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(230.dp)) {
            GridItemList(
                viewModel,
                onItemClick = { selectedItem ->
                    println("Item clicked: ${selectedItem.title}")
                }
            )
        }


    }
}



data class ItemMenu(val imageUrl: Int, val title: String)

@Composable
fun MenuIcon(itemMenu: List<ItemMenu>) {
    LazyRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(itemMenu) { item ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .height(115.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(Color.LightGray, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = item.imageUrl),
                        contentDescription = null,
                        modifier = Modifier.size(30.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = item.title,
                        fontSize = 13.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolbarMe(
    cityName: String,
    onLogoClick: () -> Unit,
    onSearchClick: () -> Unit
) {
    val hazeState = remember { HazeState() }
    TopAppBar(
            title = {

            },
            colors = TopAppBarDefaults.largeTopAppBarColors(Color.Transparent),
            modifier = Modifier
                .fillMaxWidth()
                .hazeChild(state = hazeState),

            actions = {
                Text(
                    text = cityName,
                    modifier = Modifier.padding(end = 16.dp),
                    style = MaterialTheme.typography.bodyMedium
                )

                IconButton(onClick = onSearchClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "Search"
                    )
                }

            },
            navigationIcon = {
                IconButton(onClick = onLogoClick) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_star_popular), // Ganti dengan logo aplikasi kamu
                        contentDescription = "App Logo"
                    )
                }
            },
        )

}


data class ImageData(val imageRes: Int, val title: String, val rating: Double)


@OptIn(ExperimentalPagerApi::class)
@Composable
fun AutoImageSlider(viewModel: HomeViewModel, onImageClick: (Int) -> Unit) {
    val pagerState = com.google.accompanist.pager.rememberPagerState()
    val coroutineScope = rememberCoroutineScope()



    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Now Playing",
                fontSize = 16.sp,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Text(
                text = "See All",
                fontSize = 12.sp,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))



        val movieResponse by viewModel.response.observeAsState()
        movieResponse?.let { response ->
            ApiResultHandler(
                result = response,
                onLoading = {
                    CircularProgressIndicator()
                },
                onSuccess = {
                    LaunchedEffect(Unit) {
                        while (true) {
                            delay(3000)
                            coroutineScope.launch {
                                val nextPage = (pagerState.currentPage + 1) % it!!.results.size
                                pagerState.animateScrollToPage(nextPage)
                            }
                        }
                    }

                    val result = it?.results
                    Log.e("TAG", "HomeScreen hasil:  ${it?.results}",)
                    // Pager for images
                    HorizontalPager(
                        count = it!!.results.size,
                        state = pagerState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    ) { page ->
                        AsyncImage(
                            model = "${IMG_URL}${result?.get(page)?.backdrop_path}",
                            contentDescription = result?.get(page)?.original_title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                                .clickable { onImageClick(page) },
                            contentScale = ContentScale.FillWidth
                        )
                    }

                    // Title and Rating
                    val currentImage = result?.get(pagerState.currentPage)
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(top = 10.dp)
                    ) {
                        Text(
                            text = currentImage!!.title,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        RatingStars(voteAverage = currentImage.vote_average)
                        Text(
                            text = "Release Date: ${formatDate(currentImage.release_date)}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                },
                onFailure = {
                    Log.e("TAG", "HomeScreen failed:  ${it?.status_message}",)
                }
            )
        }


    }
}

@Composable
fun GridItemList(viewModel: HomeViewModel, onItemClick: (TvShow) -> Unit) {
    Column {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Coming soon to XX",
                fontSize = 16.sp,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Text(
                text = "See All",
                fontSize = 12.sp,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(2.dp))


        val tvResponse by viewModel.responseTvPopular.observeAsState()
        tvResponse?.let { response ->
            ApiResultHandler(
                result = response,
                onLoading = {
                    CircularProgressIndicator()
                },
                onSuccess = {
                    Log.e("TAG", "GridItemList: ${it?.results}", )

                    LazyHorizontalGrid(
                        rows = GridCells.Fixed(1),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(it!!.results) { item ->
                            GridItem(
                                item = item,
                                onClick = { onItemClick(item) })
                        }
                    }
                },
                onFailure = {

                }
            )
        }

    }
}

@Composable
fun GridItem(item: TvShow, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = "${IMG_URL}${item.posterPath}",
            contentDescription = item.title,
            modifier = Modifier
                .size(120.dp)
                .padding(bottom = 8.dp),
            contentScale = ContentScale.FillWidth
        )
        Log.e("TAG", "GridItem:COYY ${item.title}", )
        Text(text = textMax(item.title!!), style = MaterialTheme.typography.bodyLarge)
        RatingStars(voteAverage = item.voteAverage!!)
    }
}

@Composable
fun RatingStars(voteAverage: Float) {
    val maxStars = 5
    val filledStars = (voteAverage / 2).toInt() // Kalkulasi bintang yang diisi
    val remainingStars = maxStars - filledStars // Sisa bintang yang tidak diisi

    Row(
        modifier = Modifier.padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(filledStars) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = "Filled Star",
                tint = Color.Cyan,
                modifier = Modifier.size(24.dp)
            )
        }

        repeat(remainingStars) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = "Empty Star",
                tint = Color.Gray,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
