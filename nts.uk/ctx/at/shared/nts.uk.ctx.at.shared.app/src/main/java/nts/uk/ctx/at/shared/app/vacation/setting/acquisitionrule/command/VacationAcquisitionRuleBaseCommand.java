/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.vacation.setting.acquisitionrule.command;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.AcquisitionOrder;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.AcquisitionRuleGetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.Category;

/**
 * The Class VacationAcquisitionRuleBaseCommand.
 */
@Getter
@Setter
public class VacationAcquisitionRuleBaseCommand implements AcquisitionRuleGetMemento {

	/** The company id. */
	public String companyId;

	/** The setting classification. */
	public Category settingClassification;

	/** The va ac rule. */
	public List<AcquisitionOrder> vaAcRule;

	/**
	 * Gets the settingclassification.
	 *
	 * @return the settingclassification
	 */
	@Override
	public Category getSettingclassification() {
		return this.settingClassification;
	}

	/**
	 * Gets the acquisition order.
	 *
	 * @return the acquisition order
	 */
	@Override
	public List<AcquisitionOrder> getAcquisitionOrder() {
		return this.vaAcRule;
	}

	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	@Override
	public String getCompanyId() {
		return this.companyId;
	}
}
