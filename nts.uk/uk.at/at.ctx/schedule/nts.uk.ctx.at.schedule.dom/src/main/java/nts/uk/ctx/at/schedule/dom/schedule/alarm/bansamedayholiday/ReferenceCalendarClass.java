package nts.uk.ctx.at.schedule.dom.schedule.alarm.bansamedayholiday;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.ClassificationCode;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkdayDivision;
/**
 * 営業日カレンダーの参照先(分類)
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.勤務予定のアラームチェック.同日休日禁止
 * @author lan_lt
 *
 */
@Getter
@AllArgsConstructor
public class ReferenceCalendarClass implements ReferenceCalendar{
	
	private final ClassificationCode clsCode;

	
	@Override
	public BusinessDaysCalendarType getBusinessDaysCalendarType() {
		return BusinessDaysCalendarType.CLASSSICATION;
	}

	@Override
	public Optional<WorkdayDivision> getWorkdayDivision(Require require, GeneralDate date) {
		return require.getCalendarClassByDay(this.clsCode.v(), date).map(c -> c.getWorkDayDivision());
	}

}
