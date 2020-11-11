package nts.uk.ctx.at.function.dom.alarmworkplace;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionCode;
import nts.uk.ctx.at.function.dom.alarm.workplace.checkcondition.WorkplaceCategory;

import java.util.List;

/**
 * チェック条件
 */
@Getter
public class CheckCondition extends DomainObject {

	/**
	 * カテゴリ
	 */
	private WorkplaceCategory workplaceCategory;

	/**
	 * チェック条件コード一覧
	 */
	private List<AlarmCheckConditionCode> checkConditionLis;

	/**
	 * 抽出期間
	 */
	private RangeToExtract rangeToExtract;

	public CheckCondition(WorkplaceCategory workplaceCategory,
						  List<AlarmCheckConditionCode> checkConditionList, RangeToExtract rangeToExtract) {
		super();
		this.workplaceCategory = workplaceCategory;
		this.checkConditionLis = checkConditionList;
		this.rangeToExtract = rangeToExtract;
	}
}
