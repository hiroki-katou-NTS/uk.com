package nts.uk.ctx.at.record.app.command.dailyperform.affiliationInfor;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.record.app.find.dailyperform.affiliationInfor.dto.BusinessTypeOfDailyPerforDto;
import nts.uk.ctx.at.record.dom.affiliationinformation.WorkTypeOfDailyPerformance;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ConvertibleAttendanceItem;

public class BusinessTypeOfDailyPerformCommand extends DailyWorkCommonCommand {

	@Getter
	private Optional<BusinessTypeOfDailyPerforDto> data;

	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		this.data = item == null || !item.isHaveData() ? Optional.empty() 
				: Optional.of((BusinessTypeOfDailyPerforDto) item);
	}

	@Override
	public void updateData(Object data) {
		this.data = Optional.of((BusinessTypeOfDailyPerforDto) data);
	}
	
	@Override
	public Optional<WorkTypeOfDailyPerformance> toDomain() {
		return data == null ? null : data.map(bt -> bt.toDomain(getEmployeeId(), getWorkDate()));
	}
}
