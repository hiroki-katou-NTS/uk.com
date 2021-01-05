package uk.cnv.client;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class UkConvertProperty {

	private static final String INIT_FILE_PATH = "src/main/resources/common.properties";
	private static final Properties properties;

    static {
        properties = new Properties();
        try {
            properties.load(Files.newBufferedReader(Paths.get(INIT_FILE_PATH), StandardCharsets.UTF_8));
        } catch (IOException e) {
            System.err.println(String.format("ファイルの読み込みに失敗しました。ファイル名:%s", INIT_FILE_PATH));
        }
    }

	public static String getProperty(final String key) {
        return getProperty(key, "");
    }
	public static String getProperty(final String key, final String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
}
