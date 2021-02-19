package nts.uk.ctx.at.record.app.command.dailyperform.remark;

import java.util.ArrayList;
import java.util.List;
//import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.uk.ctx.at.record.app.find.dailyperform.remark.dto.RemarksOfDailyDto;
import nts.uk.ctx.at.record.dom.daily.remarks.RemarksOfDailyPerform;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ConvertibleAttendanceItem;

public class RemarkOfDailyCommand extends DailyWorkCommonCommand {

	@Getter
	private List<RemarksOfDailyPerform> data = new ArrayList<>();

	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		if(item != null && item.isHaveData()){
			updateData(((RemarksOfDailyDto) item).toDomain(getEmployeeId(), getWorkDate()));
		}
	}
	
	@Override
	public void updateData(Object data) {
		if(data != null){
			RemarksOfDailyPerform d = (RemarksOfDailyPerform) data;
			this.data.removeIf(br -> br.getRemarks().getRemarkNo() == d.getRemarks().getRemarkNo());
			this.data.add(d);
		}
	}
	
	@Override
	public List<RemarksOfDailyPerform> toDomain() {
		return this.data;
	}

	@Override
	public List<RemarksOfDailyDto> toDto() {
		return getData().stream().map(b -> RemarksOfDailyDto.getDto(b)).collect(Collectors.toList());
	}
}
