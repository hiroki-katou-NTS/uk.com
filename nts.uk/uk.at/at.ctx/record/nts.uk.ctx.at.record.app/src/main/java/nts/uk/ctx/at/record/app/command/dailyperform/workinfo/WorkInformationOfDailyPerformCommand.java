package nts.uk.ctx.at.record.app.command.dailyperform.workinfo;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.app.find.dailyperform.workinfo.dto.WorkInformationOfDailyDto;
import nts.uk.ctx.at.record.dom.workinformation.ScheduleTimeSheet;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.WorkInformation;
import nts.uk.ctx.at.record.dom.workinformation.enums.CalculationState;
import nts.uk.ctx.at.record.dom.workinformation.enums.NotUseAttribute;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ConvertibleAttendanceItem;

public class WorkInformationOfDailyPerformCommand extends DailyWorkCommonCommand {

	@Getter
	private WorkInformationOfDailyDto data;

	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		this.data = (WorkInformationOfDailyDto) item;
	}
	
	@Override
	public WorkInfoOfDailyPerformance toDomain() {
		return new WorkInfoOfDailyPerformance(getEmployeeId(),
				data.getActualWorkInfo() == null ? null : new WorkInformation(data.getActualWorkInfo().getWorkTimeCode(),
						data.getActualWorkInfo().getWorkTypeCode()),
				data.getPlanWorkInfo() == null ? null : new WorkInformation(data.getPlanWorkInfo().getWorkTimeCode(), data.getPlanWorkInfo().getWorkTypeCode()),
				EnumAdaptor.valueOf(data.getCalculationState(), CalculationState.class),
				EnumAdaptor.valueOf(data.getGoStraightAtr(), NotUseAttribute.class),
				EnumAdaptor.valueOf(data.getBackStraightAtr(), NotUseAttribute.class), getWorkDate(),
				ConvertHelper.mapTo(data.getScheduleTimeZone(),
						(c) -> new ScheduleTimeSheet(c.getWorkNo(), c.getWorking(), c.getLeave())));
	}
}
