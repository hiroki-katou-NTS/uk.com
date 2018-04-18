/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.workrecord.monthcal.employment;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.app.command.workrecord.monthcal.common.DeforWorkTimeAggrSetDto;
import nts.uk.ctx.at.record.app.command.workrecord.monthcal.common.FlexMonthWorkTimeAggrSetDto;
import nts.uk.ctx.at.record.app.command.workrecord.monthcal.common.RegularWorkTimeAggrSetDto;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.DeforWorkTimeAggrSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.FlexMonthWorkTimeAggrSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.RegularWorkTimeAggrSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.EmpDeforLaborMonthActCalSetGetMemento;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.EmpFlexMonthActCalSetGetMemento;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.EmpRegulaMonthActCalSetGetMemento;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class SaveEmpMonthCalSetCommand.
 */
@Getter
@Setter
public class SaveEmpMonthCalSetCommand implements EmpDeforLaborMonthActCalSetGetMemento,
		EmpFlexMonthActCalSetGetMemento, EmpRegulaMonthActCalSetGetMemento {

	/** The employment code. */
	private String employmentCode;

	/** The flex aggr setting. */
	private FlexMonthWorkTimeAggrSetDto flexAggrSetting;

	/** The reg aggr setting. */
	private RegularWorkTimeAggrSetDto regAggrSetting;

	/** The defor aggr setting. */
	private DeforWorkTimeAggrSetDto deforAggrSetting;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.
	 * EmpDeforLaborMonthActCalSetGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(AppContexts.user().companyId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.
	 * EmpDeforLaborMonthActCalSetGetMemento#getEmploymentCode()
	 */
	@Override
	public EmploymentCode getEmploymentCode() {
		return new EmploymentCode(this.employmentCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.
	 * EmpRegulaMonthActCalSetGetMemento#getRegularAggrSetting()
	 */
	@Override
	public RegularWorkTimeAggrSet getRegularAggrSetting() {
		return this.regAggrSetting.toDomain();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.
	 * EmpFlexMonthActCalSetGetMemento#getFlexAggrSetting()
	 */
	@Override
	public FlexMonthWorkTimeAggrSet getFlexAggrSetting() {
		return this.flexAggrSetting.toDomain();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.
	 * EmpDeforLaborMonthActCalSetGetMemento#getDeforAggrSetting()
	 */
	@Override
	public DeforWorkTimeAggrSet getDeforAggrSetting() {
		return this.deforAggrSetting.toDomain();
	}
	
	/**
	 * Gets the empl code.
	 *
	 * @return the empl code
	 */
	public String getEmplCode() {
		return this.employmentCode;
	}
	

}
