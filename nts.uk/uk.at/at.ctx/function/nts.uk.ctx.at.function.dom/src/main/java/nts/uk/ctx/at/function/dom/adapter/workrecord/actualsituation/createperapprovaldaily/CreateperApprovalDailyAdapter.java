package nts.uk.ctx.at.function.dom.adapter.workrecord.actualsituation.createperapprovaldaily;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface CreateperApprovalDailyAdapter {
	public boolean createperApprovalDaily(String companyId,String executionId,List<String> employeeIDs,int processExecType,Integer createNewEmp,GeneralDate startDateClosure);
}
