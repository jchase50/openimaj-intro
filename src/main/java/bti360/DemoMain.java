package bti360;

import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.RGBColour;
import org.openimaj.video.Video;
import org.openimaj.video.VideoDisplay;
import org.openimaj.video.capture.VideoCapture;
import org.openimaj.video.capture.VideoCaptureException;

/**
 * Created by jonathanchase on 11/14/16.
 */
public class DemoMain {
    public static void main(String[] args) throws VideoCaptureException {
        Video<MBFImage> video = new VideoCapture(780, 520);
        VideoDisplay display = VideoDisplay.createVideoDisplay(video);
//        display.addVideoListener(new SolidFillFrameListener(RGBColour.YELLOW));
//        display.addVideoListener(new FaceDetectionListener());
//        display.addVideoListener(new TransparentColorListener(RGBColour.BLACK, 0.10f));
        display.addVideoListener(new TransparentFaceListener());
    }
}
