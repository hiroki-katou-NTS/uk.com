/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.overtime.premium;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.outsideot.breakdown.BreakdownItemNo;
import nts.uk.ctx.at.shared.dom.outsideot.overtime.OvertimeNo;
import nts.uk.ctx.at.shared.dom.outsideot.premium.PremiumRate;
import nts.uk.ctx.at.shared.dom.outsideot.premium.extra.PremiumExtra60HRateGetMemento;

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
