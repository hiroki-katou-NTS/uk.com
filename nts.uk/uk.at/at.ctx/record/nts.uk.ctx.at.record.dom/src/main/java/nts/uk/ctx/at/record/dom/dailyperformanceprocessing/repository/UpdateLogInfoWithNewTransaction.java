package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.EmpCalAndSumExeLogRepository;

@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Stateless
public class UpdateLogInfoWithNewTransaction {

	@Inject
	private EmpCalAndSumExeLogRepository empCalAndSumExeLogRepository;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void updateLogInfo(String empCalAndSumExecLogID,int executionContent, int processStatus) {
		empCalAndSumExeLogRepository.updateLogInfo(empCalAndSumExecLogID, executionContent, processStatus);
	};

}
