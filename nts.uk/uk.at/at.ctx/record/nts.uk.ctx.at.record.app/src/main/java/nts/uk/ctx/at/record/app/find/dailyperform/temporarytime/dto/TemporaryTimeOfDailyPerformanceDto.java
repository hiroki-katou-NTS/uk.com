package nts.uk.ctx.at.record.app.find.dailyperform.temporarytime.dto;

import java.util.List;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.common.TimeStampDto;
import nts.uk.ctx.at.record.app.find.dailyperform.common.WithActualTimeStampDto;
import nts.uk.ctx.at.record.app.find.dailyperform.workrecord.dto.WorkLeaveTimeDto;
import nts.uk.ctx.at.record.dom.worktime.TemporaryTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeActualStamp;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingWork;
import nts.uk.ctx.at.record.dom.worktime.WorkStamp;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendanceitem.util.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendanceitem.util.annotation.AttendanceItemRoot;
import nts.uk.ctx.at.shared.dom.attendanceitem.util.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendanceitem.util.item.ConvertibleAttendanceItem;
import nts.uk.ctx.at.shared.dom.attendanceitem.util.item.ValueType;

@Data
@AttendanceItemRoot(rootName = "日別実績の臨時出退勤")
public class TemporaryTimeOfDailyPerformanceDto implements ConvertibleAttendanceItem {

	private String employeeId;

	@AttendanceItemLayout(layout = "A", jpPropertyName = "勤務回数")
	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer workTimes;

	@AttendanceItemLayout(layout = "B", jpPropertyName = "出退勤", listMaxLength = 3, indexField = "workNo")
	private List<WorkLeaveTimeDto> workLeaveTime;

	private GeneralDate ymd;
	
	public static TemporaryTimeOfDailyPerformanceDto getDto(TemporaryTimeOfDailyPerformance domain) {
		TemporaryTimeOfDailyPerformanceDto dto = new TemporaryTimeOfDailyPerformanceDto();
		if (domain != null) {
			dto.setEmployeeId(domain.getEmployeeId());
			dto.setYmd(domain.getYmd());
			dto.setWorkTimes(domain.getWorkTimes().v());
			dto.setWorkLeaveTime(ConvertHelper.mapTo(domain.getTimeLeavingWorks(), (c) -> newWorkLeaveTime(c)));
		}
		return dto;
	}

	private static WorkLeaveTimeDto newWorkLeaveTime(TimeLeavingWork c) {
		return new WorkLeaveTimeDto(c.getWorkNo().v(), newTimeWithActual(c.getAttendanceStamp().get()),
					newTimeWithActual(c.getLeaveStamp().get()));
	}

	private static WithActualTimeStampDto newTimeWithActual(TimeActualStamp c) {
		return c == null ? null : new WithActualTimeStampDto(
					newTimeStamp(c.getStamp().orElse(null)), 
					newTimeStamp(c.getActualStamp()),
					c.getNumberOfReflectionStamp());
	}

	private static TimeStampDto newTimeStamp(WorkStamp c) {
		return c == null ? null : new TimeStampDto(
					c.getTimeWithDay() == null ? null : c.getTimeWithDay().valueAsMinutes(),
					c.getAfterRoundingTime() == null ? null : c.getAfterRoundingTime().valueAsMinutes(),
					c.getLocationCode() == null ? null : c.getLocationCode().v(),
					c.getStampSourceInfo() == null ? null : c.getStampSourceInfo().value);

	}
}
