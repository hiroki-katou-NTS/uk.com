package nts.uk.ctx.at.record.app.command.dailyperform.affiliationInfor;

//import java.util.Optional;

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
		this.data = item == null || !item.isHaveData() ? null : new AffiliationInforOfDailyPerfor(getEmployeeId(), getWorkDate(),  ((AffiliationInforOfDailyPerforDto) item).toDomain(getEmployeeId(), getWorkDate()));
	}

	@Override
	public void updateData(Object data) {
		if(data == null){ return; }
		this.data = (AffiliationInforOfDailyPerfor) data;
	}

	@Override
	public AffiliationInforOfDailyPerfor toDomain() {
		return data;
	}
	
	@Override
	public AffiliationInforOfDailyPerforDto toDto() {
		return AffiliationInforOfDailyPerforDto.getDto(getData());
	}
}
