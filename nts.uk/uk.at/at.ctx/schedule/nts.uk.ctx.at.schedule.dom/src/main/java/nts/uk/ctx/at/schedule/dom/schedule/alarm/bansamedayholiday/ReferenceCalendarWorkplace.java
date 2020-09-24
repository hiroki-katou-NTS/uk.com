package nts.uk.ctx.at.schedule.dom.schedule.alarm.bansamedayholiday;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkdayDivision;
/**
 * 営業日カレンダーの参照先(職場)
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.勤務予定のアラームチェック.同日休日禁止
 * @author lan_lt
 *
 */
@Getter
@AllArgsConstructor
public class ReferenceCalendarWorkplace implements ReferenceCalendar{
	
	private  final String workplaceID;
	
	@Override
	public BusinessDaysCalendarType getBusinessDaysCalendarType() {
		return BusinessDaysCalendarType.WORKPLACE;
	}

	@Override
	public Optional<WorkdayDivision> getWorkdayDivision(Require require, GeneralDate day) {
		return require.getCalendarWorkplaceByDay(this.workplaceID, day).map(c -> c.getWorkDayDivision());
	}

}
