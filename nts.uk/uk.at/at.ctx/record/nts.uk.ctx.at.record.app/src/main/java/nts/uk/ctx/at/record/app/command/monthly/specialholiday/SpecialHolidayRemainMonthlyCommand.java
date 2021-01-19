package nts.uk.ctx.at.record.app.command.monthly.specialholiday;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.uk.ctx.at.record.app.command.monthly.MonthlyWorkCommonCommand;
import nts.uk.ctx.at.record.app.find.monthly.root.SpecialHolidayRemainDataDto;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ConvertibleAttendanceItem;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialHolidayRemainData;

public class SpecialHolidayRemainMonthlyCommand extends MonthlyWorkCommonCommand{

	@Getter
	private List<SpecialHolidayRemainDataDto> data = new ArrayList<>();
	
	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		if(item != null && item.isHaveData()){
			updateData(item);
		}
	}

	
	@Override
	public void updateData(Object data) {
		SpecialHolidayRemainDataDto d = (SpecialHolidayRemainDataDto) data;
		if (data != null) {
			this.data.removeIf(br -> br.employeeId().equals(d.getEmployeeId()) && br.getClosureID() == d.getClosureID()
					&& br.getClosureDate().getClosureDay() == d.getClosureDate().getClosureDay()
					&& br.getClosureDate().getLastDayOfMonth().booleanValue() == d.getClosureDate().getLastDayOfMonth()
							.booleanValue() && br.getNo() == d.getNo());
			this.data.add(d);
		}

	}

	@Override
	public List<SpecialHolidayRemainData> toDomain() {
		return data == null ? new ArrayList<>()
				: data.stream().map(x -> x.toDomain(getEmployeeId(), getYearMonth(), getClosureId(), getClosureDate()))
						.collect(Collectors.toList());
	}
	
	

}