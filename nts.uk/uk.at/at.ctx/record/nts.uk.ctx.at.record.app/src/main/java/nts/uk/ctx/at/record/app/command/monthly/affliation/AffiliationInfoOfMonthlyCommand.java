package nts.uk.ctx.at.record.app.command.monthly.affliation;

import lombok.Getter;
import nts.uk.ctx.at.record.app.command.monthly.MonthlyWorkCommonCommand;
import nts.uk.ctx.at.record.app.find.monthly.root.AffiliationInfoOfMonthlyDto;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemCommon;

public class AffiliationInfoOfMonthlyCommand extends MonthlyWorkCommonCommand{

	@Getter
	private AffiliationInfoOfMonthlyDto data;
	
	@Override
	public void setRecords(AttendanceItemCommon item) {
		this.data = item == null ? null : (AffiliationInfoOfMonthlyDto) item;
	}

	@Override
	public void updateData(Object data) {
		// TODO Auto-generated method stub
		
	}

}
