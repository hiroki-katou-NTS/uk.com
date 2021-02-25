package nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.limitworktime;

import java.util.List;

import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
/**
 * 就業時間帯の期間内上限勤務
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.勤務予定のアラームチェック.連続勤務.就業時間帯の期間内上限.就業時間帯の期間内上限勤務
 * @author lan_lt
 *
 */
@Value
public class MaxDayOfWorkTime implements DomainValue{
	/**  就業時間帯コードリスト*/
	private final List<WorkTimeCode> workTimeCodeList;
	
	/** 勤務上限日数  */
	private final MaxDay maxDay;

}
