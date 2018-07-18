package nts.uk.ctx.at.shared.dom.specialholidaynew.periodinformation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 特別休暇の有効期限
 * 
 * @author tanlv
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SpecialVacationDeadline {
	/** 月数 */
	private SpecialVacationMonths months;
	
	/** 年数 */
	private SpecialVacationYears years;

	public static SpecialVacationDeadline createFromJavaType(SpecialVacationMonths months, SpecialVacationYears years) {
		return new SpecialVacationDeadline(months, years);
	}
}
