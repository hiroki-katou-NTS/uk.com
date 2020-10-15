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
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.emp.EmpFlexMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.DeforLaborCalSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.emp.EmpDeforLaborMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.emp.EmpRegulaMonthActCalSet;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

/**
 * The Class SaveEmpMonthCalSetCommand.
 */
@Getter
@Setter
public class SaveEmpMonthCalSetCommand {

	/** The employment code. */
	private String employmentCode;

	/** The flex aggr setting. */
	private FlexMonthWorkTimeAggrSetDto flexAggrSetting;

	/** The reg aggr setting. */
	private RegularWorkTimeAggrSetDto regAggrSetting;

	/** The defor aggr setting. */
	private DeforWorkTimeAggrSetDto deforAggrSetting;

	public EmpDeforLaborMonthActCalSet defor(String cid) {
		return EmpDeforLaborMonthActCalSet.of(new EmploymentCode(employmentCode), cid,
				deforAggrSetting.getAggregateTimeSet().domain(), 
				deforAggrSetting.getExcessOutsideTimeSet().domain(), 
				new DeforLaborCalSetting(deforAggrSetting.isOtTransCriteria()), 
				deforAggrSetting.getSettlementPeriod().domain());
	}
	public EmpRegulaMonthActCalSet regular(String cid) {
		return EmpRegulaMonthActCalSet.of(new EmploymentCode(employmentCode), cid, 
				regAggrSetting.getAggregateTimeSet().domain(), 
				regAggrSetting.getExcessOutsideTimeSet().domain());
	}
	public EmpFlexMonthActCalSet flex(String cid) {
		
		return EmpFlexMonthActCalSet.of(cid, 
				flexAggrSetting.aggrMethod(), 
				flexAggrSetting.insufficSet(), 
				flexAggrSetting.legalAggrSet(), 
				flexAggrSetting.flexTimeHandle(),
				new EmploymentCode(employmentCode));
	}
}
