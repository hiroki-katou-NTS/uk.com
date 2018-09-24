package nts.uk.ctx.at.shared.app.find.specialholiday.periodinformation;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.specialholiday.periodinformation.GrantPeriodic;

@Value
public class GrantPeriodicDto {
	/** 会社ID */
	private String companyId;
	
	/** 特別休暇コード */
	private int specialHolidayCode;
	
	/** 期限指定方法 */
	private int timeSpecifyMethod;
	
	/** 使用可能期間 */
	private AvailabilityPeriodDto availabilityPeriod;
	
	/** 有効期限 */
	private SpecialVacationDeadlineDto expirationDate;
	
	/** 繰越上限日数 */
	private int limitCarryoverDays;

	public static GrantPeriodicDto fromDomain(GrantPeriodic grantPeriodic) {
		if(grantPeriodic == null) {
			return null;
		}
		
		AvailabilityPeriodDto availabilityPeriodDto = new AvailabilityPeriodDto(grantPeriodic.getAvailabilityPeriod().getStartDateValue(), grantPeriodic.getAvailabilityPeriod().getEndDateValue());
		
		SpecialVacationDeadlineDto expirationDate = SpecialVacationDeadlineDto.fromDomain(grantPeriodic.getExpirationDate() != null ? grantPeriodic.getExpirationDate() : null);
		
		return new GrantPeriodicDto(
				grantPeriodic.getCompanyId(),
				grantPeriodic.getSpecialHolidayCode().v(),
				grantPeriodic.getTimeSpecifyMethod().value,
				availabilityPeriodDto,
				expirationDate,
				grantPeriodic.getLimitCarryoverDays().v()
		);
	}
}
