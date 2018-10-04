package nts.uk.ctx.at.function.dom.processexecution.repository;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.function.dom.processexecution.ProcessExecution;

public interface ProcessExecutionRepository {
	
	List<ProcessExecution> getProcessExecutionByCompanyId(String companyId);
	
	Optional<ProcessExecution> getProcessExecutionByCidAndExecCd(String companyId, String execItemCd);
	
	void insert(ProcessExecution domain);
	
	void update(ProcessExecution domain);
	
	void remove(String companyId, String execItemCd);
}
