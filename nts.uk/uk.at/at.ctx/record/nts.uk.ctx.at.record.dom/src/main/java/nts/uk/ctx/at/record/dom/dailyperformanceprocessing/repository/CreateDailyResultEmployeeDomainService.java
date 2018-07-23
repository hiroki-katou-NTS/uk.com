package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import nts.arc.layer.app.command.AsyncCommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.generalinfo.dtoimport.EmployeeGeneralInfoImport;
import nts.uk.ctx.at.record.dom.calculationsetting.StampReflectionManagement;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.CreateDailyResultDomainServiceImpl.ProcessState;
import nts.uk.ctx.at.record.dom.organization.EmploymentHistoryImported;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ExecutionLog;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 
 * @author nampt 作成処理 日別作成Mgrクラス . アルゴリズム . 社員の日別実績を作成する. ⑤社員の日別実績を作成する
 *
 */
public interface CreateDailyResultEmployeeDomainService {

	ProcessState createDailyResultEmployee(AsyncCommandHandlerContext asyncContext, String employeeId,
			DatePeriod periodTimes, String companyId, String empCalAndSumExecLogID, Optional<ExecutionLog> executionLog,
			boolean reCreateWorkType, EmployeeGeneralInfoImport employeeGeneralInfoImport,
			Optional<StampReflectionManagement> stampReflectionManagement,
			Map<String, Map<String, WorkingConditionItem>> mapWorkingConditionItem,
			Map<String, Map<String, DateHistoryItem>> mapDateHistoryItem);

	ProcessState createDailyResultEmployeeNew(AsyncCommandHandlerContext asyncContext,
			String employeeId, List<GeneralDate> executedDate, String companyId, String empCalAndSumExecLogID,
			Optional<ExecutionLog> executionLog, boolean reCreateWorkType,
			EmployeeGeneralInfoImport employeeGeneralInfoImport,
			Optional<StampReflectionManagement> stampReflectionManagement,
			Map<String, Map<String, WorkingConditionItem>> mapWorkingConditionItem,
			Map<String, Map<String, DateHistoryItem>> mapDateHistoryItem,
			Optional<EmploymentHistoryImported> employmentHisOptional,
			String employmentCode);

	/**
	 * create method for kbt002 call
	 * 
	 * @param asyncContext
	 * @param employeeId
	 * @param periodTimes
	 * @param companyId
	 * @param empCalAndSumExecLogID
	 * @param executionLog
	 * @param reCreateWorkType
	 * @return
	 */
	ProcessState createDailyResultEmployeeWithNoInfoImport(AsyncCommandHandlerContext asyncContext, String employeeId,
			DatePeriod periodTimes, String companyId, String empCalAndSumExecLogID, Optional<ExecutionLog> executionLog,
			boolean reCreateWorkType, Optional<StampReflectionManagement> stampReflectionManagement);
	
	ProcessState createDailyResultEmployeeWithNoInfoImportNew(AsyncCommandHandlerContext asyncContext,
			String employeeId, List<GeneralDate> executeDate, String companyId,
			String empCalAndSumExecLogID, Optional<ExecutionLog> executionLog, boolean reCreateWorkType,
			Optional<StampReflectionManagement> stampReflectionManagement, 
			Optional<EmploymentHistoryImported> employmentHisOptional,
			String employmentCode);

}
