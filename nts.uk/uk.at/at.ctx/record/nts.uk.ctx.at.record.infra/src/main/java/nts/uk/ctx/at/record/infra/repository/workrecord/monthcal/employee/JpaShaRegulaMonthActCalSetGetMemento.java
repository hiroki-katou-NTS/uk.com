/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.monthcal.employee;

import nts.uk.ctx.at.record.dom.workrecord.monthcal.RegularWorkTimeAggrSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.ShaRegulaMonthActCalSetGetMemento;
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.employee.KrcstShaRegMCalSet;
import nts.uk.ctx.at.record.infra.repository.workrecord.monthcal.JpaRegularWorkTimeAggrSetGetMemento;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;

/**
 * The Class JpaShaRegulaMonthActCalSetGetMemento.
 */
public class JpaShaRegulaMonthActCalSetGetMemento implements ShaRegulaMonthActCalSetGetMemento {

	/** The type value. */
	private KrcstShaRegMCalSet typeValue;

	/**
	 * Instantiates a new jpa sha regula month act cal set get memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaShaRegulaMonthActCalSetGetMemento(KrcstShaRegMCalSet typeValue) {
		super();
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.
	 * ShaRegulaMonthActCalSetGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.typeValue.getKrcstShaRegMCalSetPK().getCid());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.
	 * ShaRegulaMonthActCalSetGetMemento#getEmployeeId()
	 */
	@Override
	public EmployeeId getEmployeeId() {
		return new EmployeeId(this.typeValue.getKrcstShaRegMCalSetPK().getSid());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.
	 * ShaRegulaMonthActCalSetGetMemento#getRegularAggrSetting()
	 */
	@Override
	public RegularWorkTimeAggrSet getRegularAggrSetting() {
		return new RegularWorkTimeAggrSet(new JpaRegularWorkTimeAggrSetGetMemento<>(this.typeValue));
	}

}
