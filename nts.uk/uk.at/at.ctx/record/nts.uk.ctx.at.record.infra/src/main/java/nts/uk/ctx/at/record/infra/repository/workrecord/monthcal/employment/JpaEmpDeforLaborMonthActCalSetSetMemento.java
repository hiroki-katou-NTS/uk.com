/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.monthcal.employment;

import nts.uk.ctx.at.record.dom.workrecord.monthcal.DeforWorkTimeAggrSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.EmpDeforLaborMonthActCalSetSetMemento;
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.employment.KrcstEmpDeforMCalSet;
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.employment.KrcstEmpDeforMCalSetPK;
import nts.uk.ctx.at.record.infra.repository.workrecord.monthcal.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

/**
 * The Class JpaEmpDeforLaborMonthActCalSetSetMemento.
 */
public class JpaEmpDeforLaborMonthActCalSetSetMemento implements EmpDeforLaborMonthActCalSetSetMemento {

	/** The type value. */
	private KrcstEmpDeforMCalSet typeValue;

	/**
	 * Instantiates a new jpa emp defor labor month act cal set set memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaEmpDeforLaborMonthActCalSetSetMemento(KrcstEmpDeforMCalSet typeValue) {
		super();
		if(typeValue.getKrcstEmpDeforMCalSetPK() == null){				
			typeValue.setKrcstEmpDeforMCalSetPK(new KrcstEmpDeforMCalSetPK());
		}
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.
	 * EmpDeforLaborMonthActCalSetSetMemento#setCompanyId(nts.uk.ctx.at.shared.
	 * dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId cid) {
		this.typeValue.getKrcstEmpDeforMCalSetPK().setCid(cid.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.
	 * EmpDeforLaborMonthActCalSetSetMemento#setAggrSetting(nts.uk.ctx.at.record
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
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.
	 * EmpDeforLaborMonthActCalSetSetMemento#setEmploymentCode(nts.uk.ctx.at.
	 * shared.dom.vacation.setting.compensatoryleave.EmploymentCode)
	 */
	@Override
	public void setEmploymentCode(EmploymentCode empCode) {
		this.typeValue.getKrcstEmpDeforMCalSetPK().setEmpCd(empCode.v());
	}

}
