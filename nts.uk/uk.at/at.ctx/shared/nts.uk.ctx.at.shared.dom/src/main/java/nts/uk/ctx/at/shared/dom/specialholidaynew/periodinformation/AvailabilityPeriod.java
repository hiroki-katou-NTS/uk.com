package nts.uk.ctx.at.shared.dom.specialholidaynew.periodinformation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

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
	private GeneralDate startDate;
	
	/** 年数 */
	private GeneralDate endDate;

	public static AvailabilityPeriod createFromJavaType(GeneralDate startDate, GeneralDate endDate) {
		return new AvailabilityPeriod(startDate, endDate);
	}
}
