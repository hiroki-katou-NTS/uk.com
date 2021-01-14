package uk.cnv.client.infra.impls;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
//import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import nts.arc.layer.app.file.storage.StoredFileInfo;
import uk.cnv.client.UkConvertProperty;


public class FileUploader{

    private static final String EOL = "\r\n";
    private static final String FILEID_JSON_NAME = "id";

    public FileUploader() {
    }

	public StoredFileInfo store(Path pathToSource, String stereotype, String fileType) {
		StoredFileInfo retult;
		try {
			retult = callUploadApi(pathToSource, stereotype, fileType);
		} catch (IOException e) {
			System.err.println("\r\n");
			System.err.println("ファイルアップロードに失敗しました。\r\n" + pathToSource.toFile().getPath() + "\r\n");
			e.printStackTrace();
			return null;
		}

		return retult;
	}

	private StoredFileInfo callUploadApi(Path pathToSource, String stereotype, String fileType) throws JsonParseException, JsonMappingException, IOException {

		loginUk();

		String serverUrl = UkConvertProperty.getProperty("UkApServerUrl");
		URL url = new URL(serverUrl + "nts.uk.com.web/webapi/ntscommons/arc/filegate/upload");

		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<String, Object>();
		String jsonStr = null;

		jsonStr = upload(pathToSource.toFile().getPath(), url, stereotype);
		jsonStr = jsonStr.replace("[", "").replace("]", "");
		map = mapper.readValue(jsonStr, new TypeReference<Map<String, String>>(){});
		//StoredFileInfo fileInfo = mapper.readValue(jsonStr, StoredFileInfo.class);

		return StoredFileInfo.createNewWithId(
				(String) map.get(FILEID_JSON_NAME),
				(String) map.get("originalName"),
				(String) map.get("fileType"),
				(String) map.get("mimeType"),
				Long.parseLong((String) map.get("originalSize"))
			);
	}

	private String loginUk() throws IOException {
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

		return resultStr;
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

    private String upload(String filepath, URL url, String stereotype) throws IOException {

    	HttpURLConnection httpConn = null;

    	try (FileInputStream fileStream = new FileInputStream(filepath)) {
    		httpConn = (HttpURLConnection) url.openConnection();

    		final String boundary = UUID.randomUUID().toString();

    		httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
    		httpConn.setRequestMethod("POST");// HTTPメソッド
    		httpConn.setRequestProperty("connection", "Keep-Alive");
    		httpConn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
    		httpConn.setRequestProperty("Charsert", "UTF-8");
    		httpConn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);


    		try (OutputStream out = httpConn.getOutputStream()) {
    			File file = new File(filepath);

    			// stereotype
    			StringBuilder sb = new StringBuilder();
    			sb.append("--");
    			sb.append(boundary);
    			sb.append(EOL);
    			sb.append("Content-Disposition: form-data; ");
    			sb.append("name=\"stereotype\"" + EOL+ EOL);
    			sb.append(stereotype + EOL);

    			// filename
    			sb.append("--");
    			sb.append(boundary);
    			sb.append(EOL);
    			sb.append("Content-Disposition: form-data; ");
    			sb.append("name=\"filename\"" + EOL+ EOL);
    			sb.append(file.getName() + EOL);

    			// userfile
				sb.append("--");
				sb.append(boundary);
				sb.append(EOL);
				sb.append("Content-Disposition: form-data;");
				sb.append("name=\"userfile\"; ");
				sb.append("filename=\""+ file.getName() + "\";" + EOL);
				sb.append("Content-Type:" + Files.probeContentType(file.toPath()) + EOL+ EOL);

    			byte[] buffer = sb.toString().getBytes(StandardCharsets.UTF_8);
    			out.write(buffer);

    			int size = -1;
    			while (-1 != (size = fileStream.read(buffer))) {
    				out.write(buffer, 0, size);
    			}
				out.write(EOL.getBytes());

    			out.write((EOL + "--" + boundary + "--" + EOL).getBytes(StandardCharsets.UTF_8));
    			out.flush();
    			out.close();

    			int status = httpConn.getResponseCode();

    			switch(status) {
    				case 200:
    				case 201:
	    				{
			    			BufferedReader reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));

			    			StringBuilder responce = new StringBuilder();
			    			String line = null;
			    			while ((line = reader.readLine()) != null) {
			    				responce.append(line + EOL);
			    			}
			    			reader.close();
			    			responce.toString();

			    			return responce.toString();
	    				}
    			}
    		} finally {
    			httpConn.disconnect();
    		}

    		return null;

    	}
	}

}
