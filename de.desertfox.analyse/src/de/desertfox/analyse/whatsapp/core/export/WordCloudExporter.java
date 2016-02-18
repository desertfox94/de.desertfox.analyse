package de.desertfox.analyse.whatsapp.core.export;

import java.awt.Color;
import java.io.File;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import wordcloud.CollisionMode;
import wordcloud.WordCloud;
import wordcloud.WordFrequency;
import wordcloud.bg.CircleBackground;
import wordcloud.font.scale.SqrtFontScalar;
import wordcloud.nlp.FrequencyAnalyzer;
import wordcloud.nlp.normalize.CharacterStrippingNormalizer;
import wordcloud.nlp.normalize.UpperCaseNormalizer;
import wordcloud.palette.ColorPalette;

public class WordCloudExporter extends BaseExporter {
	
	public WordCloudExporter(IAnalyser analyser) {
        super(analyser);
    }

    @SuppressWarnings("unchecked")
    @Override
	public IStatus export(File targetDir) {
		finished = false;
		try {
			final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
			frequencyAnalyzer.addNormalizer(new UpperCaseNormalizer());
			frequencyAnalyzer.setWordFrequencesToReturn(750);
			// frequencyAnalyzer.addNormalizer(new UpsideDownNormalizer());
			frequencyAnalyzer.addNormalizer(new CharacterStrippingNormalizer());
			frequencyAnalyzer.setMinWordLength(2);
			final List<WordFrequency> wordFrequencies = (List<WordFrequency>) analyser.getResult();
			final WordCloud wordCloud = new WordCloud(600, 600, CollisionMode.PIXEL_PERFECT);
			wordCloud.setPadding(2);
			wordCloud.setBackground(new CircleBackground(300));
			wordCloud.setColorPalette(new ColorPalette(new Color(0x4055F1), new Color(0x408DF1), new Color(0x40AAF1),
					new Color(0x40C5F1), new Color(0x40D3F1), new Color(0xFFFFFF)));
			wordCloud.setFontScalar(new SqrtFontScalar(10, 40));
			wordCloud.build(wordFrequencies);
			wordCloud.writeToFile(targetDir + "\\wordCloud-" + System.currentTimeMillis() + ".png");
			return Status.OK_STATUS;
		} catch (Exception e) {
			e.printStackTrace();
			return Status.CANCEL_STATUS;
		} finally {
			finished = true;
		}
		
	}
}
