package nts.uk.ctx.at.request.dom.application.applist.service;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.applist.service.detail.AppAbsenceFull;
import nts.uk.ctx.at.request.dom.application.applist.service.detail.AppGoBackInfoFull;
import nts.uk.ctx.at.request.dom.application.applist.service.detail.AppHolidayWorkFull;
import nts.uk.ctx.at.request.dom.application.applist.service.detail.AppOverTimeInfoFull;
import nts.uk.ctx.at.request.dom.application.applist.service.detail.AppWorkChangeFull;
@Value
public class AppListOutPut {

	private DataMasterOutput dataMaster;
	private List<Application_New> lstApp;
	private List<AppOverTimeInfoFull> lstAppOt;
	private List<AppGoBackInfoFull> lstAppGoBack;
	private List<AppHolidayWorkFull> lstAppHdWork;
	private List<AppWorkChangeFull> lstAppWorkChange;
	private List<AppAbsenceFull> lstAppAbsence;
	private List<AppCompltLeaveSync> lstAppCompltLeaveSync;
	//TH: approval (count)
	private ApplicationStatus appStatusCount;
	private List<ApplicationFullOutput> lstAppFull;
	private List<CheckColorTime> lstTimeColor; 
	private List<String> lstFramStatus; 
	private List<PhaseStatus> lstPhaseStatus;
	private List<AppPrePostGroup> lstAppGroup;
}
