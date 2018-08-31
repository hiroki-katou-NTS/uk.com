package nts.uk.ctx.at.shared.dom.specialholiday.periodinformation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
	private StartDate startDate;
	
	/** 年数 */
	private EndDate endDate;

	public static AvailabilityPeriod createFromJavaType(StartDate startDate, EndDate endDate) {
		return new AvailabilityPeriod(startDate, endDate);
	}
}
