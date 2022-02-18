package nts.uk.ctx.exio.dom.input.context;

import lombok.Value;
import nts.uk.ctx.exio.dom.input.canonicalize.ImportingMode;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainId;

/**
 * 外部受入の実行コンテキスト
 */
@Value
public class DefaultExecutionContext implements ExecutionContext{

	/** 会社ID */
	String companyId;
	
	/** 受入設定コード */
	String settingCode;
	
	/** 受入グループID */
	ImportingDomainId domainId;
	
	/** 受入モード */
	ImportingMode mode;

}
