package nts.uk.ctx.at.record.pub.workrecord.actualsituation.createapproval.dailyperformance.createperapprovalmonthly;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface CreateperApprovalMonthlyPub {
	public boolean createperApprovalMonthly(String companyId,String executionId,List<String> employeeIDs,int processExecType,GeneralDate startDateClosure);
}
