/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.outsideot.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.holiday.PremiumExtra60HRate;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.holiday.SuperHD60HConMedSetMemento;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.holiday.SuperHDOccUnit;

/**
 * The Class SuperHD60HConMedDto.
 */
@Getter
@Setter
public class SuperHD60HConMedDto implements SuperHD60HConMedSetMemento{
	
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.holiday.SuperHD60HConMedSetMemento#
	 * setCompanyId(nts.uk.ctx.at.shared.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		// No thing code
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.holiday.SuperHD60HConMedSetMemento#
	 * setTimeRoundingSetting(nts.uk.ctx.at.shared.dom.common.timerounding.
	 * TimeRoundingSetting)
	 */
	@Override
	public void setTimeRoundingSetting(TimeRoundingSetting timeRoundingSetting) {
		this.roundingTime = timeRoundingSetting.getRoundingTime().value;
		this.rounding = timeRoundingSetting.getRounding().value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.holiday.SuperHD60HConMedSetMemento#
	 * setSuperHolidayOccurrenceUnit(nts.uk.ctx.at.shared.dom.overtime.holiday.
	 * SuperHDOccUnit)
	 */
	@Override
	public void setSuperHolidayOccurrenceUnit(SuperHDOccUnit superHolidayOccurrenceUnit) {
		this.superHolidayOccurrenceUnit = superHolidayOccurrenceUnit.valueAsMinutes();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.holiday.SuperHD60HConMedSetMemento#
	 * setPremiumExtra60HRates(java.util.List)
	 */
	@Override
	public void setPremiumExtra60HRates(List<PremiumExtra60HRate> premiumExtra60HRates) {
		if (!CollectionUtil.isEmpty(premiumExtra60HRates)) {
			this.premiumExtra60HRates = premiumExtra60HRates.stream().map(domain -> {
				PremiumExtra60HRateDto dto = new PremiumExtra60HRateDto();
				domain.saveToMemento(dto);
				return dto;
			}).collect(Collectors.toList());
		}
	}
}
