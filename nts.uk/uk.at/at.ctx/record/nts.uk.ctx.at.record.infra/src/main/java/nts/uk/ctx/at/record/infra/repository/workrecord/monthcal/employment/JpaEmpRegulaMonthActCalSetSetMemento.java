/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.monthcal.employment;

import nts.uk.ctx.at.record.dom.workrecord.monthcal.RegularWorkTimeAggrSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.EmpRegulaMonthActCalSetSetMemento;
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.employment.KrcstEmpRegMCalSet;
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.employment.KrcstEmpRegMCalSetPK;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;

/**
 * The Class JpaEmpRegulaMonthActCalSetSetMemento.
 */
public class JpaEmpRegulaMonthActCalSetSetMemento implements EmpRegulaMonthActCalSetSetMemento {

	/** The type value. */
	private KrcstEmpRegMCalSet typeValue;

	/**
	 * Instantiates a new jpa emp regula month act cal set set memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaEmpRegulaMonthActCalSetSetMemento(KrcstEmpRegMCalSet typeValue) {
		super();
		if(typeValue.getKrcstEmpRegMCalSetPK() == null){				
			typeValue.setKrcstEmpRegMCalSetPK(new KrcstEmpRegMCalSetPK());
		}
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.
	 * EmpRegulaMonthActCalSetSetMemento#setCompanyId(nts.uk.ctx.at.shared.dom.
	 * common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId cid) {
		this.typeValue.getKrcstEmpRegMCalSetPK().setCid(cid.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.
	 * EmpRegulaMonthActCalSetSetMemento#setAggrSetting(nts.uk.ctx.at.record.dom
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
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.
	 * EmpRegulaMonthActCalSetSetMemento#setEmploymentCode(nts.uk.ctx.at.shared.
	 * dom.vacation.setting.compensatoryleave.EmploymentCode)
	 */
	@Override
	public void setEmploymentCode(EmploymentCode empCode) {
		this.typeValue.getKrcstEmpRegMCalSetPK().setEmpCd(empCode.v());
	}

}
