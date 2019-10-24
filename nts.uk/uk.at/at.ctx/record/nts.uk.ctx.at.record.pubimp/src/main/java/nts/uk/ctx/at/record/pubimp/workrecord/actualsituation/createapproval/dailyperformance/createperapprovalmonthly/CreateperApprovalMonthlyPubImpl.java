package nts.uk.ctx.at.record.pubimp.workrecord.actualsituation.createapproval.dailyperformance.createperapprovalmonthly;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.monthlyperformance.createperapprovalmonthly.CreateperApprovalMonthlyService;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.monthlyperformance.createperapprovalmonthly.OutputCreatePerApprovalMon;
import nts.uk.ctx.at.record.pub.workrecord.actualsituation.createapproval.dailyperformance.createperapprovalmonthly.CreateperApprovalMonthlyPub;
import nts.uk.ctx.at.record.pub.workrecord.actualsituation.createapproval.dailyperformance.createperapprovalmonthly.OutputCreatePerAppMonExport;

@Stateless
public class CreateperApprovalMonthlyPubImpl implements CreateperApprovalMonthlyPub {

	@Inject
	private CreateperApprovalMonthlyService createperApprovalMonthlyService;
	@Override
	public OutputCreatePerAppMonExport createperApprovalMonthly(String companyId, String executionId, List<String> employeeIDs,
			int processExecType, GeneralDate startDateClosure) {
		OutputCreatePerApprovalMon data = createperApprovalMonthlyService.createperApprovalMonthly(companyId, executionId, employeeIDs, processExecType, startDateClosure);
		return new OutputCreatePerAppMonExport(data.isCreateperApprovalMon(),data.isCheckStop());
	}

}
