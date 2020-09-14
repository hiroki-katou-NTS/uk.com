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
import nts.uk.ctx.at.shared.dom.workrecord.monthcal.calcmethod.flex.AggregateSetting;
import nts.uk.ctx.at.shared.dom.workrecord.monthcal.calcmethod.flex.AggregateTimeSetting;
import nts.uk.ctx.at.shared.dom.workrecord.monthcal.calcmethod.flex.CarryforwardSetInShortageFlex;
import nts.uk.ctx.at.shared.dom.workrecord.monthcal.calcmethod.flex.FlexAggregateMethod;
import nts.uk.ctx.at.shared.dom.workrecord.monthcal.calcmethod.flex.FlexTimeHandle;
import nts.uk.ctx.at.shared.dom.workrecord.monthcal.calcmethod.flex.SettlePeriod;
import nts.uk.ctx.at.shared.dom.workrecord.monthcal.calcmethod.flex.SettlePeriodMonths;
import nts.uk.ctx.at.shared.dom.workrecord.monthcal.calcmethod.flex.ShortageFlexSetting;

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
	private Integer aggrMethod;

	/** The insuffic set. */
	private Integer insufficSet;

	/** The legal aggr set. */
	private Integer legalAggrSet;

	/** The include over time. */
	private Boolean includeOverTime;

	/** The include holiday work. */
	private Boolean includeHdwk;

	/** The settlement period. */
	private Integer settlePeriod;

	/** The start month. */
	private Integer startMonth;

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
				EnumAdaptor.valueOf(period, SettlePeriodMonths.class));
	}
	public AggregateTimeSetting legalAggrSet() {
		
		return AggregateTimeSetting.of(EnumAdaptor.valueOf(legalAggrSet, AggregateSetting.class));
	}
	public FlexTimeHandle flexTimeHandle() {
		
		return FlexTimeHandle.of(includeOverTime, includeHdwk);
	}
}
