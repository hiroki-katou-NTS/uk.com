/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.overtime.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.overtime.OvertimeNo;
import nts.uk.ctx.at.shared.dom.overtime.breakdown.BreakdownItemNo;
import nts.uk.ctx.at.shared.dom.overtime.premium.PremiumRate;
import nts.uk.ctx.at.shared.dom.overtime.premium.extra.PremiumExtra60HRateSetMemento;

/**
 * The Class PremiumExtra60HRateDto.
 */

@Getter
@Setter
public class PremiumExtra60HRateDto implements PremiumExtra60HRateSetMemento{

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
	 * PremiumExtra60HRateSetMemento#setBreakdownItemNo(nts.uk.ctx.at.shared.dom
	 * .overtime.breakdown.BreakdownItemNo)
	 */
	@Override
	public void setBreakdownItemNo(BreakdownItemNo breakdownItemNo) {
		this.breakdownItemNo = breakdownItemNo.value;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.overtime.premium.extra.
	 * PremiumExtra60HRateSetMemento#setPremiumRate(nts.uk.ctx.at.shared.dom.
	 * overtime.premium.PremiumRate)
	 */
	@Override
	public void setPremiumRate(PremiumRate premiumRate) {
		this.premiumRate = premiumRate.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.overtime.premium.extra.
	 * PremiumExtra60HRateSetMemento#setOvertimeNo(nts.uk.ctx.at.shared.dom.
	 * overtime.OvertimeNo)
	 */
	@Override
	public void setOvertimeNo(OvertimeNo overtimeNo) {
		this.overtimeNo = overtimeNo.value;
	}

}
