package nts.uk.ctx.bs.employee.dom.workplace.master;

import java.util.List;

/**
 * 
 * @author HungTT
 *
 */
public interface WorkplaceInformationRepository {

	public List<WorkplaceInformation> getAllWorkplaceByCompany(String companyId, String wkpHistId);
	
	public List<WorkplaceInformation> getAllActiveWorkplaceByCompany(String companyId, String wkpHistId);
	
	public void addWorkplace(WorkplaceInformation workplace);
	
	public void addWorkplaces(List<WorkplaceInformation> listWorkplace);
	
	public void deleteDepartmentInfo(String departmentHistoryId);

}
