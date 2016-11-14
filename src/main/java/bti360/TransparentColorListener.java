package bti360;

import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.Image;
import org.openimaj.image.MBFImage;
import org.openimaj.video.VideoDisplay;
import org.openimaj.video.VideoDisplayListener;

/**
 * Created by jonathanchase on 11/14/16.
 */
public class TransparentColorListener implements VideoDisplayListener<MBFImage> {
    private Float flatTColor;
    private Float tolerance;
    private MBFImage bgImage;

    public TransparentColorListener(Float[] tColor, Float tolerance) {
        this.flatTColor = flattenRGB(tColor);
        this.tolerance = tolerance;
    }

    @Override
    public void afterUpdate(VideoDisplay display) {

    }

    @Override
    public void beforeUpdate(MBFImage frame) {
        if(bgImage == null){
            bgImage = frame.clone();
//            DisplayUtilities.display(bgImage);
            return;
        }
        replaceTransparentPixels(frame);
    }

    private void replaceTransparentPixels(MBFImage frame) {
        for(int x = 0; x < frame.getWidth(); x++){
            for(int y=0; y < frame.getHeight(); y++){
                if(isTransparentPixel(frame.getPixel(x, y))){
                    frame.setPixel(x, y, bgImage.getPixel(x, y));
                }
            }
        }
    }

    private boolean isTransparentPixel(Float[] pixel) {
        return isWithinRange(flattenRGB(pixel), flatTColor, tolerance);
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

}
