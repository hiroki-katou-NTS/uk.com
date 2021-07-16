package nts.uk.client.exi;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class ExiClientProperty {
    public static final String LOG_FILE_PATH = "LogFilePath";
	public static final String CSV_FOLDER_PATH = "CsvFolderPath";

	public static final String UK_SERVER_URL = "UkServerUrl";

	public static final String UK_CONTRACT_CODE = "TenantCode";
	public static final String UK_CONTRACT_LOGIN_PASS = "TenantLoginPassword";
	public static final String UK_COMPANY_CODE = "CompanyCode";

	public static final String SETTING_CODE="SettingCode";
	
	////////////////////////////////////////////////////////////////////////
	
//	public static final String ERP_DBCONN_STR = "ErpDbConnString";
//	public static final String UK_DBCONN_STR = "UkDbConnString";
//	public static final String UKCNV_DBCONN_STR = "UkCnvDbConnString";
//
//	public static final String UKDB_HOST = "UkDbServerHost";
//	public static final String UKDB_USER = "UkDbUser";
//	public static final String UKDB_PASS = "UkDbPassword";
//	public static final String UKDB_DBNAME = "UkDbName";
//
//	public static final String ERP_WWWROOT_PATH = "ErpWwwrootPath";
//	public static final String UK_CLOUD_SERVER_URL = "UkCloudServerUrl";
//
//	public static final String CONVERT_CODE_FILE = "convertCodeFile";
//
//	public static final String CREATE_CONVERT_DB_SCRIPT = "createConvertDbScript";
//	public static final String CREATE_UKDB_SCRIPT = "createUkDbScript";
//
//	public static final String CSV_DIR = "csvDirectory";
//	public static final String TEMP_DIR = "temporaryDirectory";


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
