package nts.uk.ctx.at.record.app.command.monthly.specialholiday;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.uk.ctx.at.record.app.command.monthly.MonthlyWorkCommonCommand;
import nts.uk.ctx.at.record.app.find.monthly.root.SpecialHolidayRemainDataDto;
import nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.SpecialHolidayRemainData;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ConvertibleAttendanceItem;

public class SpecialHolidayRemainMonthlyCommand extends MonthlyWorkCommonCommand{

	@Getter
	private List<SpecialHolidayRemainDataDto> data = new ArrayList<>();
	
	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		if(item != null && item.isHaveData()){
			this.data.add((SpecialHolidayRemainDataDto) item);
		}
	}

	@Override
	public void updateData(Object data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<SpecialHolidayRemainData> toDomain() {
		return data.stream().map(d -> d.toDomain(getEmployeeId(), getYearMonth(), getClosureId(), getClosureDate())).collect(Collectors.toList());
	}
	
	

}