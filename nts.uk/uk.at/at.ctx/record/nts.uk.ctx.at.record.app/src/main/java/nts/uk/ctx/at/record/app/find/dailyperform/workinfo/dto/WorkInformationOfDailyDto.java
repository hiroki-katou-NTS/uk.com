package nts.uk.ctx.at.record.app.find.dailyperform.workinfo.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workinformation.ScheduleTimeSheet;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.enums.CalculationState;
import nts.uk.ctx.at.record.dom.workinformation.enums.NotUseAttribute;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemRoot;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemCommon;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.DayOfWeek;

/** 日別実績の勤務情報 */
@Data
@AttendanceItemRoot(rootName = ItemConst.DAILY_WORK_INFO_NAME)
public class WorkInformationOfDailyDto extends AttendanceItemCommon {

	/** 勤務実績の勤務情報: 勤務情報 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = ACTUAL)
	private WorkInfoDto actualWorkInfo;

	/** 勤務予定の勤務情報: 勤務情報 */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = PLAN)
	private WorkInfoDto planWorkInfo;

	/** 勤務予定時間帯: 予定時間帯 */
	@AttendanceItemLayout(layout = LAYOUT_C, jpPropertyName = PLAN + TIME_ZONE, 
			listMaxLength = 2, indexField = DEFAULT_INDEX_FIELD_NAME)
	private List<ScheduleTimeZoneDto> scheduleTimeZone;

	private String employeeId;

	private GeneralDate date;

	private int calculationState;

	// 直行区分
	private int goStraightAtr;

	// 直帰区分
	private int backStraightAtr;

	private int dayOfWeek;

	public static WorkInformationOfDailyDto getDto(WorkInfoOfDailyPerformance workInfo) {
		WorkInformationOfDailyDto result = new WorkInformationOfDailyDto();
		if (workInfo != null) {
			result.setEmployeeId(workInfo.getEmployeeId());
			result.setDate(workInfo.getYmd());
			result.setActualWorkInfo(createWorkInfo(workInfo.getRecordInfo()));
			result.setBackStraightAtr(workInfo.getBackStraightAtr().value);
			result.setCalculationState(workInfo.getCalculationState().value);
			result.setGoStraightAtr(workInfo.getGoStraightAtr().value);
			result.setPlanWorkInfo(createWorkInfo(workInfo.getScheduleInfo()));
			
			result.setScheduleTimeZone(getScheduleTimeZone(workInfo.getScheduleTimeSheets()));
			result.setDayOfWeek(workInfo.getDayOfWeek() != null ? workInfo.getDayOfWeek().value : 0);
			result.exsistData();
		}
		return result;
	}

	private static List<ScheduleTimeZoneDto> getScheduleTimeZone(List<ScheduleTimeSheet> sheets) {
		return sheets == null ? new ArrayList<>() : sheets.stream().map(sts -> {
			return new ScheduleTimeZoneDto(sts.getWorkNo() == null ? null : sts.getWorkNo().v().intValue(),
					sts.getAttendance() == null ? null : sts.getAttendance().v(),
					sts.getLeaveWork() == null ? null : sts.getLeaveWork().v());
		}).sorted((s1, s2) -> s1.getNo().compareTo(s2.getNo())).collect(Collectors.toList());
	}

	private static WorkInfoDto createWorkInfo(WorkInformation workInfo) {
		return workInfo == null ? null
				: new WorkInfoDto(workInfo.getWorkTypeCode() == null ? null : workInfo.getWorkTypeCode().v(),
						workInfo.getWorkTimeCode() == null ? null : workInfo.getWorkTimeCode().v());
	}

	@Override
	public String employeeId() {
		return this.employeeId;
	}

	@Override
	public GeneralDate workingDate() {
		return this.date;
	}

	@Override
	public WorkInfoOfDailyPerformance toDomain(String employeeId, GeneralDate date) {
		if (!this.isHaveData()) {
			return null;
		}
		if (employeeId == null) {
			employeeId = this.employeeId();
		}
		if (date == null) {
			date = this.workingDate();
		}
		return new WorkInfoOfDailyPerformance(employeeId, getWorkInfo(actualWorkInfo), getWorkInfo(planWorkInfo),
				EnumAdaptor.valueOf(calculationState, CalculationState.class), EnumAdaptor.valueOf(goStraightAtr, NotUseAttribute.class), EnumAdaptor.valueOf(backStraightAtr, NotUseAttribute.class), date, EnumAdaptor.valueOf(dayOfWeek, DayOfWeek.class),
				ConvertHelper.mapTo(this.getScheduleTimeZone(), 
						(c) -> new ScheduleTimeSheet(c.getNo(),
										c.getWorking() == null ? 0 : c.getWorking(), 
										c.getLeave() == null ? 0 : c.getLeave())));
	}

	private WorkInformation getWorkInfo(WorkInfoDto dto) {
		return dto == null ? null : new WorkInformation(dto.getWorkTimeCode() == null || dto.getWorkTimeCode().isEmpty() ? null : dto.getWorkTimeCode(), dto.getWorkTypeCode());
	}
}
