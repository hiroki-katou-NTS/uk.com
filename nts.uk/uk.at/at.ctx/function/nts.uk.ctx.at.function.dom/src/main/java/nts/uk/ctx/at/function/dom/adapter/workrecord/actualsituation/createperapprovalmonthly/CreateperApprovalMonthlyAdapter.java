package nts.uk.ctx.at.function.dom.adapter.workrecord.actualsituation.createperapprovalmonthly;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface CreateperApprovalMonthlyAdapter {
<<<<<<< HEAD
	public OutputCreatePerAppMonImport createperApprovalMonthly(String companyId,String executionId,List<String> employeeIDs,int processExecType,GeneralDate startDateClosure);
=======
	public boolean createperApprovalMonthly(String companyId, String executionId, List<String> employeeIDs,
			int processExecType, GeneralDate startDateClosure);

	List<AppDataInfoMonthlyImport> getAppDataInfoMonthlyByExeID(String executionId);

	Optional<AppDataInfoMonthlyImport> getAppDataInfoMonthlyByID(String employeeId, String executionId);

	void addAppDataInfoMonthly(AppDataInfoMonthlyImport appDataInfoMonthly);
>>>>>>> 2d85ea9... fixbug kbt002 108717
}
