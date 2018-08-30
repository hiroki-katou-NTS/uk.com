package nts.uk.ctx.at.record.pubimp.workrecord.actualsituation.createapproval.dailyperformance.createperapprovaldaily;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.dailyperformance.createperapprovaldaily.CreateperApprovalDailyService;
import nts.uk.ctx.at.record.pub.workrecord.actualsituation.createapproval.dailyperformance.createperapprovaldaily.CreateperApprovalDailyPub;

@Stateless
public class CreateperApprovalDailyPubImpl implements CreateperApprovalDailyPub {

	@Inject
	private CreateperApprovalDailyService createperApprovalDailyService;
	@Override
	public boolean createperApprovalDaily(String companyId, String executionId, List<String> employeeIDs,
			int processExecType, Integer createNewEmp, GeneralDate startDateClosure) {
		return createperApprovalDailyService.createperApprovalDaily(companyId, executionId, employeeIDs, processExecType, createNewEmp, startDateClosure);
	}

}
