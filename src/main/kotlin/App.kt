import kotlinx.browser.window
import kotlinx.coroutines.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import react.FC
import react.Props
import react.dom.html.ReactHTML.h3
import react.useEffectOnce
import react.useState

val mainScope = MainScope()

val App = FC<Props> {
    var currentVideo: Video? by useState(null)
    var unwatchedVideos: List<Video> by useState(emptyList())
    var watchedVideos: List<Video> by useState(emptyList())

    useEffectOnce {
        mainScope.launch {
            unwatchedVideos = fetchVideos()
        }
    }

    val setCurrentVideo: (Video) -> Unit = { video -> currentVideo = video }

    h3 {
        +"Videos to watch"
    }
    VideoList {
        videos = unwatchedVideos
        selectedVideo = currentVideo
        onSelectVideo = setCurrentVideo
    }
    h3 {
        +"Videos watched"
    }
    VideoList {
        videos = watchedVideos
        selectedVideo = currentVideo
        onSelectVideo = setCurrentVideo
    }
    currentVideo?.let { curr ->
        VideoPlayer {
            video = curr
            unWatchedVideo = curr in unwatchedVideos
            onWatchedButtonPressed = {
                if (it in unwatchedVideos) {
                    watchedVideos = watchedVideos + it
                    unwatchedVideos = unwatchedVideos - it
                } else {
                    watchedVideos = watchedVideos - it
                    unwatchedVideos = unwatchedVideos + it
                }
            }
        }
    }
}

suspend fun fetchVideos(): List<Video> = coroutineScope {
    (1..25).map { id ->
        async {
            fetchVideo(id)
        }
    }.awaitAll()
}

suspend fun fetchVideo(id: Int): Video {
    val response = window
        .fetch("https://my-json-server.typicode.com/kotlin-hands-on/kotlinconf-json/videos/$id")
        .await()
        .text()
        .await()
    return Json.decodeFromString(response)
}
