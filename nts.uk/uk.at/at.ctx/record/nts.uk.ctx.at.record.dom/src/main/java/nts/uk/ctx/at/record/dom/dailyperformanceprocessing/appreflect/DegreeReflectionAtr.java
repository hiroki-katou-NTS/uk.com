package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect;

import lombok.AllArgsConstructor;

/**
 * 反映先区分（”勤務予定”、”勤務実績”）
 * @author do_dt
 *
 */
@AllArgsConstructor
public enum DegreeReflectionAtr {
	/**勤務予定 */
	SCHEDULE(0, "勤務予定"),
	/**	勤務実績 */
	RECORD(1, "勤務実績");
	
	public final Integer value;
	
	public final String name;
}
