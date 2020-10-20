package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.wkp;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.DeforLaborCalSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.DeforLaborSettlementPeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.DeforWorkTimeAggrSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.ExcessOutsideTimeSetReg;

/**
 * 職場別変形労働集計設定
 */
@Getter
public class WkpDeforLaborMonthActCalSet extends DeforWorkTimeAggrSet {
	
	/** Serializable */
	private static final long serialVersionUID = 1L;
	
	/** 職場ID */
	private String workplaceId;
	
	private WkpDeforLaborMonthActCalSet (String workplaceId) {
		super();
		
		this.workplaceId = workplaceId;
	}
	
	public static WkpDeforLaborMonthActCalSet of (String workplaceId, String comId, 
			ExcessOutsideTimeSetReg aggregateTimeSet,
			ExcessOutsideTimeSetReg excessOutsideTimeSet,
			DeforLaborCalSetting deforLaborCalSetting,
			DeforLaborSettlementPeriod settlementPeriod) {
		
		val domain = new WkpDeforLaborMonthActCalSet(workplaceId);

		domain.set(comId, aggregateTimeSet, excessOutsideTimeSet, 
				deforLaborCalSetting, settlementPeriod);
		
		return domain;
	}
}
