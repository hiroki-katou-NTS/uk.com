package nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * スケジュールチーム名称
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務予定.社員情報.スケジュールチーム
 * @author Hieult
 *
 */

@StringMaxLength(4)
public class ScheduleTeamName extends StringPrimitiveValue<ScheduleTeamName> {

	private static final long serialVersionUID = 1L;
	
	public ScheduleTeamName(String rawValue) {
		super(rawValue);
	}

}
