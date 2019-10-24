package nts.uk.ctx.at.function.dom.adapter.workrecord.actualsituation.createperapprovalmonthly;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface CreateperApprovalMonthlyAdapter {
	public OutputCreatePerAppMonImport createperApprovalMonthly(String companyId,String executionId,List<String> employeeIDs,int processExecType,GeneralDate startDateClosure);
}
