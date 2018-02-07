package nts.uk.ctx.at.record.app.command.dailyperform.calculationattribute;

import lombok.Getter;
import nts.uk.ctx.at.record.app.find.dailyperform.calculationattribute.dto.CalcAttrOfDailyPerformanceDto;
import nts.uk.ctx.at.record.dom.calculationattribute.CalAttrOfDailyPerformance;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ConvertibleAttendanceItem;

public class CalcAttrOfDailyPerformanceCommand extends DailyWorkCommonCommand {

	@Getter
	private CalAttrOfDailyPerformance data;

	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		this.data = item == null ? null : ((CalcAttrOfDailyPerformanceDto) item).toDomain();
	}

	@Override
	public void updateData(Object data) {
		this.data = (CalAttrOfDailyPerformance) data;
	}
}
