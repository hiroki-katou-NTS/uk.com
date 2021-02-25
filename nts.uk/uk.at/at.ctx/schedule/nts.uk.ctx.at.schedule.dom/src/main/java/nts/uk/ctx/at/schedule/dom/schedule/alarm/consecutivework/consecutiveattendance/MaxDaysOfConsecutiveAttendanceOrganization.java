package nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveattendance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

/**
 * 組織の連続出勤できる上限日数
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.勤務予定のアラームチェック.連続勤務.連続出勤.組織の連続出勤できる上限日数
 * @author lan_lt
 *
 */
@AllArgsConstructor
public class MaxDaysOfConsecutiveAttendanceOrganization implements DomainAggregate{

	/** 対象組織  */
	@Getter
	private final TargetOrgIdenInfor targeOrg;
	
	/** 日数  */
	@Getter
	private MaxDaysOfConsecutiveAttendance numberOfDays;

}
