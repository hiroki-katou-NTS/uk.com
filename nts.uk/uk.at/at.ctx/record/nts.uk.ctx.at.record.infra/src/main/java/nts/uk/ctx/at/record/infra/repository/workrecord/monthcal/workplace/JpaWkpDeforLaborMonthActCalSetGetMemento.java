/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.monthcal.workplace;

import nts.uk.ctx.at.record.dom.workrecord.monthcal.DeforWorkTimeAggrSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.WkpDeforLaborMonthActCalSetGetMemento;
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.workplace.KrcstWkpDeforMCalSet;
import nts.uk.ctx.at.record.infra.repository.workrecord.monthcal.JpaDeforWorkTimeAggrSetGetMemento;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;

/**
 * The Class JpaWkpDeforLaborMonthActCalSetGetMemento.
 */
public class JpaWkpDeforLaborMonthActCalSetGetMemento implements WkpDeforLaborMonthActCalSetGetMemento {

	/** The type value. */
	private KrcstWkpDeforMCalSet typeValue;

	/**
	 * Instantiates a new jpa wkp defor labor month act cal set get memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaWkpDeforLaborMonthActCalSetGetMemento(KrcstWkpDeforMCalSet typeValue) {
		super();
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.
	 * WkpDeforLaborMonthActCalSetGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.typeValue.getKrcstWkpDeforMCalSetPK().getCid());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.
	 * WkpDeforLaborMonthActCalSetGetMemento#getWorkplaceId()
	 */
	@Override
	public WorkplaceId getWorkplaceId() {
		return new WorkplaceId(this.typeValue.getKrcstWkpDeforMCalSetPK().getWkpId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.
	 * WkpDeforLaborMonthActCalSetGetMemento#getDeforAggrSetting()
	 */
	@Override
	public DeforWorkTimeAggrSet getDeforAggrSetting() {
		return new DeforWorkTimeAggrSet(new JpaDeforWorkTimeAggrSetGetMemento<>(this.typeValue));
	}

}
