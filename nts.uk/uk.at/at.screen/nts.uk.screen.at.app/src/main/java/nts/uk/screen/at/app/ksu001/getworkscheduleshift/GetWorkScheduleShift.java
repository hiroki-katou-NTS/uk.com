/**
 * 
 */
package nts.uk.screen.at.app.ksu001.getworkscheduleshift;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.ScheManaStatuTempo;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
import nts.uk.ctx.at.schedule.dom.workschedule.domainservice.WorkScheManaStatusService;
import nts.uk.ctx.at.shared.dom.adapter.employee.AffCompanyHistSharedImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.employwork.leaveinfo.EmpLeaveWorkPeriodImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.employwork.leaveinfo.EmployeeLeaveJobPeriodImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.employwork.leaveinfo.EmploymentPeriod;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;

/**
 * @author laitv
 * ScreenQuery 勤務予定（シフト）を取得する
 *
 */
@Stateless
public class GetWorkScheduleShift {
	
	public WorkScheduleShiftResult getData(GetWorkScheduleShiftParam param){
		WorkScheManaStatusServiceReqIml require = new WorkScheManaStatusServiceReqIml();
		Map<ScheManaStatuTempo, Optional<WorkSchedule>> sMap = WorkScheManaStatusService.getScheduleManagement(require,
				param.listSid, new DatePeriod(param.startDate, param.endDate));
		
		
		
		return null;
	}
	
	@AllArgsConstructor
	private static class WorkScheManaStatusServiceReqIml implements WorkScheManaStatusService.Require {
		
		
		@Override
		public Optional<AffCompanyHistSharedImport> getAffCompanyHistByEmployee(String sid, DatePeriod datePeriod) {
			return null;
		}

		@Override
		public Optional<WorkingConditionItem> getBySidAndStandardDate(String employeeId, GeneralDate baseDate) {
			return null;
		}

		@Override
		public Optional<EmployeeLeaveJobPeriodImport> getByDatePeriod(List<String> lstEmpID, DatePeriod datePeriod) {
			return null;
		}

		@Override
		public Optional<EmpLeaveWorkPeriodImport> specAndGetHolidayPeriod(List<String> lstEmpID,
				DatePeriod datePeriod) {
			return null;
		}

		@Override
		public Optional<EmploymentPeriod> getEmploymentHistory(List<String> lstEmpID, DatePeriod datePeriod) {
			return null;
		}

		@Override
		public Optional<WorkSchedule> get(String employeeID, GeneralDate ymd) {
			return null;
		}
		

		
	}
}
