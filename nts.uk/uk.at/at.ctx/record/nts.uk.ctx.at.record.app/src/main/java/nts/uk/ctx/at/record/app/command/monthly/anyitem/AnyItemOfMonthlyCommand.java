package nts.uk.ctx.at.record.app.command.monthly.anyitem;

import lombok.Getter;
import nts.uk.ctx.at.record.app.command.monthly.MonthlyWorkCommonCommand;
import nts.uk.ctx.at.record.app.find.monthly.root.AnyItemOfMonthlyDto;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemCommon;

public class AnyItemOfMonthlyCommand extends MonthlyWorkCommonCommand{

	@Getter
	private AnyItemOfMonthlyDto data;
	
	@Override
	public void setRecords(AttendanceItemCommon item) {
		this.data = item == null ? null : (AnyItemOfMonthlyDto) item;
	}

	@Override
	public void updateData(Object data) {
		// TODO Auto-generated method stub
		
	}

}