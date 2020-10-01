package nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.limitworktime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
/**
 * 会社の就業時間帯の期間内上限勤務
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.勤務予定のアラームチェック.連続勤務.就業時間帯の期間内上限.会社の就業時間帯の期間内上限勤務
 * @author lan_lt
 *
 */
@Getter
@AllArgsConstructor
public class MaxDayOfWorkTimeCompany implements DomainAggregate{
	/** コード  */
	private MaxDayOfWorkTimeCode code;
	
	/** 名称  */
	private MaxDayOfWorkTimeName name;
	
	/** 上限勤務 */
	private MaxDayOfWorkTime maxDayOfWorkTime;
}
