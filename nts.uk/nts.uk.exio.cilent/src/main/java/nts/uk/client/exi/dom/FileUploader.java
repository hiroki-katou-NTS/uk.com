package nts.uk.client.exi.dom;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import nts.arc.layer.app.file.storage.StoredFileInfo;
import nts.uk.client.exi.ExiClientProperty;
import nts.uk.client.exi.LogManager;

public class FileUploader {
    private static final String EOL = "\r\n";
    private static final String FILEID_JSON_NAME = "id";
	private static final String STEREOTYPE = "import_csv";
	private static final String SERVICE_URL = "/nts.uk.com.web/webapi/ntscommons/arc/filegate/upload";

	public List<StoredFileInfo> doWork(String csvFolderPath) {
		LogManager.out("ファイルのアップロード -- 開始 --");
		
		File csvFolder = new File(csvFolderPath);
		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File file, String str){
				return str.endsWith("csv");	// 拡張子csvでフィルタ
			}
		};

		File[] csvFiles = csvFolder.listFiles(filter);
		if(csvFiles == null) {
			LogManager.err("指定フォルダ内にcsvファイルが見つかりません。:" + csvFolder.getPath() + "\r\n");
			return Collections.emptyList();
		}

		List<Path> filePathList = Arrays.stream(csvFiles)
			.map(f -> Paths.get(f.getPath()))
			.collect(Collectors.toList());

		int errorCount = 0;
		List<StoredFileInfo> infoList = new ArrayList<>();
		for(Path filePath : filePathList) {
			Optional<StoredFileInfo> info = this.store(filePath, STEREOTYPE, "");
			if (info.isPresent()) {
				infoList.add(info.get());
			}
			else{
				errorCount+=1;
			};
		}
		
		LogManager.out("ファイルのアップロード -- 終了 -- [正常:" + infoList.size() + "件、エラー:" + errorCount + "件]");

		return infoList;
	}

	public Optional<StoredFileInfo> store(Path pathToSource, String stereotype, String fileType) {
		StoredFileInfo retult;
		try {
			retult = callUploadApi(pathToSource, stereotype, fileType);
		} catch (IOException e) {
			LogManager.err("\r\n");
			LogManager.err("ファイルアップロードに失敗しました。\r\n" + pathToSource.toFile().getPath() + "\r\n");
			LogManager.err(e);
			return Optional.empty();
		}

		return Optional.of(retult);
	}

	private StoredFileInfo callUploadApi(Path filePath, String stereotype, String fileType) throws JsonParseException, JsonMappingException, IOException {

		String serverUrl = ExiClientProperty.getProperty(ExiClientProperty.UK_SERVER_URL);
		URL url = new URL(serverUrl + SERVICE_URL);

		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<String, Object>();
		String jsonStr = null;

		jsonStr = upload(filePath.toFile().getPath(), url, stereotype);
		jsonStr = jsonStr.replace("[", "").replace("]", "");
		map = mapper.readValue(jsonStr, new TypeReference<Map<String, String>>(){});

		return StoredFileInfo.createNewWithId(
				(String) map.get(FILEID_JSON_NAME),
				(String) map.get("originalName"),
				(String) map.get("fileType"),
				(String) map.get("mimeType"),
				Long.parseLong((String) map.get("originalSize"))
			);
	}

    private String upload(String filepath, URL url, String stereotype) throws IOException {

		final String boundary = UUID.randomUUID().toString();
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
		
		int status = 0;
    	HttpURLConnection httpConn = null;
		StringBuilder responce = new StringBuilder();
    	try {
    		httpConn = (HttpURLConnection) url.openConnection();

    		httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
    		httpConn.setRequestMethod("POST");// HTTPメソッド
    		httpConn.setRequestProperty("Charsert", "UTF-8");
    		httpConn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

    		try (FileInputStream fileStream = new FileInputStream(filepath);
    				OutputStream out = httpConn.getOutputStream()) {
    			out.write(buffer);

    			int size = -1;
    			while (-1 != (size = fileStream.read(buffer))) {
    				out.write(buffer, 0, size);
    			}
				out.write(EOL.getBytes());
    			out.write((EOL + "--" + boundary + "--" + EOL).getBytes(StandardCharsets.UTF_8));
    			out.flush();
    		}
    		
			status = httpConn.getResponseCode();
			
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream()))) {
				String line = null;
				while ((line = reader.readLine()) != null) {
					responce.append(line + EOL);
				}
			}
			
			if(status != 200) {
				String errorMessage = "ERROR" + status + "](" + url.toString() + "):"
						+ httpConn.getResponseMessage()
						+ (!responce.toString().isEmpty() ? "\r\n" + responce.toString() : "");
					throw new RuntimeException(errorMessage);
			}
    	} finally {
			httpConn.disconnect();
		}

		return responce.toString();
	}
}
