package nts.uk.ctx.at.shared.dom;

import nts.uk.ctx.at.shared.dom.common.color.ColorCode;
import nts.uk.ctx.at.shared.dom.worktime.common.AbolishAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDisplayName;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDivision;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeNote;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.CalculateMethod;
import nts.uk.ctx.at.shared.dom.worktype.DailyWork;
import nts.uk.ctx.at.shared.dom.worktype.DeprecateClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeAbbreviationName;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeMemo;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeName;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeSymbolicName;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeUnit;
import nts.uk.shr.com.primitive.Memo;

public class WorkinfoHelper {
	public static WorkTimeSetting getWorkTimeSettingDefault() {
		return new WorkTimeSetting("companyId",
				new WorkTimeCode("WorkTimeCode"),
				new WorkTimeDivision(), AbolishAtr.ABOLISH, new ColorCode("colorCode"),
				new WorkTimeDisplayName(), new Memo("memo"), new WorkTimeNote("WorkTimeNote"));

	}

	public static WorkType createWorkType(boolean isDeprecated, String cid, String code, String name, String abbr, String symbol, DailyWork dailyWork) {
		return new WorkType(
						cid										// 会社ID
					,	new WorkTypeCode(code)					// 勤務種類コード
					,	new WorkTypeSymbolicName(symbol)		// 記号
					,	new WorkTypeName(name)					// 名称
					,	new WorkTypeAbbreviationName(abbr)		// 略名
					,	new WorkTypeMemo("")					// メモ
					,	dailyWork	// 1日
					,	(isDeprecated) ? DeprecateClassification.Deprecated : DeprecateClassification.NotDeprecated	// 廃止区分
					,	CalculateMethod.MAKE_ATTENDANCE_DAY	// 計算方法
				);
	}

	public static DailyWork createDailyWorkAsOneDay(WorkTypeClassification forOneDay) {
		return new DailyWork(WorkTypeUnit.OneDay, forOneDay, WorkTypeClassification.Holiday, WorkTypeClassification.Holiday);
	}

	public static DailyWork createDailyWorkAsHalfDay(WorkTypeClassification forAm, WorkTypeClassification forPm) {
		return new DailyWork(WorkTypeUnit.MonringAndAfternoon, WorkTypeClassification.Holiday, forAm, forPm);
	}
}
