package nts.uk.ctx.at.record.app.command.dailyperform.affiliationInfor;

import lombok.Getter;
import nts.uk.ctx.at.record.app.find.dailyperform.affiliationInfor.dto.AffiliationInforOfDailyPerforDto;
import nts.uk.ctx.at.record.dom.affiliationinformation.AffiliationInforOfDailyPerfor;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ConvertibleAttendanceItem;

public class AffiliationInforOfDailyPerformCommand extends DailyWorkCommonCommand {

	@Getter
	private AffiliationInforOfDailyPerfor data;

	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		this.data = item == null ? null : ((AffiliationInforOfDailyPerforDto) item).toDomain();
	}

	@Override
	public void updateData(Object data) {
		this.data = (AffiliationInforOfDailyPerfor) data;
	}

}
