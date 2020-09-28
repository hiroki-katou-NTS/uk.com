/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.outsideot.overtime.premium;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.BreakdownItemNo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.holiday.PremiumExtra60HRateGetMemento;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.holiday.PremiumRate;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.OvertimeNo;

/**
 * The Class PremiumExtra60HRateSaveDto.
 */
@Getter
@Setter
public class PremiumExtra60HRateSaveDto implements  PremiumExtra60HRateGetMemento{

	/** The breakdown item no. */
	private Integer breakdownItemNo;
	
	/** The premium rate. */
	private Integer premiumRate;
	
	/** The overtime no. */
	private  Integer overtimeNo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.overtime.premium.extra.
	 * PremiumExtra60HRateGetMemento#getBreakdownItemNo()
	 */
	@Override
	public BreakdownItemNo getBreakdownItemNo() {
		return BreakdownItemNo.valueOf(this.breakdownItemNo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.overtime.premium.extra.
	 * PremiumExtra60HRateGetMemento#getPremiumRate()
	 */
	@Override
	public PremiumRate getPremiumRate() {
		return new PremiumRate(this.premiumRate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.overtime.premium.extra.
	 * PremiumExtra60HRateGetMemento#getOvertimeNo()
	 */
	@Override
	public OvertimeNo getOvertimeNo() {
		return OvertimeNo.valueOf(this.overtimeNo);
	}
}
