/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.monthcal.employment;

import nts.uk.ctx.at.record.dom.workrecord.monthcal.FlexMonthWorkTimeAggrSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.EmpFlexMonthActCalSetGetMemento;
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.employment.KrcstEmpFlexMCalSet;
import nts.uk.ctx.at.record.infra.repository.workrecord.monthcal.JpaFlexMonthWorkTimeAggrSetGetMemento;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

/**
 * The Class JpaEmpFlexMonthActCalSetGetMemento.
 */
public class JpaEmpFlexMonthActCalSetGetMemento implements EmpFlexMonthActCalSetGetMemento {

	/** The type value. */
	private KrcstEmpFlexMCalSet typeValue;

	/**
	 * Instantiates a new jpa emp flex month act cal set get memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaEmpFlexMonthActCalSetGetMemento(KrcstEmpFlexMCalSet typeValue) {
		super();
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.
	 * EmpFlexMonthActCalSetGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.typeValue.getKrcstEmpFlexMCalSetPK().getCid());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.
	 * EmpFlexMonthActCalSetGetMemento#getEmploymentCode()
	 */
	@Override
	public EmploymentCode getEmploymentCode() {
		return new EmploymentCode(this.typeValue.getKrcstEmpFlexMCalSetPK().getEmpCd());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.
	 * EmpFlexMonthActCalSetGetMemento#getFlexAggrSetting()
	 */
	@Override
	public FlexMonthWorkTimeAggrSet getFlexAggrSetting() {
		return new FlexMonthWorkTimeAggrSet(new JpaFlexMonthWorkTimeAggrSetGetMemento<>(this.typeValue));
	}

}
