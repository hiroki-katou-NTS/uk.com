package nts.uk.ctx.at.record.app.find.dailyperform.attendanceleavinggate.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.common.TimeSheetDto;
import nts.uk.ctx.at.record.app.find.dailyperform.common.TimeStampDto;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.AttendanceLeavingGate;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.AttendanceLeavingGateOfDaily;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocationCD;
import nts.uk.ctx.at.record.dom.worktime.WorkStamp;
import nts.uk.ctx.at.record.dom.worktime.enums.StampSourceInfo;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemRoot;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ConvertibleAttendanceItem;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkNo;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Data
@AttendanceItemRoot(rootName = "日別実績の入退門")
public class AttendanceLeavingGateOfDailyDto implements ConvertibleAttendanceItem {

	private String employeeId;
	
	private GeneralDate ymd;
	
	@AttendanceItemLayout(layout = "A", jpPropertyName = "入退門時刻", listMaxLength = 3, indexField = "timeSheetNo")
	private List<TimeSheetDto> attendanceLeavingGateTime;
	
	public static AttendanceLeavingGateOfDailyDto getDto(AttendanceLeavingGateOfDaily domain){
		AttendanceLeavingGateOfDailyDto dto = new AttendanceLeavingGateOfDailyDto();
		if (domain != null) {
			dto.setAttendanceLeavingGateTime(ConvertHelper.mapTo(domain.getAttendanceLeavingGates(),
					(c) -> new TimeSheetDto(c.getWorkNo().v(),
							createTimeStamp(c.getAttendance()),
							createTimeStamp(c.getLeaving()),
							0

					)));
			dto.setEmployeeId(domain.getEmployeeId());
			dto.setYmd(domain.getYmd());
		}
		return dto;
	}

	private static TimeStampDto createTimeStamp(WorkStamp c) {
		return c == null ? null : new TimeStampDto(
				c.getTimeWithDay() == null ? null : c.getTimeWithDay().valueAsMinutes(),
				c.getAfterRoundingTime() == null ? null : c.getAfterRoundingTime().valueAsMinutes(),
				c.getLocationCode().v(),
				c.getStampSourceInfo().value);
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
	public AttendanceLeavingGateOfDaily toDomain() {
		return new AttendanceLeavingGateOfDaily(employeeId, ymd,
					attendanceLeavingGateTime == null ? new ArrayList<>() : ConvertHelper.mapTo(attendanceLeavingGateTime,
						(c) -> new AttendanceLeavingGate(new WorkNo(c.getTimeSheetNo()),
								createWorkStamp(c.getStart()),
								createWorkStamp(c.getEnd()))));
	}

	private WorkStamp createWorkStamp(TimeStampDto c) {
		return c == null ? null : new WorkStamp(new TimeWithDayAttr(c.getAfterRoundingTimesOfDay()),
				new TimeWithDayAttr(c.getTimesOfDay()),
				new WorkLocationCD(c.getPlaceCode()),
				c == null ? StampSourceInfo.HAND_CORRECTION_BY_MYSELF : EnumAdaptor.valueOf(c.getStampSourceInfo(), StampSourceInfo.class));
	}
}
