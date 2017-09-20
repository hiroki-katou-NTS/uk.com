/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;

/**
 * The Class CompanyCompensatoryLeave.
 */
// 60H超休管理設定
@Getter
public class Com60HourVacation extends DomainObject {

	/** The company id. */
	// 会社ID
	private String companyId;

	/** The setting. */
	// 設定
	private SixtyHourVacationSetting setting;

	/**
	 * Checks if is managed.
	 *
	 * @return true, if is managed
	 */
	public boolean isManaged() {
		return this.setting.getIsManage().equals(ManageDistinct.YES);
	}

	/**
	 * Instantiates a new com 60 hour vacation.
	 *
	 * @param companyId the company id
	 * @param setting the setting
	 */
	public Com60HourVacation(String companyId, SixtyHourVacationSetting setting) {
		super();
		this.companyId = companyId;
		this.setting = setting;
	}

	// =================== Memento State Support Method ===================
	/**
	 * Instantiates a new labor insurance office.
	 *
	 * @param memento
	 *            the memento
	 */
	public Com60HourVacation(Com60HourVacationGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.setting = memento.getSetting();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(Com60HourVacationSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setSetting(this.setting);
	}

}
