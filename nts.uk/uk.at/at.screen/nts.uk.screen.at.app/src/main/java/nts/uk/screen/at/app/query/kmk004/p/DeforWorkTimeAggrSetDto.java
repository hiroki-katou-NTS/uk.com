package nts.uk.screen.at.app.query.kmk004.p;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.DeforWorkTimeAggrSet;

/**
 * 
 * @author tutt
 *
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeforWorkTimeAggrSetDto {

	private ExcessOutsideTimeSetRegDto aggregateTimeSet;

	private ExcessOutsideTimeSetRegDto excessOutsideTimeSet;

	private DeforLaborSettlementPeriodDto settlementPeriod;

	/**
	 * 
	 * @param domain
	 * @return DeforWorkTimeAggrSetDto
	 */
	public static DeforWorkTimeAggrSetDto fromDomain (DeforWorkTimeAggrSet domain) {
		
		ExcessOutsideTimeSetRegDto aggregateTimeSetDto = 
				new ExcessOutsideTimeSetRegDto(domain.getAggregateTimeSet().isLegalOverTimeWork(),
						domain.getAggregateTimeSet().isLegalHoliday());
		
		ExcessOutsideTimeSetRegDto excessOutsideTimeSetDto = 
				new ExcessOutsideTimeSetRegDto(domain.getExcessOutsideTimeSet().isLegalOverTimeWork(),
						domain.getAggregateTimeSet().isLegalHoliday());
						
		DeforLaborSettlementPeriodDto settlementPeriodDto =
				new DeforLaborSettlementPeriodDto(domain.getSettlementPeriod().getStartMonth().v(),
						domain.getSettlementPeriod().getPeriod().v(),
						domain.getSettlementPeriod().isRepeat());
		
		return new DeforWorkTimeAggrSetDto(aggregateTimeSetDto, excessOutsideTimeSetDto, settlementPeriodDto);
	}
}
