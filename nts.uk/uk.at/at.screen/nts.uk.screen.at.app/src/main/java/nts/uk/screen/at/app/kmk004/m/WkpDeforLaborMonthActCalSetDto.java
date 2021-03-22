package nts.uk.screen.at.app.kmk004.m;

import lombok.Data;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.wkp.WkpDeforLaborMonthActCalSet;
import nts.uk.screen.at.app.query.kmk004.p.DeforLaborSettlementPeriodDto;
import nts.uk.screen.at.app.query.kmk004.p.DeforWorkTimeAggrSetDto;
import nts.uk.screen.at.app.query.kmk004.p.ExcessOutsideTimeSetRegDto;

/**
 * 職場別変形労働集計設定
 * 
 * @author tutt
 *
 */
@Data
public class WkpDeforLaborMonthActCalSetDto extends DeforWorkTimeAggrSetDto {

	/** 職場ID */
	private String wkpId;

	public WkpDeforLaborMonthActCalSetDto(String wkpId, String comId, ExcessOutsideTimeSetRegDto aggregateTimeSet,
			ExcessOutsideTimeSetRegDto excessOutsideTimeSet, DeforLaborSettlementPeriodDto settlementPeriod) {
		super(comId, aggregateTimeSet, excessOutsideTimeSet, settlementPeriod);
		this.wkpId = wkpId;
	}

	public static WkpDeforLaborMonthActCalSetDto fromDomain(WkpDeforLaborMonthActCalSet domain) {

		return new WkpDeforLaborMonthActCalSetDto(domain.getWorkplaceId(), domain.getComId(),
				ExcessOutsideTimeSetRegDto.from(domain.getAggregateTimeSet()),
				ExcessOutsideTimeSetRegDto.from(domain.getExcessOutsideTimeSet()),
				DeforLaborSettlementPeriodDto.from(domain.getSettlementPeriod()));
	}

}
