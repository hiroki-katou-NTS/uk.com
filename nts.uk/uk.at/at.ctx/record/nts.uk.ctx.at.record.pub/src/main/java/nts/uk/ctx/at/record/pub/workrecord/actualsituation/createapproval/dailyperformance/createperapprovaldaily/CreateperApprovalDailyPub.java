package nts.uk.ctx.at.record.pub.workrecord.actualsituation.createapproval.dailyperformance.createperapprovaldaily;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface CreateperApprovalDailyPub {
	public OutputCreatePerAppDailyExport createperApprovalDaily(String companyId,String executionId,List<String> employeeIDs,int processExecType,Integer createNewEmp,GeneralDate startDateClosure,GeneralDate endDateClosure);
}
