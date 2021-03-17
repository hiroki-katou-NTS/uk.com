package nts.uk.ctx.at.record.pub.worklocation;

import java.util.List;

public interface WorkLocationPub {
	/**
	 * get workLocation Name
	 * 
	 * @param workLocationCd
	 * @return
	 */
	WorkLocationPubExport getLocationName(String companyID, String workLocationCd);
	
	List<WorkLocationPubExport> findAll(String companyId);
}
