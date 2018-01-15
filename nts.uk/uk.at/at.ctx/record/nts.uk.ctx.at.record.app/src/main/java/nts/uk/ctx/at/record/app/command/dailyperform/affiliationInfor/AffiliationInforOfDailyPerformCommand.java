package nts.uk.ctx.at.record.app.command.dailyperform.affiliationInfor;

import lombok.Getter;
import nts.uk.ctx.at.record.app.find.dailyperform.affiliationInfor.dto.AffiliationInforOfDailyPerforDto;
import nts.uk.ctx.at.record.dom.affiliationinformation.AffiliationInforOfDailyPerfor;
import nts.uk.ctx.at.record.dom.affiliationinformation.primitivevalue.ClassificationCode;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ConvertibleAttendanceItem;
import nts.uk.ctx.at.shared.dom.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

public class AffiliationInforOfDailyPerformCommand extends DailyWorkCommonCommand {

	@Getter
	private AffiliationInforOfDailyPerforDto data;

	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		this.data = (AffiliationInforOfDailyPerforDto) item;
	}

	public AffiliationInforOfDailyPerfor toDomain() {
		return new AffiliationInforOfDailyPerfor(new EmploymentCode(data.getEmploymentCode()), this.getEmployeeId(),
				data.getJobId(), data.getWorkplaceID(), this.getWorkDate(),
				new ClassificationCode(data.getClassificationCode()),
				new BonusPaySettingCode(data.getSubscriptionCode()));
	}
}
