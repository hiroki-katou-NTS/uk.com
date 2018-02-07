package nts.uk.ctx.at.request.dom.application.applicationlist.service;

public interface AppDetailInfoRepository {

	public AppOverTimeInfoFull getAppOverTimeInfo(String companyID, String appId);
	
	public AppGoBackInfoFull getAppGoBackInfo(String companyID, String appId);
}
