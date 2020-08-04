/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.workrecord.monthcal.employee;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.app.find.workrecord.monthcal.common.DeforWorkTimeAggrSetDto;
import nts.uk.ctx.at.record.app.find.workrecord.monthcal.common.FlexMonthWorkTimeAggrSetDto;
import nts.uk.ctx.at.record.app.find.workrecord.monthcal.common.RegularWorkTimeAggrSetDto;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.DeforWorkTimeAggrSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.FlexMonthWorkTimeAggrSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.RegularWorkTimeAggrSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.ShaDeforLaborMonthActCalSetSetMemento;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.ShaFlexMonthActCalSetSetMemento;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.ShaRegulaMonthActCalSetSetMemento;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;

/**
 * The Class ShaMonthCalSetDto.
 */

@Getter
@Setter
@Builder
public class ShaMonthCalSetDto implements ShaRegulaMonthActCalSetSetMemento,
		ShaFlexMonthActCalSetSetMemento, ShaDeforLaborMonthActCalSetSetMemento {

	/** The employee id. */
	private String employeeId;
	
	/** The company id. */
	private String companyId;;
	
	/** The flex aggr setting. */
	private FlexMonthWorkTimeAggrSetDto flexAggrSetting;

	/** The reg aggr setting. */
	private RegularWorkTimeAggrSetDto regAggrSetting;

	/** The defor aggr setting. */
	private DeforWorkTimeAggrSetDto deforAggrSetting;


	/* 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.ShaDeforLaborMonthActCalSetSetMemento#setAggrSetting(nts.uk.ctx.at.record.dom.workrecord.monthcal.DeforWorkTimeAggrSet)
	 */
	@Override
	public void setAggrSetting(DeforWorkTimeAggrSet legalAggrSetOfIrgNew) {
		deforAggrSetting = DeforWorkTimeAggrSetDto.builder().build();
		deforAggrSetting.fromDomain(legalAggrSetOfIrgNew);
	}

	/* 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.ShaFlexMonthActCalSetSetMemento#setAggrSetting(nts.uk.ctx.at.record.dom.workrecord.monthcal.FlexMonthWorkTimeAggrSet)
	 */
	@Override
	public void setAggrSetting(FlexMonthWorkTimeAggrSet aggrSettingMonthlyOfFlxNew) {
		flexAggrSetting = FlexMonthWorkTimeAggrSetDto.builder().build();
		flexAggrSetting.fromDomain(aggrSettingMonthlyOfFlxNew);
	}

	/* 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.ShaRegulaMonthActCalSetSetMemento#setAggrSetting(nts.uk.ctx.at.record.dom.workrecord.monthcal.RegularWorkTimeAggrSet)
	 */
	@Override
	public void setAggrSetting(RegularWorkTimeAggrSet legalAggrSetOfRegNew) {
		regAggrSetting = RegularWorkTimeAggrSetDto.builder().build();
		regAggrSetting.fromDomain(legalAggrSetOfRegNew);
	}

	/* 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.ShaRegulaMonthActCalSetSetMemento#setCompanyId(nts.uk.ctx.at.shared.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		this.companyId = companyId.v();
	}

	/* 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.ShaRegulaMonthActCalSetSetMemento#setEmployeeId(nts.uk.ctx.at.shared.dom.common.EmployeeId)
	 */
	@Override
	public void setEmployeeId(EmployeeId employeeId) {
		this.employeeId = employeeId.v();
	}


}
