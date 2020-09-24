package nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship;

import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.shr.com.context.AppContexts;
/**
 * 勤務方法(休日)
 * @author lan_lt
 *
 */
public class WorkMethodHoliday implements WorkMethod{

	@Override
	public WorkMethodClassfication getWorkMethodClassification() {
		return WorkMethodClassfication.HOLIDAY;
	}

	@Override
	public boolean determineIfApplicable(Require require, WorkInformation workInfo) {
		return require.checkHoliday(AppContexts.user().companyId(), workInfo.getWorkTypeCode().v());
	}

}
