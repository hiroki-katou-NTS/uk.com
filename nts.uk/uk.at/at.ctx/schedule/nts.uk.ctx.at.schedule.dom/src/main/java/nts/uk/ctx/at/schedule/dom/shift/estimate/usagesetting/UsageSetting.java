/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.usagesetting;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class UsageSetting.
 */
// 目安利用区分
@Getter
public class UsageSetting extends DomainObject {

	/** The company id. */
	// 会社ID
	private CompanyId companyId;

	/** The employment setting. */
	// 雇用利用設定
	private UseClassification employmentSetting;

	/** The personal setting. */
	// 個人利用設定
	private UseClassification personalSetting;

	/**
	 * Instantiates a new usage setting.
	 *
	 * @param memento
	 *            the memento
	 */
	public UsageSetting(UsageSettingGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.employmentSetting = memento.getEmploymentSetting();
		this.personalSetting = memento.getPersonalSetting();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(UsageSettingSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setEmploymentSetting(this.employmentSetting);
		memento.setPersonalSetting(this.personalSetting);
	}
}
