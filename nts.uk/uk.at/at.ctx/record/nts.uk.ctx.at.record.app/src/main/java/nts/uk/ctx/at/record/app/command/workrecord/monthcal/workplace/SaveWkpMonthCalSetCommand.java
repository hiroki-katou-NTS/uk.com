/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.workrecord.monthcal.workplace;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.app.command.workrecord.monthcal.common.DeforWorkTimeAggrSetDto;
import nts.uk.ctx.at.record.app.command.workrecord.monthcal.common.FlexMonthWorkTimeAggrSetDto;
import nts.uk.ctx.at.record.app.command.workrecord.monthcal.common.RegularWorkTimeAggrSetDto;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.wkp.WkpFlexMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.DeforLaborCalSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.wkp.WkpDeforLaborMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.wkp.WkpRegulaMonthActCalSet;

/**
 * The Class SaveWkpMonthCalSetCommand.
 */
@Getter
@Setter
public class SaveWkpMonthCalSetCommand {

	/** The wkp id. */
	private String workplaceId;

	/** The flex aggr setting. */
	private FlexMonthWorkTimeAggrSetDto flexAggrSetting;

	/** The reg aggr setting. */
	private RegularWorkTimeAggrSetDto regAggrSetting;

	/** The defor aggr setting. */
	private DeforWorkTimeAggrSetDto deforAggrSetting;

	public WkpDeforLaborMonthActCalSet defor(String cid) {
		return WkpDeforLaborMonthActCalSet.of(workplaceId, cid,
				deforAggrSetting.getAggregateTimeSet().domain(), 
				deforAggrSetting.getExcessOutsideTimeSet().domain(), 
				new DeforLaborCalSetting(deforAggrSetting.isOtTransCriteria()), 
				deforAggrSetting.getSettlementPeriod().domain());
	}
	public WkpRegulaMonthActCalSet regular(String cid) {
		return WkpRegulaMonthActCalSet.of(workplaceId, cid, 
				regAggrSetting.getAggregateTimeSet().domain(), 
				regAggrSetting.getExcessOutsideTimeSet().domain());
	}
	public WkpFlexMonthActCalSet flex(String cid) {
		
		return WkpFlexMonthActCalSet.of(cid, 
				flexAggrSetting.aggrMethod(), 
				flexAggrSetting.insufficSet(), 
				flexAggrSetting.legalAggrSet(), 
				flexAggrSetting.flexTimeHandle(),
				workplaceId);
	}
}
