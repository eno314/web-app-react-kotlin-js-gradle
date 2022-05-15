import csstype.Display
import csstype.NamedColor
import csstype.Position
import csstype.px
import react.FC
import react.Props
import react.css.css
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h3

external interface VideoPlayerProps : Props {
    var video: Video
    var onWatchedButtonPressed: (Video) -> Unit
    var unWatchedVideo: Boolean
}

val VideoPlayer = FC<VideoPlayerProps> { props ->
    div {
        css {
            position = Position.absolute
            top = 10.px
            right = 10.px
        }
        h3 {
            +"${props.video.speaker}: ${props.video.title}"
        }
        button {
            css {
                display = Display.block
                backgroundColor = if (props.unWatchedVideo) {
                    NamedColor.lightgreen
                } else {
                    NamedColor.red
                }
            }
            onClick = {
                props.onWatchedButtonPressed(props.video)
            }
            if (props.unWatchedVideo) {
                +"Mark as watched"
            } else {
                +"Mark as unwatched"
            }
        }
        div {
            css {
                display = Display.flex
                marginBottom = 10.px
            }
            EmailShareButton {
                url = props.video.videoUrl
                EmailIcon {
                    size = 32
                    round = true
                }
            }
            TelegramShareButton {
                url = props.video.videoUrl
                TelegramIcon {
                    size = 32
                    round = true
                }
            }
        }
        ReactPlayer {
            url = props.video.videoUrl
        }
    }
}
