package nts.uk.ctx.at.record.app.command.monthly.reserveleave;

import lombok.Getter;
import nts.uk.ctx.at.record.app.command.monthly.MonthlyWorkCommonCommand;
import nts.uk.ctx.at.record.app.find.monthly.root.RsvLeaRemNumEachMonthDto;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ConvertibleAttendanceItem;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.reserveleave.RsvLeaRemNumEachMonth;

public class RsvLeaRemNumEachMonthCommand extends MonthlyWorkCommonCommand{

	@Getter
	private RsvLeaRemNumEachMonthDto data;
	
	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		this.data = item == null || !item.isHaveData() ? null : (RsvLeaRemNumEachMonthDto) item;
	}

	@Override
	public void updateData(Object data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public RsvLeaRemNumEachMonth toDomain() {
		// TODO Auto-generated method stub
		return data.toDomain(getEmployeeId(), getYearMonth(), getClosureId(), getClosureDate());
	}

	
}