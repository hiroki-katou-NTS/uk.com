package nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition;

import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.days.MonthlyDays;

/**
 * 所定公休日数チェック条件
 * @author tutk
 *
 */
@Getter
public class SpecHolidayCheckCon extends AggregateRoot {
	/**ID*/
	private String errorAlarmCheckID;
	/**比較演算子*/
	private int compareOperator; 
	/**所定公休日数との差分の日数1*/
	private MonthlyDays numberDayDiffHoliday1;
	/**所定公休日数との差分の日数2*/
	private Optional<MonthlyDays> numberDayDiffHoliday2;
	public SpecHolidayCheckCon(String errorAlarmCheckID, int compareOperator, MonthlyDays numberDayDiffHoliday1, MonthlyDays numberDayDiffHoliday2) {
		super();
		this.errorAlarmCheckID = errorAlarmCheckID;
		this.compareOperator = compareOperator;
		this.numberDayDiffHoliday1 = numberDayDiffHoliday1;
		this.numberDayDiffHoliday2 = Optional.ofNullable(numberDayDiffHoliday2);
	}
	
}
