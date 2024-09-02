package com.dika.moviecompose.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.dika.moviecompose.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


data class Item(val title: String, val imageRes: Int, val rating: Double)


@Composable
fun HomeScreen() {
    Scaffold(
        topBar = {
            Toolbar(
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
            val scrollState = rememberScrollState()



            Log.i("ScrollStateValue:", "current: ${scrollState.value}, max: ${scrollState.maxValue}")
            HomeUI(scrollStates = scrollState)
        }

    }


}

@Composable
fun HomeUI(scrollStates : ScrollState){
    val images = listOf(
        ImageData(R.drawable.ic_star_popular, "Image 1", 4.5),
        ImageData(R.drawable.ic_star_popular, "Image 2", 3.8),
        ImageData(R.drawable.ic_star_popular, "Image 3", 4.2),
        ImageData(R.drawable.ic_star_popular, "Image 4", 5.0),
        ImageData(R.drawable.ic_star_popular, "Image 5", 3.6)
    )

    val items = listOf(
        Item("Item 1", R.drawable.ic_star_popular, 4.5),
        Item("Item 2", R.drawable.ic_star_popular, 3.8),
        Item("Item 3", R.drawable.ic_star_popular, 4.2),
        Item("Item 4", R.drawable.ic_star_popular, 5.0),
        Item("Item 5", R.drawable.ic_star_popular, 3.6)
    )


    val sampleItems = listOf(
        ItemMenu(
            imageUrl = R.drawable.ic_star_popular,
            title = "Movies"
        ),
        ItemMenu(
            imageUrl = R.drawable.ic_star_popular,
            title = "m.food"
        ),
        ItemMenu(
            imageUrl = R.drawable.ic_star_popular,
            title = "Cinema"
        ),
        ItemMenu(
            imageUrl = R.drawable.ic_star_popular,
            title = "Private\nBooking"
        )
    )


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = scrollStates)


    ) {
        Spacer(modifier = Modifier.height(16.dp))


        Text(text = "Afternoon, Deditian",
            fontSize = 16.sp,
             style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
             modifier = Modifier.padding(horizontal = 16.dp))

        Spacer(modifier = Modifier.height(16.dp))


        MenuIcon(sampleItems)


        Spacer(modifier = Modifier.height(16.dp))


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

        Spacer(modifier = Modifier.height(5.dp))

        AutoImageSlider(
            images = images,
            onImageClick = { clickedImageIndex ->
                println("Image clicked: Index $clickedImageIndex")
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(modifier = Modifier
            .weight(1f)) {
            GridItemList(
                items = items,
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
                modifier = Modifier
                    .padding(8.dp)
                    .width(70.dp)
                    .height(100.dp),
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(shape = MaterialTheme.shapes.medium)
                    ) {
                        Image(
                            painter = painterResource(id = item.imageUrl),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp)
                        )
                    }
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


@Composable
fun Toolbar(
    cityName: String,
    onLogoClick: () -> Unit,
    onSearchClick: () -> Unit
) {
    TopAppBar(
        title = {

        },
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
            // Logo aplikasi di sebelah kiri
            IconButton(onClick = onLogoClick) {
                Image(
                    painter = painterResource(id = R.drawable.ic_star_popular), // Ganti dengan logo aplikasi kamu
                    contentDescription = "App Logo"
                )
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}


data class ImageData(val imageRes: Int, val title: String, val rating: Double)


@OptIn(ExperimentalPagerApi::class)
@Composable
fun AutoImageSlider(images: List<ImageData>, onImageClick: (Int) -> Unit) {
    val pagerState = com.google.accompanist.pager.rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            coroutineScope.launch {
                val nextPage = (pagerState.currentPage + 1) % images.size
                pagerState.animateScrollToPage(nextPage)
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        // Pager for images
        HorizontalPager(
            count = images.size,
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) { page ->
            Image(
                painter = painterResource(id = images[page].imageRes),
                contentDescription = "Image $page",
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { onImageClick(page) },
                contentScale = ContentScale.Crop
            )
        }

        // Title and Rating
        val currentImage = images[pagerState.currentPage]
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(text = currentImage.title, style = MaterialTheme.typography.bodyLarge)
            Text(text = "Rating: ${currentImage.rating}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun GridItemList(items: List<Item>, onItemClick: (Item) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize() // Menggunakan fillMaxSize agar grid bisa menyesuaikan tinggi yang tersedia
    ) {
        items(items) { item ->
            GridItem(item = item, onClick = { onItemClick(item) })
        }
    }
}

@Composable
fun GridItem(item: Item, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = item.imageRes),
            contentDescription = item.title,
            modifier = Modifier
                .size(120.dp)
                .padding(bottom = 8.dp),
            contentScale = ContentScale.Crop
        )
        Text(text = item.title)
        Text(text = "Rating: ${item.rating}")
    }
}
