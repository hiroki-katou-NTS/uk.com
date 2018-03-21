package nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AppDegreeReflectionAtr {
	/**勤務予定 */
	SCHEDULE(0, "勤務予定"),
	/**	勤務実績 */
	RECORD(1, "勤務実績");
	
	public final Integer value;
	
	public final String name;
}
