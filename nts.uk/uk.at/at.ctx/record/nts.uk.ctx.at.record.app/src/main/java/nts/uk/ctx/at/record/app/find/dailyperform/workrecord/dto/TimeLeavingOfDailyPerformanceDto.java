package nts.uk.ctx.at.record.app.find.dailyperform.workrecord.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.common.TimeStampDto;
import nts.uk.ctx.at.record.app.find.dailyperform.common.WithActualTimeStampDto;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocationCD;
import nts.uk.ctx.at.record.dom.worktime.TimeActualStamp;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingWork;
import nts.uk.ctx.at.record.dom.worktime.WorkStamp;
import nts.uk.ctx.at.record.dom.worktime.enums.StampSourceInfo;
import nts.uk.ctx.at.record.dom.worktime.primitivevalue.WorkNo;
import nts.uk.ctx.at.record.dom.worktime.primitivevalue.WorkTimes;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemRoot;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ConvertibleAttendanceItem;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.shr.com.time.TimeWithDayAttr;

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
		return c == null ? null
				: new WithActualTimeStampDto(getTimeStamp(c.getStamp().orElse(null)), getTimeStamp(c.getActualStamp()),
						c.getNumberOfReflectionStamp());
	}

	private static TimeStampDto getTimeStamp(WorkStamp c) {
		return c == null ? null
				: new TimeStampDto(c.getTimeWithDay() == null ? null : c.getTimeWithDay().valueAsMinutes(),
						c.getAfterRoundingTime() == null ? null : c.getAfterRoundingTime().valueAsMinutes(),
						c.getLocationCode() == null ? null : c.getLocationCode().v(), c.getStampSourceInfo().value);

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
	public TimeLeavingOfDailyPerformance toDomain() {
		return new TimeLeavingOfDailyPerformance(getEmployeeId(), new WorkTimes(toWorkTime()),
				workAndLeave == null ? new ArrayList<>() : ConvertHelper.mapTo(workAndLeave, c -> toTimeLeaveWork(c)),
				ymd);
	}

	private int toWorkTime() {
		return workTimes == null ? (workAndLeave == null ? 0 : workAndLeave.size()) : (workTimes);
	}

	private TimeLeavingWork toTimeLeaveWork(WorkLeaveTimeDto c) {
		return c == null ? null
				: new TimeLeavingWork(new WorkNo(c.getWorkNo()), toTimeActualStamp(c.getWorking()),
						toTimeActualStamp(c.getLeave()));
	}

	private Optional<TimeActualStamp> toTimeActualStamp(WithActualTimeStampDto c) {
		return c == null ? Optional.empty()
				: Optional.of(new TimeActualStamp(toWorkStamp(c.getActualTime()), toWorkStamp(c.getTime()),
						c.getNumberOfReflectionStamp()));
	}

	private WorkStamp toWorkStamp(TimeStampDto c) {
		return c == null ? null
				: new WorkStamp(
						c.getAfterRoundingTimesOfDay() == null ? null
								: new TimeWithDayAttr(c.getAfterRoundingTimesOfDay()),
						c.getTimesOfDay() == null ? null : new TimeWithDayAttr(c.getTimesOfDay()),
						c.getPlaceCode() == null ? null : new WorkLocationCD(c.getPlaceCode()),
						c.getStampSourceInfo() == null ? StampSourceInfo.HAND_CORRECTION_BY_MYSELF
								: ConvertHelper.getEnum(c.getStampSourceInfo(), StampSourceInfo.class));
	}
}
