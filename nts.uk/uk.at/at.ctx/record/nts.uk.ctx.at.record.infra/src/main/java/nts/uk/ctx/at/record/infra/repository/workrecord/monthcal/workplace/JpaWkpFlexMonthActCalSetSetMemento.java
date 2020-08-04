/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.monthcal.workplace;

import nts.uk.ctx.at.record.dom.workrecord.monthcal.FlexMonthWorkTimeAggrSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.WkpFlexMonthActCalSetSetMemento;
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.workplace.KrcstWkpFlexMCalSet;
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.workplace.KrcstWkpFlexMCalSetPK;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;

/**
 * The Class JpaWkpFlexMonthActCalSetSetMemento.
 */
public class JpaWkpFlexMonthActCalSetSetMemento implements WkpFlexMonthActCalSetSetMemento {

	/** The type value. */
	private KrcstWkpFlexMCalSet typeValue;

	/**
	 * Instantiates a new jpa wkp flex month act cal set set memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaWkpFlexMonthActCalSetSetMemento(KrcstWkpFlexMCalSet typeValue) {
		super();
		if(typeValue.getKrcstWkpFlexMCalSetPK() == null){				
			typeValue.setKrcstWkpFlexMCalSetPK(new KrcstWkpFlexMCalSetPK());
		}
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.
	 * WkpFlexMonthActCalSetSetMemento#setCompanyId(nts.uk.ctx.at.shared.dom.
	 * common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId cid) {
		this.typeValue.getKrcstWkpFlexMCalSetPK().setCid(cid.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.
	 * WkpFlexMonthActCalSetSetMemento#setAggrSetting(nts.uk.ctx.at.record.dom.
	 * workrecord.monthcal.FlexMonthWorkTimeAggrSet)
	 */
	@Override
	public void setAggrSetting(FlexMonthWorkTimeAggrSet aggrSettingMonthlyOfFlxNew) {
		
		this.typeValue.setAggrMethod(aggrSettingMonthlyOfFlxNew.getAggrMethod().value);
		this.typeValue.setInsufficSet(aggrSettingMonthlyOfFlxNew.getInsufficSet().getCarryforwardSet().value);
		this.typeValue.setLegalAggrSet(aggrSettingMonthlyOfFlxNew.getLegalAggrSet().getAggregateSet().value);
		this.typeValue.setIncludeOt(aggrSettingMonthlyOfFlxNew.getIncludeOverTime() ? 1 : 0);
		this.typeValue.setIncludeHdwk(aggrSettingMonthlyOfFlxNew.getIncludeIllegalHdwk() ? 1 : 0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.
	 * WkpFlexMonthActCalSetSetMemento#setWorkplaceId(nts.uk.ctx.at.shared.dom.
	 * common.WorkplaceId)
	 */
	@Override
	public void setWorkplaceId(WorkplaceId wkpId) {
		this.typeValue.getKrcstWkpFlexMCalSetPK().setWkpId(wkpId.v());
	}

}
