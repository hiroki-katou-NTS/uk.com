package nts.uk.client.exi;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.Properties;
import java.util.regex.Pattern;

public class ExiClientProperty {
    public static final String LOG_FILE_PATH = "LogFile";

	public static final String UK_SERVER_URL = "UkServerUrl";

	public static final String UK_CONTRACT_CODE = "TenantCode";
	public static final String UK_CONTRACT_LOGIN_PASS = "TenantLoginPassword";
	public static final String UK_COMPANY_CODE = "CompanyCode";
	public static final String UK_LOGIN_EMPLOYEE_CODE = "EmployeeCode";
	public static final String UK_LOGIN_PASSWORD = "Password";

	private static final Properties properties;

    static {
        properties = new Properties();
        Path settingFile = Paths.get("");
        try {
        	settingFile = getSettingFile();
            properties.load(Files.newBufferedReader(settingFile, StandardCharsets.UTF_8));
        } catch (IOException | URISyntaxException e) {
            System.err.println(String.format("設定ファイルの読み込みに失敗しました。ファイル名:%s", settingFile.toString()));
            throw new ExceptionInInitializerError(e);
        }
    }

	public static String getProperty(final String key) {
        return getProperty(key, "");
    }
	public static String getProperty(final String key, final String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

	private static Path getSettingFile() throws URISyntaxException {
		Path settingFile;
		Path path = getApplicationPath(Main.class);
		String jarName;
		if (path.toFile().isDirectory()) {
			jarName = "default";
		}
		else {
			jarName = path.getFileName().toString().split(Pattern.quote("."))[0];
		}
		settingFile = Paths.get(path.getParent().toString(), jarName + ".settings");
		return settingFile;
	}
	
	private static Path getApplicationPath(Class<?> cls) throws URISyntaxException {
		ProtectionDomain pd = cls.getProtectionDomain();
		CodeSource cs = pd.getCodeSource();
		URL location = cs.getLocation();
		URI uri = location.toURI();
		Path path = Paths.get(uri);
		return path;
	}
}
