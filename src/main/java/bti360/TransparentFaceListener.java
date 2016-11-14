package bti360;

import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.FImage;
import org.openimaj.image.MBFImage;
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
public class TransparentFaceListener implements VideoDisplayListener<MBFImage> {
    MBFImage bgImage;

    @Override
    public void afterUpdate(VideoDisplay<MBFImage> display) {

    }

    @Override
    public void beforeUpdate(MBFImage frame) {
        if(bgImage == null){
            bgImage = frame.clone();
//            DisplayUtilities.display(bgImage);
            return;
        }

        doTransparentFace(frame);
    }

    private void doTransparentFace(MBFImage frame) {
        FaceDetector<DetectedFace,FImage> fd = new HaarCascadeDetector(40);
        List<DetectedFace> faces = fd.detectFaces(Transforms.calculateIntensity(frame));
        for( DetectedFace face : faces ) {
            for(int i = (int) face.getBounds().minX(); i < face.getBounds().maxX(); i++){
                for(int j = (int) face.getBounds().minY(); j < face.getBounds().maxY(); j++){
                    frame.setPixel(i, j, bgImage.getPixel(i, j));
                }
            }
        }
    }
}
