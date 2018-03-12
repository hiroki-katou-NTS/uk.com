package nts.uk.ctx.at.request.dom.application.applicationlist.service;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.at.request.dom.application.Application_New;
@Value
public class AppListOutPut {

	private List<AppMasterInfo> lstMasterInfo;
	private List<Application_New> lstApp;
	private List<AppOverTimeInfoFull> lstAppOt;
	private List<AppGoBackInfoFull> lstAppGoBack;
	//TH: approval (count)
	private ApplicationStatus appStatusCount;
	private List<ApplicationFullOutput> lstAppFull;
	private List<CheckColorTime> lstTimeColor; 
	private List<String> lstFramStatus; 
	private List<PhaseStatus> lstPhaseStatus;
	private List<AppPrePostGroup> lstAppGroup;
}
