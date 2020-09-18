package nts.uk.ctx.at.schedule.dom.schedule.alarm.continuouswork.limitworktime;

import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;
/**
 * 就業時間帯の期間内上限勤務
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.勤務予定のアラームチェック.連続勤務.就業時間帯の期間内上限
 * @author lan_lt
 *
 */
@Value
public class MaxNumberOfWorkingDayOfPeriods implements DomainValue{
	//勤務上限日数
	private MaxNumberOfWorkingDay maxNumberOfWorkingDay;

}
