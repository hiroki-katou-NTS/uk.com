package nts.uk.ctx.at.record.app.command.monthly.care;

import lombok.Getter;
import nts.uk.ctx.at.record.app.command.monthly.MonthlyWorkCommonCommand;
import nts.uk.ctx.at.record.app.find.monthly.root.MonthlyCareHdRemainDto;
import nts.uk.ctx.at.record.dom.monthly.information.care.MonCareHdRemain;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ConvertibleAttendanceItem;

public class MonthCareRemainCommand extends MonthlyWorkCommonCommand{

	@Getter
	private MonthlyCareHdRemainDto data;
	
	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		this.data = item == null || !item.isHaveData() ? null : (MonthlyCareHdRemainDto) item;
	}

	@Override
	public void updateData(Object data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public MonCareHdRemain toDomain() {
		return data.toDomain(getEmployeeId(), getYearMonth(), getClosureId(), getClosureDate());
	}

	
}