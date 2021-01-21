/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.workrecord.monthcal.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.common.Month;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.AggregateSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.AggregateTimeSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.CarryforwardSetInShortageFlex;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.FlexAggregateMethod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.FlexTimeHandle;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.SettlePeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.SettlePeriodMonths;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.ShortageFlexSetting;

/**
 * The Class FlexMonthWorkTimeAggrSetDto.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlexMonthWorkTimeAggrSetDto {

	/** The aggr method. */
	private int aggrMethod;

	/** The insuffic set. */
	private int insufficSet;

	/** The legal aggr set. */
	private int legalAggrSet;

	/** The include over time. */
	private boolean includeOverTime;

	/** The include holiday work. */
	private boolean includeHdwk;

	/** The settlement period. */
	private int settlePeriod;

	/** The start month. */
	private int startMonth;

	/** The period. (settlement period months) */
	private Integer period;
	
	/** 所定労動時間使用区分 */
	private boolean withinTimeUsageAttr;
	
	public FlexAggregateMethod aggrMethod() {
		
		return EnumAdaptor.valueOf(aggrMethod, FlexAggregateMethod.class);
	}
	
	public ShortageFlexSetting insufficSet() {
		
		return ShortageFlexSetting.of(
				EnumAdaptor.valueOf(insufficSet, CarryforwardSetInShortageFlex.class), 
				EnumAdaptor.valueOf(settlePeriod, SettlePeriod.class), 
				new Month(startMonth), 
				period == null ? SettlePeriodMonths.TWO 
						: EnumAdaptor.valueOf(period, SettlePeriodMonths.class));
	}
	public AggregateTimeSetting legalAggrSet() {
		
		return AggregateTimeSetting.of(EnumAdaptor.valueOf(legalAggrSet, AggregateSetting.class));
	}
	public FlexTimeHandle flexTimeHandle() {
		
		return FlexTimeHandle.of(includeOverTime, includeHdwk);
	}
}
