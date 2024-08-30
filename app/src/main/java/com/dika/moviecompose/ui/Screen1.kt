package com.dika.moviecompose.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dika.moviecompose.R

data class Item(val title: String, val imageRes: Int, val rating: Double)

@Composable
fun Screen1() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        val items = listOf(
            Item("Item 1", R.drawable.ic_star_popular, 4.5),
            Item("Item 2", R.drawable.ic_star_popular, 3.8),
            Item("Item 3", R.drawable.ic_star_popular, 4.2),
            Item("Item 4", R.drawable.ic_star_popular, 5.0),
            Item("Item 2", R.drawable.ic_star_popular, 3.8),
            Item("Item 3", R.drawable.ic_star_popular, 4.2),
            Item("Item 4", R.drawable.ic_star_popular, 5.0),
            Item("Item 3", R.drawable.ic_star_popular, 4.2),
            Item("Item 4", R.drawable.ic_star_popular, 5.0),
            Item("Item 2", R.drawable.ic_star_popular, 3.8),
            Item("Item 3", R.drawable.ic_star_popular, 4.2),
            Item("Item 4", R.drawable.ic_star_popular, 5.0),
            Item("Item 5", R.drawable.ic_star_popular, 3.6)
        )
        GridItemList(
            items = items,
            onItemClick = { selectedItem ->
                // Aksi ketika item diklik
                println("Item clicked: ${selectedItem.title}")
            }
        )
    }
}



@Composable
fun GridItemList(items: List<Item>, onItemClick: (Item) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize()
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
            .padding(16.dp)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = item.imageRes),
            contentDescription = item.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(120.dp)
                .padding(bottom = 8.dp)
        )

        Text(text = item.title)
        Text(text = "Rating: ${item.rating}")
    }
}
