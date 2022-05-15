import react.FC
import react.Props
import react.dom.html.ReactHTML.h3
import react.useState


val App = FC<Props> {
    var currentVideo: Video? by useState(null)
    var unwatchedVideos: List<Video> by useState(
        listOf(
            Video(1, "Opening Keynote", "Andrey Breslav", "https://youtu.be/PsaFVLr8t4E"),
            Video(2, "Dissecting the stdlib", "Huyen Tue Dao", "https://youtu.be/Fzt_9I733Yg"),
            Video(3, "Kotlin and Spring Boot", "Nicolas Frankel", "https://youtu.be/pSiZVAeReeg")
        )
    )
    var watchedVideos: List<Video> by useState(
        listOf(
            Video(4, "Creating Internal DSLs in Kotlin", "Venkat Subramaniam", "https://youtu.be/JzTeAM8N1-o")
        )
    )

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
