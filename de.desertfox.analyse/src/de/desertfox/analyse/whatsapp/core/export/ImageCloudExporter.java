package de.desertfox.analyse.whatsapp.core.export;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;

import wordcloud.CollisionMode;
import wordcloud.WordCloud;
import wordcloud.WordFrequency;
import wordcloud.bg.PixelBoundryBackground;
import wordcloud.font.scale.LinearFontScalar;
import wordcloud.palette.ColorPalette;
import de.desertfox.analyse.whatsapp.core.analyser.IAnalyser;
import de.desertfox.analyse.whatsapp.model.ImageTemplate;

public class ImageCloudExporter extends BaseExporter {

    private ImageTemplate template;
    
    public ImageCloudExporter(IAnalyser analyser, ImageTemplate template) {
        super(analyser);
        this.template = template;
    }

    @SuppressWarnings("unchecked")
    @Override
    public IStatus export(File targetDir) {
        finished = false;
        try {
            final List<WordFrequency> wordFrequencies = (List<WordFrequency>) analyser.getResult();
            InputStream inputStream = getInputStream(template.getPath());
            Image image = new Image(Display.getCurrent(), inputStream);
            Rectangle bounds = image.getBounds();
            final WordCloud wordCloud = new WordCloud(bounds.width, bounds.height, CollisionMode.PIXEL_PERFECT);
            wordCloud.setPadding(2);
            wordCloud.setBackground(new PixelBoundryBackground(getInputStream(template.getPath())));
            wordCloud.setColorPalette(new ColorPalette(new Color(0x4055F1), new Color(0x408DF1), new Color(0x40AAF1), new Color(0x40C5F1), new Color(0x40D3F1), new Color(0xFFFFFF)));
            wordCloud.setFontScalar(new LinearFontScalar(10, 40));
            wordCloud.build(wordFrequencies);
            wordCloud.writeToFile(targetDir.getPath() + "\\" + template.getDisplayName() + System.currentTimeMillis() + ".png");
            System.out.println("fin");
            return Status.OK_STATUS;
        } catch (Exception e) {
            e.printStackTrace();
            return Status.CANCEL_STATUS;
        } finally {
            finished = true;
        }
    }

    private InputStream getInputStream(String path) {
        try {
            return new FileInputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
