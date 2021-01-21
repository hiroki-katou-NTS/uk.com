package nts.uk.query.pub.ccg005.work.information;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import nts.uk.query.pub.ccg005.work.information.dto.EmployeeDailyPerErrorExport;
import nts.uk.query.pub.ccg005.work.information.dto.TimeLeavingOfDailyPerformanceExport;
import nts.uk.query.pub.ccg005.work.information.dto.WorkInfoOfDailyPerformanceExport;
import nts.uk.query.pub.ccg005.work.information.dto.WorkScheduleExport;
import nts.uk.query.pub.ccg005.work.information.dto.WorkTypeExport;

/*
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.ステータス.勤務情報の取得.社員の勤務情報
 */
@Builder
@Data
public class EmployeeWorkInformationExport {

	//社員ID
	private String sid;
	
	//勤務予定
	private WorkScheduleExport workScheduleDto;
	
	//勤務種類
	private WorkTypeExport workTypeDto;
	
	//日別実績の出退勤
	private TimeLeavingOfDailyPerformanceExport timeLeavingOfDailyPerformanceDto;
	
	//日別実績の勤務情報
	private WorkInfoOfDailyPerformanceExport workPerformanceDto;
	
	//社員の日別実績エラー一覧
	private List<EmployeeDailyPerErrorExport> employeeDailyPerErrorDtos;
}
