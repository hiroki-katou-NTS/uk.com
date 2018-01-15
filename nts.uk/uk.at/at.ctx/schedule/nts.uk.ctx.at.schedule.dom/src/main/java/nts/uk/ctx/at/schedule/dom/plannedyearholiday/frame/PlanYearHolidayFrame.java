/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

@Getter
@Setter
/**
 * The Class PlanYearHolidayFrame.
 */
//計画年休枠
public class PlanYearHolidayFrame extends AggregateRoot{
	
	/** The company id. */
	// 会社ID
	private String companyId;
	
	/** The plan year holiday fr no. */
	//計画年休枠NO
	private PlanYearHolidayFrameNo planYearHolidayFrNo;
	
	/** The use classification. */
	//使用区分
	private NotUseAtr useClassification;
	
	/** The plan year holiday fr name. */
	//計画年休枠名称
	private PlanYearHolidayFrameName planYearHolidayFrName;
	
	/**
	 * Instantiates a new plan year holiday frame.
	 *
	 * @param memento the memento
	 */
	public PlanYearHolidayFrame(PlanYearHolidayFrameGetMemento memento) {
		this.companyId = memento.getCompanyId().v();
		this.planYearHolidayFrNo = memento.getPlanYearHolidayFrameNo();
		this.useClassification = memento.getUseClassification();
		this.planYearHolidayFrName = memento.getPlanYearHolidayFrameName();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(PlanYearHolidayFrameSetMemento memento) {
		memento.setCompanyId(new CompanyId(this.companyId));
		memento.setPlanYearHolidayFrameNo(this.planYearHolidayFrNo);
		memento.setUseClassification(this.useClassification);
		memento.setPlanYearHolidayFrameName(this.planYearHolidayFrName);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((planYearHolidayFrNo == null) ? 0 : planYearHolidayFrNo.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
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
		PlanYearHolidayFrame other = (PlanYearHolidayFrame) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		
		if (planYearHolidayFrNo == null) {
			if (other.planYearHolidayFrNo != null)
				return false;
		} else if (!planYearHolidayFrNo.equals(other.planYearHolidayFrNo))
			return false;
		
		return true;
	}
}
