package uk.cnv.client;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class UkConvertProperty {
	public static final String ERP_DBCONN_STR = "ErpDbConnString";
	public static final String UK_DBCONN_STR = "UkDbConnString";
	public static final String UKCNV_DBCONN_STR = "UkCnvDbConnString";

	public static final String UKDB_HOST = "UkDbServerHost";
	public static final String UKDB_USER = "UkDbUser";
	public static final String UKDB_PASS = "UkDbPassword";
	public static final String UKDB_DBNAME = "UkDbName";

	public static final String ERP_WWWROOT_PATH = "ErpWwwrootPath";
	public static final String UK_SERVER_URL = "UkServerUrl";
	public static final String UK_CLOUD_SERVER_URL = "UkCloudServerUrl";

    public static final String UK_CONTRACT_CODE = "UkContractCode";

	public static final String CONVERT_CODE_FILE = "ConvertCodeFile";

	public static final String CREATE_CONVERT_DB_SCRIPT = "CreateConvertDbScript";
	public static final String CREATE_UKDB_SCRIPT = "CreateUkDbScript";

    public static final String LOG_FILE_PATH = "LogfilePath";
	public static final String CSV_DIR = "CsvDirectory";
	public static final String TEMP_DIR = "TemporaryDirectory";


	private static final String INIT_FILE_PATH = "common.properties";
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
