package nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveworktime;

import java.util.List;

import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.ConsecutiveNumberOfDays;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;

/**
 * 就業時間帯の連続勤務できる上限日数
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.勤務予定のアラームチェック.連続勤務.就業時間帯の連続勤務.就業時間帯の連続勤務できる上限日数
 * @author lan_lt
 *
 */
@Value
public class MaxDaysOfConsecutiveWorkTime implements DomainValue{
	/** 就業時間帯コードリスト */
	private final List<WorkTimeCode> workTimeCodes;
	
	/** 日数 */
	private ConsecutiveNumberOfDays numberOfDays;
}
