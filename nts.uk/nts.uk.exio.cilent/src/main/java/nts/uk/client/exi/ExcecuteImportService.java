package nts.uk.client.exi;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
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

	private final String MAPKEY_SETTING_CODE = "SettingCode";
	private final String MAPKEY_CSV_FILEPATH = "CsvFilePath";
	private final String MAPKEY_CONTINUE_FLG = "ContinueFlg";

	public ExcecuteImportService (String filePath)
	{
		this.filePath = filePath;
		this.fileUploader = new FileUploader();
	}

	public void doWork() {		
//		// 設定ファイルから取り込み対象リストのパスを読み込み
//		String targetListFilePath =
//			(this.filePath == null || this.filePath.isEmpty())
//				? ExiClientProperty.getProperty(ExiClientProperty.SETTING_FILE_PATH)
//				: this.filePath;

		List<Map<String, String>> targetList = readTargetList(this.filePath);

		targetList.stream().forEach(target -> {
			String settingCode = target.get(MAPKEY_SETTING_CODE);
			String csvFilePath = target.get(MAPKEY_CSV_FILEPATH);
			boolean continueFlg = target.get(MAPKEY_CONTINUE_FLG).toUpperCase().equals("TRUE");
			// ファイルアップロード
			List<String> fileIds = fileUploader.doWork(csvFilePath).stream()
					.map(info -> info.getId())
					.collect(Collectors.toList());

			val externalImport = new ExternalImport(settingCode, continueFlg);
			for(String fileId : fileIds) {
				// 外部受入の呼び出し
				if(!externalImport.doWork(fileId)) {
					LogManager.err("エラーが発生しました。処理を中断します");
					return;
				}
			}
		});
	}

	private List<Map<String, String>> readTargetList(String targetListFilePath) {
		List<Map<String, String>> result = new ArrayList<>();
		try (FileInputStream fs = new FileInputStream(targetListFilePath);
				InputStreamReader sr = new InputStreamReader(fs);
				BufferedReader br = new BufferedReader(sr)) {
			String line = br.readLine();
			while(line != null) {
				String[] param = line.split(",");
				if (param.length == 3) {
					Map<String, String> map = new HashMap<>();
					map.put(MAPKEY_SETTING_CODE, param[0].trim());
					map.put(MAPKEY_CSV_FILEPATH, param[1].trim());
					map.put(MAPKEY_CONTINUE_FLG, param[2].trim());
					result.add(map);
				} else {
					throw new IOException("[" + (result.size() + 1) + "]行目の取り込み対象リストの項目数が不正です。[正常:3、入力:" + param.length + "]");
				}
				line = br.readLine();
			}
		} catch (IOException e) {
			LogManager.err(e);
		}
		return result;
	}
}
