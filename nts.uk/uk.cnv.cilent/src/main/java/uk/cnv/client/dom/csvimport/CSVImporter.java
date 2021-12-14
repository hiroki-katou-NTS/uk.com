package uk.cnv.client.dom.csvimport;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import uk.cnv.client.LogManager;
import uk.cnv.client.UkConvertProperty;

public class CSVImporter {

	private static final String CSV_ENCODE = "Shift_JIS";
    private static final String EOL = "\r\n";
    private static final String TEMPDIR_PROPERTY = "java.io.tmpdir";
    private static final String ZIP_NAME = "csv.zip";

    private String constractCode;

	public CSVImporter() {
    	this.constractCode = UkConvertProperty.getProperty(UkConvertProperty.UK_CONTRACT_CODE);
	}

	public boolean doWork() {
		LogManager.out("CSVインポート -- 処理開始 --");

		String csvDir = UkConvertProperty.getProperty(UkConvertProperty.CSV_DIR);
		File csvFolder = new File(csvDir);
		List<String> fileList = Arrays.stream(csvFolder.listFiles())
			.map(f -> f.getName())
			.collect(Collectors.toList());

		Path zipFilePath = csvToZip();

		try {
			String serverUrl = UkConvertProperty.getProperty(UkConvertProperty.UK_CLOUD_SERVER_URL);
			URL url = new URL(serverUrl + "nts.uk.cloud.web/webapi/ctx/cld/operate/tenant/csvimport");
			csvImport(zipFilePath.toAbsolutePath().toString(), fileList, url);
		}
		catch (Exception e){
			System.out.println(EOL);
			LogManager.err(e);
			return false;
		}

		LogManager.out("CSVインポート -- 正常終了 --");
		return true;
	}


    private Path csvToZip() {
		String tempDir = UkConvertProperty.getProperty(UkConvertProperty.TEMP_DIR);
		String csvDir = UkConvertProperty.getProperty(UkConvertProperty.CSV_DIR);
    	System.setProperty(TEMPDIR_PROPERTY, tempDir);

		// 文字コード
		Charset charset = Charset.forName(CSV_ENCODE);
		// 入力ファイル
		Path csvFolder = Paths.get(csvDir);
		// 出力ファイル
    	Path zipFile = Paths.get(tempDir, ZIP_NAME);

        try(
                FileOutputStream fos = new FileOutputStream(zipFile.toString());
                BufferedOutputStream bos = new BufferedOutputStream(fos);
        		ZipOutputStream zos = new ZipOutputStream(bos,charset);
        ) {
        	for(File file : csvFolder.toFile().listFiles()) {
        		// zipの中のファイル1
        		byte[] data1 = Files.readAllBytes(file.toPath());
        		ZipEntry zip1 = new ZipEntry(file.getName());
        		zos.putNextEntry(zip1);
        		zos.write(data1);
        	}
		} catch (IOException e) {
			e.printStackTrace();
		}

    	return zipFile;
	}


	private int csvImport(String zipFilepath, List<String> fileList, URL url) throws IOException {

    	HttpURLConnection httpConn = null;
    	int status = 0;
    	try (FileInputStream fileStream = new FileInputStream(zipFilepath)) {
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
    			File file = new File(zipFilepath);

    			// contractCode
    			StringBuilder sb = new StringBuilder();
    			sb.append("--");
    			sb.append(boundary);
    			sb.append(EOL);
    			sb.append("Content-Disposition: form-data; ");
    			sb.append("name=\"contractCode\"" + EOL+ EOL);
    			sb.append(constractCode + EOL);

    			// filenamelist
    			sb.append("--");
    			sb.append(boundary);
    			sb.append(EOL);
    			sb.append("Content-Disposition: form-data; ");
    			sb.append("name=\"filenamelist\"" + EOL+ EOL);
    			sb.append(String.join(",", fileList) + EOL);

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

    			status = httpConn.getResponseCode();

    			if(status >= 200 && status < 300) {
	    			BufferedReader reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));

	    			StringBuilder responce = new StringBuilder();
	    			String line = null;
	    			while ((line = reader.readLine()) != null) {
	    				responce.append(line + EOL);
	    			}
	    			reader.close();
	    			LogManager.out(responce.toString());
    			}
    			else {
    				throw new RuntimeException("Server returned error code:" + status);
    			}
    		} finally {
    			httpConn.disconnect();
    		}

    		return status;

    	}
	}
}
