package nts.uk.ctx.at.record.dom.workrecord.log.aggregateroot;

import java.util.Optional;

public interface EmpCalAndSumExeLogRepository {
	
	Optional<EmpCalAndSumExeLog> getEmpCalAndSumExeLogByID(String companyID, long empCalAndSumExecLogId );

}
