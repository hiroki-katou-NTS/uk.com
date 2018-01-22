package nts.uk.ctx.at.record.app.find.dailyperform.workrecord.dto;

import java.util.List;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.common.TimeStampDto;
import nts.uk.ctx.at.record.app.find.dailyperform.common.WithActualTimeStampDto;
import nts.uk.ctx.at.record.dom.worktime.TimeActualStamp;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.WorkStamp;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemRoot;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.item.ConvertibleAttendanceItem;
import nts.uk.ctx.at.shared.app.util.attendanceitem.item.ValueType;

@AttendanceItemRoot(rootName = "日別実績の出退勤")
@Data
public class TimeLeavingOfDailyPerformanceDto implements ConvertibleAttendanceItem {

	private String employeeId;

	private GeneralDate ymd;

	@AttendanceItemLayout(layout = "A", jpPropertyName = "出退勤", listMaxLength = 2, indexField = "workNo")
	private List<WorkLeaveTimeDto> workAndLeave;

	@AttendanceItemLayout(layout = "B", jpPropertyName = "勤務回数")
	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer workTimes;

	public static TimeLeavingOfDailyPerformanceDto getDto(TimeLeavingOfDailyPerformance domain) {
		TimeLeavingOfDailyPerformanceDto dto = new TimeLeavingOfDailyPerformanceDto();
		if (domain != null) {
			dto.setEmployeeId(domain.getEmployeeId());
			dto.setYmd(domain.getYmd());
			dto.setWorkTimes(domain.getWorkTimes() != null ? domain.getWorkTimes().v() : null);
			dto.setWorkAndLeave(ConvertHelper.mapTo(domain.getTimeLeavingWorks(),
					(c) -> new WorkLeaveTimeDto(c.getWorkNo().v(),
							getActualTimeStamp(c.getAttendanceStamp().orElse(null)),
							getActualTimeStamp(c.getLeaveStamp().orElse(null)))));
		}
		return dto;
	}

	private static WithActualTimeStampDto getActualTimeStamp(TimeActualStamp c) {
		return c == null ? null : new WithActualTimeStampDto(
				getTimeStamp(c.getStamp().orElse(null)), 
				getTimeStamp(c.getActualStamp()),
				c.getNumberOfReflectionStamp());
	}

	private static TimeStampDto getTimeStamp(WorkStamp c) {
		return c == null ? null : new TimeStampDto(
					c.getTimeWithDay() == null ? null : c.getTimeWithDay().valueAsMinutes(),
					c.getAfterRoundingTime() == null ? null : c.getAfterRoundingTime().valueAsMinutes(),
					c.getLocationCode() == null ? null : c.getLocationCode().v(), c.getStampSourceInfo().value);

	}
}
