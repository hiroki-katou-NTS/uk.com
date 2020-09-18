package nts.uk.ctx.at.schedule.dom.schedule.alarm.continuouswork.continuousworktime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
/**
 * 会社の就業時間帯の連続勤務できる上限日数	
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.勤務予定のアラームチェック.連続勤務.就業時間帯の連続勤務
 * @author lan_lt
 *
 */
@Getter
@AllArgsConstructor
public class MaxNumberDaysOfContinuousWorkTimeCompany implements DomainAggregate{

	//会社ID
	private final String companyId;
	
	//コード
	private WorkTimeContinuousCode code;
	
	//名称
	private WorkTimeContinuousName name;
	
	//日数
	private MaxNumberOfContinuousWorktime MaxNumberDaysOfContinuousWorktime;
	
}
