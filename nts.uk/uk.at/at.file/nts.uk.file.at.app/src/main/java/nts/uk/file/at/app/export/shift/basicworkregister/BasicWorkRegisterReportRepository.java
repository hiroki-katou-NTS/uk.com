package nts.uk.file.at.app.export.shift.basicworkregister;

import java.util.List;

/**
 * Basic Work Register Repository
 * 
 * @author HiepTH
 *
 */
public interface BasicWorkRegisterReportRepository {
	public List<CompanyBasicWorkData> findCompanyBasicWork(String companyId);
	public List<WorkplaceBasicWorkData> findWorkplaceBasicWork(String companyId);
	public List<ClassBasicWorkData> findClassBasicWork(String companyId);
}
