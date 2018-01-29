package nts.uk.ctx.at.function.dom.processexecution.repository;

import java.util.List;

import nts.uk.ctx.at.function.dom.processexecution.ProcessExecutionScopeItem;

public interface ExecutionScopeItemRepository {
	// insert
	public void insert(String companyId, String execItemCd, List<ProcessExecutionScopeItem> wkpList);
	
	// remove
	public void removeAllByCidAndExecCd(String companyId, String execItemCd);
		
}
