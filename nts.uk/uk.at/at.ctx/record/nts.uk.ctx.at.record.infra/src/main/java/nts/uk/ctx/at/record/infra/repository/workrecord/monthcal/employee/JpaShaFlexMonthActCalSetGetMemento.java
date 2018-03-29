/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.monthcal.employee;

import nts.uk.ctx.at.record.dom.workrecord.monthcal.FlexMonthWorkTimeAggrSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.ShaFlexMonthActCalSetGetMemento;
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.employee.KrcstShaFlexMCalSet;
import nts.uk.ctx.at.record.infra.repository.workrecord.monthcal.JpaFlexMonthWorkTimeAggrSetGetMemento;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;

/**
 * The Class JpaShaFlexMonthActCalSetGetMemento.
 */
public class JpaShaFlexMonthActCalSetGetMemento implements ShaFlexMonthActCalSetGetMemento {

	/** The type value. */
	private KrcstShaFlexMCalSet typeValue;

	/**
	 * Instantiates a new jpa sha flex month act cal set get memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaShaFlexMonthActCalSetGetMemento(KrcstShaFlexMCalSet typeValue) {
		super();
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.
	 * ShaFlexMonthActCalSetGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.typeValue.getKrcstShaFlexMCalSetPK().getCid());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.
	 * ShaFlexMonthActCalSetGetMemento#getEmployeeId()
	 */
	@Override
	public EmployeeId getEmployeeId() {
		return new EmployeeId(this.typeValue.getKrcstShaFlexMCalSetPK().getSid());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.
	 * ShaFlexMonthActCalSetGetMemento#getFlexAggrSetting()
	 */
	@Override
	public FlexMonthWorkTimeAggrSet getFlexAggrSetting() {
		return new FlexMonthWorkTimeAggrSet(new JpaFlexMonthWorkTimeAggrSetGetMemento<>(this.typeValue));
	}

}
