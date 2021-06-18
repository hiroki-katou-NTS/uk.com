/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;

/**
 * The Class CompensatoryDigestiveTimeUnit.
 */
// 時間代休の消化単位
@Getter
public class CompensatoryDigestiveTimeUnit {
    
	// 管理区分
	/** The is manage by time. */
	private ManageDistinct isManageByTime;
	
	// 消化単位
	/** The digestive unit. */
	private TimeDigestiveUnit digestiveUnit;

	/**
	 * Instantiates a new compensatory digestive time unit.
	 *
	 * @param memento
	 *            the memento
	 */
	public CompensatoryDigestiveTimeUnit(CompensatoryDigestiveTimeUnitGetMemento memento) {
		this.isManageByTime = memento.getIsManageByTime();
		this.digestiveUnit = memento.getDigestiveUnit();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(CompensatoryDigestiveTimeUnitSetMemento memento) {
		memento.setDigestiveUnit(this.digestiveUnit);
		memento.setIsManageByTime(this.isManageByTime);
	}

	public CompensatoryDigestiveTimeUnit(ManageDistinct isManageByTime, TimeDigestiveUnit digestiveUnit) {
		super();
		this.isManageByTime = isManageByTime;
		this.digestiveUnit = digestiveUnit;
	}
	
}
