package nts.uk.ctx.at.function.pub.alarm.checkcondition.eventdto;

import java.math.BigDecimal;

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
	private BigDecimal numberDayDiffHoliday1;
	/**所定公休日数との差分の日数2*/
	private BigDecimal numberDayDiffHoliday2;
	public SpecHolidayCheckConAdapterPubDto(String errorAlarmCheckID, int compareOperator, BigDecimal numberDayDiffHoliday1, BigDecimal numberDayDiffHoliday2) {
		super();
		this.errorAlarmCheckID = errorAlarmCheckID;
		this.compareOperator = compareOperator;
		this.numberDayDiffHoliday1 = numberDayDiffHoliday1;
		this.numberDayDiffHoliday2 = numberDayDiffHoliday2;
	}
	
	
}
