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
								ConvertHelper.getEnum(c.getChildCareAttr(), ChildCareAttribute.class),
								new TimeWithDayAttr(c.getStartTime()), new TimeWithDayAttr(c.getEndTime()),
								new AttendanceTime(c.getDeductionTime()), new AttendanceTime(c.getShortTime()))),
				getWorkDate()) : null;
	}
}
