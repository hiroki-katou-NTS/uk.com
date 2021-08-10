/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workdayoff.frame;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;

@Getter
@Setter
/**
 * The Class WorkdayoffFrame.
 */
//休出枠
public class WorkdayoffFrame extends AggregateRoot{
	
	/** The company id. */
	// 会社ID
	private String companyId;
	
	/** The workdayoff fr no. */
	//休出枠NO
	private WorkdayoffFrameNo workdayoffFrNo;
	
	/** The use classification. */
	//使用区分
	private NotUseAtr useClassification;
	
	/** The transfer fr name. */
	//振替枠名称
	private WorkdayoffFrameName transferFrName;
	
	/** The workdayoff fr name. */
	//休出枠名称
	private WorkdayoffFrameName workdayoffFrName;
	
	/** The role */
	//役割
	private WorkdayoffFrameRole role;
	
	/**
	 * Instantiates a new workdayoff frame.
	 *
	 * @param memento the memento
	 */
	public WorkdayoffFrame(WorkdayoffFrameGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.workdayoffFrNo = memento.getWorkdayoffFrameNo();
		this.useClassification = memento.getUseClassification();
		this.transferFrName = memento.getTransferFrameName();
		this.workdayoffFrName = memento.getWorkdayoffFrameName();
		this.role = memento.getRole();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(WorkdayoffFrameSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setWorkdayoffFrameNo(this.workdayoffFrNo);
		memento.setUseClassification(this.useClassification);
		memento.setTransferFrameName(this.transferFrName);
		memento.setWorkdayoffFrameName(this.workdayoffFrName);
		memento.setRole(role);
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
		result = prime * result + ((workdayoffFrNo == null) ? 0 : workdayoffFrNo.hashCode());
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
		WorkdayoffFrame other = (WorkdayoffFrame) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		
		if (workdayoffFrNo == null) {
			if (other.workdayoffFrNo != null)
				return false;
		} else if (!workdayoffFrNo.equals(other.workdayoffFrNo))
			return false;
		
		return true;
	}
	
	public boolean isUse() {
		return this.useClassification == NotUseAtr.USE;
	}
}
