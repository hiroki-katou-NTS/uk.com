package nts.uk.ctx.at.record.app.command.dailyperform.affiliationInfor;

import lombok.Getter;
import nts.uk.ctx.at.record.app.find.dailyperform.affiliationInfor.dto.AffiliationInforOfDailyPerforDto;
import nts.uk.ctx.at.record.dom.affiliationinformation.AffiliationInforOfDailyPerfor;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemCommon;

public class AffiliationInforOfDailyPerformCommand extends DailyWorkCommonCommand {

	@Getter
	private AffiliationInforOfDailyPerforDto data;

	@Override
	public void setRecords(AttendanceItemCommon item) {
		this.data = item == null || !item.isHaveData() ? null : (AffiliationInforOfDailyPerforDto) item;
	}

	@Override
	public void updateData(Object data) {
		if(data == null){ return; }
		setRecords(AffiliationInforOfDailyPerforDto.getDto((AffiliationInforOfDailyPerfor) data));
	}

	@Override
	public AffiliationInforOfDailyPerfor toDomain() {
		return data == null ? null : data.toDomain(getEmployeeId(), getWorkDate());
	}

}
