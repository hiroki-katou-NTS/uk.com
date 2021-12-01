package uk.cnv.client.dom.fileimport;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
//import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import nts.arc.layer.app.file.storage.StoredFileInfo;
import uk.cnv.client.LogManager;
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
			LogManager.err("\r\n");
			LogManager.err("ファイルアップロードに失敗しました。\r\n" + pathToSource.toFile().getPath() + "\r\n");
			LogManager.err(e);
			return null;
		}

		return retult;
	}

	private StoredFileInfo callUploadApi(Path pathToSource, String stereotype, String fileType) throws JsonParseException, JsonMappingException, IOException {

		String serverUrl = UkConvertProperty.getProperty(UkConvertProperty.UK_SERVER_URL);
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

    			if(status >= 200 && status <= 299) {
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
    			else {
    				throw new RuntimeException("ERROR(" + status + "):" + url.toString());
    			}
    		} finally {
    			httpConn.disconnect();
    		}
    	}
	}

}
