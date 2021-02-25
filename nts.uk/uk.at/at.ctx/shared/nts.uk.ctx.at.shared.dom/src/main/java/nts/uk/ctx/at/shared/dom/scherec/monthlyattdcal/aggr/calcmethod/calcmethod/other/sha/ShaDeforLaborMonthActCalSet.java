package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.sha;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.DeforLaborCalSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.DeforLaborSettlementPeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.DeforWorkTimeAggrSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.ExcessOutsideTimeSetReg;

/**
 * 社員別変形労働集計設定
 */
@Getter
public class ShaDeforLaborMonthActCalSet extends DeforWorkTimeAggrSet {

	/** Serializable */
	private static final long serialVersionUID = 1L;
	
	/** 社員ID */
	private String employeeId;

	private ShaDeforLaborMonthActCalSet (String employeeId) {
		super();
		
		this.employeeId = employeeId;
	}
	
	public static ShaDeforLaborMonthActCalSet of (String employeeId, String comId, 
			ExcessOutsideTimeSetReg aggregateTimeSet,
			ExcessOutsideTimeSetReg excessOutsideTimeSet,
			DeforLaborCalSetting deforLaborCalSetting,
			DeforLaborSettlementPeriod settlementPeriod) {
		
		val domain = new ShaDeforLaborMonthActCalSet(employeeId);

		domain.set(comId, aggregateTimeSet, excessOutsideTimeSet, 
				deforLaborCalSetting, settlementPeriod);
		
		return domain;
	}

}
