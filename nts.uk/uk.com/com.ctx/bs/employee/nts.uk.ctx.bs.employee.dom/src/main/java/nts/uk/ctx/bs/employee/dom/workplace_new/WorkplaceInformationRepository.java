package nts.uk.ctx.bs.employee.dom.workplace_new;

import java.util.List;

/**
 * 
 * @author HungTT
 *
 */
public interface WorkplaceInformationRepository {

	public List<WorkplaceInformation> getAllWorkplaceByCompany(String companyId, String wkpHistId);
	
	public List<WorkplaceInformation> getAllActiveWorkplaceByCompany(String companyId, String wkpHistId);
	
	public void deleteDepartmentInfo(String departmentHistoryId);

}
