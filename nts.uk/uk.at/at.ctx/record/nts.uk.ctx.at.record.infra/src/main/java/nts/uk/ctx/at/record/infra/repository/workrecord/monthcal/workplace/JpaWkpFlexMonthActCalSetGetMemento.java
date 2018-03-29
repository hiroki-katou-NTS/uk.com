/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.monthcal.workplace;

import nts.uk.ctx.at.record.dom.workrecord.monthcal.FlexMonthWorkTimeAggrSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.WkpFlexMonthActCalSetGetMemento;
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.workplace.KrcstWkpFlexMCalSet;
import nts.uk.ctx.at.record.infra.repository.workrecord.monthcal.JpaFlexMonthWorkTimeAggrSetGetMemento;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;

/**
 * The Class JpaWkpFlexMonthActCalSetGetMemento.
 */
public class JpaWkpFlexMonthActCalSetGetMemento implements WkpFlexMonthActCalSetGetMemento {

	/** The type value. */
	private KrcstWkpFlexMCalSet typeValue;

	/**
	 * Instantiates a new jpa wkp flex month act cal set get memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaWkpFlexMonthActCalSetGetMemento(KrcstWkpFlexMCalSet typeValue) {
		super();
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.
	 * WkpFlexMonthActCalSetGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.typeValue.getKrcstWkpFlexMCalSetPK().getCid());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.
	 * WkpFlexMonthActCalSetGetMemento#getWorkplaceId()
	 */
	@Override
	public WorkplaceId getWorkplaceId() {
		return new WorkplaceId(this.typeValue.getKrcstWkpFlexMCalSetPK().getWkpId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.
	 * WkpFlexMonthActCalSetGetMemento#getFlexAggrSetting()
	 */
	@Override
	public FlexMonthWorkTimeAggrSet getFlexAggrSetting() {
		return new FlexMonthWorkTimeAggrSet(new JpaFlexMonthWorkTimeAggrSetGetMemento<>(this.typeValue));
	}

}
