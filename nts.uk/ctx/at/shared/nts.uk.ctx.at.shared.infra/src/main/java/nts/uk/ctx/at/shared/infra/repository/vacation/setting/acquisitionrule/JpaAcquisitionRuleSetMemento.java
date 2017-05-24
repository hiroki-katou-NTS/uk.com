/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.acquisitionrule;

import java.util.List;

import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.AcquisitionOrder;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.AcquisitionRuleSetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.Category;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.acquisitionrule.KmfstAcquisitionRule;

/**
 * The Class JpaAcquisitionRuleSetMemento.
 */
public class JpaAcquisitionRuleSetMemento implements AcquisitionRuleSetMemento {

	/** The type value. */
	private KmfstAcquisitionRule typeValue;

	/**
	 * Instantiates a new jpa acquisition rule set memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaAcquisitionRuleSetMemento(KmfstAcquisitionRule typeValue) {
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.vacation.setting.acquisitionrule.
	 * VaAcRuleSetMemento#setCompanyId(java.lang.String)
	 */
	@Override
	public void setCompanyId(String companyId) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.vacation.setting.acquisitionrule.
	 * VaAcRuleSetMemento#setSettingclassification(nts.uk.ctx.pr.core.dom.
	 * vacation.setting.acquisitionrule.Settingclassification)
	 */
	@Override
	public void setSettingclassification(Category settingclassification) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.vacation.setting.acquisitionrule.
	 * VaAcRuleSetMemento#setAcquisitionOrder(java.util.List)
	 */
	@Override
	public void setAcquisitionOrder(List<AcquisitionOrder> listVacationAcquisitionOrder) {
		// TODO Auto-generated method stub

	}

}
