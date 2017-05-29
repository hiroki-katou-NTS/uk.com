/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.vacation.setting.ExpirationTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeVacationDigestiveUnit;

/**
 * The Class NormalVacationSetting.
 */

/**
 * Gets the digestive unit.
 *
 * @return the digestive unit
 */
@Getter
public class NormalVacationSetting extends DomainObject{
	
	/** The expiration time. */
	private ExpirationTime expirationTime;
	
	/** The preemption permit. */
	private Boolean preemptionPermit;
	
	/** The is manage by time. */
	private ManageDistinct isManageByTime;
	
	/** The digestive unit. */
	private TimeVacationDigestiveUnit digestiveUnit; 
	
	/**
	 * Instantiates a new normal vacation setting.
	 *
	 * @param memento the memento
	 */
	public NormalVacationSetting(NormalVacationGetMemento memento) {
		this.expirationTime = memento.getExpirationTime();
		this.preemptionPermit = memento.getPreemptionPermit();
		this.isManageByTime = memento.getIsManageByTime();
		this.digestiveUnit = memento.getdigestiveUnit();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(NormalVacationSetMemento memento) {
		memento.setDigestiveUnit(this.digestiveUnit);
		memento.setExpirationTime(this.expirationTime);
		memento.setIsManageByTime(this.isManageByTime);
		memento.setPreemptionPermit(this.preemptionPermit);
	}
 }
