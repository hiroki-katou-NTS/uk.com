/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.monthcal.employment;

import nts.uk.ctx.at.record.dom.workrecord.monthcal.FlexMonthWorkTimeAggrSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.EmpFlexMonthActCalSetSetMemento;
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.employment.KrcstEmpFlexMCalSet;
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.employment.KrcstEmpFlexMCalSetPK;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

/**
 * The Class JpaEmpFlexMonthActCalSetSetMemento.
 */
public class JpaEmpFlexMonthActCalSetSetMemento implements EmpFlexMonthActCalSetSetMemento {

	/** The type value. */
	private KrcstEmpFlexMCalSet typeValue;

	/**
	 * Instantiates a new jpa emp flex month act cal set set memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaEmpFlexMonthActCalSetSetMemento(KrcstEmpFlexMCalSet typeValue) {
		super();
		if(typeValue.getKrcstEmpFlexMCalSetPK() == null){				
			typeValue.setKrcstEmpFlexMCalSetPK(new KrcstEmpFlexMCalSetPK());
		}
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.
	 * EmpFlexMonthActCalSetSetMemento#setCompanyId(nts.uk.ctx.at.shared.dom.
	 * common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId cid) {
		this.typeValue.getKrcstEmpFlexMCalSetPK().setCid(cid.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.
	 * EmpFlexMonthActCalSetSetMemento#setAggrSetting(nts.uk.ctx.at.record.dom.
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
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.
	 * EmpFlexMonthActCalSetSetMemento#setEmploymentCode(nts.uk.ctx.at.shared.
	 * dom.vacation.setting.compensatoryleave.EmploymentCode)
	 */
	@Override
	public void setEmploymentCode(EmploymentCode empCode) {
		this.typeValue.getKrcstEmpFlexMCalSetPK().setEmpCd(empCode.v());
	}

}
