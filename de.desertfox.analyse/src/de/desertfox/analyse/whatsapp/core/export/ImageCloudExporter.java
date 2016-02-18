package de.desertfox.analyse.whatsapp.core.export;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import de.desertfox.analyse.whatsapp.core.analyser.IAnalyser;
import wordcloud.CollisionMode;
import wordcloud.WordCloud;
import wordcloud.WordFrequency;
import wordcloud.bg.PixelBoundryBackground;
import wordcloud.font.scale.LinearFontScalar;
import wordcloud.palette.ColorPalette;

public class ImageCloudExporter extends BaseExporter {

    public ImageCloudExporter(IAnalyser analyser) {
        super(analyser);
    }

    @SuppressWarnings("unchecked")
    @Override
    public IStatus export(File targetDir) {
        finished = false;
        try {
            final List<WordFrequency> wordFrequencies = (List<WordFrequency>) analyser.getResult();
            final WordCloud wordCloud = new WordCloud(500, 500, CollisionMode.PIXEL_PERFECT);
            wordCloud.setPadding(2);
            wordCloud.setBackground(new PixelBoundryBackground(getInputStream("C:\\Users\\d.donges\\Desktop\\cat.png")));
            wordCloud.setColorPalette(new ColorPalette(new Color(0x4055F1), new Color(0x408DF1), new Color(0x40AAF1), new Color(0x40C5F1), new Color(0x40D3F1), new Color(0xFFFFFF)));
            wordCloud.setFontScalar(new LinearFontScalar(10, 40));
            wordCloud.build(wordFrequencies);
            wordCloud.writeToFile("C:\\Users\\d.donges\\Desktop\\cat_out.png");
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
