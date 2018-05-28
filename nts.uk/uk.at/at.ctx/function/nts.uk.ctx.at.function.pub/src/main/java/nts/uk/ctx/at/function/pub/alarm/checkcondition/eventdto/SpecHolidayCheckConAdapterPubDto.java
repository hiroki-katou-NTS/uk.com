package nts.uk.ctx.at.function.pub.alarm.checkcondition.eventdto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SpecHolidayCheckConAdapterPubDto {
	/**ID*/
	private String errorAlarmCheckID;
	/**比較演算子*/
	private int compareOperator; 
	/**所定公休日数との差分の日数1*/
	private int numberDayDiffHoliday1;
	/**所定公休日数との差分の日数2*/
	private Integer numberDayDiffHoliday2;
	public SpecHolidayCheckConAdapterPubDto(String errorAlarmCheckID, int compareOperator, int numberDayDiffHoliday1, Integer numberDayDiffHoliday2) {
		super();
		this.errorAlarmCheckID = errorAlarmCheckID;
		this.compareOperator = compareOperator;
		this.numberDayDiffHoliday1 = numberDayDiffHoliday1;
		this.numberDayDiffHoliday2 = numberDayDiffHoliday2;
	}
	
	
}
