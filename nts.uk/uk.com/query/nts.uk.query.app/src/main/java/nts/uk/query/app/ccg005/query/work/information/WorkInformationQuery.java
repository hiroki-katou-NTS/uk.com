package nts.uk.query.app.ccg005.query.work.information;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerErrorRepository;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkScheduleRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.ctx.office.dom.dto.EmployeeDailyPerErrorDto;
import nts.uk.ctx.office.dom.dto.WorkTypeDto;

/*
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.ステータス.勤務情報の取得.勤務情報の取得
 */
@Stateless
public class WorkInformationQuery {

	@Inject
	private WorkInformationRepository workInfoRepo;
	
	@Inject
	private WorkScheduleRepository workScheduleRepo;
	
	@Inject
	private TimeLeavingOfDailyPerformanceRepository timeLeaveRepo;

	@Inject
	private EmployeeDailyPerErrorRepository employeeErrorRepo;

	@Inject
	private WorkTypeRepository workTypeRepo;
	
	public List<EmployeeWorkInformationDto> getWorkInformationQuery(List<String> sids, GeneralDate baseDate) {
		DatePeriod datePeriod = new DatePeriod(baseDate, baseDate);
		// 1. get(社員IDリスト、基準日): List<日別実績の勤務情報>
		List<WorkInfoOfDailyPerformance> workInfoList = workInfoRepo.findByPeriodOrderByYmdAndEmps(sids, datePeriod);
		List<String> subSids = workInfoList.stream().map(mapper -> mapper.getEmployeeId()).collect(Collectors.toList());
		
		// 2. get(社員IDリスト、基準日): List<勤務予定>
		List<String> allSids = new ArrayList<String>();
		allSids.addAll(sids);
		allSids.removeAll(subSids);
		List<WorkSchedule> workScheduleList = workScheduleRepo.getList(allSids, datePeriod);
		
		// 3. get(社員IDリスト、基準日): List<日別実績の出退勤> 
		List<TimeLeavingOfDailyPerformance> timeLeaveList = timeLeaveRepo.finds(sids, datePeriod);

		// 4. get(社員IDリスト、基準日、勤務実績のエラーアラームコード＝007、008) : List<社員の日別実績エラー一覧>
		List<String> errorAlarmCodeLst = new ArrayList<>();
		errorAlarmCodeLst.add("007");
		errorAlarmCodeLst.add("008");
		List<EmployeeDailyPerError> employeeDailyErrorList = employeeErrorRepo.findsByCodeLst(sids, datePeriod, errorAlarmCodeLst);
		
		// 5. get(ログイン会社ID): List<勤務種類>
		String loginCid = AppContexts.user().companyId();
		List<WorkType> workTypeList = workTypeRepo.findByCompanyId(loginCid);
		
		return sids.stream().map(sid -> {
			WorkInfoOfDailyPerformance workInfo = workInfoList.stream().filter(wi -> wi.getEmployeeId().equalsIgnoreCase(sid)).findFirst().orElse(null);
			WorkSchedule workSchedule = workScheduleList.stream().filter(ws -> ws.getEmployeeID().equalsIgnoreCase(sid)).findFirst().orElse(null);
			TimeLeavingOfDailyPerformance timeLeave = timeLeaveList.stream().filter(tl -> tl.getEmployeeId().equalsIgnoreCase(sid)).findFirst().orElse(null);
			List<EmployeeDailyPerErrorDto> employeeDailyErrors = employeeDailyErrorList.stream()
					.filter(tl -> tl.getEmployeeID().equalsIgnoreCase(sid))
					.map(mapper -> EmployeeWorkInformationDto.employeeDailyPerErrorToDto(mapper))
					.collect(Collectors.toList());
			WorkTypeDto workTypeDto = WorkTypeDto.builder().build();
			
			//条件：日別実績の勤務情報.勤務情報.勤務種類コード　＝　「勤務種類」.コード
			if(workInfo != null) {
				String workTypeCode = workInfo.getWorkInformation().getRecordInfo().getWorkTypeCode().v();
				WorkType workType = workTypeList.stream().filter(wt -> (wt.getWorkTypeCode().v()).equalsIgnoreCase(workTypeCode)).findFirst().orElse(null);
				workTypeDto = EmployeeWorkInformationDto.workTypeToDto(workType);
				
			//日別実績の勤務情報がない場合：勤務予定.勤務情報.勤務情報.勤務種類コード　＝　「勤務種類」.コード
			} else {
				String workTypeCode = workSchedule.getWorkInfo().getRecordInfo().getWorkTypeCode().v();
				WorkType workType = workTypeList.stream().filter(wt -> (wt.getWorkTypeCode().v()).equalsIgnoreCase(workTypeCode)).findFirst().orElse(null);
				workTypeDto = EmployeeWorkInformationDto.workTypeToDto(workType);
			}
			return EmployeeWorkInformationDto.builder()
					.sid(sid)
					.workScheduleDto(EmployeeWorkInformationDto.workScheduleToDto(workSchedule))
					.workTypeDto(workTypeDto)
					.timeLeavingOfDailyPerformanceDto(EmployeeWorkInformationDto.timeLeavingOfDailyPerformanceToDto(timeLeave))
					.workPerformanceDto(EmployeeWorkInformationDto.workInfoOfDailyPerformanceToDto(workInfo))
					.employeeDailyPerErrorDtos(employeeDailyErrors)
					.build();
		}).collect(Collectors.toList());
	}
}
