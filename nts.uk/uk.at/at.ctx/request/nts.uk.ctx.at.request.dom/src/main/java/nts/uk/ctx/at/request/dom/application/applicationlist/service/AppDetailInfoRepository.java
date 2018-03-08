package nts.uk.ctx.at.request.dom.application.applicationlist.service;

public interface AppDetailInfoRepository {

	/**
	 * get Application Over Time Info
	 * @param companyID
	 * @param appId
	 * @return
	 */
	public AppOverTimeInfoFull getAppOverTimeInfo(String companyID, String appId);
	/**
	 * get Application Go Back Info
	 * @param companyID
	 * @param appId
	 * @return
	 */
	public AppGoBackInfoFull getAppGoBackInfo(String companyID, String appId);
}
