/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.workrecord.monthcal.company;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.app.command.workrecord.monthcal.common.DeforWorkTimeAggrSetDto;
import nts.uk.ctx.at.record.app.command.workrecord.monthcal.common.FlexMonthWorkTimeAggrSetDto;
import nts.uk.ctx.at.record.app.command.workrecord.monthcal.common.RegularWorkTimeAggrSetDto;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.com.ComFlexMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.DeforLaborCalSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.com.ComDeforLaborMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.com.ComRegulaMonthActCalSet;

/**
 * The Class SaveComMonthCalSetCommand.
 */
@Getter
@Setter
@AllArgsConstructor
public class SaveComMonthCalSetCommand {

	/** The flex aggr setting. */
	private FlexMonthWorkTimeAggrSetDto flexAggrSetting;

	/** The reg aggr setting. */
	private RegularWorkTimeAggrSetDto regAggrSetting;

	/** The defor aggr setting. */
	private DeforWorkTimeAggrSetDto deforAggrSetting;

	public ComDeforLaborMonthActCalSet defor(String cid) {
		return ComDeforLaborMonthActCalSet.of(cid, 
				deforAggrSetting.getAggregateTimeSet().domain(), 
				deforAggrSetting.getExcessOutsideTimeSet().domain(), 
				new DeforLaborCalSetting(deforAggrSetting.isOtTransCriteria()), 
				deforAggrSetting.getSettlementPeriod().domain());
	}
	public ComRegulaMonthActCalSet regular(String cid) {
		return ComRegulaMonthActCalSet.of(cid, 
				regAggrSetting.getAggregateTimeSet().domain(), 
				regAggrSetting.getExcessOutsideTimeSet().domain());
	}
	public ComFlexMonthActCalSet flex(String cid) {
		
		return ComFlexMonthActCalSet.of(cid, 
				flexAggrSetting.aggrMethod(), 
				flexAggrSetting.insufficSet(), 
				flexAggrSetting.legalAggrSet(), 
				flexAggrSetting.flexTimeHandle(),
				flexAggrSetting.isWithinTimeUsageAttr());
	}
}
