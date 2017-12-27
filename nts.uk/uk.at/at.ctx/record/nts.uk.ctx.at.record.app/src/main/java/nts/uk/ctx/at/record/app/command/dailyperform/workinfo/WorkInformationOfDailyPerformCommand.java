package nts.uk.ctx.at.record.app.command.dailyperform.workinfo;

import lombok.Getter;
import nts.uk.ctx.at.record.app.find.dailyperform.workinfo.dto.WorkInformationOfDailyDto;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ConvertibleAttendanceItem;

public class WorkInformationOfDailyPerformCommand extends DailyWorkCommonCommand {

	@Getter
	private WorkInformationOfDailyDto data;

	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		this.data = (WorkInformationOfDailyDto) item;
	}
}
