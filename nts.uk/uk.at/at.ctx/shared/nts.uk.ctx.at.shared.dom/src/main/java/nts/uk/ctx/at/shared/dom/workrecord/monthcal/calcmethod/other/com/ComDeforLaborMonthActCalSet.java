package nts.uk.ctx.at.shared.dom.workrecord.monthcal.calcmethod.other.com;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.workrecord.monthcal.calcmethod.other.DeforLaborCalSetting;
import nts.uk.ctx.at.shared.dom.workrecord.monthcal.calcmethod.other.DeforLaborSettlementPeriod;
import nts.uk.ctx.at.shared.dom.workrecord.monthcal.calcmethod.other.DeforWorkTimeAggrSet;
import nts.uk.ctx.at.shared.dom.workrecord.monthcal.calcmethod.other.ExcessOutsideTimeSetReg;

/**
 * 会社別変形労働集計設定
 */
@Getter
public class ComDeforLaborMonthActCalSet extends DeforWorkTimeAggrSet {

	/** Serializable */
	private static final long serialVersionUID = 1L;

	private ComDeforLaborMonthActCalSet () {
		super();
	}
	
	public static ComDeforLaborMonthActCalSet of (String comId, 
			ExcessOutsideTimeSetReg aggregateTimeSet,
			ExcessOutsideTimeSetReg excessOutsideTimeSet,
			DeforLaborCalSetting deforLaborCalSetting,
			DeforLaborSettlementPeriod settlementPeriod) {
		
		val domain = new ComDeforLaborMonthActCalSet();

		domain.set(comId, aggregateTimeSet, excessOutsideTimeSet, 
				deforLaborCalSetting, settlementPeriod);
		
		return domain;
	}

}
