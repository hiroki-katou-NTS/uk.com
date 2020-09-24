package nts.uk.ctx.at.schedule.dom.schedule.alarm.continuouswork.continuousattendance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
/**
 * 会社の連続出勤できる上限日数
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.勤務予定のアラームチェック.連続勤務.連続出勤.会社の連続出勤できる上限日数
 * @author lan_lt
 * 	
 *
 */
@AllArgsConstructor
public class MaxNumberDaysOfContinuousAttendanceCom implements DomainAggregate{
	//会社ID
	@Getter
	private final String companyId;
	
	//日数
	@Getter
	private MaxNumberDaysOfContinuousAttendance numberOfDays;
}
