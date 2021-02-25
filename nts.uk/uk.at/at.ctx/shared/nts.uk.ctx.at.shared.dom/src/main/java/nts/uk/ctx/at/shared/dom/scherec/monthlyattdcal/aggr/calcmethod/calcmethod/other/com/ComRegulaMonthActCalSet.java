package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.com;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.ExcessOutsideTimeSetReg;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.RegularWorkTimeAggrSet;

/**
 * 会社別通常勤務集計設定
 */
@Getter
public class ComRegulaMonthActCalSet extends RegularWorkTimeAggrSet {

	/** Serializable */
	private static final long serialVersionUID = 1L;
	
	private ComRegulaMonthActCalSet () {
		super();
	}
	
	public static ComRegulaMonthActCalSet of (String comId, 
			ExcessOutsideTimeSetReg aggregateTimeSet,
			ExcessOutsideTimeSetReg excessOutsideTimeSet) {
		
		val domain = new ComRegulaMonthActCalSet();

		domain.set(comId, aggregateTimeSet, excessOutsideTimeSet);
		
		return domain;
	}
}
