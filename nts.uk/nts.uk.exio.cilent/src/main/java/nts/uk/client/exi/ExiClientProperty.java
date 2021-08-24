package nts.uk.client.exi;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class ExiClientProperty {
    public static final String LOG_FILE_PATH = "LogFile";
	public static final String SETTING_FILE_PATH = "SettingFile";

	public static final String UK_SERVER_URL = "UkServerUrl";

	public static final String UK_CONTRACT_CODE = "TenantCode";
	public static final String UK_CONTRACT_LOGIN_PASS = "TenantLoginPassword";
	public static final String UK_COMPANY_CODE = "CompanyCode";
	public static final String UK_LOGIN_EMPLOYEE_CODE = "EmployeeCode";
	public static final String UK_LOGIN_PASSWORD = "Password";

	private static final String INIT_FILE_PATH = "default.settings";
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
