/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.monthcal.company;

import nts.uk.ctx.at.record.dom.workrecord.monthcal.RegularWorkTimeAggrSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.company.ComRegulaMonthActCalSetSetMemento;
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.company.KrcstComRegMCalSet;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class JpaWorkfixedGetMemento.
 */
public class JpaComRegulaMonthActCalSetSetMemento implements ComRegulaMonthActCalSetSetMemento {

	/** The type value. */
	private KrcstComRegMCalSet typeValue;

	/**
	 * Instantiates a new jpa com regula month act cal set set memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaComRegulaMonthActCalSetSetMemento(KrcstComRegMCalSet typeValue) {
		super();
		this.typeValue = typeValue;
	}

	@Override
	public void setCompanyId(CompanyId companyId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setAggrSetting(RegularWorkTimeAggrSet legalAggrSetOfRegNew) {
		// TODO Auto-generated method stub

	}

}
