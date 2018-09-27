package nts.uk.ctx.at.function.ac.workrecord.actualsituation.createperapprovalmonthly;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.workrecord.actualsituation.createperapprovalmonthly.CreateperApprovalMonthlyAdapter;
import nts.uk.ctx.at.record.pub.workrecord.actualsituation.createapproval.dailyperformance.createperapprovalmonthly.CreateperApprovalMonthlyPub;

@Stateless
public class CreateperApprovalMonthlyAc implements CreateperApprovalMonthlyAdapter {

	@Inject
	private CreateperApprovalMonthlyPub createperApprovalMonthlyPub;
	
	@Override
	public boolean createperApprovalMonthly(String companyId, String executionId, List<String> employeeIDs,
			int processExecType, GeneralDate startDateClosure) {
		boolean check = createperApprovalMonthlyPub.createperApprovalMonthly(companyId, executionId, employeeIDs, processExecType, startDateClosure);
		return check;
	}

}
