/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.monthcal.employment;

import nts.uk.ctx.at.record.dom.workrecord.monthcal.DeforWorkTimeAggrSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.EmpDeforLaborMonthActCalSetGetMemento;
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.employment.KrcstEmpDeforMCalSet;
import nts.uk.ctx.at.record.infra.repository.workrecord.monthcal.JpaDeforWorkTimeAggrSetGetMemento;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

/**
 * The Class JpaEmpDeforLaborMonthActCalSetGetMemento.
 */
public class JpaEmpDeforLaborMonthActCalSetGetMemento implements EmpDeforLaborMonthActCalSetGetMemento {

	/** The type value. */
	private KrcstEmpDeforMCalSet typeValue;

	/**
	 * Instantiates a new jpa emp defor labor month act cal set get memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaEmpDeforLaborMonthActCalSetGetMemento(KrcstEmpDeforMCalSet typeValue) {
		super();
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.
	 * EmpDeforLaborMonthActCalSetGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.typeValue.getKrcstEmpDeforMCalSetPK().getCid());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.
	 * EmpDeforLaborMonthActCalSetGetMemento#getEmploymentCode()
	 */
	@Override
	public EmploymentCode getEmploymentCode() {
		return new EmploymentCode(this.typeValue.getKrcstEmpDeforMCalSetPK().getEmpCd());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.
	 * EmpDeforLaborMonthActCalSetGetMemento#getDeforAggrSetting()
	 */
	@Override
	public DeforWorkTimeAggrSet getDeforAggrSetting() {
		return new DeforWorkTimeAggrSet(new JpaDeforWorkTimeAggrSetGetMemento<>(this.typeValue));
	}

}
