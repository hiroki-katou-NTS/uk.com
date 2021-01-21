package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.sha;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.ExcessOutsideTimeSetReg;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.RegularWorkTimeAggrSet;

/**
 * 社員別通常勤務集計設定
 */
@Getter
public class ShaRegulaMonthActCalSet extends RegularWorkTimeAggrSet {

	/** Serializable */
	private static final long serialVersionUID = 1L;
	
	/** 社員ID */
	private String employeeId;
	
	private ShaRegulaMonthActCalSet (String employeeId) {
		super();
		
		this.employeeId = employeeId;
	}
	
	public static ShaRegulaMonthActCalSet of (String employeeId, String comId, 
			ExcessOutsideTimeSetReg aggregateTimeSet,
			ExcessOutsideTimeSetReg excessOutsideTimeSet) {
		
		val domain = new ShaRegulaMonthActCalSet(employeeId);

		domain.set(comId, aggregateTimeSet, excessOutsideTimeSet);
		
		return domain;
	}
}
