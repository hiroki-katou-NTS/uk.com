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
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.DeforWorkTimeAggrSet;

/**
 * The Class DeforWorkTimeAggrSetDto.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeforWorkTimeAggrSetDto {

	/** The aggregate time set. */
	private ExcessOutsideTimeSetRegDto aggregateTimeSet;

	/** The excess outside time set. */
	private ExcessOutsideTimeSetRegDto excessOutsideTimeSet;

	/** The is ot trans criteria. */
	private boolean isOtTransCriteria;

	/** The settlement period. */
	private DeforLaborSettlementPeriodDto settlementPeriod;

	/**
	 * 
	 * @param domain
	 * @return DeforWorkTimeAggrSetDto
	 */
	public static DeforWorkTimeAggrSetDto fromDomain (DeforWorkTimeAggrSet domain) {
		
		ExcessOutsideTimeSetRegDto aggregateTimeSetDto = 
				new ExcessOutsideTimeSetRegDto(domain.getAggregateTimeSet().isLegalOverTimeWork(),
						domain.getAggregateTimeSet().isLegalHoliday(),
						domain.getAggregateTimeSet().isSurchargeWeekMonth(),
						domain.getAggregateTimeSet().isExceptLegalHdwk());
		
		ExcessOutsideTimeSetRegDto excessOutsideTimeSetDto = 
				new ExcessOutsideTimeSetRegDto(domain.getExcessOutsideTimeSet().isLegalOverTimeWork(),
						domain.getAggregateTimeSet().isLegalHoliday(),
						domain.getAggregateTimeSet().isSurchargeWeekMonth(),
						domain.getAggregateTimeSet().isExceptLegalHdwk());
		
		DeforLaborSettlementPeriodDto settlementPeriodDto =
				new DeforLaborSettlementPeriodDto(domain.getSettlementPeriod().getStartMonth().v(),
						domain.getSettlementPeriod().getPeriod().v(),
						domain.getSettlementPeriod().isRepeat());
		
		return new DeforWorkTimeAggrSetDto(aggregateTimeSetDto, excessOutsideTimeSetDto, domain.getDeforLaborCalSetting().isOtTransCriteria(), settlementPeriodDto);
	}
}
