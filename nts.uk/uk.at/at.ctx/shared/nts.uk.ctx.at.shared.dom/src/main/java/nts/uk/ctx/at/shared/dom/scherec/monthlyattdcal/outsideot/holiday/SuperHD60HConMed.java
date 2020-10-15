/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.holiday;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;

/**
 * The Class SuperHD60HConMed.
 */
// 60H超休への換算方法
@Getter
public class SuperHD60HConMed extends AggregateRoot{

	/** The company id. */
	// 会社ID
	private CompanyId companyId;
	
	/** The time rounding setting. */
	// 時間丸め設定
	private TimeRoundingSetting timeRoundingSetting;
	
	
	/** The super holiday occurrence unit. */
	// 超休発生単位
	private SuperHDOccUnit superHolidayOccurrenceUnit;
	
	/** The premium extra 60 H rates. */
	// 60H超休の割増率一覧
	private List<PremiumExtra60HRate> premiumExtra60HRates;
	
	
	/**
	 * Instantiates a new super HD 60 H con med.
	 *
	 * @param memento the memento
	 */
	public SuperHD60HConMed(SuperHD60HConMedGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.timeRoundingSetting = memento.getTimeRoundingSetting();
		this.superHolidayOccurrenceUnit = memento.getSuperHolidayOccurrenceUnit();
		this.premiumExtra60HRates = memento.getPremiumExtra60HRates();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(SuperHD60HConMedSetMemento memento){
		memento.setCompanyId(this.companyId);
		memento.setTimeRoundingSetting(this.timeRoundingSetting);
		memento.setSuperHolidayOccurrenceUnit(this.superHolidayOccurrenceUnit);
		memento.setPremiumExtra60HRates(this.premiumExtra60HRates);
	}
}
