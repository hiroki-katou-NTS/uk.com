/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.outsideot.holiday;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.command.outsideot.overtime.premium.PremiumExtra60HRateSaveDto;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.holiday.PremiumExtra60HRate;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.holiday.SuperHD60HConMedGetMemento;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.holiday.SuperHDOccUnit;

/**
 * The Class SuperHD60HConMedSaveDto.
 */

@Getter
@Setter
public class SuperHD60HConMedSaveDto implements SuperHD60HConMedGetMemento{

	/** The rounding time. */
	private Integer roundingTime;
	
	/** The rounding. */
	private Integer rounding;
	
	/** The super holiday occurrence unit. */
	private Integer superHolidayOccurrenceUnit;
	
	/** The premium extra 60 H rates. */
	private List<PremiumExtra60HRateSaveDto> premiumExtra60HRates;
	
	/** The company id. */
	private String companyId;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.overtime.holiday.SuperHD60HConMedGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.holiday.SuperHD60HConMedGetMemento#
	 * getTimeRoundingSetting()
	 */
	@Override
	public TimeRoundingSetting getTimeRoundingSetting() {
		return new TimeRoundingSetting(Unit.valueOf(this.roundingTime),
				Rounding.valueOf(this.rounding));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.holiday.SuperHD60HConMedGetMemento#
	 * getSuperHolidayOccurrenceUnit()
	 */
	@Override
	public SuperHDOccUnit getSuperHolidayOccurrenceUnit() {
		return new SuperHDOccUnit(this.superHolidayOccurrenceUnit);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.holiday.SuperHD60HConMedGetMemento#
	 * getPremiumExtra60HRates()
	 */
	@Override
	public List<PremiumExtra60HRate> getPremiumExtra60HRates() {
		return this.premiumExtra60HRates.stream().map(dto -> new PremiumExtra60HRate(dto))
				.collect(Collectors.toList());
	}

}
