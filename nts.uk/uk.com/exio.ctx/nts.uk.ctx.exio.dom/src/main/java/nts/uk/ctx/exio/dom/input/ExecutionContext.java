package nts.uk.ctx.exio.dom.input;

import lombok.Value;
import nts.uk.ctx.exio.dom.input.canonicalize.ImportingMode;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainId;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;

@Value
public class ExecutionContext{

	String companyId;
	
	String settingCode;
	
	ImportingDomainId domainId;
	
	ImportingMode mode;
	
	public ExternalImportCode getExternalImportCode() {
		return new ExternalImportCode(settingCode);
	}
	
	/**
	 * 実行コンテキストを個人基本情報へ偽装 
	 */
	public ExecutionContext impersonateEmployeeBasicExecutionContext() {
		return new ExecutionContext(
				this.companyId,
				this.settingCode,
				ImportingDomainId.EMPLOYEE_BASIC,
				this.mode);
	}
	
	public static ExecutionContext createForErrorTableName(String companyId) {
		return new ExecutionContext(companyId, "", null, null); 
	}
}