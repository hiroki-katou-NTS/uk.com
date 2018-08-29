package nts.uk.ctx.at.record.app.command.monthly.specialholiday;

import lombok.Getter;
import nts.uk.ctx.at.record.app.command.monthly.MonthlyWorkCommonCommand;
import nts.uk.ctx.at.record.app.find.monthly.root.SpecialHolidayRemainDataDto;
import nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.SpecialHolidayRemainData;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ConvertibleAttendanceItem;

public class SpecialHolidayRemainMonthlyCommand extends MonthlyWorkCommonCommand{

	@Getter
	private SpecialHolidayRemainDataDto data;
	
	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		this.data = item == null || !item.isHaveData() ? null : (SpecialHolidayRemainDataDto) item;
	}

	@Override
	public void updateData(Object data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SpecialHolidayRemainData toDomain() {
		return data.toDomain(getEmployeeId(), getYearMonth(), getClosureId(), getClosureDate());
	}
	
	

}