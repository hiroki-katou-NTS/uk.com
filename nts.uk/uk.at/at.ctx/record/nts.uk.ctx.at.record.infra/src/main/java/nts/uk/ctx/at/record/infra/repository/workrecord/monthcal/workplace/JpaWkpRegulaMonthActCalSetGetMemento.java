/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.monthcal.workplace;

import nts.uk.ctx.at.record.dom.workrecord.monthcal.RegularWorkTimeAggrSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.WkpRegulaMonthActCalSetGetMemento;
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.workplace.KrcstWkpRegMCalSet;
import nts.uk.ctx.at.record.infra.repository.workrecord.monthcal.JpaRegularWorkTimeAggrSetGetMemento;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;

/**
 * The Class JpaWkpRegulaMonthActCalSetGetMemento.
 */
public class JpaWkpRegulaMonthActCalSetGetMemento implements WkpRegulaMonthActCalSetGetMemento {

	/** The type value. */
	private KrcstWkpRegMCalSet typeValue;

	/**
	 * Instantiates a new jpa wkp regula month act cal set get memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaWkpRegulaMonthActCalSetGetMemento(KrcstWkpRegMCalSet typeValue) {
		super();
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.
	 * WkpRegulaMonthActCalSetGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.typeValue.getKrcstWkpRegMCalSetPK().getCid());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.
	 * WkpRegulaMonthActCalSetGetMemento#getWorkplaceId()
	 */
	@Override
	public WorkplaceId getWorkplaceId() {
		return new WorkplaceId(this.typeValue.getKrcstWkpRegMCalSetPK().getWkpid());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.
	 * WkpRegulaMonthActCalSetGetMemento#getRegularAggrSetting()
	 */
	@Override
	public RegularWorkTimeAggrSet getRegularAggrSetting() {
		return new RegularWorkTimeAggrSet(new JpaRegularWorkTimeAggrSetGetMemento<>(this.typeValue));
	}

}
