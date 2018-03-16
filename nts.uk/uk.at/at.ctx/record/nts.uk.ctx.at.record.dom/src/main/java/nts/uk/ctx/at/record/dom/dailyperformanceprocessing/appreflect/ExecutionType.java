package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect;

import lombok.AllArgsConstructor;
/**
 * 実行種別	
 * @author do_dt
 *
 */
@AllArgsConstructor
public enum ExecutionType {
	/**通常実行*/
	EXCECUTION(0, "勤務予定"),
	/**	再実行*/
	RETURN(1, "勤務実績");
	
	public final Integer value;
	
	public final String name;
}
