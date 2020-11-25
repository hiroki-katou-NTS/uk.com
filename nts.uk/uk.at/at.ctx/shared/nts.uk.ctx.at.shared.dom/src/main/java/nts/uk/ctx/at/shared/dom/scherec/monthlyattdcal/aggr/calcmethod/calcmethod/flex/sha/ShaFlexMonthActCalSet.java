package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.sha;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.AggregateTimeSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.FlexAggregateMethod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.FlexMonthWorkTimeAggrSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.FlexTimeHandle;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.ShortageFlexSetting;

@Getter
/** 社員別フレックス勤務集計方法 */
public class ShaFlexMonthActCalSet extends FlexMonthWorkTimeAggrSet {

	/** 社員ID */
	private String empId;
	
	/***/
	private static final long serialVersionUID = 1L;

	private ShaFlexMonthActCalSet() {
		super();
	}
	
	/**
	 * @param comId 会社ID
	 * @param aggrMethod 集計方法
	 * @param insufficSet 不足設定
	 * @param legalAggrSet 法定内集計設定
	 * @param flexTimeHandle フレックス時間の扱い
	 * @param empId 社員ID
	 * @return 社員別フレックス勤務集計方法
	 */
	public static ShaFlexMonthActCalSet of(String comId, 
			FlexAggregateMethod aggrMethod,
			ShortageFlexSetting insufficSet,
			AggregateTimeSetting legalAggrSet,
			FlexTimeHandle flexTimeHandle,
			String empId){
		
		val domain = new ShaFlexMonthActCalSet();
		domain.comId = comId;
		domain.aggrMethod = aggrMethod;
		domain.legalAggrSet = legalAggrSet;
		domain.insufficSet = insufficSet;
		domain.flexTimeHandle = flexTimeHandle;
		domain.empId = empId;
		return domain;
	}
}
