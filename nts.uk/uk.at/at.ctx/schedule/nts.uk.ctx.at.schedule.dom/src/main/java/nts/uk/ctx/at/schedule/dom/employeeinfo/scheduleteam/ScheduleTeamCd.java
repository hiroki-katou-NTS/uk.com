package nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
import nts.uk.shr.com.primitive.ZeroPaddedCode;


/**
 * スケジュールチームコード
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務予定.社員情報.スケジュールチーム
 * @author Hieult
 *
 */
@StringMaxLength(2)
@StringCharType(CharType.NUMERIC)
@ZeroPaddedCode
public class ScheduleTeamCd extends CodePrimitiveValue<ScheduleTeamCd>{
	private static final long serialVersionUID = 1L;

	public ScheduleTeamCd(String rawValue) {
		super(rawValue);
	}
	
}
