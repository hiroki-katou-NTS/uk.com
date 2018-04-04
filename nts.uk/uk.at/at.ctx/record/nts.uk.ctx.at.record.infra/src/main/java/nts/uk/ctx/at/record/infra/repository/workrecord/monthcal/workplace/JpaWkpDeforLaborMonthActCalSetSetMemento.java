/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.monthcal.workplace;

import nts.uk.ctx.at.record.dom.workrecord.monthcal.DeforWorkTimeAggrSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.WkpDeforLaborMonthActCalSetSetMemento;
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.workplace.KrcstWkpDeforMCalSet;
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.workplace.KrcstWkpDeforMCalSetPK;
import nts.uk.ctx.at.record.infra.repository.workrecord.monthcal.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;

/**
 * The Class JpaWkpDeforLaborMonthActCalSetSetMemento.
 */
public class JpaWkpDeforLaborMonthActCalSetSetMemento implements WkpDeforLaborMonthActCalSetSetMemento {

	/** The type value. */
	private KrcstWkpDeforMCalSet typeValue;

	/**
	 * Instantiates a new jpa wkp defor labor month act cal set set memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaWkpDeforLaborMonthActCalSetSetMemento(KrcstWkpDeforMCalSet typeValue) {
		super();
		if(typeValue.getKrcstWkpDeforMCalSetPK() == null){				
			typeValue.setKrcstWkpDeforMCalSetPK(new KrcstWkpDeforMCalSetPK());
		}
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.
	 * WkpDeforLaborMonthActCalSetSetMemento#setCompanyId(nts.uk.ctx.at.shared.
	 * dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId cid) {
		this.typeValue.getKrcstWkpDeforMCalSetPK().setCid(cid.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.
	 * WkpDeforLaborMonthActCalSetSetMemento#setAggrSetting(nts.uk.ctx.at.record
	 * .dom.workrecord.monthcal.DeforWorkTimeAggrSet)
	 */
	@Override
	public void setAggrSetting(DeforWorkTimeAggrSet legalAggrSetOfIrgNew) {
		
		this.typeValue.setIncludeLegalAggr(
				BooleanGetAtr.getAtrByBoolean(legalAggrSetOfIrgNew.getAggregateTimeSet().getLegalOverTimeWork()));
		this.typeValue.setIncludeHolidayAggr(
				BooleanGetAtr.getAtrByBoolean(legalAggrSetOfIrgNew.getAggregateTimeSet().getLegalHoliday()));
		this.typeValue.setIncludeExtraAggr(
				BooleanGetAtr.getAtrByBoolean(legalAggrSetOfIrgNew.getAggregateTimeSet().getSurchargeWeekMonth()));
		
		this.typeValue.setIncludeLegalOt(
				BooleanGetAtr.getAtrByBoolean(legalAggrSetOfIrgNew.getExcessOutsideTimeSet().getLegalOverTimeWork()));
		this.typeValue.setIncludeHolidayOt(
				BooleanGetAtr.getAtrByBoolean(legalAggrSetOfIrgNew.getExcessOutsideTimeSet().getLegalHoliday()));
		this.typeValue.setIncludeExtraOt(
				BooleanGetAtr.getAtrByBoolean(legalAggrSetOfIrgNew.getExcessOutsideTimeSet().getSurchargeWeekMonth()));
		
		this.typeValue.setIsOtIrg(BooleanGetAtr.getAtrByBoolean(legalAggrSetOfIrgNew.getDeforLaborCalSetting().isOtTransCriteria()));
		
		this.typeValue.setPeriod(legalAggrSetOfIrgNew.getSettlementPeriod().getPeriod().v());
		this.typeValue.setStrMonth(legalAggrSetOfIrgNew.getSettlementPeriod().getStartMonth().v());
		this.typeValue.setRepeatAtr(BooleanGetAtr.getAtrByBoolean(legalAggrSetOfIrgNew.getSettlementPeriod().getRepeatAtr()));	

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.
	 * WkpDeforLaborMonthActCalSetSetMemento#setWorkplaceId(nts.uk.ctx.at.shared
	 * .dom.common.WorkplaceId)
	 */
	@Override
	public void setWorkplaceId(WorkplaceId wkpId) {
		this.typeValue.getKrcstWkpDeforMCalSetPK().setWkpId(wkpId.v());
	}

}
