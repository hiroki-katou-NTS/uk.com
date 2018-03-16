package nts.uk.ctx.at.record.pub.dailyperform.appreflect;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum DegreeReflectionPubAtr {
	/**勤務予定 */
	SCHEDULE(0, "勤務予定"),
	/**	勤務実績 */
	RECORD(1, "勤務実績");
	
	public final Integer value;
	
	public final String name;
}
