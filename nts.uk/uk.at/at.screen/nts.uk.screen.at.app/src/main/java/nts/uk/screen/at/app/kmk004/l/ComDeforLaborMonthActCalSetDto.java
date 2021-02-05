package nts.uk.screen.at.app.kmk004.l;

import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.com.ComDeforLaborMonthActCalSet;
import nts.uk.screen.at.app.query.kmk004.p.DeforLaborSettlementPeriodDto;
import nts.uk.screen.at.app.query.kmk004.p.DeforWorkTimeAggrSetDto;
import nts.uk.screen.at.app.query.kmk004.p.ExcessOutsideTimeSetRegDto;

/**
 * 
 * @author tutt
 *
 */
@Setter
public class ComDeforLaborMonthActCalSetDto extends DeforWorkTimeAggrSetDto {

	public ComDeforLaborMonthActCalSetDto(String comId, ExcessOutsideTimeSetRegDto aggregateTimeSet,
			ExcessOutsideTimeSetRegDto excessOutsideTimeSet, DeforLaborSettlementPeriodDto settlementPeriod) {
		super(comId, aggregateTimeSet, excessOutsideTimeSet, settlementPeriod);
	}
	
	public static ComDeforLaborMonthActCalSetDto fromDomain(ComDeforLaborMonthActCalSet domain) {
		
		return new ComDeforLaborMonthActCalSetDto(domain.getComId(), 
				ExcessOutsideTimeSetRegDto.from(domain.getAggregateTimeSet()),
				ExcessOutsideTimeSetRegDto.from(domain.getExcessOutsideTimeSet()),
				DeforLaborSettlementPeriodDto.from(domain.getSettlementPeriod()));

	}
}
