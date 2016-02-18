package de.desertfox.analyse.whatsapp.core;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilteredStringBuilder {

	private StringBuilder builder = new StringBuilder();

	public FilteredStringBuilder() {
	}

	public FilteredStringBuilder(String unescaped) {
		builder = new StringBuilder();
		append(unescaped);
	}

	public FilteredStringBuilder append(String unescaped) {
		String escaped = "";
		try {
			byte[] escapedBytes = unescaped.getBytes("UTF-8");

			escaped = new String(escapedBytes, "UTF-8");

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Pattern unicodeOutliers = Pattern.compile("[^\\p{L}\\p{N}\\x00-\\x7F]",
				Pattern.UNICODE_CASE | Pattern.CANON_EQ | Pattern.CASE_INSENSITIVE);
		Matcher unicodeOutlierMatcher = unicodeOutliers.matcher(escaped);

		escaped = unicodeOutlierMatcher.replaceAll("");
		builder.append(escaped);
		return this;
	}

	@Override
	public String toString() {
		return builder.toString();
	}

}
