package nts.uk.ctx.at.schedule.app.command.executionlog;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.schedule.setting.UseAtr;

/**
 * 
 * @author sonnh1
 *
 */
@Getter
@Setter
public class PersonalSchedule {
	// パターンコード
	private String patternCd;
	// 休日反映方法
	private int holidayReflect;
	// パターン開始日
	private GeneralDate patternStartDate;
	// 法内休日利用区分
	private UseAtr legalHolidayUseAtr;
	// 法内休日勤務種類
	private String legalHolidayWorkType;
	// 法外休日利用区分
	private UseAtr statutoryHolidayUseAtr;
	// 法外休日勤務種類
	private String statutoryHolidayWorkType;
	// 祝日利用区分
	private UseAtr holidayUseAtr;
	// 祝日勤務種類
	private String holidayWorkType;
}
