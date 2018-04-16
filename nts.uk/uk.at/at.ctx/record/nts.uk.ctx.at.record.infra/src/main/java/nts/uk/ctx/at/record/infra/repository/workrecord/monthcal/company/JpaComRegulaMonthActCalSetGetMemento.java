/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.monthcal.company;

import nts.uk.ctx.at.record.dom.workrecord.monthcal.RegularWorkTimeAggrSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.company.ComRegulaMonthActCalSetGetMemento;
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.company.KrcstComRegMCalSet;
import nts.uk.ctx.at.record.infra.repository.workrecord.monthcal.JpaRegularWorkTimeAggrSetGetMemento;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class JpaComRegulaMonthActCalSetGetMemento.
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.company.
	 * ComRegulaMonthActCalSetGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.typeValue.getCid());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.company.
	 * ComRegulaMonthActCalSetGetMemento#getRegulaAggrSetting()
	 */
	@Override
	public RegularWorkTimeAggrSet getRegulaAggrSetting() {
		return new RegularWorkTimeAggrSet(new JpaRegularWorkTimeAggrSetGetMemento<>(typeValue));
	}

}
