package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output;

import lombok.AllArgsConstructor;

/**
 * @author nampt<br/>
 * 手動 - 0<br/>
 * 自動 - 1
 */
@AllArgsConstructor
public enum ExecutionAttr {
	/* 手動  */
	MANUAL(0),

	/* 自動 */
	AUTO(1);
	
	public final int value;

}
