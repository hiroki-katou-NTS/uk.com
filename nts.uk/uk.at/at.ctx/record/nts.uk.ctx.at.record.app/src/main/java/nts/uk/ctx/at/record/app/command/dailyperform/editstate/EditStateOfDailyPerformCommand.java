package nts.uk.ctx.at.record.app.command.dailyperform.editstate;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.record.app.find.dailyperform.editstate.EditStateOfDailyPerformanceDto;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ConvertibleAttendanceItem;

public class EditStateOfDailyPerformCommand extends DailyWorkCommonCommand {

	@Getter
	private List<EditStateOfDailyPerformanceDto> data = new ArrayList<>();

	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		this.data.add((EditStateOfDailyPerformanceDto) item);
	}
}
