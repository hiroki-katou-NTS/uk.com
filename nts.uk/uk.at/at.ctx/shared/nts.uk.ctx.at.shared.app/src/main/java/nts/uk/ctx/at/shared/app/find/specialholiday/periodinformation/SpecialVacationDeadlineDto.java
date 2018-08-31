package nts.uk.ctx.at.shared.app.find.specialholiday.periodinformation;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.specialholiday.periodinformation.SpecialVacationDeadline;

@Value
public class SpecialVacationDeadlineDto {
	/** 月数 */
	private int months;
	
	/** 年数 */
	private int years;

	public static SpecialVacationDeadlineDto fromDomain(SpecialVacationDeadline expirationDate) {
		if(expirationDate == null) {
			return null;
		}
		
		return new SpecialVacationDeadlineDto(
				expirationDate.getMonths().v(),
				expirationDate.getYears().v()
		);
	}
}
