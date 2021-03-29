package nts.uk.ctx.at.record.app.command.dailyperform.workinfo;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.workinfo.dto.WorkInformationOfDailyDto;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ConvertibleAttendanceItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;

public class WorkInformationOfDailyPerformCommand extends DailyWorkCommonCommand {

	@Getter
	private WorkInfoOfDailyPerformance data;
	
	@Getter
	@Setter
	private boolean isTriggerEvent = false;

	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		if(item == null) {
			this.data = null; 
		}else {
			WorkInfoOfDailyPerformance workInfoOfDailyPerformance = new WorkInfoOfDailyPerformance(getEmployeeId(),
					getWorkDate(), ((WorkInformationOfDailyDto) item).toDomain(getEmployeeId(), getWorkDate()));
			this.data = item == null || !item.isHaveData() ? null
					:workInfoOfDailyPerformance ;
		}
	}

	@Override
	public void updateData(Object data) {
		if(data == null){ return; }
		this.data = (WorkInfoOfDailyPerformance) data;
	}
	
	public void updateDataByAtt(Object data) {
		if(data == null){ return; }//set ver doan nay a
		WorkInfoOfDailyPerformance workInfoOfDailyPerformance = new WorkInfoOfDailyPerformance(getEmployeeId(),
				getWorkDate(), (WorkInfoOfDailyAttendance) data);
		workInfoOfDailyPerformance.setVersion(((WorkInfoOfDailyAttendance) data).getVer());
		this.data = workInfoOfDailyPerformance;
	}

	@Override
	public WorkInfoOfDailyPerformance toDomain() {
		return data;
	}

	@Override
	public WorkInformationOfDailyDto toDto() {
		return WorkInformationOfDailyDto.getDto(getData());
	}
}
