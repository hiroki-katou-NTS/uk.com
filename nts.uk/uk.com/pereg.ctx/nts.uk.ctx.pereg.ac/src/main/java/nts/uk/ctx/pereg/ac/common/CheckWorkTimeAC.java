package nts.uk.ctx.pereg.ac.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.pub.worktime.predset.PredetemineTimeSettingPub;
import nts.uk.ctx.at.shared.pub.worktime.worktimeset.WorkTimeSettingPub;
import nts.uk.ctx.pereg.dom.common.CheckResult;
import nts.uk.ctx.pereg.dom.common.CheckWorkTimeRepo;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class CheckWorkTimeAC implements CheckWorkTimeRepo {

	@Inject
	private WorkTimeSettingPub workTimeSetting;

	@Inject
	private PredetemineTimeSettingPub predetemineTimeSetting;

	@Override
	public List<CheckResult> checkWorkTime(List<String> workTimeCodes) {
		String companyId = AppContexts.user().companyId();
//		Map<String, Boolean> flowWorkResult = workTimeSetting.checkFlowWork(companyId, workTimeCodes);
//		Map<String, Boolean> workingTwiceResult = predetemineTimeSetting.checkWorkingTwice(companyId, workTimeCodes);
//
//		List<CheckResult> result = new ArrayList<>();
//		for (String worktimeCode : workTimeCodes) {
//			Boolean isFlowWork = flowWorkResult.get(worktimeCode);
//			Boolean isWorkingTwice = workingTwiceResult.get(worktimeCode);
//
//			if (isFlowWork == null) {
//				isFlowWork = false;
//			}
//
//			if (isWorkingTwice == null) {
//				isWorkingTwice = false;
//			}
//
//			result.add(new CheckResult(worktimeCode, isFlowWork, isWorkingTwice));
//
//		}
		return Collections.EMPTY_LIST;
	}

}
