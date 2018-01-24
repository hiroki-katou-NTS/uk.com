package nts.uk.ctx.at.record.app.command.dailyperform.attendanceleavinggate;

import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.app.find.dailyperform.attendanceleavinggate.dto.AttendanceLeavingGateOfDailyDto;
import nts.uk.ctx.at.record.app.find.dailyperform.common.TimeStampDto;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.AttendanceLeavingGate;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.AttendanceLeavingGateOfDaily;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocationCD;
import nts.uk.ctx.at.record.dom.worktime.WorkStamp;
import nts.uk.ctx.at.record.dom.worktime.enums.StampSourceInfo;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ConvertibleAttendanceItem;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkNo;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class AttendanceLeavingGateOfDailyCommand extends DailyWorkCommonCommand {

	@Getter
	private Optional<AttendanceLeavingGateOfDailyDto> data;

	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		this.data = item == null ? Optional.empty() : Optional.of((AttendanceLeavingGateOfDailyDto) item);
	}

	@Override
	public AttendanceLeavingGateOfDaily toDomain() {
		return !data.isPresent() ? null : new AttendanceLeavingGateOfDaily(this.getEmployeeId(), this.getWorkDate(),
				ConvertHelper.mapTo(data.get().getAttendanceLeavingGateTime(),
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
