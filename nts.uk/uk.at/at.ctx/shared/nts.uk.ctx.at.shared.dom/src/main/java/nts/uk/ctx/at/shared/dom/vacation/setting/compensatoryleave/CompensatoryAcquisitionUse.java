/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.vacation.setting.ApplyPermission;
import nts.uk.ctx.at.shared.dom.vacation.setting.ExpirationTime;

/**
 * The Class CompensatoryAcquisitionUse.
 */
// 代休取得・使用方法
@Getter
public class CompensatoryAcquisitionUse extends DomainObject {
	
	// 休暇使用期限
	/** The expiration time. */
	private ExpirationTime expirationTime;
	
	//先取り許可
	/** The preemption permit. */
	private ApplyPermission preemptionPermit;
	
	// 代休期限チェック月数
	private DeadlCheckMonth deadlCheckMonth;
	
	//期限日の管理方法 
	private TermManagement TermManagement;
	/**
	 * Instantiates a new compensatory acquisition use.
	 *
	 * @param memento the memento
	 */
	public CompensatoryAcquisitionUse(CompensatoryAcquisitionUseGetMemento memento) {
		this.expirationTime = memento.getExpirationTime();
		this.preemptionPermit = memento.getPreemptionPermit();
		this.deadlCheckMonth = memento.getDeadlCheckMonth();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(CompensatoryAcquisitionUseSetMemento memento) {
		memento.setExpirationTime(this.expirationTime);
		memento.setPreemptionPermit(this.preemptionPermit);
		memento.setDeadlCheckMonth(this.deadlCheckMonth);
	}
}
