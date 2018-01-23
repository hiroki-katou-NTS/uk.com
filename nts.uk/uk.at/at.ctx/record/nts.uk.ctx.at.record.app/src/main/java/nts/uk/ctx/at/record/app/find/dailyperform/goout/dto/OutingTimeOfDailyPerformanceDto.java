package nts.uk.ctx.at.record.app.find.dailyperform.goout.dto;

import java.util.List;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.common.TimeStampDto;
import nts.uk.ctx.at.record.app.find.dailyperform.common.WithActualTimeStampDto;
import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeActualStamp;
import nts.uk.ctx.at.record.dom.worktime.WorkStamp;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemRoot;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ConvertibleAttendanceItem;

@AttendanceItemRoot(rootName = "日別実績の外出時間帯")
@Data
public class OutingTimeOfDailyPerformanceDto implements ConvertibleAttendanceItem {

	private String employeeId;
	
	private GeneralDate ymd;
	
	@AttendanceItemLayout(layout = "A", jpPropertyName = "時間帯", listMaxLength = 10, indexField = "workNo")
	private List<OutingTimeZoneDto> timeZone;
	
	public static OutingTimeOfDailyPerformanceDto getDto(OutingTimeOfDailyPerformance domain) {
		OutingTimeOfDailyPerformanceDto dto = new OutingTimeOfDailyPerformanceDto();
		if (domain != null) {
			dto.setEmployeeId(domain.getEmployeeId());
			dto.setYmd(domain.getYmd());
			dto.setTimeZone(ConvertHelper.mapTo(domain.getOutingTimeSheets(),
					(c) -> new OutingTimeZoneDto(
							c.getOutingFrameNo().v(),
							toWithActualTimeStamp(c.getGoOut().orElse(null)),
							toWithActualTimeStamp(c.getComeBack().orElse(null)),
							c.getReasonForGoOut().value, 
							c.getOutingTimeCalculation() == null ? null : c.getOutingTimeCalculation().valueAsMinutes(),
							c.getOutingTime() == null ? null : c.getOutingTime().valueAsMinutes())));
		}
		return dto;
	}

	private static WithActualTimeStampDto toWithActualTimeStamp(TimeActualStamp c) {
		return c == null ? null : new WithActualTimeStampDto(
				getTimeStamp(c.getStamp().orElse(null)),
				getTimeStamp(c.getActualStamp()),
				c.getNumberOfReflectionStamp());
	}

	private static TimeStampDto getTimeStamp(WorkStamp c) {
		return c == null ? null : new TimeStampDto(
				c.getTimeWithDay() == null ? null : c.getTimeWithDay().valueAsMinutes(),
				c.getAfterRoundingTime() == null ? null : c.getAfterRoundingTime().valueAsMinutes(),
				c.getLocationCode() == null ? null : c.getLocationCode().v(), 
				c.getStampSourceInfo() == null ? null : c.getStampSourceInfo().value);
	}

	@Override
	public String employeeId() {
		return this.employeeId;
	}

	@Override
	public GeneralDate workingDate() {
		return this.ymd;
	}
}
