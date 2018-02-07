package nts.uk.ctx.at.record.app.find.dailyperform.goout.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.common.TimeStampDto;
import nts.uk.ctx.at.record.app.find.dailyperform.common.WithActualTimeStampDto;
import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeSheet;
import nts.uk.ctx.at.record.dom.breakorgoout.enums.GoingOutReason;
import nts.uk.ctx.at.record.dom.breakorgoout.primitivevalue.OutingFrameNo;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocationCD;
import nts.uk.ctx.at.record.dom.worktime.TimeActualStamp;
import nts.uk.ctx.at.record.dom.worktime.WorkStamp;
import nts.uk.ctx.at.record.dom.worktime.enums.StampSourceInfo;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemRoot;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ConvertibleAttendanceItem;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.shr.com.time.TimeWithDayAttr;

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
	

	@Override
	public OutingTimeOfDailyPerformance toDomain() {
		return new OutingTimeOfDailyPerformance(employeeId, ymd, 
					timeZone == null ? new ArrayList<>() : ConvertHelper.mapTo(timeZone,
						(c) -> new OutingTimeSheet(new OutingFrameNo(c.getWorkNo()), createTimeActual(c.getOuting()),
								new AttendanceTime(c.getOutTimeCalc()), new AttendanceTime(c.getOutTIme()),
								ConvertHelper.getEnum(c.getReason(), GoingOutReason.class),
								createTimeActual(c.getComeBack()))));
	}

	private Optional<TimeActualStamp> createTimeActual(WithActualTimeStampDto c) {
		return c == null ? Optional.empty() : Optional.of(new TimeActualStamp(createWorkStamp(c.getActualTime()), createWorkStamp(c.getTime()),
				c.getNumberOfReflectionStamp()));
	}

	private WorkStamp createWorkStamp(TimeStampDto c) {
		return c == null ? null : new WorkStamp(
				c.getAfterRoundingTimesOfDay() == null ? null : new TimeWithDayAttr(c.getAfterRoundingTimesOfDay()),
				c.getTimesOfDay() == null ? null : new TimeWithDayAttr(c.getTimesOfDay()),
				c.getPlaceCode() == null ? null : new WorkLocationCD(c.getPlaceCode()),
				c.getStampSourceInfo() == null ? StampSourceInfo.HAND_CORRECTION_BY_MYSELF : ConvertHelper.getEnum(c.getStampSourceInfo(), StampSourceInfo.class));
	}
}
