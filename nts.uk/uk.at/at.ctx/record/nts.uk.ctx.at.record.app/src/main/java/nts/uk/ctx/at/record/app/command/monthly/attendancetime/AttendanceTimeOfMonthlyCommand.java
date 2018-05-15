package nts.uk.ctx.at.record.app.command.monthly.attendancetime;

import lombok.Getter;
import nts.uk.ctx.at.record.app.command.monthly.MonthlyWorkCommonCommand;
import nts.uk.ctx.at.record.app.find.monthly.root.AttendanceTimeOfMonthlyDto;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemCommon;

public class AttendanceTimeOfMonthlyCommand extends MonthlyWorkCommonCommand{

	@Getter
	private AttendanceTimeOfMonthlyDto data;
	
	@Override
	public void setRecords(AttendanceItemCommon item) {
		this.data = item == null ? null : (AttendanceTimeOfMonthlyDto) item;
	}

	@Override
	public void updateData(Object data) {
		// TODO Auto-generated method stub
		
	}

}