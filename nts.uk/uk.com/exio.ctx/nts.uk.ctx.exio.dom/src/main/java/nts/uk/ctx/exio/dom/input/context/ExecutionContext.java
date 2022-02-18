package nts.uk.ctx.exio.dom.input.context;

import nts.uk.ctx.exio.dom.input.canonicalize.ImportingMode;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainId;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;

public interface ExecutionContext {

	public String getCompanyId();
	
	public String getSettingCode();
	
	public 	ImportingDomainId getDomainId();
	
	public ImportingMode getMode();
	
	default boolean isImportingWithEmployeeBasic() {
		return false;
	}
	
	default ExternalImportCode getExternalImportCode() {
		return new ExternalImportCode(getSettingCode());
	}
	
	/**
	 * 実行コンテキストを個人基本情報へ偽装 
	 */
	default ExecutionContext impersonateEmployeeBasicExecutionContext() {
		return new DefaultExecutionContext(
				this.getCompanyId(),
				this.getSettingCode(),
				ImportingDomainId.EMPLOYEE_BASIC,
				this.getMode());
	}
	
	public static ExecutionContext createForErrorTableName(String companyId) {
		//companyIdを埋めて自分が作れればOKなので、準備・実行どっちでもいい。
		return new DefaultExecutionContext(companyId, "", null, null); 
	}
}