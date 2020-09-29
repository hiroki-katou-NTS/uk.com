/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.holiday;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.BreakdownItemNo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.OvertimeNo;

/**
 * The Class PremiumExtra60HRate.
 */
// 60H超休の割増率
@Getter
public class PremiumExtra60HRate extends DomainObject {
	
	/** The breakdown item no. */
	// 内訳項目NO
	private BreakdownItemNo breakdownItemNo;
	
	/** The premium rate. */
	//割増率
	private PremiumRate premiumRate;
	
	/** The overtime no. */
	// 超過時間NO
	private  OvertimeNo overtimeNo;

	/**
	 * Instantiates a new premium extra 60 H rate.
	 *
	 * @param memento the memento
	 */
	public PremiumExtra60HRate(PremiumExtra60HRateGetMemento memento) {
		this.breakdownItemNo = memento.getBreakdownItemNo();
		this.premiumRate = memento.getPremiumRate();
		this.overtimeNo = memento.getOvertimeNo();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(PremiumExtra60HRateSetMemento memento){
		memento.setBreakdownItemNo(this.breakdownItemNo);
		memento.setPremiumRate(this.premiumRate);
		memento.setOvertimeNo(this.overtimeNo);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((breakdownItemNo == null) ? 0 : breakdownItemNo.hashCode());
		result = prime * result + ((overtimeNo == null) ? 0 : overtimeNo.hashCode());
		return result;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PremiumExtra60HRate other = (PremiumExtra60HRate) obj;
		if (breakdownItemNo != other.breakdownItemNo)
			return false;
		if (overtimeNo != other.overtimeNo)
			return false;
		return true;
	}
	
	
}
