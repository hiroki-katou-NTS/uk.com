package nts.uk.ctx.at.shared.dom.specialholiday.periodinformation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.shr.com.time.calendar.MonthDay;

/**
 * 使用可能期間 
 * 
 * @author tanlv
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AvailabilityPeriod {
	/** 月数 */
	private MonthDay startDate;
	
	/** 年数 */
	private MonthDay endDate;

	public static AvailabilityPeriod createFromJavaType(Integer startDate, Integer endDate) {

		MonthDay start = startDate != null ? createMonthDayFromInt(startDate) : null;

		MonthDay end = endDate != null ? createMonthDayFromInt(endDate) : null;

		return new AvailabilityPeriod(start, end);
	}

	private static MonthDay createMonthDayFromInt(Integer value) {

		return new MonthDay(value / 100, value % 100);
	}
	
	public Integer getStartDateValue(){
		MonthDay startDate = this.startDate;
		return startDate != null ? startDate.getMonth() * 100 + startDate.getDay() : null;
	}
	
	public Integer getEndDateValue() {
		MonthDay endDate = this.endDate;
		return endDate != null ? endDate.getMonth() * 100 + endDate.getDay() : null;
	}
}
