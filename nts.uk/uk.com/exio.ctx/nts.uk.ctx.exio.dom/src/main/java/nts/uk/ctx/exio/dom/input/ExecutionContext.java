package nts.uk.ctx.exio.dom.input;

import lombok.Value;
import nts.uk.ctx.exio.dom.input.canonicalize.ImportingMode;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainId;
import nts.uk.ctx.exio.dom.input.setting.DomainImportSetting;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;

/**
 * 外部受入の実行コンテキスト
 */
@Value
public class ExecutionContext {

	/** 会社ID */
	String companyId;
	
	/** 受入設定コード */
	String settingCode;
	
	/** 受入グループID */
	ImportingDomainId domainId;
	
	/** 受入モード */
	ImportingMode mode;
	
	public static ExecutionContext create(String companyId, ExternalImportCode code, DomainImportSetting source) {
		return source.executionContext(companyId, code);
	}
	
	public ExternalImportCode getExternalImportCode() {
		return new ExternalImportCode(settingCode);
	}
	
}