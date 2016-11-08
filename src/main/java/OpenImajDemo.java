import org.openimaj.image.MBFImage;
import org.openimaj.video.Video;
import org.openimaj.video.VideoDisplay;
import org.openimaj.video.VideoDisplayListener;
import org.openimaj.video.capture.VideoCapture;
import org.openimaj.video.capture.VideoCaptureException;

/**
 * Created by jonathanchase on 11/7/16.
 */
public class OpenImajDemo implements VideoDisplayListener<MBFImage> {
    private Video<MBFImage> video;
    private VideoDisplay<MBFImage> display;

    public OpenImajDemo() throws VideoCaptureException {
        video = new VideoCapture(640, 480);
        display = VideoDisplay.createVideoDisplay(video);
        display.addVideoListener(this);
    }

    @Override
    public void afterUpdate(VideoDisplay<MBFImage> display) {

    }

    @Override
    public void beforeUpdate(MBFImage frame) {

    }

    public static void main(String[] args)throws Exception{
        OpenImajDemo osliceDemo = new OpenImajDemo();

    }
}
