package nts.uk.ctx.at.schedule.dom.schedule.alarm.bansamedayholiday;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.simultaneousattendance.ban.ApplicableTimeZoneCls;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.simultaneousattendance.ban.MaxOfNumberEmployeeTogether;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.simultaneousattendance.ban.SimultaneousAttendanceBan;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.simultaneousattendance.ban.SimultaneousAttendanceBanCode;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.simultaneousattendance.ban.SimultaneousAttendanceBanName;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
@Getter
@AllArgsConstructor
public class BanSameDayHoliday implements DomainAggregate{
	//対象組織
	private final TargetOrgIdenInfor targetOrg;
	
	//コード
	private final BanSameDayHolidayCode banSameDayHolidayCode;
	
	//名称
	private BanSameDayHolidayName banSameDayHolidayName;
	
	//同日の休日取得を禁止する社員
	private List<String> empsCanNotSameHoliday;
	
	//最低限出勤すべき人数
	private SameDayMinOfNumberEmployee minNumberOfEmployeeToWork;

	/**
	 * [C-2] 夜勤時間帯を指定して作成する
	 * @param targetOrg
	 * @param simultaneousAttBanCode
	 * @param simultaneousAttendanceBanName
	 * @param empBanWorkTogetherLst
	 * @param allowableNumberOfEmp
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
	 * [C-2] 夜勤時間帯を指定して作成する
	 * @param targetOrg
	 * @param simultaneousAttBanCode
	 * @param simultaneousAttendanceBanName
	 * @param empBanWorkTogetherLst
	 * @param allowableNumberOfEmp
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
