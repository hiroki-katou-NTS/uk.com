package nts.uk.ctx.at.record.app.command.dailyperform.specificdatetttr;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.record.app.find.dailyperform.specificdatetttr.dto.SpecificDateAttrOfDailyPerforDto;
import nts.uk.ctx.at.record.dom.raisesalarytime.SpecificDateAttrOfDailyPerfor;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemCommon;

public class SpecificDateAttrOfDailyCommand extends DailyWorkCommonCommand {

	@Getter
	private Optional<SpecificDateAttrOfDailyPerforDto> data;

	@Override
	public void setRecords(AttendanceItemCommon item) {
		this.data = item == null || !item.isHaveData() ? Optional.empty() : Optional.of((SpecificDateAttrOfDailyPerforDto) item);
	}

	@Override
	public void updateData(Object item) {
		if(data == null){ return; }
		setRecords(SpecificDateAttrOfDailyPerforDto.getDto((SpecificDateAttrOfDailyPerfor) item));
	}
	
	@Override
	public Optional<SpecificDateAttrOfDailyPerfor> toDomain() {
		return data == null ? null : data.map(c -> c.toDomain(getEmployeeId(), getWorkDate()));
	}
}
