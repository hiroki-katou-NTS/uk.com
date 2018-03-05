package nts.uk.ctx.at.request.app.find.application.applicationlist;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.at.request.app.find.application.common.ApplicationDto_New;
import nts.uk.ctx.at.request.app.find.setting.company.request.approvallistsetting.ApprovalListDisplaySetDto;
import nts.uk.ctx.at.request.dom.application.applicationlist.service.AppGoBackInfoFull;
import nts.uk.ctx.at.request.dom.application.applicationlist.service.AppMasterInfo;
import nts.uk.ctx.at.request.dom.application.applicationlist.service.AppOverTimeInfoFull;
import nts.uk.ctx.at.request.dom.application.applicationlist.service.AppPrePostGroup;
import nts.uk.ctx.at.request.dom.application.applicationlist.service.ApplicationStatus;

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
//	private List<AppStatusApproval> lstStatusApproval;
//	private List<String> lstTimeColor; 
//	private List<String> lstFramStatus;
	private List<AppPrePostGroup> lstAppGroup;
	private List<Integer> lstAppType;
	
}
