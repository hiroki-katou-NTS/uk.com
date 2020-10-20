package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.emp;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.AggregateTimeSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.FlexAggregateMethod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.FlexMonthWorkTimeAggrSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.FlexTimeHandle;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.ShortageFlexSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

@Getter
/** 雇用別フレックス勤務集計方法 */
public class EmpFlexMonthActCalSet extends FlexMonthWorkTimeAggrSet {

	/** 雇用コード */
	private EmploymentCode employmentCode;
	
	/***/
	private static final long serialVersionUID = 1L;

	private EmpFlexMonthActCalSet() {
		super();
	}
	
	/**
	 * @param comId 会社ID
	 * @param aggrMethod 集計方法
	 * @param insufficSet 不足設定
	 * @param legalAggrSet 法定内集計設定
	 * @param flexTimeHandle フレックス時間の扱い
	 * @param employmentCode 雇用コード
	 * @return 雇用別フレックス勤務集計方法 
	 */
	public static EmpFlexMonthActCalSet of(String comId, 
			FlexAggregateMethod aggrMethod,
			ShortageFlexSetting insufficSet,
			AggregateTimeSetting legalAggrSet,
			FlexTimeHandle flexTimeHandle,
			EmploymentCode employmentCode){
		
		val domain = new EmpFlexMonthActCalSet();
		domain.comId = comId;
		domain.aggrMethod = aggrMethod;
		domain.legalAggrSet = legalAggrSet;
		domain.insufficSet = insufficSet;
		domain.flexTimeHandle = flexTimeHandle;
		domain.employmentCode = employmentCode;
		return domain;
	}
}
