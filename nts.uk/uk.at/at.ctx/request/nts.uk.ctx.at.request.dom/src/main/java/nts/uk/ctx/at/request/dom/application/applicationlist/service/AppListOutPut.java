package nts.uk.ctx.at.request.dom.application.applicationlist.service;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.at.request.dom.application.Application_New;
@Value
public class AppListOutPut {

	private List<AppMasterInfo> lstMasterInfo;
	private List<Application_New> lstApp;
	List<AppOverTimeInfoFull> lstAppOt;
	List<AppGoBackInfoFull> lstAppGoBack;
	//TH: approval (count)
	private ApplicationStatus appStatusCount;
	private List<ApplicationFullOutput> lstAppFull;
}
