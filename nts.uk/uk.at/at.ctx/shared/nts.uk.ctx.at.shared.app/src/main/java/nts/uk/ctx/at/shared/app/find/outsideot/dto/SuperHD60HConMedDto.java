/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.outsideot.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.holiday.SuperHD60HConMed;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.holiday.SuperHDOccUnit;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class SuperHD60HConMedDto.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SuperHD60HConMedDto {
	
	/** The is setting. */
	private boolean setting;

	/** The rounding time. */
	private Integer roundingTime;
	
	/** The rounding. */
	private Integer rounding;
	
	/** The super holiday occurrence unit. */
	private Integer superHolidayOccurrenceUnit;
	
	/** The premium extra 60 H rates. */
	private List<PremiumExtra60HRateDto> premiumExtra60HRates;

	public static SuperHD60HConMedDto of (SuperHD60HConMed domain) {
		
		return new SuperHD60HConMedDto(true, domain.getTimeRoundingSetting().getRoundingTime().value, 
										domain.getTimeRoundingSetting().getRounding().value, 
										domain.getSuperHolidayOccurrenceUnit().v(), new ArrayList<>());
	}
	
	public SuperHD60HConMed domain() {
		
		return new SuperHD60HConMed(AppContexts.user().companyId(), 
					new TimeRoundingSetting(roundingTime, rounding), 
					new SuperHDOccUnit(superHolidayOccurrenceUnit));
	}
}
