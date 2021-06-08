package nts.uk.ctx.exio.app.input.prepare;

import lombok.Value;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;

/**
 * 外部受入の準備
 */
@Value
public class ExternalImportPrepareCommand {

	/** 受入設定コード */
	String settingCode;
	
	/** アップロードした受入CSVファイルのID */
	String uploadedCsvFileId;
	
	public ExternalImportCode getExternalImportCode() {
		return new ExternalImportCode(settingCode);
	}
}
