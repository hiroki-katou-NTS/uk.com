package nts.uk.ctx.at.record.app.command.dailyperform.erroralarm;

import lombok.Getter;
import nts.uk.ctx.at.record.app.find.dailyperform.erroralarm.dto.EmployeeDailyPerErrorDto;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ErrorAlarmWorkRecordCode;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ConvertibleAttendanceItem;

public class EmployeeDailyPerErrorCommand extends DailyWorkCommonCommand {

	@Getter
	private EmployeeDailyPerErrorDto data;

	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		this.data = (EmployeeDailyPerErrorDto) item;
	}
	
	@Override
	public EmployeeDailyPerError toDomain() {
		return new EmployeeDailyPerError(data.getCompanyID(), getEmployeeId(), getWorkDate(),
				new ErrorAlarmWorkRecordCode(data.getErrorCode()), data.getAttendanceItemList(), 0);
	}
}
