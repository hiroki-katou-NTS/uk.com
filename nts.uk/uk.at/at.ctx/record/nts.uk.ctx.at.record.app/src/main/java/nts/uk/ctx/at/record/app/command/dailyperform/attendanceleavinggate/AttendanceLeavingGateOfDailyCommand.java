package nts.uk.ctx.at.record.app.command.dailyperform.attendanceleavinggate;

import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.app.find.dailyperform.attendanceleavinggate.dto.AttendanceLeavingGateOfDailyDto;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.AttendanceLeavingGate;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.AttendanceLeavingGateOfDaily;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocationCD;
import nts.uk.ctx.at.record.dom.worktime.WorkStamp;
import nts.uk.ctx.at.record.dom.worktime.enums.StampSourceInfo;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ConvertibleAttendanceItem;
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
		return data.isPresent() ? new AttendanceLeavingGateOfDaily(this.getEmployeeId(), this.getWorkDate(),
				ConvertHelper.mapTo(data.get().getAttendanceLeavingGateTime(),
						(c) -> new AttendanceLeavingGate(new WorkNo(c.getTimeSheetNo()),
								new WorkStamp(new TimeWithDayAttr(c.getStart().getAfterRoundingTimesOfDay()),
										new TimeWithDayAttr(c.getStart().getTimesOfDay()),
										new WorkLocationCD(c.getStart().getPlaceCode()),
										EnumAdaptor.valueOf(c.getStart().getStampSourceInfo(), StampSourceInfo.class)),
								new WorkStamp(new TimeWithDayAttr(c.getEnd().getAfterRoundingTimesOfDay()),
										new TimeWithDayAttr(c.getEnd().getTimesOfDay()),
										new WorkLocationCD(c.getEnd().getPlaceCode()),
										EnumAdaptor.valueOf(c.getEnd().getStampSourceInfo(), StampSourceInfo.class))))) : null;
	}
}
