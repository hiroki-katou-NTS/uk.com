/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.workrecord.monthcal.employment;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.app.find.workrecord.monthcal.common.DeforWorkTimeAggrSetDto;
import nts.uk.ctx.at.record.app.find.workrecord.monthcal.common.FlexMonthWorkTimeAggrSetDto;
import nts.uk.ctx.at.record.app.find.workrecord.monthcal.common.RegularWorkTimeAggrSetDto;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.DeforWorkTimeAggrSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.FlexMonthWorkTimeAggrSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.RegularWorkTimeAggrSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.EmpDeforLaborMonthActCalSetSetMemento;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.EmpFlexMonthActCalSetSetMemento;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.EmpRegulaMonthActCalSetSetMemento;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

/**
 * The Class EmpMonthCalSetDto.
 */

@Getter
@Setter
@Builder
public class EmpMonthCalSetDto implements EmpRegulaMonthActCalSetSetMemento,
		EmpFlexMonthActCalSetSetMemento, EmpDeforLaborMonthActCalSetSetMemento {

	/** The employment code. */
	private String employmentCode;
	
	/** The company id. */
	private String companyId;;
	
	/** The flex aggr setting. */
	private FlexMonthWorkTimeAggrSetDto flexAggrSetting;

	/** The reg aggr setting. */
	private RegularWorkTimeAggrSetDto regAggrSetting;

	/** The defor aggr setting. */
	private DeforWorkTimeAggrSetDto deforAggrSetting;


	/* 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.EmpDeforLaborMonthActCalSetSetMemento#setAggrSetting(nts.uk.ctx.at.record.dom.workrecord.monthcal.DeforWorkTimeAggrSet)
	 */
	@Override
	public void setAggrSetting(DeforWorkTimeAggrSet legalAggrSetOfIrgNew) {
		deforAggrSetting = DeforWorkTimeAggrSetDto.builder().build();
		deforAggrSetting.fromDomain(legalAggrSetOfIrgNew);
	}

	/* 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.EmpFlexMonthActCalSetSetMemento#setAggrSetting(nts.uk.ctx.at.record.dom.workrecord.monthcal.FlexMonthWorkTimeAggrSet)
	 */
	@Override
	public void setAggrSetting(FlexMonthWorkTimeAggrSet aggrSettingMonthlyOfFlxNew) {
		flexAggrSetting = FlexMonthWorkTimeAggrSetDto.builder().build();
		flexAggrSetting.fromDomain(aggrSettingMonthlyOfFlxNew);
	}

	/* 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.EmpRegulaMonthActCalSetSetMemento#setAggrSetting(nts.uk.ctx.at.record.dom.workrecord.monthcal.RegularWorkTimeAggrSet)
	 */
	@Override
	public void setAggrSetting(RegularWorkTimeAggrSet legalAggrSetOfRegNew) {
		regAggrSetting = RegularWorkTimeAggrSetDto.builder().build();
		regAggrSetting.fromDomain(legalAggrSetOfRegNew);
	}

	/* 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.EmpRegulaMonthActCalSetSetMemento#setCompanyId(nts.uk.ctx.at.shared.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		this.companyId = companyId.v();
	}

	/* 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.EmpRegulaMonthActCalSetSetMemento#setEmploymentCode(nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode)
	 */
	@Override
	public void setEmploymentCode(EmploymentCode employmentCode) {
		this.employmentCode = employmentCode.v();
	}


}
