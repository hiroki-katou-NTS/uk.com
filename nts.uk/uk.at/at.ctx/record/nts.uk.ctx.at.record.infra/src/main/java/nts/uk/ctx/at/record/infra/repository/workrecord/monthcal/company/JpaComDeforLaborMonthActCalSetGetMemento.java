/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.monthcal.company;

import nts.uk.ctx.at.record.dom.workrecord.monthcal.DeforWorkTimeAggrSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.company.ComDeforLaborMonthActCalSetGetMemento;
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.company.KrcstComDeforMCalSet;
import nts.uk.ctx.at.record.infra.repository.workrecord.monthcal.JpaDeforWorkTimeAggrSetGetMemento;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class JpaComDeforLaborMonthActCalSetGetMemento.
 */
public class JpaComDeforLaborMonthActCalSetGetMemento
		implements ComDeforLaborMonthActCalSetGetMemento {

	/** The type value. */
	private KrcstComDeforMCalSet typeValue;

	/**
	 * Instantiates a new jpa com defor labor month act cal set get memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaComDeforLaborMonthActCalSetGetMemento(KrcstComDeforMCalSet typeValue) {
		super();
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.company.
	 * ComDeforLaborMonthActCalSetGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.typeValue.getCid());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.company.
	 * ComDeforLaborMonthActCalSetGetMemento#getAggrSetting()
	 */
	@Override
	public DeforWorkTimeAggrSet getDeforAggrSetting() {
		return new DeforWorkTimeAggrSet(new JpaDeforWorkTimeAggrSetGetMemento<>(this.typeValue));
	}

}
