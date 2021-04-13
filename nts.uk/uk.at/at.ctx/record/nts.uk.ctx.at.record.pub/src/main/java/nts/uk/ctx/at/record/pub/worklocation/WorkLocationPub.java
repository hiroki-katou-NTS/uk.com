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
	
	/**
	 * コードリストから勤務場所名称を取得するPublish
	 * @param listWorkLocationCd
	 * @return
	 */
	List<WorkLocationExportNew> getWorkLocationName(List<String> listWorkLocationCd);

	List<WorkLocationPubExport> findAll(String companyId);

}
