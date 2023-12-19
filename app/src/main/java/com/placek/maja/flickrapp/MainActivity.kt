package com.placek.maja.flickrapp

import Photo
import PhotosViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.placek.maja.flickrapp.ui.theme.FlickrAppTheme

class MainActivity : ComponentActivity() {
    private val photosViewModel = PhotosViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlickrAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    photosViewModel.fetchPhotos()
                    PhotoList(photos = photosViewModel.photosState.value.photos)
                }
            }
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun PhotoItem(photo: Photo) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.padding_medium))
    ) {
        Image(
            painter = rememberImagePainter(data = photo.url),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(id = R.dimen.image_height))
                .clip(MaterialTheme.shapes.medium),
            contentScale = ContentScale.Crop
        )

        Text(
            text = photo.title,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun PhotoList(photos: List<Photo>) {
    LazyColumn {
        items(photos) { photo ->
            PhotoItem(photo = photo)
        }
    }
}