package nts.uk.client.exi;

import java.util.List;
import java.util.stream.Collectors;

import lombok.val;
import nts.uk.client.exi.dom.FileUploader;
import nts.uk.client.exi.dom.csvimport.ExternalImport;

public class ExcecuteImportService {

	public ExcecuteImportService () {
	}

	public void doWork() {
		LogManager.init();
		
		// ファイルアップロード
		val fileUploader = new FileUploader();
		List<String> fileIds = fileUploader.doWork().stream()
				.map(info -> info.getId())
				.collect(Collectors.toList());

		for(String fileId : fileIds) {
			// 外部受入の呼び出し
			val externalImport = new ExternalImport();
			if(!externalImport.doWork(fileId)) {
				LogManager.err("エラーが発生しました。処理を中断します");
				return;
			}
		}
	}
}
