/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.monthcal.company;

import nts.uk.ctx.at.record.dom.workrecord.monthcal.FlexMonthWorkTimeAggrSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.company.ComFlexMonthActCalSetGetMemento;
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.company.KrcstComFlexMCalSet;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class JpaWorkfixedGetMemento.
 */
public class JpaComFlexMonthActCalSetGetMemento implements ComFlexMonthActCalSetGetMemento {

	/** The type value. */
	private KrcstComFlexMCalSet typeValue;

	/**
	 * Instantiates a new jpa com flex month act cal set get memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaComFlexMonthActCalSetGetMemento(KrcstComFlexMCalSet typeValue) {
		super();
		this.typeValue = typeValue;
	}

	@Override
	public CompanyId getCompanyId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FlexMonthWorkTimeAggrSet getAggrSetting() {
		// TODO Auto-generated method stub
		return null;
	}

}
