package nts.uk.ctx.at.record.app.command.monthly.remarks;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.uk.ctx.at.record.app.command.monthly.MonthlyWorkCommonCommand;
import nts.uk.ctx.at.record.app.find.monthly.root.MonthlyRemarksDto;
import nts.uk.ctx.at.record.dom.monthly.remarks.RemarksMonthlyRecord;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ConvertibleAttendanceItem;

public class MonthlyRemarksCommand extends MonthlyWorkCommonCommand{

	@Getter
	private List<MonthlyRemarksDto> data = new ArrayList<>();
	
	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		if(item != null && item.isHaveData()){
			this.data.add((MonthlyRemarksDto) item);
		}
	}

	@Override
	public void updateData(Object data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<RemarksMonthlyRecord> toDomain() {
		return data.stream().map(d -> d.toDomain(getEmployeeId(), getYearMonth(), getClosureId(), getClosureDate())).collect(Collectors.toList());
	}
	

}