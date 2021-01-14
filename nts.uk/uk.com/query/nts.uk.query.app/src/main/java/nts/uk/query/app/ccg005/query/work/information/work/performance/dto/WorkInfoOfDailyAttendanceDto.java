package nts.uk.query.app.ccg005.query.work.information.work.performance.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WorkInfoOfDailyAttendanceDto {

	// 勤務実績の勤務情報
	private WorkInformationDto recordInfo;

	// 計算状態
	private Integer calculationState;

	// 直行区分
	private Integer goStraightAtr;

	// 直帰区分
	private Integer backStraightAtr;

	// 曜日
	private Integer dayOfWeek;

	// 勤務予定時間帯
	private List<ScheduleTimeSheetDto> scheduleTimeSheets;

	// 振休振出として扱う日数
	private NumberOfDaySuspensionDto numberDaySuspension;

	// Ver
	private long ver;
}
