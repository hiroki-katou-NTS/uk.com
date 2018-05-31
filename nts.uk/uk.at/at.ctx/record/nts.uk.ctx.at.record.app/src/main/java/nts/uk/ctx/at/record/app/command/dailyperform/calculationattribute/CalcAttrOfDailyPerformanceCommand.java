package nts.uk.ctx.at.record.app.command.dailyperform.calculationattribute;

import lombok.Getter;
import nts.uk.ctx.at.record.app.find.dailyperform.calculationattribute.dto.CalcAttrOfDailyPerformanceDto;
import nts.uk.ctx.at.record.dom.calculationattribute.CalAttrOfDailyPerformance;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ConvertibleAttendanceItem;

public class CalcAttrOfDailyPerformanceCommand extends DailyWorkCommonCommand {

	@Getter
	private CalcAttrOfDailyPerformanceDto data;

	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		this.data = item == null || !item.isHaveData() ? null : (CalcAttrOfDailyPerformanceDto) item;
	}

	@Override
	public void updateData(Object data) {
		if(data == null){ return; }
		setRecords(CalcAttrOfDailyPerformanceDto.getDto((CalAttrOfDailyPerformance) data));
	}

	@Override
	public CalAttrOfDailyPerformance toDomain() {
		return data == null ? null : data.toDomain(getEmployeeId(), getWorkDate());
	}
}
