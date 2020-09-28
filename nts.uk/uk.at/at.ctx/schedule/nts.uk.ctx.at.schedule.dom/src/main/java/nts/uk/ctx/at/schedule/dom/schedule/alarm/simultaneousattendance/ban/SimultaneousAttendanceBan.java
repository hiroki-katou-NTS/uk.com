package nts.uk.ctx.at.schedule.dom.schedule.alarm.simultaneousattendance.ban;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
/**
 * 同時出勤禁止
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.勤務予定のアラームチェック.同時出勤.同時出勤禁止.同時出勤禁止
 * @author lan_lt
 *
 */
@AllArgsConstructor
@Getter
public class SimultaneousAttendanceBan implements DomainAggregate{
	/** 対象組織 */
	private final TargetOrgIdenInfor targetOrg;
	
	/** 同時出勤禁止コード */
	private final SimultaneousAttendanceBanCode simultaneousAttBanCode;
	
	/** 同時出勤禁止名称 */
	private SimultaneousAttendanceBanName simultaneousAttendanceBanName;
	
	/** 適用する時間帯 */
	private final ApplicableTimeZoneCls applicableTimeZoneCls;
	
	/** 禁止する社員の組み合わせ */
	private List<String> empBanWorkTogetherLst;
	
	/** 許容する人数 */
	private MaxOfNumberEmployeeTogether allowableNumberOfEmp;

	/**
	 * 終日を指定して作成する
	 * @param targetOrg 対象組織
	 * @param simultaneousAttBanCode 同時出勤禁止コード
	 * @param simultaneousAttendanceBanName 同時出勤禁止名称 
	 * @param empBanWorkTogetherLst 禁止する社員の組み合わせ
	 * @param allowableNumberOfEmp 許容する人数
	 * @return
	 */
	public static SimultaneousAttendanceBan createBySpecifyingAllDay(TargetOrgIdenInfor targetOrg, 
			SimultaneousAttendanceBanCode simultaneousAttBanCode,
			SimultaneousAttendanceBanName simultaneousAttendanceBanName,
			List<String> empBanWorkTogetherLst,
			MaxOfNumberEmployeeTogether allowableNumberOfEmp) {
		
		if(empBanWorkTogetherLst.isEmpty() || empBanWorkTogetherLst.size() <= 1) {
			throw new BusinessException("Msg_1875");
		}
		
		if(empBanWorkTogetherLst.size() < allowableNumberOfEmp.v().intValue()) {
			throw new BusinessException("Msg_1787");
		}
		
		return new SimultaneousAttendanceBan(targetOrg, simultaneousAttBanCode, 
				simultaneousAttendanceBanName, ApplicableTimeZoneCls.ALLDAY,
				empBanWorkTogetherLst, allowableNumberOfEmp);
	}
	

	/**
	 * 夜勤時間帯を指定して作成する
	 * @param targetOrg 対象組織
	 * @param simultaneousAttBanCode 同時出勤禁止コード
	 * @param simultaneousAttendanceBanName 同時出勤禁止名称 
	 * @param empBanWorkTogetherLst 禁止する社員の組み合わせ
	 * @param allowableNumberOfEmp 許容する人数
	 * @return
	 */
	public static SimultaneousAttendanceBan createByNightShift(TargetOrgIdenInfor targetOrg, 
			SimultaneousAttendanceBanCode simultaneousAttBanCode,
			SimultaneousAttendanceBanName simultaneousAttendanceBanName,
			List<String> empBanWorkTogetherLst,
			MaxOfNumberEmployeeTogether allowableNumberOfEmp) {
		
		if(empBanWorkTogetherLst.isEmpty() || empBanWorkTogetherLst.size() <= 1) {
			throw new BusinessException("Msg_1875");
		}
		
		if(empBanWorkTogetherLst.size() < allowableNumberOfEmp.v().intValue()) {
			throw new BusinessException("Msg_1787");
		}
		
		return new SimultaneousAttendanceBan(targetOrg, simultaneousAttBanCode, 
				simultaneousAttendanceBanName, ApplicableTimeZoneCls.NIGHTSHIFT,
				empBanWorkTogetherLst, allowableNumberOfEmp);
	}
	
}
