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
import nts.uk.ctx.at.shared.dom.workrecord.monthcal.calcmethod.flex.sha.ShaFlexMonthActCalSet;
import nts.uk.ctx.at.shared.dom.workrecord.monthcal.calcmethod.other.DeforLaborCalSetting;
import nts.uk.ctx.at.shared.dom.workrecord.monthcal.calcmethod.other.sha.ShaDeforLaborMonthActCalSet;
import nts.uk.ctx.at.shared.dom.workrecord.monthcal.calcmethod.other.sha.ShaRegulaMonthActCalSet;

/**
 * The Class SaveShaMonthCalSetCommand.
 */
@Getter
@Setter
public class SaveShaMonthCalSetCommand {

	/** The sid. */
	private String employeeId;
	
	/** The flex aggr setting. */
	private FlexMonthWorkTimeAggrSetDto flexAggrSetting;

	/** The reg aggr setting. */
	private RegularWorkTimeAggrSetDto regAggrSetting;

	/** The defor aggr setting. */
	private DeforWorkTimeAggrSetDto deforAggrSetting;

	public ShaDeforLaborMonthActCalSet defor(String cid) {
		return ShaDeforLaborMonthActCalSet.of(cid, employeeId,
				deforAggrSetting.getAggregateTimeSet().domain(), 
				deforAggrSetting.getExcessOutsideTimeSet().domain(), 
				new DeforLaborCalSetting(deforAggrSetting.isOtTransCriteria()), 
				deforAggrSetting.getSettlementPeriod().domain());
	}
	public ShaRegulaMonthActCalSet regular(String cid) {
		return ShaRegulaMonthActCalSet.of(cid, employeeId, 
				regAggrSetting.getAggregateTimeSet().domain(), 
				regAggrSetting.getExcessOutsideTimeSet().domain());
	}
	public ShaFlexMonthActCalSet flex(String cid) {
		
		return ShaFlexMonthActCalSet.of(cid, 
				flexAggrSetting.aggrMethod(), 
				flexAggrSetting.insufficSet(), 
				flexAggrSetting.legalAggrSet(), 
				flexAggrSetting.flexTimeHandle(),
				employeeId);
	}
}
