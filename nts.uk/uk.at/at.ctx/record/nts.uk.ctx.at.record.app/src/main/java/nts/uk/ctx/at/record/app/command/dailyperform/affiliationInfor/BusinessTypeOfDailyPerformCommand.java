package nts.uk.ctx.at.record.app.command.dailyperform.affiliationInfor;

import lombok.Getter;
import nts.uk.ctx.at.record.app.find.dailyperform.affiliationInfor.dto.BusinessTypeOfDailyPerforDto;
import nts.uk.ctx.at.record.dom.affiliationinformation.WorkTypeOfDailyPerformance;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemCommon;

public class BusinessTypeOfDailyPerformCommand extends DailyWorkCommonCommand {

	@Getter
	private BusinessTypeOfDailyPerforDto data;

	@Override
	public void setRecords(AttendanceItemCommon item) {
		this.data = item == null || !item.isHaveData() ? null : (BusinessTypeOfDailyPerforDto) item;
	}

	@Override
	public void updateData(Object data) {
		this.data = (BusinessTypeOfDailyPerforDto) data;
	}
	
	@Override
	public WorkTypeOfDailyPerformance toDomain() {
		return data == null ? null : data.toDomain(getEmployeeId(), getWorkDate());
	}
}
