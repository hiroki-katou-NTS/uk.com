/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.monthcal.employee;

import nts.uk.ctx.at.record.dom.workrecord.monthcal.DeforWorkTimeAggrSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.ShaDeforLaborMonthActCalSetSetMemento;
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.employee.KrcstShaDeforMCalSet;
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.employee.KrcstShaDeforMCalSetPK;
import nts.uk.ctx.at.record.infra.repository.workrecord.monthcal.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;

/**
 * The Class JpaShaDeforLaborMonthActCalSetSetMemento.
 */
public class JpaShaDeforLaborMonthActCalSetSetMemento implements ShaDeforLaborMonthActCalSetSetMemento {

	/** The type value. */
	private KrcstShaDeforMCalSet typeValue;

	/**
	 * Instantiates a new jpa sha defor labor month act cal set set memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaShaDeforLaborMonthActCalSetSetMemento(KrcstShaDeforMCalSet typeValue) {
		super();
		if(typeValue.getKrcstShaDeforMCalSetPK() == null){				
			typeValue.setKrcstShaDeforMCalSetPK(new KrcstShaDeforMCalSetPK());
		}
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.
	 * ShaDeforLaborMonthActCalSetSetMemento#setCompanyId(nts.uk.ctx.at.shared.
	 * dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId cid) {
		this.typeValue.getKrcstShaDeforMCalSetPK().setCid(cid.v());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.
	 * ShaDeforLaborMonthActCalSetSetMemento#setAggrSetting(nts.uk.ctx.at.record
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
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.
	 * ShaDeforLaborMonthActCalSetSetMemento#setEmployeeId(nts.uk.ctx.at.shared.
	 * dom.common.EmployeeId)
	 */
	@Override
	public void setEmployeeId(EmployeeId sid) {
		this.typeValue.getKrcstShaDeforMCalSetPK().setSid(sid.v());
	}

}
