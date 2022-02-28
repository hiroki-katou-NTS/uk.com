package nts.uk.client.exi.dom.csvimport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;
import nts.uk.client.exi.ExiClientProperty;
import nts.uk.client.exi.LogManager;

public class ExternalImport {
    private static final String EOL = "\r\n";
    private static final String SERVICE_URL_LOGIN = "/nts.uk.com.web/webapi/ctx/sys/gateway/login/password";
    private static final String SERVICE_URL_PREPARE = "/nts.uk.com.web/webapi/exio/input/prepare";
    private static final String SERVICE_URL_EXECUTE = "/nts.uk.com.web/webapi/exio/input/execute";
    private static final String SERVICE_URL_ASYNC = "/nts.uk.com.web/webapi/ntscommons/arc/task/async/info/";
	private static final String SERVICE_URL_CHECKERROR =  "/nts.uk.com.web/webapi/exio/input/errors/";

	private String serverUrl;
    private String constractCode;
    private String tenantLoginPassword;
    private String companyCode;
    
    private String settingCode;
	private String employeeCode;
	private String password;

	private boolean continueFlg;

	public ExternalImport(String settingCode, boolean continueFlg) {
		this.serverUrl = ExiClientProperty.getProperty(ExiClientProperty.UK_SERVER_URL);
		
    	this.constractCode = ExiClientProperty.getProperty(ExiClientProperty.UK_CONTRACT_CODE);
    	this.tenantLoginPassword = ExiClientProperty.getProperty(ExiClientProperty.UK_CONTRACT_LOGIN_PASS);
		this.companyCode = ExiClientProperty.getProperty(ExiClientProperty.UK_COMPANY_CODE);
		this.settingCode = settingCode;

    	this.employeeCode = ExiClientProperty.getProperty(ExiClientProperty.UK_LOGIN_EMPLOYEE_CODE);
		this.password = ExiClientProperty.getProperty(ExiClientProperty.UK_LOGIN_PASSWORD);

		this.continueFlg = continueFlg;
	}

	public boolean doWork(String fileId) {

		try {
			CallWebServiceResult result = login();
			boolean preparaResult = prepare(fileId, result.setCookies);
			if(continueFlg || preparaResult) {
				execute(result.setCookies);
			}
		}
		catch (Exception e){
			System.out.println(EOL);
			LogManager.err(e);
			return false;
		}
		return true;
	}
	
	private CallWebServiceResult login() throws IOException {
	    String json = "{"
				+ "\"contractCode\": \"" + this.constractCode + "\","
				+ "\"contractPassword\": \"" + this.tenantLoginPassword + "\","
				+ "\"companyCode\": \"" + this.companyCode + "\","
				+ "\"employeeCode\": \"" + this.employeeCode + "\","
				+ "\"password\": \"" + this.password + "\""
			+ "}";

			URL url = new URL(serverUrl + SERVICE_URL_LOGIN);
			
			return callWebService(url, json);
	}

	private boolean prepare(String fileId, List<String> cookieList) throws IOException, InterruptedException {
		LogManager.out("ExternalImport.prepare start");

	    String json = "{"
				+ "\"settingCode\": \"" + this.settingCode + "\","
				+ "\"uploadedCsvFileId\": \"" + fileId + "\""
			+ "}";
	    
		URL url = new URL(serverUrl + SERVICE_URL_PREPARE);
		CallWebServiceResult wsResult = callWebService(url, json, cookieList);
		String taskId = (String) wsResult.jsonAsyncTaskInfo.get("id");

		boolean result = awaitComplated(cookieList, taskId);
		if (!result) return result;

		result = checkErrorMessage("受入準備",cookieList);
		LogManager.out("ExternalImport.prepare end");

		return result;
	}

	private void execute(List<String> cookieList) throws IOException, InterruptedException {
		LogManager.out("ExternalImport.execute start");

	    String json = "{"
				+ "\"settingCode\": \"" + this.settingCode + "\""
			+ "}";

		URL url = new URL(serverUrl + SERVICE_URL_EXECUTE);
		CallWebServiceResult result = callWebService(url, json, cookieList);

		String taskId = (String) result.jsonAsyncTaskInfo.get("id");

		if ((boolean) result.jsonAsyncTaskInfo.get("pending")
				|| (boolean) result.jsonAsyncTaskInfo.get("running")) {
			awaitComplated(cookieList, taskId);
		}

		checkErrorMessage("受入実行", cookieList);
		
		LogManager.out("ExternalImport.execute end");
	}

	private boolean awaitComplated(List<String> cookieList, String taskId) throws IOException, InterruptedException {
		URL checkAsyncTaskUrl = new URL(serverUrl + SERVICE_URL_ASYNC +  taskId);
		CallWebServiceResult result;
		while(true) {
			result = callWebService(checkAsyncTaskUrl, "", cookieList);
			if ((boolean) result.jsonAsyncTaskInfo.get("pending") == false
					&& (boolean) result.jsonAsyncTaskInfo.get("running") == false) {
				break;
			}
			Thread.sleep(1000);
		}
		
		if ((boolean) result.jsonAsyncTaskInfo.get("succeeded") == false) {
			LogManager.err(result.jsonAsyncTaskInfo.get("error").toString());
			return false;
		}

		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		String taskDataString = result.jsonAsyncTaskInfo.get("taskDatas").toString()
				.replaceAll("=([^,}]*)([,|}])", "=\"$1\"$2")
				.replaceAll("=", ":");
		
		List<TaskData> taskData = new ArrayList<>();
		try {
			taskData = mapper.readValue(taskDataString,new TypeReference<List<TaskData>>() {});
		} catch (JsonProcessingException e) {
			LogManager.err(e);
		}
		
		boolean failed = taskData.stream()
			.filter(t -> "process".equals(t.key))
			.map(t -> t.getValueAsString())
			.anyMatch(val -> "failed".contentEquals(val));
		if(failed) {
			taskData.stream()
				.filter(t -> "message".equals(t.key))
				.map(t -> t.getValueAsString())
				.forEach(msg -> LogManager.err(msg));
			return false;
		}
		return true;
	}

	private CallWebServiceResult callWebService(URL url, String json) throws IOException {
		return callWebService(url, json, new ArrayList<>());
	}
	
	private CallWebServiceResult callWebService(URL url, String json, List<String> cookieList) throws IOException {
		int status = 0;
    	HttpURLConnection httpConn = null;
		Map<String, Object> responceMap;
    	try {
			httpConn = (HttpURLConnection) url.openConnection();
		    if (cookieList != null && cookieList.size() > 0) {
	        	httpConn.setRequestProperty("Cookie", String.join(",", cookieList));
		    }

			httpConn.setDoInput(true);
	        httpConn.setDoOutput(true);
			httpConn.setRequestMethod("POST"); // HTTPメソッド
    		httpConn.setRequestProperty("connection", "Keep-Alive");
			httpConn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
			
			if(!json.isEmpty()) {
				PrintStream ps = new PrintStream(httpConn.getOutputStream());
				ps.print(json);
				ps.close();
			}
			
			ObjectMapper mapper = new ObjectMapper();
			StringBuilder responce = new StringBuilder();
			httpConn.connect();
			status = httpConn.getResponseCode();
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream(httpConn, status)))) {
				String line = null;
				while ((line = reader.readLine()) != null) {
					responce.append(line + EOL);
				}
				responceMap = mapper.readValue(responce.toString(), new TypeReference<Map<String, Object>>(){});
			}
    	} finally {
			httpConn.disconnect();
		}

		if(status != 200) {
			String errorMessage = "ERROR[" + status + "](" + url.toString() + "):"
				+ httpConn.getResponseMessage()
				+ (!responceMap.isEmpty() ? "\r\n" + responceMap.get("stackTrace") : "");
			throw new RuntimeException(errorMessage);
		}
		
		Map<String, List<String>> headerFiesds = httpConn.getHeaderFields();
	    return new CallWebServiceResult(headerFiesds.get("Set-Cookie"), responceMap);
	}
	
	private InputStream inputStream(HttpURLConnection httpConn, int status) throws IOException {
		return (status / 100 == 4 || status / 100 == 5)
			? httpConn.getErrorStream()
			: httpConn.getInputStream();
	}
	
	private class CallWebServiceResult{
		public List<String> setCookies;
		public Map<String, Object> jsonAsyncTaskInfo;
		
		public CallWebServiceResult(List<String> setCookies, Map<String, Object> jsonAsyncTaskInfo) {
			this.setCookies = setCookies;
			this.jsonAsyncTaskInfo = jsonAsyncTaskInfo;
		}
	}

	private boolean checkErrorMessage(String processName, List<String> cookieList) throws IOException {
		StringBuilder sb = new StringBuilder();
		int errorsCount = 1;
		for (int requestCount = 1;; requestCount++) {
			URL checkErrorsUrl = new URL(serverUrl + SERVICE_URL_CHECKERROR + this.settingCode + "/" + requestCount);
			CallWebServiceResult errorCheckResult = callWebService(checkErrorsUrl, "", cookieList);
			sb.append(errorCheckResult.jsonAsyncTaskInfo.get("text"));
			errorsCount = (int) errorCheckResult.jsonAsyncTaskInfo.get("errorsCount");
			
			if(errorsCount == 0) break;
		}
		
		if(sb.length()>0) {
			String errorMessage = processName + "が完了しましたが、以下のエラーが発生しています。\r\n" + sb.toString();
			LogManager.err(errorMessage);

			return false;
		}

		return true;
	}
	
	@Data
	public static class TaskData {
		String key;
		String valueAsString;
		String valueAsBoolean;
		String valueAsNumber;
		
		public TaskData() {
		}
	}
}
