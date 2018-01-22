package nts.uk.ctx.at.record.app.command.dailyperform.workinfo;

import java.util.ArrayList;

import lombok.Getter;
import nts.uk.ctx.at.record.app.find.dailyperform.workinfo.dto.WorkInfoDto;
import nts.uk.ctx.at.record.app.find.dailyperform.workinfo.dto.WorkInformationOfDailyDto;
import nts.uk.ctx.at.record.dom.workinformation.ScheduleTimeSheet;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.WorkInformation;
import nts.uk.ctx.at.record.dom.workinformation.enums.CalculationState;
import nts.uk.ctx.at.record.dom.workinformation.enums.NotUseAttribute;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.app.util.attendanceitem.item.ConvertibleAttendanceItem;

public class WorkInformationOfDailyPerformCommand extends DailyWorkCommonCommand {

	@Getter
	private WorkInformationOfDailyDto data;

	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		this.data = (WorkInformationOfDailyDto) item;
	}

	@Override
	public WorkInfoOfDailyPerformance toDomain() {
		return new WorkInfoOfDailyPerformance(getEmployeeId(), getWorkInfo(data.getActualWorkInfo()),
				getWorkInfo(data.getPlanWorkInfo()),
				ConvertHelper.getEnum(data.getCalculationState(), CalculationState.class),
				ConvertHelper.getEnum(data.getGoStraightAtr(), NotUseAttribute.class),
				ConvertHelper.getEnum(data.getBackStraightAtr(), NotUseAttribute.class), getWorkDate(),
				data.getScheduleTimeZone() == null ? new ArrayList<>() :
						ConvertHelper.mapTo(data.getScheduleTimeZone(), (c) -> 
							new ScheduleTimeSheet(c.getWorkNo(), c.getWorking() == null ?  0 : c.getWorking(), c.getLeave() == null ? 0: c.getLeave())));
	}

	private WorkInformation getWorkInfo(WorkInfoDto dto) {
		return dto == null ? null : new WorkInformation(dto.getWorkTimeCode(), dto.getWorkTypeCode());
	}
}
