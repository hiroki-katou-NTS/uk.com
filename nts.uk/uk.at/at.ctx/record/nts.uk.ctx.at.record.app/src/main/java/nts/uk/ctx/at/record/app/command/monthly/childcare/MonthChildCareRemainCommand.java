package nts.uk.ctx.at.record.app.command.monthly.childcare;

import lombok.Getter;
import nts.uk.ctx.at.record.app.command.monthly.MonthlyWorkCommonCommand;
import nts.uk.ctx.at.record.app.find.monthly.root.MonthlyChildCareHdRemainDto;
import nts.uk.ctx.at.record.dom.monthly.information.childnursing.MonChildHdRemain;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ConvertibleAttendanceItem;

public class MonthChildCareRemainCommand extends MonthlyWorkCommonCommand{

	@Getter
	private MonthlyChildCareHdRemainDto data;
	
	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		this.data = item == null || !item.isHaveData() ? null : (MonthlyChildCareHdRemainDto) item;
	}

	@Override
	public void updateData(Object data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public MonChildHdRemain toDomain() {
		return data.toDomain(getEmployeeId(), getYearMonth(), getClosureId(), getClosureDate());
	}

	
}