package nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveworktime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
/**
 * 会社の就業時間帯の連続勤務できる上限日数	
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.勤務予定のアラームチェック.連続勤務.就業時間帯の連続勤務.会社の就業時間帯の連続勤務できる上限日数
 * @author lan_lt
 *
 */
@Getter
@AllArgsConstructor
public class MaxDaysOfContinuousWorkTimeCompany implements DomainAggregate{
	/** コード */
	private ConsecutiveWorkTimeCode code;
	
	/** 名称 */
	private ConsecutiveWorkTimeName name;
	
	/** 日数 */
	private MaxDaysOfConsecutiveWorkTime maxDaysContiWorktime;
	
}
