package nts.uk.ctx.exio.app.input.execute;

import lombok.Value;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;

@Value
public class ExternalImportExecuteCommand {

	/** 受入設定コード */
	String settingCode;
	
	public ExternalImportCode getExternalImportCode() {
		return new ExternalImportCode(settingCode);
	}
}
