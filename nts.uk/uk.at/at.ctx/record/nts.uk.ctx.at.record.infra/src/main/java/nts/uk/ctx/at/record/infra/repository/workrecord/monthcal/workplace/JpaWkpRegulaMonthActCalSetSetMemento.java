/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.monthcal.workplace;

import nts.uk.ctx.at.record.dom.workrecord.monthcal.RegularWorkTimeAggrSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.WkpRegulaMonthActCalSetSetMemento;
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.workplace.KrcstWkpRegMCalSet;
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.workplace.KrcstWkpRegMCalSetPK;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;

/**
 * The Class JpaWkpRegulaMonthActCalSetSetMemento.
 */
public class JpaWkpRegulaMonthActCalSetSetMemento implements WkpRegulaMonthActCalSetSetMemento {

	/** The type value. */
	private KrcstWkpRegMCalSet typeValue;

	/**
	 * Instantiates a new jpa wkp regula month act cal set set memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaWkpRegulaMonthActCalSetSetMemento(KrcstWkpRegMCalSet typeValue) {
		super();
		if(typeValue.getKrcstWkpRegMCalSetPK() == null){				
			typeValue.setKrcstWkpRegMCalSetPK(new KrcstWkpRegMCalSetPK());
		}
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.
	 * WkpRegulaMonthActCalSetSetMemento#setCompanyId(nts.uk.ctx.at.shared.dom.
	 * common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId cid) {
		this.typeValue.getKrcstWkpRegMCalSetPK().setCid(cid.v());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.
	 * WkpRegulaMonthActCalSetSetMemento#setAggrSetting(nts.uk.ctx.at.record.dom
	 * .workrecord.monthcal.RegularWorkTimeAggrSet)
	 */
	@Override
	public void setAggrSetting(RegularWorkTimeAggrSet legalAggrSetOfRegNew) {
		this.typeValue.setIncludeLegalAggr(
				BooleanGetAtr.getAtrByBoolean(legalAggrSetOfRegNew.getAggregateTimeSet().getLegalOverTimeWork()));
		this.typeValue.setIncludeHolidayAggr(
				BooleanGetAtr.getAtrByBoolean(legalAggrSetOfRegNew.getAggregateTimeSet().getLegalHoliday()));
		this.typeValue.setIncludeExtraAggr(
				BooleanGetAtr.getAtrByBoolean(legalAggrSetOfRegNew.getAggregateTimeSet().getSurchargeWeekMonth()));
		
		this.typeValue.setIncludeLegalOt(
				BooleanGetAtr.getAtrByBoolean(legalAggrSetOfRegNew.getExcessOutsideTimeSet().getLegalOverTimeWork()));
		this.typeValue.setIncludeHolidayOt(
				BooleanGetAtr.getAtrByBoolean(legalAggrSetOfRegNew.getExcessOutsideTimeSet().getLegalHoliday()));
		this.typeValue.setIncludeExtraOt(
				BooleanGetAtr.getAtrByBoolean(legalAggrSetOfRegNew.getExcessOutsideTimeSet().getSurchargeWeekMonth()));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.
	 * WkpRegulaMonthActCalSetSetMemento#setWorkplaceId(nts.uk.ctx.at.shared.dom
	 * .common.WorkplaceId)
	 */
	@Override
	public void setWorkplaceId(WorkplaceId wkpId) {
		this.typeValue.getKrcstWkpRegMCalSetPK().setWkpid(wkpId.v());

	}

}
