package nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * スケジュールチーム備考
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務予定.社員情報.スケジュールチーム
 * @author Hieult
 *
 */
@StringMaxLength(12)
public class ScheduleTeamRemarks extends StringPrimitiveValue<ScheduleTeamRemarks>{


	private static final long serialVersionUID = 1L;
	
	public ScheduleTeamRemarks(String rawValue) {
		super(rawValue);
	}

}
