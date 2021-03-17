package nts.uk.ctx.at.record.app.command.monthly.specialholiday;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.record.app.command.monthly.MonthlyWorkCommonCommand;
import nts.uk.ctx.at.record.app.find.monthly.root.dto.SpecialHolidayRemainDataDtoWrap;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ConvertibleAttendanceItem;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialHolidayRemainData;

public class SpecialHolidayRemainMonthlyCommand extends MonthlyWorkCommonCommand {

	@Getter
	private SpecialHolidayRemainDataDtoWrap data;

	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		if (item != null && item.isHaveData()) {
			this.data = (SpecialHolidayRemainDataDtoWrap) item;
		}
	}

	@Override
	public void updateData(Object data) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<SpecialHolidayRemainData> toDomain() {
		return this.data == null ? new ArrayList<>()
				: this.data.toDomain(getEmployeeId(), getYearMonth(), getClosureId(), getClosureDate());
	}

}