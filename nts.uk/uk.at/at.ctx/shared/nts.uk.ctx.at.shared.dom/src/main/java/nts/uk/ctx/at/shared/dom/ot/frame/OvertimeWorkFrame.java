/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.ot.frame;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roleofovertimework.roleofovertimework.RoleOvertimeWorkEnum;

@Getter
@Setter
/**
 * The Class OvertimeWorkFrame.
 */
//残業枠枠
public class OvertimeWorkFrame extends AggregateRoot{
	
	/** The company id. */
	// 会社ID
	private String companyId;
	
	/** The overtime work fr no. */
	//残業枠NO
	private OvertimeWorkFrameNo overtimeWorkFrNo;
	
	/** The use classification. */
	//使用区分
	private NotUseAtr useClassification;
	
	/** The transfer fr name. */
	//振替枠名称
	private OvertimeWorkFrameName transferFrName;
	
	/** The overtime work fr name. */
	//残業枠名称
	private OvertimeWorkFrameName overtimeWorkFrName;
	
	/** The role. */
	// 役割
	private RoleOvertimeWorkEnum role;
	
	/** The transfer atr. */
	// 代休振替対象
	private NotUseAtr transferAtr;
	
	/**
	 * Instantiates a new overtime work frame.
	 *
	 * @param memento the memento
	 */
	public OvertimeWorkFrame(OvertimeWorkFrameGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.overtimeWorkFrNo = memento.getOvertimeWorkFrameNo();
		this.useClassification = memento.getUseClassification();
		this.transferFrName = memento.getTransferFrameName();
		this.overtimeWorkFrName = memento.getOvertimeWorkFrameName();
		this.role = memento.getRole();
		this.transferAtr = memento.getTransferAtr();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(OvertimeWorkFrameSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setOvertimeWorkFrameNo(this.overtimeWorkFrNo);
		memento.setUseClassification(this.useClassification);
		memento.setTransferFrameName(this.transferFrName);
		memento.setOvertimeWorkFrameName(this.overtimeWorkFrName);
		memento.setRole(this.role);
		memento.setTransferAtr(this.transferAtr);
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
		result = prime * result + ((overtimeWorkFrNo == null) ? 0 : overtimeWorkFrNo.hashCode());
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
		OvertimeWorkFrame other = (OvertimeWorkFrame) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		
		if (overtimeWorkFrNo == null) {
			if (other.overtimeWorkFrNo != null)
				return false;
		} else if (!overtimeWorkFrNo.equals(other.overtimeWorkFrNo))
			return false;
		
		return true;
	}
	
	public boolean isUse() {
		return useClassification == NotUseAtr.USE;
	}
}
