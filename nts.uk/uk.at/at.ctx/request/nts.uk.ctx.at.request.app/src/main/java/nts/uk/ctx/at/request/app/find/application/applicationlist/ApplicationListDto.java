package nts.uk.ctx.at.request.app.find.application.applicationlist;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.at.request.app.find.application.common.ApplicationDto_New;
import nts.uk.ctx.at.request.app.find.setting.company.request.approvallistsetting.ApprovalListDisplaySetDto;
import nts.uk.ctx.at.request.app.find.setting.company.vacationapplicationsetting.HdAppSetDto;
import nts.uk.ctx.at.request.dom.application.applist.service.AppCompltLeaveSync;
import nts.uk.ctx.at.request.dom.application.applist.service.AppMasterInfo;
import nts.uk.ctx.at.request.dom.application.applist.service.AppPrePostGroup;
import nts.uk.ctx.at.request.dom.application.applist.service.ApplicationStatus;
import nts.uk.ctx.at.request.dom.application.applist.service.detail.AppAbsenceFull;
import nts.uk.ctx.at.request.dom.application.applist.service.detail.AppGoBackInfoFull;
import nts.uk.ctx.at.request.dom.application.applist.service.detail.AppHolidayWorkFull;
import nts.uk.ctx.at.request.dom.application.applist.service.detail.AppOverTimeInfoFull;
import nts.uk.ctx.at.request.dom.application.applist.service.detail.AppWorkChangeFull;

@Value
public class ApplicationListDto {
	private String startDate;
	private String endDate;
	private ApprovalListDisplaySetDto displaySet;
	private List<AppMasterInfo> lstMasterInfo;
	private List<ApplicationDto_New> lstApp;
	private List<AppOverTimeInfoFull> lstAppOt;
	private List<AppGoBackInfoFull> lstAppGoBack;
	//TH: approval (count)
	private	ApplicationStatus appStatusCount;
	private List<AppPrePostGroup> lstAppGroup;
	//phuc vu cho viec loc theo aptype, dem lai trang thai don
	private List<ApproveAgent> lstAgent;
	private List<AppHolidayWorkFull> lstAppHdWork;
	private List<AppWorkChangeFull> lstAppWorkChange;
	private List<AppAbsenceFull> lstAppAbsence;
	private List<AppInfor> lstAppInfor;
	private HdAppSetDto hdAppSet;
	private List<AppCompltLeaveSync> lstAppCompltLeaveSync;
}
