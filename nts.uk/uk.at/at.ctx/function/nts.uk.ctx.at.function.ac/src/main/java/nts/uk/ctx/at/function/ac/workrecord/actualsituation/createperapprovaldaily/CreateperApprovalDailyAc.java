package nts.uk.ctx.at.function.ac.workrecord.actualsituation.createperapprovaldaily;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.workrecord.actualsituation.createperapprovaldaily.CreateperApprovalDailyAdapter;
import nts.uk.ctx.at.function.dom.adapter.workrecord.actualsituation.createperapprovaldaily.OutputCreatePerAppDailyImport;
import nts.uk.ctx.at.record.pub.workrecord.actualsituation.createapproval.dailyperformance.createperapprovaldaily.CreateperApprovalDailyPub;
import nts.uk.ctx.at.record.pub.workrecord.actualsituation.createapproval.dailyperformance.createperapprovaldaily.OutputCreatePerAppDailyExport;

@Stateless
public class CreateperApprovalDailyAc implements CreateperApprovalDailyAdapter {

	@Inject
	private CreateperApprovalDailyPub createperApprovalDailyPub;
	
	@Override
	public OutputCreatePerAppDailyImport createperApprovalDaily(String companyId, String executionId, List<String> employeeIDs,
			int processExecType, Integer createNewEmp, GeneralDate startDateClosure,GeneralDate endDateClosure) {
		OutputCreatePerAppDailyExport check = createperApprovalDailyPub.createperApprovalDaily(companyId, executionId, employeeIDs, processExecType, createNewEmp, startDateClosure,endDateClosure);
		return new OutputCreatePerAppDailyImport(check.isCreateperApprovalDaily(),check.isCheckStop());
	}

}
