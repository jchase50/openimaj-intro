package bti360;

import org.openimaj.image.FImage;
import org.openimaj.image.Image;
import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.RGBColour;
import org.openimaj.image.colour.Transforms;
import org.openimaj.image.processing.face.detection.DetectedFace;
import org.openimaj.image.processing.face.detection.FaceDetector;
import org.openimaj.image.processing.face.detection.HaarCascadeDetector;
import org.openimaj.video.VideoDisplay;
import org.openimaj.video.VideoDisplayListener;

import java.util.List;

/**
 * Created by jonathanchase on 11/14/16.
 */
public class FaceDetectionListener implements VideoDisplayListener<MBFImage> {
    @Override
    public void afterUpdate(VideoDisplay display) {

    }

    @Override
    public void beforeUpdate(MBFImage frame) {
        FaceDetector<DetectedFace, FImage> fd = new HaarCascadeDetector(40);
        List<DetectedFace> faces = fd.detectFaces(Transforms.calculateIntensity(frame));
        for(DetectedFace face : faces){
            frame.drawShape(face.getBounds(), RGBColour.RED);
        }
    }
}
