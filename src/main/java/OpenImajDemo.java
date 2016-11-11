import org.openimaj.image.DisplayUtilities;
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
    float flatTransparentColor;
    private float transparentTolerance;
    private MBFImage backgroundFrame;

    public OpenImajDemo(Float[] transparentColor, Float transparentTolerance) throws VideoCaptureException {
        video = new VideoCapture(1024, 768);
        display = VideoDisplay.createVideoDisplay(video);
        display.addVideoListener(this);
        this.flatTransparentColor = flattenRGB(transparentColor);
        this.transparentTolerance = transparentTolerance;
    }

    @Override
    public void afterUpdate(VideoDisplay display) {

    }

    @Override
    public void beforeUpdate(MBFImage frame) {
        if(backgroundFrame == null){
            backgroundFrame = isBlackFrame(frame) ? null : frame.clone();
            if(backgroundFrame != null) DisplayUtilities.display(backgroundFrame);
            return;
        }
        replaceTransparentPixels(frame);
    }

    private boolean isBlackFrame(MBFImage frame) {
        return frame.flatten().max() < 0.1f;
    }

    private void replaceTransparentPixels(MBFImage frame) {
        for(int x = 0; x < frame.getWidth(); x++){
            for(int y=0; y < frame.getHeight(); y++){
                if(isTransparentPixel(frame.getPixel(x, y))){
                    frame.setPixel(x, y, backgroundFrame.getPixel(x, y));
                }
            }
        }
    }

    private boolean isTransparentPixel(Float[] pixel) {
        return isWithinRange(flattenRGB(pixel), flatTransparentColor, transparentTolerance);
    }

    private boolean isWithinRange(float eval, float target, float tolerance) {
        return target - tolerance <= eval && eval <= target + tolerance;
    }

    private float flattenRGB(Float[] rgbColor) {
        float sum = 0;
        for(int i=0; i< rgbColor.length; i++){
            sum += rgbColor[i];
        }
        return sum / rgbColor.length;
    }

    public static void main (String[] args) throws VideoCaptureException {
        OpenImajDemo sliceDemo = new OpenImajDemo(new Float[]{0f,0f,0f}, 0.10f);
    }
}
