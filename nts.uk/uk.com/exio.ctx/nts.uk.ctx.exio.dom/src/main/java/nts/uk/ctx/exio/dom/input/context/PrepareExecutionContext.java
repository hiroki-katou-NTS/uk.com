package nts.uk.ctx.exio.dom.input.context;

import lombok.Value;
import nts.uk.ctx.exio.dom.input.canonicalize.ImportingMode;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainId;

/**
 * 受入準備時の実行コンテキスト 
 */
@Value
public class PrepareExecutionContext implements ExecutionContext{

	/** 会社ID */
	String companyId;
	
	/** 受入設定コード */
	String settingCode;
	
	/** 受入グループID */
	ImportingDomainId domainId;
	
	/** 受入モード */
	ImportingMode mode;
	
	/** 個人基本情報と一緒に取込もうとしているか*/
	boolean importingWithEmployeeBasic;
	
	@Override
	public boolean isImportingWithEmployeeBasic() {
		return this.importingWithEmployeeBasic;
	}
}
