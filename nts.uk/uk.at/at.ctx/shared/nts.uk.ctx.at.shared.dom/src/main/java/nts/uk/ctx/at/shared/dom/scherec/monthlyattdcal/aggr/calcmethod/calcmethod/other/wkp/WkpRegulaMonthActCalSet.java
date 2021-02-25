package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.wkp;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.ExcessOutsideTimeSetReg;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.RegularWorkTimeAggrSet;

/**
 * 職場別通常勤務集計設定
 */
@Getter
public class WkpRegulaMonthActCalSet extends RegularWorkTimeAggrSet {

	/** Serializable */
	private static final long serialVersionUID = 1L;
	
	/** 職場ID */
	private String workplaceId;
	
	private WkpRegulaMonthActCalSet (String workplaceId) {
		super();
		
		this.workplaceId = workplaceId;
	}
	
	public static WkpRegulaMonthActCalSet of (String workplaceId, String comId, 
			ExcessOutsideTimeSetReg aggregateTimeSet,
			ExcessOutsideTimeSetReg excessOutsideTimeSet) {
		
		val domain = new WkpRegulaMonthActCalSet(workplaceId);

		domain.set(comId, aggregateTimeSet, excessOutsideTimeSet);
		
		return domain;
	}

}
