package nts.uk.ctx.at.function.dom.processexecution.repository;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.function.dom.processexecution.UpdateProcessAutoExecution;

public interface ProcessExecutionRepository {
	
	List<UpdateProcessAutoExecution> getProcessExecutionByCompanyId(String companyId);
	
	Optional<UpdateProcessAutoExecution> getProcessExecutionByCidAndExecCd(String companyId, String execItemCd);
	
	List<UpdateProcessAutoExecution> findByCidAndExecItemCds(String cid, List<String> execItemCd);
	
	void insert(UpdateProcessAutoExecution domain);
	
	void update(UpdateProcessAutoExecution domain);
	
	void remove(String companyId, String execItemCd);
}
