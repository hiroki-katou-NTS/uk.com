package nts.uk.ctx.at.record.app.command.dailyperform.shorttimework;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.record.app.find.dailyperform.shorttimework.dto.ShortTimeOfDailyDto;
import nts.uk.ctx.at.record.dom.shorttimework.ShortTimeOfDailyPerformance;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ConvertibleAttendanceItem;

public class ShortTimeOfDailyCommand extends DailyWorkCommonCommand {

	@Getter
	private Optional<ShortTimeOfDailyPerformance> data;

	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		this.data = item == null ? Optional.empty() : Optional.of(((ShortTimeOfDailyDto) item).toDomain(getEmployeeId(), getWorkDate()));
	}

	@Override
	public void updateData(Object item) {
		this.data = item == null ? Optional.empty() : Optional.of((ShortTimeOfDailyPerformance) item);
	}

}
