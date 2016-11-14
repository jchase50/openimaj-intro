package bti360;

import org.openimaj.image.Image;
import org.openimaj.video.VideoDisplay;
import org.openimaj.video.VideoDisplayListener;

/**
 * Created by jonathanchase on 11/14/16.
 */
public class SolidFillFrameListener implements VideoDisplayListener {
    private Float[] rgbColor;

    public SolidFillFrameListener(Float[] rgbColor) {
        this.rgbColor = rgbColor;
    }

    @Override
    public void afterUpdate(VideoDisplay display) {

    }

    @Override
    public void beforeUpdate(Image frame) {
        frame.fill(rgbColor);
    }
}
