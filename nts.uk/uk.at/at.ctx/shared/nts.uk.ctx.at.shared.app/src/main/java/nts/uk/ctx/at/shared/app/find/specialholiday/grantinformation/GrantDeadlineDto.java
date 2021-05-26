package nts.uk.ctx.at.shared.app.find.specialholiday.grantinformation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.specialholiday.periodinformation.GrantDeadline;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GrantDeadlineDto {

	/** 期限指定方法 */
	private int timeSpecifyMethod;
	
	/** 有効期限 */
	private SpecialVacationDeadlineDto expirationDate;
	
	/** 蓄積上限 */
	private LimitAccumulationDaysDto limitAccumulationDays;
	
	public static GrantDeadlineDto fromDomain(GrantDeadline domain) {
		
		return new GrantDeadlineDto(
				domain.getTimeSpecifyMethod().value,
				domain.getExpirationDate().isPresent() ? new SpecialVacationDeadlineDto(domain.getExpirationDate().get().getMonths().v(), domain.getExpirationDate().get().getYears().v())  : null,
				domain.getLimitAccumulationDays().isPresent() ? new LimitAccumulationDaysDto(domain.getLimitAccumulationDays().get().isLimit(),
						domain.getLimitAccumulationDays().get().getLimitCarryoverDays().isPresent() ? domain.getLimitAccumulationDays().get().getLimitCarryoverDays().get().v() : null) : null);
	}
	
}
