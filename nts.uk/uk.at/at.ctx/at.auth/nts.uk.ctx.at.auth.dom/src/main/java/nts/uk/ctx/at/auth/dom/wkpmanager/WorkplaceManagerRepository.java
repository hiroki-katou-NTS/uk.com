package nts.uk.ctx.at.auth.dom.wkpmanager;

import java.util.List;

public interface WorkplaceManagerRepository {
	// Get workplace manager list by workplace id
	List<WorkplaceManager> getWkpManagerListByWkpId(String workplaceId);
	
	// Get workplace manager list by workplace id and employee id
	List<WorkplaceManager> getWkpManagerBySIdWkpId(String companyId, String workplaceId);
	
	// add new workplace manager
	void add(WorkplaceManager workplaceManager);
	
	// update workplace manager
	void update(WorkplaceManager workplaceManager);
	
	// delete workplace manager
	void delete(String wkpManagerId);
}
