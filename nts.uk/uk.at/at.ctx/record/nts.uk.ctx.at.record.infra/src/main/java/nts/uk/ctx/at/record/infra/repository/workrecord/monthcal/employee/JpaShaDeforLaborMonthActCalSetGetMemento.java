/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.monthcal.employee;

import nts.uk.ctx.at.record.dom.workrecord.monthcal.DeforWorkTimeAggrSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.ShaDeforLaborMonthActCalSetGetMemento;
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.employee.KrcstShaDeforMCalSet;
import nts.uk.ctx.at.record.infra.repository.workrecord.monthcal.JpaDeforWorkTimeAggrSetGetMemento;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;

/**
 * The Class JpaShaDeforLaborMonthActCalSetGetMemento.
 */
public class JpaShaDeforLaborMonthActCalSetGetMemento implements ShaDeforLaborMonthActCalSetGetMemento {

	/** The type value. */
	private KrcstShaDeforMCalSet typeValue;

	/**
	 * Instantiates a new jpa sha defor labor month act cal set get memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaShaDeforLaborMonthActCalSetGetMemento(KrcstShaDeforMCalSet typeValue) {
		super();
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.
	 * ShaDeforLaborMonthActCalSetGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.typeValue.getKrcstShaDeforMCalSetPK().getCid());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.
	 * ShaDeforLaborMonthActCalSetGetMemento#getEmployeeId()
	 */
	@Override
	public EmployeeId getEmployeeId() {
		return new EmployeeId(this.typeValue.getKrcstShaDeforMCalSetPK().getSid());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.
	 * ShaDeforLaborMonthActCalSetGetMemento#getDeforAggrSetting()
	 */
	@Override
	public DeforWorkTimeAggrSet getDeforAggrSetting() {
		return new DeforWorkTimeAggrSet(new JpaDeforWorkTimeAggrSetGetMemento<>(this.typeValue));
	}

}
