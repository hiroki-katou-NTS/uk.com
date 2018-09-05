package nts.uk.ctx.at.record.app.command.monthly.absenceleave;

import lombok.Getter;
import nts.uk.ctx.at.record.app.command.monthly.MonthlyWorkCommonCommand;
import nts.uk.ctx.at.record.app.find.monthly.root.AbsenceLeaveRemainDataDto;
import nts.uk.ctx.at.record.dom.monthly.vacation.absenceleave.monthremaindata.AbsenceLeaveRemainData;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ConvertibleAttendanceItem;

public class AbsenceLeaveRemainMonthlyCommand extends MonthlyWorkCommonCommand{

	@Getter
	private AbsenceLeaveRemainDataDto data;
	
	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		this.data = item == null || !item.isHaveData() ? null : (AbsenceLeaveRemainDataDto) item;
	}

	@Override
	public void updateData(Object data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AbsenceLeaveRemainData toDomain() {
		return data.toDomain(getEmployeeId(), getYearMonth(), getClosureId(), getClosureDate());
	}
	
	

}