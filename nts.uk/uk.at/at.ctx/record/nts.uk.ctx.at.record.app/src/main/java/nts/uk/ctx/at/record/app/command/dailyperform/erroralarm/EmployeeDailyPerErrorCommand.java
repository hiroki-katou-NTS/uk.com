package nts.uk.ctx.at.record.app.command.dailyperform.erroralarm;

import lombok.Getter;
import nts.uk.ctx.at.record.app.find.dailyperform.erroralarm.dto.EmployeeDailyPerErrorDto;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemCommon;

public class EmployeeDailyPerErrorCommand extends DailyWorkCommonCommand {

	@Getter
	private EmployeeDailyPerErrorDto data;

	@Override
	public void setRecords(AttendanceItemCommon item) {
		this.data = item == null || !item.isHaveData() ? null : (EmployeeDailyPerErrorDto) item;
	}

	@Override
	public void updateData(Object data) {
		if(data == null){ return; }
		setRecords(EmployeeDailyPerErrorDto.getDto((EmployeeDailyPerError) data));
	}

	@Override
	public EmployeeDailyPerError toDomain() {
		return data == null ? null : data.toDomain(getEmployeeId(), getWorkDate());
	}
}
