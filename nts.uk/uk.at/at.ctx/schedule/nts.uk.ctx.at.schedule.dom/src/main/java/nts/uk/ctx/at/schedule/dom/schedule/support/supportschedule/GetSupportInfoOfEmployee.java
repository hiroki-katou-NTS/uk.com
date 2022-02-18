package nts.uk.ctx.at.schedule.dom.schedule.support.supportschedule;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.supportmanagement.SupportInfoOfEmployee;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportableemployee.GetSupportInfoOfEmployeeFromSupportableEmployee;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.GetTargetIdentifiInforService;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

/**
 * 社員の応援情報を取得する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.応援.応援予定.社員の応援情報を取得する
 * @author dan_pv
 */
public class GetSupportInfoOfEmployee {

	/**
	 * 予定の情報を取得する
	 * @param require
	 * @param employeeId 社員ID
	 * @param date 年月日
	 * @return
	 */
	public static SupportInfoOfEmployee getScheduleInfo(Require require, EmployeeId employeeId, GeneralDate date) {
		
		// TODO implementing
		return SupportInfoOfEmployee.createWithoutSupport(employeeId, date, 
				TargetOrgIdenInfor.creatIdentifiWorkplaceGroup("mockID"));
	}
	
	/**
	 * 実績の情報を取得する
	 * @return
	 */
	public static SupportInfoOfEmployee getRecordInfo(Require require, EmployeeId employeeId, GeneralDate date) {
		
		// TODO implementing
		return SupportInfoOfEmployee.createWithoutSupport(employeeId, date, 
				TargetOrgIdenInfor.creatIdentifiWorkplaceGroup("mockID"));
	}
	
	public static interface Require 
		extends GetSupportInfoOfEmployeeFromSupportableEmployee.Require, 
				GetTargetIdentifiInforService.Require{
		
		/**
		 * 勤務予定を取得する
		 * @param employeeId 社員ID
		 * @param date 年月日
		 * @return
		 */
		Optional<WorkSchedule> getWorkSchedule(String employeeId, GeneralDate date);
		
		/**
		 * 日別実績を取得する
		 * @param employeeId 社員ID
		 * @param date 年月日
		 * @return
		 */
		Optional<IntegrationOfDaily> getRecord(String employeeId, GeneralDate date);
		
	}
}
