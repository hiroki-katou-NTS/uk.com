package uk.cnv.client.infra.impls;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
//import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import uk.cnv.client.UkConvertProperty;


public class FileStorageImpl{

    private static final String EOL = "\r\n";

	public void store(Path pathToSource, String originalFileName, String fileType) {
		callUploadApi(pathToSource, originalFileName, fileType);

		//TODO: FileIdMappingへの書き込み

		return;
	}

	private void callUploadApi(Path pathToSource, String originalFileName, String fileType) {
		try {
			loginUk();
		} catch (IOException e1) {
			e1.printStackTrace();
			return;
		}

		String serverUrl = UkConvertProperty.getProperty("UkApServerUrl");
		URL url = null;
		try {
			url = new URL(serverUrl + "nts.uk.com.web/webapi/ntscommons/arc/filegate/upload");
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return;
		}
//
//		HttpRequest.BodyPublisher dataProcessor1 = HttpRequest.BodyPublishers.ofString("{\"stereotype\":\"" + fileType +  "\"}");
//		HttpRequest.BodyPublisher dataProcessor2 = HttpRequest.BodyPublishers.ofString("{\"filename\":\"" + originalFileName +  "\"}");
//		HttpRequest.BodyPublisher fileProcessor;
//		try {
//			fileProcessor = HttpRequest.BodyPublishers.ofFile(pathToSource);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//			return null;
//		}
//		HttpRequest request = HttpRequest.newBuilder()
//				.uri(uri)
//				.headers("Content-Type","multipart/form-data","boundary","boundaryValue") // appropriate boundary values
//				.POST(dataProcessor1)
//				.POST(dataProcessor2)
//				.POST(fileProcessor)
//				.build();
//
//		HttpClient client = HttpClient.newBuilder().build();
//		HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();
//
//		HttpResponse<String> response;
//		try {
//			response = client.send(request, bodyHandler);
//		} catch (IOException | InterruptedException e) {
//			e.printStackTrace();
//			return null;
//		}
//		return response;

    	HttpURLConnection httpConn = null;

    	try (FileInputStream file = new FileInputStream(pathToSource.toFile())) {
    		httpConn = (HttpURLConnection) url.openConnection();

    		final String boundary = UUID.randomUUID().toString();

            httpConn.setDoOutput(true);
    		httpConn.setRequestMethod("POST");// HTTPメソッド
    		httpConn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

    		try (OutputStream out = httpConn.getOutputStream()) {
    			out.write(("--" + boundary + EOL +
    					"Content-Disposition: form-data; name=\"file\"; " +
    					"filename=\"" + originalFileName + "\"" + EOL +
    					"Content-Type: application/octet-stream" + EOL + EOL)
    					.getBytes(StandardCharsets.UTF_8)
    					);
    			byte[] buffer = new byte[128];
    			int size = -1;
    			while (-1 != (size = file.read(buffer))) {
    				out.write(buffer, 0, size);
    			}
    			out.write((EOL + "--" + boundary + "--" + EOL).getBytes(StandardCharsets.UTF_8));
    			out.flush();
    			System.err.println(httpConn.getResponseMessage());
    			return;

    		} finally {
    			httpConn.disconnect();
    		}
    	} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loginUk() throws IOException {
		String serverUrl = UkConvertProperty.getProperty("UkApServerUrl");
		URL url = null;

		// パスワード認証ログインUri
		try {
			url = new URL(serverUrl + "nts.uk.com.web/webapi/ctx/sys/gateway/login/password");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		String companyCode = UkConvertProperty.getProperty("UkCompanyCode");
		String contractCode = UkConvertProperty.getProperty("UkContractCode");
		String contractPassword = UkConvertProperty.getProperty("UkContractPassword");
		String employeeCode = UkConvertProperty.getProperty("UkEmployeeCode");
		String password = UkConvertProperty.getProperty("UkPassword");

        final String postJson = "{"
				+ "\"companyCode\":\"" + companyCode +  "\","
				+ "\"contractCode\":\"" + contractCode +  "\","
				+ "\"contractPassword\":\"" + contractPassword +  "\","
				+ "\"employeeCode\":\"" + employeeCode +  "\","
				+ "\"password\":\"" + password +  "\""
				+ "}";

        final Map<String, String> httpHeaders = new LinkedHashMap<String, String>();
        httpHeaders.put("Content-Type", "application/json");
        final String resultStr = doPost(url, "UTF-8", httpHeaders, postJson);

		return;
	}

    private String doPost(URL url, String encoding, Map<String, String> headers, String jsonString) throws IOException {

        final int TIMEOUT_MILLIS = 0;// タイムアウトミリ秒：0は無限

        final StringBuffer sb = new StringBuffer("");

        HttpURLConnection httpConn = null;
        BufferedReader br = null;
        InputStream is = null;
        InputStreamReader isr = null;

        try {
            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setConnectTimeout(TIMEOUT_MILLIS);// 接続にかかる時間
            httpConn.setReadTimeout(TIMEOUT_MILLIS);// データの読み込みにかかる時間
            httpConn.setRequestMethod("POST");// HTTPメソッド
            httpConn.setUseCaches(false);// キャッシュ利用
            httpConn.setDoOutput(true);// リクエストのボディの送信を許可(GETのときはfalse,POSTのときはtrueにする)
            httpConn.setDoInput(true);// レスポンスのボディの受信を許可

            if (headers != null) {
                for (String key : headers.keySet()) {
                    httpConn.setRequestProperty(key, headers.get(key));// HTTPヘッダをセット
                }
            }

            final OutputStream os = httpConn.getOutputStream();
            final boolean autoFlash = true;
            final PrintStream ps = new PrintStream(os, autoFlash, encoding);
            ps.print(jsonString);
            ps.close();

            final int responseCode = httpConn.getResponseCode();

            String _responseEncoding = httpConn.getContentEncoding();
            if (_responseEncoding == null) {
                _responseEncoding = "UTF-8";
            }

            if (responseCode == HttpURLConnection.HTTP_OK) {
                is = httpConn.getInputStream();
                isr = new InputStreamReader(is, _responseEncoding);
                br = new BufferedReader(isr);
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
            } else {
                // ステータスがHTTP_OK(200)以外の場合
                throw new IOException("responseCode is " + responseCode);
            }

        } catch (IOException e) {
            throw e;
        } finally {
            // Java1.6 Compliant
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException e) {
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
            if (httpConn != null) {
                httpConn.disconnect();
            }
        }
        return sb.toString();
    }

    private void upload(String filename, URL url) throws IOException {

    	HttpURLConnection httpConn = null;

    	try (FileInputStream file = new FileInputStream(filename)) {
    		httpConn = (HttpURLConnection) url.openConnection();

    		final String boundary = UUID.randomUUID().toString();

            httpConn.setDoOutput(true);
    		httpConn.setRequestMethod("POST");// HTTPメソッド
    		httpConn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

    		try (OutputStream out = httpConn.getOutputStream()) {
    			out.write(("--" + boundary + EOL +
    					"Content-Disposition: form-data; name=\"file\"; " +
    					"filename=\"" + filename + "\"" + EOL +
    					"Content-Type: application/octet-stream" + EOL + EOL)
    					.getBytes(StandardCharsets.UTF_8)
    					);
    			byte[] buffer = new byte[128];
    			int size = -1;
    			while (-1 != (size = file.read(buffer))) {
    				out.write(buffer, 0, size);
    			}
    			out.write((EOL + "--" + boundary + "--" + EOL).getBytes(StandardCharsets.UTF_8));
    			out.flush();
    			System.err.println(httpConn.getResponseMessage());
    			return;

    		} finally {
    			httpConn.disconnect();
    		}
    	}
	}

}
