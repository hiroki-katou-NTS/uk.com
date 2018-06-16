package nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.monthlycheckcondition;

import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.days.MonthlyDays;

@Getter
@Setter
@NoArgsConstructor
public class SpecHolidayCheckConPubEx {
	/**ID*/
	private String errorAlarmCheckID;
	/**比較演算子*/
	private int compareOperator; 
	/**所定公休日数との差分の日数1*/
	private int numberDayDiffHoliday1;
	/**所定公休日数との差分の日数2*/
	private Integer numberDayDiffHoliday2;
	public SpecHolidayCheckConPubEx(String errorAlarmCheckID, int compareOperator, int numberDayDiffHoliday1, Integer numberDayDiffHoliday2) {
		super();
		this.errorAlarmCheckID = errorAlarmCheckID;
		this.compareOperator = compareOperator;
		this.numberDayDiffHoliday1 = numberDayDiffHoliday1;
		this.numberDayDiffHoliday2 = numberDayDiffHoliday2;
	}
	
	
}
