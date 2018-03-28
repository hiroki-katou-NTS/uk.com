/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.monthcal.company;

import nts.uk.ctx.at.record.dom.workrecord.monthcal.FlexMonthWorkTimeAggrSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.company.ComFlexMonthActCalSetGetMemento;
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.company.KrcstComFlexMCalSet;
import nts.uk.ctx.at.record.infra.repository.workrecord.monthcal.JpaFlexMonthWorkTimeAggrSetGetMemento;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class JpaComFlexMonthActCalSetGetMemento.
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.company.
	 * ComFlexMonthActCalSetGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.typeValue.getCid());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.company.
	 * ComFlexMonthActCalSetGetMemento#getFlexAggrSetting()
	 */
	@Override
	public FlexMonthWorkTimeAggrSet getFlexAggrSetting() {
		return new FlexMonthWorkTimeAggrSet(new JpaFlexMonthWorkTimeAggrSetGetMemento<>(this.typeValue));
	}

}
