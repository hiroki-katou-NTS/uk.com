package nts.uk.client.exi.dom.csvimport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nts.uk.client.exi.ExiClientProperty;
import nts.uk.client.exi.LogManager;

public class ExternalImport {
    private static final String EOL = "\r\n";
    private static final String SERVICE_URL_LOGIN = "/nts.uk.com.web/webapi/ctx/sys/gateway/login/password";
    private static final String SERVICE_URL_PREPARE = "/nts.uk.com.web/webapi/exio/input/prepare";
    private static final String SERVICE_URL_EXECUTE = "/nts.uk.com.web/webapi/exio/input/execute";

	private String serverUrl;
    private String constractCode;
    private String tenantLoginPassword;
    private String companyCode;
    
    private String settingCode;

	public ExternalImport(String settingCode) {
		this.serverUrl = ExiClientProperty.getProperty(ExiClientProperty.UK_SERVER_URL);
		
    	this.constractCode = ExiClientProperty.getProperty(ExiClientProperty.UK_CONTRACT_CODE);
    	this.tenantLoginPassword = ExiClientProperty.getProperty(ExiClientProperty.UK_CONTRACT_LOGIN_PASS);
		this.companyCode = ExiClientProperty.getProperty(ExiClientProperty.UK_COMPANY_CODE);

		this.settingCode = settingCode;
	}

	public boolean doWork(String fileId) {

		try {
			List<String> cookieList = login();
			prepare(fileId, cookieList);
			execute(cookieList);
		}
		catch (Exception e){
			System.out.println(EOL);
			LogManager.err(e);
			return false;
		}
		return true;
	}
	
	private List<String> login() throws IOException {
	    String json = "{"
				+ "\"contractCode\": \"" + this.constractCode + "\","
				+ "\"contractPassword\": \"" + this.tenantLoginPassword + "\","
				+ "\"companyCode\": \"" + this.companyCode + "\","
				+ "\"employeeCode\": \"" + "system" + "\","
				+ "\"password\": \"" + "kinjirou" + "\""
			+ "}";

			URL url = new URL(serverUrl + SERVICE_URL_LOGIN);
			
			return callWebService(url, json);
	}

	private void prepare(String fileId, List<String> cookieList) throws IOException {
		LogManager.out("外部受入 事前チェック -- 開始 --");

	    String json = "{"
				+ "\"settingCode\": \"" + this.settingCode + "\","
				+ "\"uploadedCsvFileId\": \"" + fileId + "\""
			+ "}";
	    
		URL url = new URL(serverUrl + SERVICE_URL_PREPARE);
		callWebService(url, json, cookieList);

		LogManager.out("外部受入 事前チェック -- 終了 --");
	}

	private void execute(List<String> cookieList) throws IOException {
		LogManager.out("外部受入 実行 -- 開始 --");

	    String json = "{"
				+ "\"settingCode\": \"" + this.settingCode + "\""
			+ "}";

		URL url = new URL(serverUrl + SERVICE_URL_EXECUTE);
		callWebService(url, json, cookieList);

		LogManager.out("外部受入 実行 -- 終了 --");
	}

	private List<String> callWebService(URL url, String json) throws IOException {
		return callWebService(url, json, new ArrayList<>());
	}
	
	private List<String> callWebService(URL url, String json, List<String> cookieList) throws IOException {
		int status = 0;
    	HttpURLConnection httpConn = null;
		StringBuilder responce = new StringBuilder();
    	try {
			httpConn = (HttpURLConnection) url.openConnection();
		    if (cookieList != null) {
		        for (String cookie : cookieList) {
		        	httpConn.setRequestProperty("Cookie", cookie);
		        }
		    }

			httpConn.setDoInput(true);
	        httpConn.setDoOutput(true);
			httpConn.setRequestMethod("POST"); // HTTPメソッド
    		httpConn.setRequestProperty("connection", "Keep-Alive");
			httpConn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
			
			PrintStream ps = new PrintStream(httpConn.getOutputStream());
			ps.print(json);
			ps.close();

			status = httpConn.getResponseCode();

			try (BufferedReader reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream()))) {
				String line = null;
				while ((line = reader.readLine()) != null) {
					responce.append(line + EOL);
				}
			}
    	} finally {
			httpConn.disconnect();
		}

		if(status != 200) {
			String errorMessage = "ERROR" + status + "](" + url.toString() + "):"
				+ httpConn.getResponseMessage()
				+ (!responce.toString().isEmpty() ? "\r\n" + responce.toString() : "");
			throw new RuntimeException(errorMessage);
		}
		
		Map<String, List<String>> headerFiesds = httpConn.getHeaderFields();
	    return headerFiesds.get("Set-Cookie");
	}
}
