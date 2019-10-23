package nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.monthlyperformance.createperapprovalmonthly;

import java.util.List;

import nts.arc.time.GeneralDate;
/**
 * 月別実績の承認ルート中間データの作成
 * @author tutk
 *
 */
public interface CreateperApprovalMonthlyService {
	public boolean createperApprovalMonthly(String companyId,String executionId,List<String> employeeIDs,int processExecType,GeneralDate startDateClosure);
}
