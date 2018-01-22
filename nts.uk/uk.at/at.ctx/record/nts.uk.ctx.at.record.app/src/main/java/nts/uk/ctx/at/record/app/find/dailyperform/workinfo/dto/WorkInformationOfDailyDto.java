package nts.uk.ctx.at.record.app.find.dailyperform.workinfo.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.WorkInformation;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemRoot;
import nts.uk.ctx.at.shared.app.util.attendanceitem.item.ConvertibleAttendanceItem;

/** 日別実績の勤務情報 */
@Data
@AttendanceItemRoot(rootName = "日別実績の勤務情報")
public class WorkInformationOfDailyDto implements ConvertibleAttendanceItem {

	/** 勤務実績の勤務情報: 勤務情報 */
	@AttendanceItemLayout(layout = "A", jpPropertyName = "勤務実績の勤務情報")
	private WorkInfoDto actualWorkInfo;

	/** 勤務予定の勤務情報: 勤務情報 */
	@AttendanceItemLayout(layout = "B", jpPropertyName = "勤務予定の勤務情報")
	private WorkInfoDto planWorkInfo;

	/** 勤務予定時間帯: 予定時間帯 */
	@AttendanceItemLayout(layout = "C", jpPropertyName = "勤務予定時間帯", listMaxLength = 2, indexField = "workNo")
	private List<ScheduleTimeZoneDto> scheduleTimeZone;
	
	private String employeeId;
	
	private GeneralDate date;

	private int calculationState;

	// 直行区分
	private int goStraightAtr;

	// 直帰区分
	private int backStraightAtr;
	
	public static WorkInformationOfDailyDto getDto(WorkInfoOfDailyPerformance workInfo) {
		WorkInformationOfDailyDto result = new WorkInformationOfDailyDto();
		if (workInfo != null) {
			result.setActualWorkInfo(createWorkInfo(workInfo.getRecordWorkInformation()));
			result.setBackStraightAtr(workInfo.getBackStraightAtr().value);
			result.setCalculationState(workInfo.getCalculationState().value);
			result.setGoStraightAtr(workInfo.getGoStraightAtr().value);
			result.setPlanWorkInfo(createWorkInfo(workInfo.getScheduleWorkInformation()));
			result.setScheduleTimeZone(workInfo.getScheduleTimeSheets().stream().map(sts -> {
				return new ScheduleTimeZoneDto(sts.getWorkNo().v().intValue(), sts.getAttendance().v(),
						sts.getLeaveWork().v());
			}).sorted((s1, s2) -> s1.getWorkNo().compareTo(s2.getWorkNo())).collect(Collectors.toList()));
		}
		return result;
	}

	private static WorkInfoDto createWorkInfo(WorkInformation workInfo) {
		return workInfo == null ? null : new WorkInfoDto(workInfo.getWorkTypeCode().v(),
				workInfo.getWorkTimeCode().v());
	}

}
