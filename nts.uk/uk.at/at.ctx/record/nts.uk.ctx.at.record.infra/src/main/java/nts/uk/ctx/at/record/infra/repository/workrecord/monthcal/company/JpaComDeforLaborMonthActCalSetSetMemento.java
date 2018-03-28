/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.monthcal.company;

import nts.uk.ctx.at.record.dom.workrecord.monthcal.DeforWorkTimeAggrSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.company.ComDeforLaborMonthActCalSetSetMemento;
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.company.KrcstComDeforMCalSet;
import nts.uk.ctx.at.record.infra.repository.workrecord.monthcal.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class JpaComDeforLaborMonthActCalSetSetMemento.
 */
public class JpaComDeforLaborMonthActCalSetSetMemento implements ComDeforLaborMonthActCalSetSetMemento {

	/** The type value. */
	private KrcstComDeforMCalSet typeValue;

	/**
	 * Instantiates a new jpa com defor labor month act cal set set memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaComDeforLaborMonthActCalSetSetMemento(KrcstComDeforMCalSet typeValue) {
		super();
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.company.
	 * ComDeforLaborMonthActCalSetSetMemento#setCompanyId(nts.uk.ctx.at.shared.
	 * dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		this.typeValue.setCid(companyId.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.company.
	 * ComDeforLaborMonthActCalSetSetMemento#setDeforAggrSetting(nts.uk.ctx.at.
	 * record.dom.workrecord.monthcal.DeforWorkTimeAggrSet)
	 */
	@Override
	public void setDeforAggrSetting(DeforWorkTimeAggrSet legalAggrSetOfIrgNew) {
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

}
