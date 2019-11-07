package nts.uk.ctx.at.record.pub.workrecord.actualsituation.createapproval.dailyperformance.createperapprovaldaily;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.dailyperformance.AppDataInfoDaily;

public interface CreateperApprovalDailyPub {

	public OutputCreatePerAppDailyExport createperApprovalDaily(String companyId,String executionId,List<String> employeeIDs,int processExecType,Integer createNewEmp,GeneralDate startDateClosure,GeneralDate endDateClosure);

	List<AppDataInfoDaily> getAppDataInfoDailyByExeID(String executionId);
	
	Optional<AppDataInfoDaily> getAppDataInfoDailyByID(String employeeId,String executionId);
	
	void addAppDataInfoDaily(AppDataInfoDaily appDataInfoDaily);

}
