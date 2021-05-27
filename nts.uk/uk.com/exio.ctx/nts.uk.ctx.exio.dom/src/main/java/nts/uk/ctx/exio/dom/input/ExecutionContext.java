package nts.uk.ctx.exio.dom.input;

import lombok.Value;
import nts.uk.ctx.exio.dom.input.canonicalize.ImportingMode;

/**
 * 外部受入の実行コンテキスト
 */
@Value
public class ExecutionContext {

	/** 会社ID */
	String companyId;
	
	/** カテゴリID */
	int categoryId;
	
	/** 受入モード */
	ImportingMode importingMode;
}