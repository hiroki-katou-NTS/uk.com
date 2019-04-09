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
	
	public List<WorkplaceInformation> getWorkplaceByWkpIds(String companyId, String wkpHistId, List<String> listWorkplaceId);
	
	public void addWorkplace(WorkplaceInformation workplace);
	
	public void addWorkplaces(List<WorkplaceInformation> listWorkplace);
	
	public void deleteWorkplaceInforOfHistory(String companyId, String wkpHistId);

}
