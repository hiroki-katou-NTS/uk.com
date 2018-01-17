package nts.uk.ctx.at.record.app.command.dailyperform.shorttimework;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.record.app.find.dailyperform.shorttimework.dto.ShortTimeOfDailyDto;
import nts.uk.ctx.at.record.dom.shorttimework.ShortTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.shorttimework.ShortWorkingTimeSheet;
import nts.uk.ctx.at.record.dom.shorttimework.enums.ChildCareAttribute;
import nts.uk.ctx.at.record.dom.shorttimework.primitivevalue.ShortWorkTimFrameNo;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ConvertibleAttendanceItem;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class ShortTimeOfDailyCommand extends DailyWorkCommonCommand {

	@Getter
	private Optional<ShortTimeOfDailyDto> data;

	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		this.data = item == null ? Optional.empty() : Optional.of((ShortTimeOfDailyDto) item);
	}

	@Override
	public ShortTimeOfDailyPerformance toDomain() {
		return data.isPresent() ? new ShortTimeOfDailyPerformance(getEmployeeId(),
				ConvertHelper.mapTo(data.get().getShortWorkingTimeSheets(),
						(c) -> new ShortWorkingTimeSheet(new ShortWorkTimFrameNo(c.getShortWorkTimeFrameNo()),
								c.getChildCareAttr() == null ? ChildCareAttribute.CHILD_CARE : ConvertHelper.getEnum(c.getChildCareAttr(), ChildCareAttribute.class),
								createTimeWithDayAttr(c.getStartTime()), createTimeWithDayAttr(c.getEndTime()),
								createAttendanceTime(c.getDeductionTime()), createAttendanceTime(c.getShortTime()))),
				getWorkDate()) : null;
	}

	private TimeWithDayAttr createTimeWithDayAttr(Integer c) {
		return c == null ? null : new TimeWithDayAttr(c);
	}
	
	private AttendanceTime createAttendanceTime(Integer c) {
		return c == null ? null : new AttendanceTime(c);
	}
}
