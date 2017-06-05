/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeVacationDigestiveUnit;

/**
 * Gets the digestive unit.
 *
 * @return the digestive unit
 */
@Getter
public class EmploymentCompensatoryTimeManageSetting {
	// 管理区分
	/** The is managed. */
	private ManageDistinct isManaged;

	// 消化単位
	/** The digestive unit. */
	private TimeVacationDigestiveUnit digestiveUnit;

	/**
	 * Instantiates a new employment compensatory time manage setting.
	 *
	 * @param memento
	 *            the memento
	 */
	public EmploymentCompensatoryTimeManageSetting(EmploymentTimeManageGetMemento memento) {
		this.isManaged = memento.getIsManaged();
		this.digestiveUnit = memento.getDigestiveUnit();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(EmploymentTimeManageSetMemento memento) {
		memento.setIsManaged(this.isManaged);
		memento.setDigestiveUnit(this.digestiveUnit);
	}
}
