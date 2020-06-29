package nts.uk.ctx.at.record.app.command.dailyperform.workinfo;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.app.find.dailyperform.workinfo.dto.WorkInformationOfDailyDto;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ConvertibleAttendanceItem;

public class WorkInformationOfDailyPerformCommand extends DailyWorkCommonCommand {

	@Getter
	private WorkInfoOfDailyPerformance data;
	
	@Getter
	@Setter
	private boolean isTriggerEvent = false;

	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		WorkInfoOfDailyPerformance workInfoOfDailyPerformance = new WorkInfoOfDailyPerformance(getEmployeeId(),
				getWorkDate(), ((WorkInformationOfDailyDto) item).toDomain(getEmployeeId(), getWorkDate()));
		this.data = item == null || !item.isHaveData() ? null
				:workInfoOfDailyPerformance ;
	}

	@Override
	public void updateData(Object data) {
		if(data == null){ return; }
		this.data = (WorkInfoOfDailyPerformance) data;
	}

	@Override
	public WorkInfoOfDailyPerformance toDomain() {
		return data;
	}

	@Override
	public WorkInformationOfDailyDto toDto() {
		return WorkInformationOfDailyDto.getDto(getData());
	}
}
