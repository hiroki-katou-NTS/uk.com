package nts.uk.query.app.ccg005.query.work.information;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
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
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.query.app.ccg005.query.work.information.employee.daily.error.EmployeeDailyPerErrorDto;
import nts.uk.shr.com.context.AppContexts;

/*
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.ステータス.勤務情報の取得.勤務情報の取得
 */
//TODO
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
		// 1. get(社員IDリスト、基準日): List<日別実績の勤務情報> TODO
		List<WorkInfoOfDailyPerformance> workInfoList = workInfoRepo.findByPeriodOrderByYmdAndEmps(sids, datePeriod);
		
		// 2. get(社員IDリスト、基準日): List<勤務予定> TODO
		List<WorkSchedule> workScheduleList = workScheduleRepo.getList(sids, datePeriod);
		
		// 3. get(社員IDリスト、基準日): List<日別実績の出退勤> TODO: convert to DTO
		List<TimeLeavingOfDailyPerformance> timeLeaveDto = timeLeaveRepo.finds(sids, datePeriod);

		// 4. get(社員IDリスト、基準日、勤務実績のエラーアラームコード＝007、008) : List<社員の日別実績エラー一覧>
		List<String> errorAlarmCodeLst = new ArrayList<>();
		errorAlarmCodeLst.add("007");
		errorAlarmCodeLst.add("008");
		List<EmployeeDailyPerErrorDto> employeeDailyErrorList = employeeErrorRepo
				.findsByCodeLst(sids, datePeriod, errorAlarmCodeLst).stream()
				.map(domain -> EmployeeDailyPerErrorDto.getDto(domain))
				.collect(Collectors.toList());
		// 5. get(ログイン会社ID): List<勤務種類> TODO
		String loginCid = AppContexts.user().companyId();
		List<WorkType> workTypeList = workTypeRepo.findByCompanyId(loginCid);
		return Collections.emptyList();
	}
}
