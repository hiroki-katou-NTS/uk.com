package nts.uk.ctx.at.function.dom.processexecution;

import lombok.AllArgsConstructor;

/**
 * 実行範囲区分
 */
@AllArgsConstructor
public enum ExecutionScopeClassification {
	/* 会社 */
	COMPANY(0),
	
	/* 職場 */
	WORKPLACE(1);
	
	/** The value. */
	public final int value;
}
