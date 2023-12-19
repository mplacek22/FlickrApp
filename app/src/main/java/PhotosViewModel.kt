import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class PhotosViewModel : ViewModel() {
    private val mutablePhotosLiveData = mutableStateOf(PhotosState())
    val photosState: State<PhotosState> = mutablePhotosLiveData

    fun fetchPhotos() {
        viewModelScope.launch {
            val searchResponse = WebClient.client.fetchImages()

            val photosList = searchResponse.photos.photo.map { photoResponse ->
                Photo(
                    id = photoResponse.id,
                    url = "https://farm${photoResponse.farm}.staticflickr.com/${photoResponse.server}/${photoResponse.id}_${photoResponse.secret}.jpg",
                    title = photoResponse.title
                )
            }

            mutablePhotosLiveData.value = PhotosState(
                photos = photosList,
                page = searchResponse.photos.page
            )
        }
    }
}

        data class PhotosState(
    val photos: List<Photo> = emptyList(),
    val page: Int = 0
)

