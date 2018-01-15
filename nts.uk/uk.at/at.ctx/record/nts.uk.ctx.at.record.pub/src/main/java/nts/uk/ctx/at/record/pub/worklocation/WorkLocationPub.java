package nts.uk.ctx.at.record.pub.worklocation;

public interface WorkLocationPub {
	/**
	 * get workLocation Name
	 * 
	 * @param workLocationCd
	 * @return
	 */
	WorkLocationPubExport getLocationName(String companyID, String workLocationCd);
}
