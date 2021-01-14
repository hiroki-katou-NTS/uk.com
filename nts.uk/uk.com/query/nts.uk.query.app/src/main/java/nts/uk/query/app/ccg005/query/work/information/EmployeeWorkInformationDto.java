package nts.uk.query.app.ccg005.query.work.information;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import nts.uk.query.app.ccg005.query.work.information.employee.daily.error.EmployeeDailyPerErrorDto;
import nts.uk.query.app.ccg005.query.work.information.time.leaving.TimeLeavingOfDailyPerformanceDto;
import nts.uk.query.app.ccg005.query.work.information.work.performance.WorkInfoOfDailyPerformanceDto;
import nts.uk.query.app.ccg005.query.work.information.work.schedule.WorkScheduleDto;
import nts.uk.query.app.ccg005.query.work.information.work.type.WorkTypeDto;

/*
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.ステータス.勤務情報の取得.社員の勤務情報
 */
@Data
@Builder
public class EmployeeWorkInformationDto {

	//社員ID
	private String sid;
	
	//勤務予定
	private WorkScheduleDto workScheduleDto;
	
	//勤務種類
	private WorkTypeDto workTypeDto;
	
	//日別実績の出退勤
	private TimeLeavingOfDailyPerformanceDto timeLeavingOfDailyPerformanceDto;
	
	//日別実績の勤務情報
	private List<WorkInfoOfDailyPerformanceDto> workPerformanceDtos;
	
	//社員の日別実績エラー一覧
	private List<EmployeeDailyPerErrorDto> employeeDailyPerErrorDtos;
}
