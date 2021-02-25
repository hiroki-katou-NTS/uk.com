package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.com;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.AggregateTimeSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.FlexAggregateMethod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.FlexMonthWorkTimeAggrSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.FlexTimeHandle;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.ShortageFlexSetting;

@Getter
/** 会社別フレックス勤務集計方法 */
public class ComFlexMonthActCalSet extends FlexMonthWorkTimeAggrSet {

	/** 所定労動時間使用区分 */
	private boolean withinTimeUsageAttr;
	
	/***/
	private static final long serialVersionUID = 1L;

	private ComFlexMonthActCalSet() {
		super();
	}
	
	/**
	 * @param comId 会社ID
	 * @param aggrMethod 集計方法
	 * @param insufficSet 不足設定
	 * @param legalAggrSet 法定内集計設定
	 * @param flexTimeHandle フレックス時間の扱い
	 * @param withinTimeUsageAttr 所定労動時間使用区分
	 * @return 会社別フレックス勤務集計方法
	 */
	public static ComFlexMonthActCalSet of(String comId, 
			FlexAggregateMethod aggrMethod,
			ShortageFlexSetting insufficSet,
			AggregateTimeSetting legalAggrSet,
			FlexTimeHandle flexTimeHandle,
			boolean withinTimeUsageAttr){
		
		val domain = new ComFlexMonthActCalSet();
		domain.comId = comId;
		domain.aggrMethod = aggrMethod;
		domain.legalAggrSet = legalAggrSet;
		domain.insufficSet = insufficSet;
		domain.flexTimeHandle = flexTimeHandle;
		domain.withinTimeUsageAttr = withinTimeUsageAttr;
		return domain;
	}
}
