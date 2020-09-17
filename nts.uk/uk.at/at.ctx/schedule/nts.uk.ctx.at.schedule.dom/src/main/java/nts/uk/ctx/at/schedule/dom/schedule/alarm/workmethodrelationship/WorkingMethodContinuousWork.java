package nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.shr.com.context.AppContexts;
/**
 * 勤務方法(連続勤務)
 * @author lan_lt
 *
 */
public class WorkingMethodContinuousWork implements WorkingMethod{

	@Override
	public WorkTypeClassification getWorkTypeClassification() {
		return WorkTypeClassification.ContinuousWork;
	}

	@Override
	public boolean determineIfApplicable(Require require, WorkInformation workInfor) {
		Optional<WorkType> workType = require.getWorkType(AppContexts.user().companyId(),
				workInfor.getWorkTypeCode().v());
		if (workType.isPresent()) {
			return workType.get().getDailyWork().isOneDay() && workType.get().getDailyWork().isContinueWork();
		}

		return false;
	}

}
