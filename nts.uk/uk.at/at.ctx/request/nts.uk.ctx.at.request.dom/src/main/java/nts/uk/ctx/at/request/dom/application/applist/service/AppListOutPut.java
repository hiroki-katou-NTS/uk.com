package nts.uk.ctx.at.request.dom.application.applist.service;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.applist.service.detail.ContentApp;
@Value
public class AppListOutPut {

	private DataMasterOutput dataMaster;
	private List<Application_New> lstApp;
	//TH: approval (count)
	private ApplicationStatus appStatusCount;
	private List<ApplicationFullOutput> lstAppFull;
	private List<CheckColorTime> lstTimeColor; 
	private List<String> lstFramStatus; 
	private List<PhaseStatus> lstPhaseStatus;
	private List<ContentApp> lstContentApp;
	private List<AppCompltLeaveSync> lstAppCompltLeaveSync;
}
