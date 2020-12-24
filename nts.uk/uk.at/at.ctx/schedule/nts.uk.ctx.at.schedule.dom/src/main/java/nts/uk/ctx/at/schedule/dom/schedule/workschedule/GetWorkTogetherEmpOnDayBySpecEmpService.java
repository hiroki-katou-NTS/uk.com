package nts.uk.ctx.at.schedule.dom.schedule.workschedule;

import java.util.List;
import java.util.stream.Collectors;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.GetEmpCanReferBySpecOrganizationService;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.GetTargetIdentifiInforService;

public class GetWorkTogetherEmpOnDayBySpecEmpService {
	/**
	 * 取得する
	 * @param require
	 * @param sid 社員ID
	 * @param baseDate 基準日
	 * @return
	 */
	public static List<String> get(Require require, String sid, GeneralDate baseDate) {
		val targetOrg = GetTargetIdentifiInforService.get(require, baseDate, sid);
		val targetEmps = GetEmpCanReferBySpecOrganizationService.getListEmpID(require, baseDate, sid, targetOrg);
		val workSchedules = require.getWorkSchedule(targetEmps, new DatePeriod(baseDate, baseDate));
		val workList = workSchedules.stream().filter(w -> w.getWorkInfo().isAttendanceRate(require))
				.collect(Collectors.toList());
		return workList.stream().map(c -> c.getEmployeeID()).filter(c -> !c.equals(sid)).collect(Collectors.toList());
	}
	
	public static interface Require extends GetTargetIdentifiInforService.Require, GetEmpCanReferBySpecOrganizationService.Require, WorkInfoOfDailyAttendance.Require{
		/**
		 * [R-1] 勤務予定を取得する
		 * @param sids 社員IDリスト
		 * @param datePerid　期間
		 * @return
		 */
		List<WorkSchedule> getWorkSchedule(List<String> sids, DatePeriod datePerid);
		
	}

}
