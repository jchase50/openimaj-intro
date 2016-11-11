import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.FImage;
import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.RGBColour;
import org.openimaj.image.colour.Transforms;
import org.openimaj.image.processing.edges.CannyEdgeDetector;
import org.openimaj.image.processing.face.detection.DetectedFace;
import org.openimaj.image.processing.face.detection.FaceDetector;
import org.openimaj.image.processing.face.detection.HaarCascadeDetector;
import org.openimaj.video.Video;
import org.openimaj.video.VideoDisplay;
import org.openimaj.video.VideoDisplayListener;
import org.openimaj.video.capture.VideoCapture;
import org.openimaj.video.capture.VideoCaptureException;

import java.util.List;

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
        video = new VideoCapture(640, 480);
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
//        doTransparentPixels(frame);
//        doEdgeDetection(frame);
//        doFaceDetection(frame);
        doTransparentFaces(frame);
    }

    private void doTransparentFaces(MBFImage frame) {
        if(backgroundFrame == null){
            backgroundFrame = frame.clone();
            DisplayUtilities.display(backgroundFrame);
            return;
        }

        FaceDetector<DetectedFace,FImage> fd = new HaarCascadeDetector(40);
        List<DetectedFace> faces = fd.detectFaces(Transforms.calculateIntensity(frame));
        for( DetectedFace face : faces ) {
            MBFImage bgForFaceRegion = backgroundFrame.extractROI(face.getBounds());
            frame.overlayInplace(bgForFaceRegion, (int) face.getBounds().minX(), (int) face.getBounds().minY());
            frame.drawShape(face.getBounds(), RGBColour.RED);
        }
    }

    private void doFaceDetection(MBFImage frame) {
        FaceDetector<DetectedFace,FImage> fd = new HaarCascadeDetector(40);
        List<DetectedFace> faces = fd.detectFaces(Transforms.calculateIntensity(frame));
        for( DetectedFace face : faces ) {
            frame.drawShape(face.getBounds(), RGBColour.RED);
        }
    }

    private void doEdgeDetection(MBFImage frame) {
        frame.processInplace(new CannyEdgeDetector());
    }

    private void doTransparentPixels(MBFImage frame){
        if(backgroundFrame == null){
            backgroundFrame = frame.clone();
            DisplayUtilities.display(backgroundFrame);
            return;
        }
        replaceTransparentPixels(frame);
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
        OpenImajDemo sliceDemo = new OpenImajDemo(new Float[]{0f,0f,0f}, 0.15f);
    }
}
