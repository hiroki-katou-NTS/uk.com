package nts.uk.ctx.at.schedule.dom.schedule.alarm.simultaneousattendance.ban;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
/**
 * 同時出勤禁止名称
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.勤務予定のアラームチェック.同時出勤.同時出勤禁止.同時出勤禁止名称
 * @author lan_lt
 *
 */
@StringMaxLength(20)
public class SimultaneousAttendanceBanName extends StringPrimitiveValue<SimultaneousAttendanceBanName>{
	/**	serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	public SimultaneousAttendanceBanName(String rawValue) {
		super(rawValue);
	}


}
