/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.workrecord.monthcal.employee;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.app.command.workrecord.monthcal.common.DeforWorkTimeAggrSetDto;
import nts.uk.ctx.at.record.app.command.workrecord.monthcal.common.FlexMonthWorkTimeAggrSetDto;
import nts.uk.ctx.at.record.app.command.workrecord.monthcal.common.RegularWorkTimeAggrSetDto;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.DeforWorkTimeAggrSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.FlexMonthWorkTimeAggrSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.RegularWorkTimeAggrSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.ShaDeforLaborMonthActCalSetGetMemento;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.ShaFlexMonthActCalSetGetMemento;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.ShaRegulaMonthActCalSetGetMemento;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class SaveShaMonthCalSetCommand.
 */
@Getter
@Setter
public class SaveShaMonthCalSetCommand implements ShaRegulaMonthActCalSetGetMemento, ShaFlexMonthActCalSetGetMemento,
		ShaDeforLaborMonthActCalSetGetMemento {

	/** The sid. */
	private String employeeId;
	
	/** The flex aggr setting. */
	private FlexMonthWorkTimeAggrSetDto flexAggrSetting;

	/** The reg aggr setting. */
	private RegularWorkTimeAggrSetDto regAggrSetting;

	/** The defor aggr setting. */
	private DeforWorkTimeAggrSetDto deforAggrSetting;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.
	 * ShaRegulaMonthActCalSetGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(AppContexts.user().companyId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.
	 * ShaRegulaMonthActCalSetGetMemento#getEmployeeId()
	 */
	@Override
	public EmployeeId getEmployeeId() {
		return new EmployeeId(this.employeeId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.
	 * ShaDeforLaborMonthActCalSetGetMemento#getDeforAggrSetting()
	 */
	@Override
	public DeforWorkTimeAggrSet getDeforAggrSetting() {
		return this.deforAggrSetting.toDomain();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.
	 * ShaFlexMonthActCalSetGetMemento#getFlexAggrSetting()
	 */
	@Override
	public FlexMonthWorkTimeAggrSet getFlexAggrSetting() {
		return this.flexAggrSetting.toDomain();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.
	 * ShaRegulaMonthActCalSetGetMemento#getRegularAggrSetting()
	 */
	@Override
	public RegularWorkTimeAggrSet getRegularAggrSetting() {
		return this.regAggrSetting.toDomain();
	}
	
	/**
	 * Gets the emp id.
	 *
	 * @return the emp id
	 */
	public String getEmpId() {
		return this.employeeId;
	}

}
