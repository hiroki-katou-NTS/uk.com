/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate;

import lombok.Data;

/**
 * The Class UnemployeeInsuranceRateItem.
 */
@Data
public class UnemployeeInsuranceRateItem {

	/** The career group. */
	private CareerGroup careerGroup;

	/** The company setting. */
	private UnemployeeInsuranceRateItemSetting companySetting;

	/** The personal setting. */
	private UnemployeeInsuranceRateItemSetting personalSetting;

	/**
	 * Instantiates a new unemployee insurance rate item.
	 */
	public UnemployeeInsuranceRateItem() {
		super();
	}

	// =================== Memento State Support Method ===================

	/**
	 * Instantiates a new unemployee insurance rate item.
	 *
	 * @param memento
	 *            the memento
	 */
	public UnemployeeInsuranceRateItem(UnemployeeInsuranceRateItemGetMemento memento) {
		this.careerGroup = memento.getCareerGroup();
		this.companySetting = memento.getCompanySetting();
		this.personalSetting = memento.getPersonalSetting();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(UnemployeeInsuranceRateItemSetMemento memento) {
		memento.setCareerGroup(this.careerGroup);
		memento.setCompanySetting(this.companySetting);
		memento.setPersonalSetting(this.personalSetting);
	}

}
