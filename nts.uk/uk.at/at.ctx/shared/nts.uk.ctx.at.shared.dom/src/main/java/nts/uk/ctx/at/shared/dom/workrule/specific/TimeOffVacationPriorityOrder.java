package nts.uk.ctx.at.shared.dom.workrule.specific;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * 時間休暇相殺優先順位.
 *
 * @author HoangNDH
 */
@Data
@AllArgsConstructor
public class TimeOffVacationPriorityOrder {
	
	// 会社ID
	private CompanyId companyId;
	
	// 代休
	private int substituteHoliday;
	
	// 60H超休
	private int sixtyHourVacation;
	
	// 特別休暇
	private int specialHoliday;
	
	// 年休
	private int annualHoliday;
	
	/**
	 * Creates the from java type.
	 *
	 * @param companyId the company id
	 * @param substituteHoliday the substitute holiday
	 * @param sixtyHourVacation the sixty hour vacation
	 * @param specialHoliday the special holiday
	 * @param annualHoliday the annual holiday
	 * @return the time off vacation priority order
	 */
	public static TimeOffVacationPriorityOrder createFromJavaType (String companyId, int substituteHoliday, int sixtyHourVacation, 
			int specialHoliday, int annualHoliday) {
		return new TimeOffVacationPriorityOrder(new CompanyId(companyId), substituteHoliday, sixtyHourVacation, specialHoliday, annualHoliday);
	}
}
