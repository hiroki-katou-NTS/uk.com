package nts.uk.ctx.pr.core.dom.adapter.employee.employment;

import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.EmpCdNameImport;

import java.util.List;

public interface SysEmploymentAdapter {
	/**
	 * RequestList89
	 * 
	 * @param companyId
	 * @return EmpCdNameImport
	 */
	List<EmpCdNameImport> findAll(String companyId);
}