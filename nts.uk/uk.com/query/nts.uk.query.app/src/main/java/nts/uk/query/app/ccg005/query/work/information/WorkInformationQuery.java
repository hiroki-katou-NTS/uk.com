package nts.uk.query.app.ccg005.query.work.information;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerErrorRepository;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.query.app.ccg005.query.work.information.employee.daily.error.EmployeeDailyPerErrorDto;
import nts.uk.shr.com.context.AppContexts;

/*
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.ステータス.勤務情報の取得.勤務情報の取得
 */
//TODO
public class WorkInformationQuery {

	@Inject
	private TimeLeavingOfDailyPerformanceRepository timeLeaveRepo;

	@Inject
	private EmployeeDailyPerErrorRepository employeeErrorRepo;

	@Inject
	private WorkTypeRepository workTypeRepo;
	public List<EmployeeWorkInformationDto> getWorkInformationQuery(List<String> sids, DatePeriod baseDate) {
		// 1. get(社員IDリスト、基準日): List<日別実績の勤務情報> TODO
		// 2. get(社員IDリスト、基準日): List<勤務予定> TODO
		// 3. get(社員IDリスト、基準日): List<日別実績の出退勤> TODO: convert to DTO
		List<TimeLeavingOfDailyPerformance> timeLeaveDto = timeLeaveRepo.finds(sids, baseDate);

		// 4. get(社員IDリスト、基準日、勤務実績のエラーアラームコード＝007、008) : List<社員の日別実績エラー一覧>
		List<String> errorAlarmCodeLst = new ArrayList<>();
		errorAlarmCodeLst.add("007");
		errorAlarmCodeLst.add("008");
		List<EmployeeDailyPerErrorDto> employeeDailyErrorList = employeeErrorRepo
				.findsByCodeLst(sids, baseDate, errorAlarmCodeLst).stream()
				.map(domain -> EmployeeDailyPerErrorDto.getDto(domain))
				.collect(Collectors.toList());
		// 5. get(ログイン会社ID): List<勤務種類> TODO
		String loginCid = AppContexts.user().companyId();
		workTypeRepo.findByCompanyId(loginCid);
		return null;
	}
}
