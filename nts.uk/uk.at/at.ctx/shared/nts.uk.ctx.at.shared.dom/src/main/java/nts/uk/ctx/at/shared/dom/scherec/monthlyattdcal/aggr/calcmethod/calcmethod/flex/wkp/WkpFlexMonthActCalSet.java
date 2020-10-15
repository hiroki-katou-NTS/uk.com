package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.wkp;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.AggregateTimeSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.FlexAggregateMethod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.FlexMonthWorkTimeAggrSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.FlexTimeHandle;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.ShortageFlexSetting;

@Getter
/** 職場別フレックス勤務集計方法 */
public class WkpFlexMonthActCalSet extends FlexMonthWorkTimeAggrSet {

	/** 職場ID */
	private String workplaceId;
	
	/***/
	private static final long serialVersionUID = 1L;

	private WkpFlexMonthActCalSet() {
		super();
	}
	
	/**
	 * @param comId 会社ID
	 * @param aggrMethod 集計方法
	 * @param insufficSet 不足設定
	 * @param legalAggrSet 法定内集計設定
	 * @param flexTimeHandle フレックス時間の扱い
	 * @param workplaceId 職場ID
	 * @return 職場別フレックス勤務集計方法
	 */
	public static WkpFlexMonthActCalSet of(String comId, 
			FlexAggregateMethod aggrMethod,
			ShortageFlexSetting insufficSet,
			AggregateTimeSetting legalAggrSet,
			FlexTimeHandle flexTimeHandle,
			String workplaceId){
		
		val domain = new WkpFlexMonthActCalSet();
		domain.comId = comId;
		domain.aggrMethod = aggrMethod;
		domain.legalAggrSet = legalAggrSet;
		domain.insufficSet = insufficSet;
		domain.flexTimeHandle = flexTimeHandle;
		domain.workplaceId = workplaceId;
		return domain;
	}
}
