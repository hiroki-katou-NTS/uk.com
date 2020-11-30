package nts.uk.ctx.at.function.dom.processexecution.repository;

import java.util.List;

public interface ExecutionScopeItemRepository {
	// insert
	public void insert(String companyId, String execItemCd, List<String> wkpIds);
	
	// remove
	public void removeAllByCidAndExecCd(String companyId, String execItemCd);
		
}
