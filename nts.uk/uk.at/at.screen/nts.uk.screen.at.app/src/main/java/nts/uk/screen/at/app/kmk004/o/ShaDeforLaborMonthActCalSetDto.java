package nts.uk.screen.at.app.kmk004.o;

import lombok.Data;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.sha.ShaDeforLaborMonthActCalSet;
import nts.uk.screen.at.app.query.kmk004.p.DeforLaborSettlementPeriodDto;
import nts.uk.screen.at.app.query.kmk004.p.DeforWorkTimeAggrSetDto;
import nts.uk.screen.at.app.query.kmk004.p.ExcessOutsideTimeSetRegDto;

/**
 * 社員別変形労働集計設定
 * 
 * @author tutt
 *
 */
@Data
public class ShaDeforLaborMonthActCalSetDto extends DeforWorkTimeAggrSetDto {

	/** 社員ID */
	private String empId;

	public ShaDeforLaborMonthActCalSetDto(String empId, String comId, ExcessOutsideTimeSetRegDto aggregateTimeSet,
			ExcessOutsideTimeSetRegDto excessOutsideTimeSet, DeforLaborSettlementPeriodDto settlementPeriod) {
		super(comId, aggregateTimeSet, excessOutsideTimeSet, settlementPeriod);
		this.empId = empId;
	}

	public static ShaDeforLaborMonthActCalSetDto fromDomain(ShaDeforLaborMonthActCalSet domain) {
		
		return new ShaDeforLaborMonthActCalSetDto(domain.getEmployeeId(), domain.getComId(),
				ExcessOutsideTimeSetRegDto.from(domain.getAggregateTimeSet()),
				ExcessOutsideTimeSetRegDto.from(domain.getExcessOutsideTimeSet()),
				DeforLaborSettlementPeriodDto.from(domain.getSettlementPeriod()));
	}

}
