package nts.uk.ctx.at.record.app.command.dailyperform.workinfo;

import lombok.Getter;
import nts.uk.ctx.at.record.app.find.dailyperform.workinfo.dto.WorkInformationOfDailyDto;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ConvertibleAttendanceItem;

public class WorkInformationOfDailyPerformCommand extends DailyWorkCommonCommand {

	@Getter
	private WorkInfoOfDailyPerformance data;

	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		this.data = item == null ? null : ((WorkInformationOfDailyDto) item).toDomain(getEmployeeId(), getWorkDate());
	}

	@Override
	public void updateData(Object data) {
		this.data = (WorkInfoOfDailyPerformance) data;
	}

}
