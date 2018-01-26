package nts.uk.ctx.at.function.dom.processexecution.repository;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.function.dom.processexecution.ProcessExecution;

public interface ProcessExecutionRepository {
	
	public List<ProcessExecution> getProcessExecutionByCompanyId(String companyId);
	public Optional<ProcessExecution> getProcessExecutionByCidAndExecCd(String companyId, String execItemCd);
	public void insert(ProcessExecution domain);
	public void update(ProcessExecution domain);
	public void remove(String companyId, String execItemCd);
}
