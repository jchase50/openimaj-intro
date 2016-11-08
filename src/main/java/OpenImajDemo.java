import org.openimaj.image.MBFImage;
import org.openimaj.video.Video;
import org.openimaj.video.VideoDisplay;
import org.openimaj.video.capture.VideoCapture;
import org.openimaj.video.capture.VideoCaptureException;

/**
 * Created by jonathanchase on 11/7/16.
 */
public class OpenImajDemo {

    public static void main (String[] args) throws VideoCaptureException {
        Video<MBFImage> video = new VideoCapture(640, 480);
        VideoDisplay display = VideoDisplay.createVideoDisplay(video);
    }
}
