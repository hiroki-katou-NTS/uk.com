package nts.uk.ctx.at.function.dom.alarm.alarmlistsetting;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.function.dom.alarm.AlarmCategory;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionByCategory;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionCode;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.ExtractionRangeBase;
import nts.uk.ctx.at.function.dom.alarm.workplace.checkcondition.WorkplaceCategory;

import java.util.ArrayList;
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
	private List<ExtractionPeriod> extractPeriodList;
}
