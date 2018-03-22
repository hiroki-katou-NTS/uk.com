/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.monthcal.company;

import nts.uk.ctx.at.record.dom.workrecord.monthcal.RegularWorkTimeAggrSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.company.ComRegulaMonthActCalSetGetMemento;
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.company.KrcstComRegMCalSet;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class JpaWorkFixedSetMemento.
 */
public class JpaComRegulaMonthActCalSetGetMemento implements ComRegulaMonthActCalSetGetMemento {

	/** The type value. */
	private KrcstComRegMCalSet typeValue;

	/**
	 * Instantiates a new jpa com regula month act cal set get memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaComRegulaMonthActCalSetGetMemento(KrcstComRegMCalSet typeValue) {
		super();
		this.typeValue = typeValue;
	}

	@Override
	public CompanyId getCompanyId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RegularWorkTimeAggrSet getAggrSetting() {
		// TODO Auto-generated method stub
		return null;
	}

}
