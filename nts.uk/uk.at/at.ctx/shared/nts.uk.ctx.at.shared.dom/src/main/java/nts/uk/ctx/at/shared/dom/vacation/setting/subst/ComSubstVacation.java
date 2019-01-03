/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.subst;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;

/**
 * The Class CompanyCompensatoryLeave.
 */
// 振休管理設定
@Getter
public class ComSubstVacation extends DomainObject {

	/** The company id. */
	// 会社ID
	private String companyId;

	/** The setting. */
	// 振休取得・使用方法
	private SubstVacationSetting setting;

	/**
	 * Checks if is managed.
	 *
	 * @return true, if is managed
	 */
	public boolean isManaged() {
		return this.setting.getIsManage().equals(ManageDistinct.YES);
	}

	/**
	 * Instantiates a new company compensatory leave.
	 *
	 * @param companyId
	 *            the company id
	 * @param setting
	 *            the setting
	 */
	public ComSubstVacation(String companyId, SubstVacationSetting setting) {
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
	public ComSubstVacation(ComSubstVacationGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.setting = memento.getSetting();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(ComSubstVacationSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setSetting(this.setting);
	}

}
