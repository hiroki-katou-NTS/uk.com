package nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.dailymonthlyprocessing;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ExecutionTypeExImport {
	/**
	 * 0: 通常実行
	 */
	NORMAL_EXECUTION(0),
	
	/**
	 * 1: 再実行
	 */
	RERUN(1);
	
	public final int value;

}
