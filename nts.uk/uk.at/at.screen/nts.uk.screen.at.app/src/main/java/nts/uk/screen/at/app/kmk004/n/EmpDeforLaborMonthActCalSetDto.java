package nts.uk.screen.at.app.kmk004.n;

import lombok.Data;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.emp.EmpDeforLaborMonthActCalSet;
import nts.uk.screen.at.app.query.kmk004.p.DeforLaborSettlementPeriodDto;
import nts.uk.screen.at.app.query.kmk004.p.DeforWorkTimeAggrSetDto;
import nts.uk.screen.at.app.query.kmk004.p.ExcessOutsideTimeSetRegDto;

/**
 * 雇用別変形労働集計設定
 * 
 * @author tutt
 *
 */
@Data
public class EmpDeforLaborMonthActCalSetDto extends DeforWorkTimeAggrSetDto {

	/** 雇用コード */
	private String empCd;

	public EmpDeforLaborMonthActCalSetDto(String empCd, String comId, ExcessOutsideTimeSetRegDto aggregateTimeSet,
			ExcessOutsideTimeSetRegDto excessOutsideTimeSet, DeforLaborSettlementPeriodDto settlementPeriod) {
		super(comId, aggregateTimeSet, excessOutsideTimeSet, settlementPeriod);
		this.empCd = empCd;
	}

	public static EmpDeforLaborMonthActCalSetDto fromDomain(EmpDeforLaborMonthActCalSet domain) {

		return new EmpDeforLaborMonthActCalSetDto(domain.getEmploymentCode().v(), domain.getComId(),
				ExcessOutsideTimeSetRegDto.from(domain.getAggregateTimeSet()),
				ExcessOutsideTimeSetRegDto.from(domain.getExcessOutsideTimeSet()),
				DeforLaborSettlementPeriodDto.from(domain.getSettlementPeriod()));
	}

}
