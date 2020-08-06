/**
 * 
 */
package nts.uk.screen.at.app.ksu001.getworkscheduleshift;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
import nts.uk.ctx.at.schedule.dom.workschedule.domainservice.WorkScheManaStatusService;
import nts.uk.ctx.at.shared.dom.adapter.employment.employwork.leaveinfo.EmpLeaveWorkPeriodImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.employwork.leaveinfo.EmployeeLeaveJobPeriodImport;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmpEnrollPeriodImport;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmploymentPeriodImported;

/**
 * @author laitv
 * ScreenQuery 勤務予定（シフト）を取得する
 *
 */
@Stateless
public class GetWorkScheduleShift {
	
	public WorkScheduleShiftResult dataSample(GetWorkScheduleShiftParam param){
		
		WorkScheduleShiftResult result = new WorkScheduleShiftResult();
		
		// fix tam data đoạn này
		List<WorkScheduleShiftDto> listWorkScheduleShift = new ArrayList<>();
				
		List<String> sids = Arrays.asList(
				"fc4304be-8121-4bad-913f-3e48f4e2a752",
				"338c26ac-9b80-4bab-aa11-485f3c624186", 
				"89ea1474-d7d8-4694-9e9b-416ea1d6381c",
				"ae7fe82e-a7bd-4ce3-adeb-5cd403a9d570",
				"8f9edce4-e135-4a1e-8dca-ad96abe405d6",
				"9787c06b-3c71-4508-8e06-c70ad41f042a",
				"62785783-4213-4a05-942b-c32a5ffc1d63",
				"4859993b-8065-4789-90d6-735e3b65626b",
				"aeaa869d-fe62-4eb2-ac03-2dde53322cb5",
				"70c48cfa-7e8d-4577-b4f6-7b715c091f24",
				"c141daf2-70a4-4f4b-a488-847f4686e848");
		for (String sid : sids) {
			for (int i = 1; i < 32; i++) {
				boolean isActive = true;
				WorkScheduleShiftDto w1 = new WorkScheduleShiftDto(sid, GeneralDate.ymd(2020, 7, i), 
						true, true, true, true , 1, 
						i%2==0? "001" : "002", i%2==0? "出勤" : "テス", new ShiftEditStateDto(sid,GeneralDate.ymd(2020, 7, i), 0), 1, isActive);
				listWorkScheduleShift.add(w1);
			}
		}
		result.setListWorkScheduleShift(listWorkScheduleShift);
		
		return result;
	}
	
	public WorkScheduleShiftResult getDataNextMonth(GetWorkScheduleShiftParam param){
		
		WorkScheduleShiftResult result = new WorkScheduleShiftResult();
		
		// fix tam data đoạn này
		List<WorkScheduleShiftDto> listWorkScheduleShift = new ArrayList<>();
				
		List<String> sids = Arrays.asList(
				"fc4304be-8121-4bad-913f-3e48f4e2a752",
				"338c26ac-9b80-4bab-aa11-485f3c624186", 
				"89ea1474-d7d8-4694-9e9b-416ea1d6381c",
				"ae7fe82e-a7bd-4ce3-adeb-5cd403a9d570",
				"8f9edce4-e135-4a1e-8dca-ad96abe405d6",
				"9787c06b-3c71-4508-8e06-c70ad41f042a",
				"62785783-4213-4a05-942b-c32a5ffc1d63",
				"4859993b-8065-4789-90d6-735e3b65626b",
				"aeaa869d-fe62-4eb2-ac03-2dde53322cb5",
				"70c48cfa-7e8d-4577-b4f6-7b715c091f24",
				"c141daf2-70a4-4f4b-a488-847f4686e848");
		for (String sid : sids) {
			for (int i = 1; i < 32; i++) {
				boolean isActive = true;
				WorkScheduleShiftDto w1 = new WorkScheduleShiftDto(sid, GeneralDate.ymd(2020, 8, i), 
						true, true, true, true , 1, 
						i%2==0? "001" : "002", i%2==0? "C" : "D", new ShiftEditStateDto(sid,GeneralDate.ymd(2020, 8, i), 0), 1, isActive);
				listWorkScheduleShift.add(w1);
			}
		}
		result.setListWorkScheduleShift(listWorkScheduleShift);
		
		return result;
	}
	
	public WorkScheduleShiftResult getDataPreMonth(GetWorkScheduleShiftParam param){
		
		WorkScheduleShiftResult result = new WorkScheduleShiftResult();
		
		// fix tam data đoạn này
		List<WorkScheduleShiftDto> listWorkScheduleShift = new ArrayList<>();
				
		List<String> sids = Arrays.asList(
				"fc4304be-8121-4bad-913f-3e48f4e2a752",
				"338c26ac-9b80-4bab-aa11-485f3c624186", 
				"89ea1474-d7d8-4694-9e9b-416ea1d6381c",
				"ae7fe82e-a7bd-4ce3-adeb-5cd403a9d570",
				"8f9edce4-e135-4a1e-8dca-ad96abe405d6",
				"9787c06b-3c71-4508-8e06-c70ad41f042a",
				"62785783-4213-4a05-942b-c32a5ffc1d63",
				"4859993b-8065-4789-90d6-735e3b65626b",
				"aeaa869d-fe62-4eb2-ac03-2dde53322cb5",
				"70c48cfa-7e8d-4577-b4f6-7b715c091f24",
				"c141daf2-70a4-4f4b-a488-847f4686e848");
		for (String sid : sids) {
			for (int i = 1; i < 31; i++) {
				boolean isActive = true;
				WorkScheduleShiftDto w1 = new WorkScheduleShiftDto(sid, GeneralDate.ymd(2020, 6, i), 
						true, true, true, true , 1, 
						i%2==0? "001" : "002", i%2==0? "X" : "Y", new ShiftEditStateDto(sid,GeneralDate.ymd(2020, 6, i), 0), 1, isActive);
				listWorkScheduleShift.add(w1);
			}
		}
		result.setListWorkScheduleShift(listWorkScheduleShift);
		
		return result;
	}
	
	@AllArgsConstructor
	private static class WorkScheManaStatusServiceReqIml implements WorkScheManaStatusService.Require {@Override
		public List<EmpEnrollPeriodImport> getAffCompanyHistByEmployee(List<String> sids, DatePeriod datePeriod) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Optional<WorkingConditionItem> getBySidAndStandardDate(String employeeId, GeneralDate baseDate) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<EmployeeLeaveJobPeriodImport> getByDatePeriod(List<String> lstEmpID, DatePeriod datePeriod) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<EmpLeaveWorkPeriodImport> specAndGetHolidayPeriod(List<String> lstEmpID, DatePeriod datePeriod) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<EmploymentPeriodImported> getEmploymentHistory(List<String> lstEmpID, DatePeriod datePeriod) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Optional<WorkSchedule> get(String employeeID, GeneralDate ymd) {
			// TODO Auto-generated method stub
			return null;
		}
		
		

	}
}
