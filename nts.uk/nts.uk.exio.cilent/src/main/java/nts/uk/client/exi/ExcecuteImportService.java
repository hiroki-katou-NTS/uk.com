package nts.uk.client.exi;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.val;
import nts.uk.client.exi.dom.FileUploader;
import nts.uk.client.exi.dom.csvimport.ExternalImport;

public class ExcecuteImportService {
	
	private FileUploader fileUploader;

	private String filePath;

	public ExcecuteImportService (String filePath)
	{
		this.filePath = filePath;
		this.fileUploader = new FileUploader();
	}

	public void doWork() {
		LogManager.init();
		
		// 設定ファイルの読み込み
		String settingFilePath =
			(this.filePath == null || this.filePath.isEmpty())
				? ExiClientProperty.getProperty(ExiClientProperty.SETTING_FILE_PATH)
				: this.filePath;

		Map<String, String> settings = readSetting(settingFilePath);
		
		settings.keySet().stream().forEach(settingCode -> {
			// ファイルアップロード
			List<String> fileIds = fileUploader.doWork(settings.get(settingCode)).stream()
					.map(info -> info.getId())
					.collect(Collectors.toList());
	
			for(String fileId : fileIds) {
				// 外部受入の呼び出し
				val externalImport = new ExternalImport(settingCode);
				if(!externalImport.doWork(fileId)) {
					LogManager.err("エラーが発生しました。処理を中断します");
					return;
				}
			}
		});
	}

	private Map<String, String> readSetting(String settingFilePath) {
		Map<String, String> result = new HashMap<>();
		try (FileInputStream fs = new FileInputStream(settingFilePath);
				InputStreamReader sr = new InputStreamReader(fs);
				BufferedReader br = new BufferedReader(sr)) {
			String[] param = br.readLine().split(",");
			if(param.length == 2) {
				result.put(param[0], param[1]);
			}
			else {
				throw new IOException("[" + (result.keySet().size() + 1) + "]行目の設定ファイルの項目数が不正です。[正常:2、入力:" + param.length + "]");
			}
		} catch (IOException e) {
			LogManager.err(e);
		}
		return result;
	}
}
