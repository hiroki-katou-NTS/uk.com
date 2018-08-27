package nts.uk.screen.at.app.kaf022.find;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.app.find.application.applicationlist.AppTypeBfFinder;
import nts.uk.ctx.at.request.app.find.application.common.ApplicationSettingFinder;
import nts.uk.ctx.at.request.app.find.application.triprequestsetting.TripRequestSetFinder;
import nts.uk.ctx.at.request.app.find.application.workchange.AppWorkChangeSetFinder;
import nts.uk.ctx.at.request.app.find.setting.applicationapprovalsetting.appovertime.AppOvertimeSettingFinder;
import nts.uk.ctx.at.request.app.find.setting.applicationapprovalsetting.hdapplicationsetting.TimeHdAppSetFinder;
import nts.uk.ctx.at.request.app.find.setting.applicationapprovalsetting.hdworkapplicationsetting.WithdrawalAppSetFinder;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.applicationlatearrival.LateEarlyRequestFinder;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.withdrawalrequestset.WithDrawalReqSetFinder;
import nts.uk.ctx.at.request.app.find.setting.company.applicationcommonsetting.AppCommonSetFinder;
import nts.uk.ctx.at.request.app.find.setting.company.applicationsetting.ProxyAppSetFinder;
import nts.uk.ctx.at.request.app.find.setting.company.displayname.AppDispNameFinder;
import nts.uk.ctx.at.request.app.find.setting.company.mailsetting.mailapplicationapproval.ApprovalTempFinder;
import nts.uk.ctx.at.request.app.find.setting.company.mailsetting.mailcontenturlsetting.UrlEmbeddedFinder;
import nts.uk.ctx.at.request.app.find.setting.company.mailsetting.mailholidayinstruction.MailHdInstructionFinder;
import nts.uk.ctx.at.request.app.find.setting.company.mailsetting.overtimeworkinstructionmail.MailOtInstructionFinder;
import nts.uk.ctx.at.request.app.find.setting.company.mailsetting.remandsetting.ContentOfRemandMailFinder;
import nts.uk.ctx.at.request.app.find.setting.company.otrestappcommon.OvertimeRestAppCommonSetFinder;
import nts.uk.ctx.at.request.app.find.setting.company.request.applicationsetting.apptypesetting.DisplayReasonFinder;
import nts.uk.ctx.at.request.app.find.setting.company.request.stamp.StampRequestSettingFinder;
import nts.uk.ctx.at.request.app.find.setting.company.vacationapplicationsetting.HdAppSetFinder;
import nts.uk.ctx.at.request.app.find.setting.request.gobackdirectlycommon.GoBackDirectlyCommonSettingFinder;
import nts.uk.ctx.at.shared.app.find.workrule.closure.ClosureHistoryFinder;
import nts.uk.ctx.workflow.app.find.approvermanagement.setting.ApprovalSettingFinder;
import nts.uk.ctx.workflow.app.find.approvermanagement.setting.JobAssignSettingFinder;

@Stateless
public class FinderDtoKaf022 {
	
	@Inject
	private ClosureHistoryFinder finder;
	
//	@Inject 
//	private ApprovalSetFinder appFinder;
	
	@Inject 
	private AppCommonSetFinder appCommonSetFinder;
	
	@Inject 
	private ProxyAppSetFinder proxyAppSetFinder;
	
	@Inject 
	private MailHdInstructionFinder mailHdInstructionFinder;
	
	@Inject 
	private MailOtInstructionFinder otFinder;
	
	@Inject
	private ApprovalTempFinder tempFinder;
	
	@Inject
	private ApplicationSettingFinder appSetFind;
	
	@Inject
	private AppDispNameFinder dispFinder;
	
	@Inject
	private StampRequestSettingFinder stampRequestSettingFinder;
	
	@Inject
	private GoBackDirectlyCommonSettingFinder goBackSettingFinder;
	
	@Inject
	private AppOvertimeSettingFinder overTimeFinder;
	@Inject
	private HdAppSetFinder hdAppFinder;
	
	@Inject
	AppWorkChangeSetFinder appWorkFinder;
	
	@Inject
	private TripRequestSetFinder tripFinder;
	
	@Inject
	private WithdrawalAppSetFinder withFinder;
	
	@Inject
	private TimeHdAppSetFinder timeFinder;
	
	@Inject
	private WithDrawalReqSetFinder withDrawalReqSetFinder;
	
	@Inject
	private LateEarlyRequestFinder lateEarlyRequestFinder;
	
	@Inject
	private AppTypeBfFinder bfreqFinder;
	
	@Inject 
	private JobAssignSettingFinder jobFinder;
	
	@Inject
	private ApprovalSettingFinder approvalSettingFinder;
	
	@Inject
	private OvertimeRestAppCommonSetFinder otRestAppComFinder;
	
	@Inject 
	private ContentOfRemandMailFinder contentMailFinder;
	
	@Inject
	private UrlEmbeddedFinder url;
	
	@Inject
	private DisplayReasonFinder dplReason;
	
	public DtoKaf022 findDtoKaf022() {
		DtoKaf022 result = new DtoKaf022();
		result.allClosure = finder.findAll();
		result.appSet = appSetFind.getAppRef();
		result.appCommon = appCommonSetFinder.findByCom();
		result.proxy = proxyAppSetFinder.findAll();
		result.mailHd = mailHdInstructionFinder.findByComId();
		result.mailOt = otFinder.findByComId();
		result.appTemp = tempFinder.findByComId();
		result.appliSet = appSetFind.finder();
		result.appName = dispFinder.findByCom();
		result.stampReq = stampRequestSettingFinder.findByCompanyID();
		result.goBack = goBackSettingFinder.findGoBackDirectlyCommonSettingbyAppID();
		result.appOt = overTimeFinder.findByCom();
		result.hdSet = hdAppFinder.findByApp();
		result.appChange = appWorkFinder.findByCom();
		result.tripReq = tripFinder.findByCid();
		result.wdApp = withFinder.findByCid();
		result.timeHd = timeFinder.findByCid();
		result.wdReq = withDrawalReqSetFinder.findByCompanyID();
		result.lateEarly = lateEarlyRequestFinder.findByCompanyID();
		// a7, 8
		result.appBf = bfreqFinder.findByCom();
		// A14
		result.jobAssign = jobFinder.findApp();
		
		result.approvalSettingDto = approvalSettingFinder.findApproSet();
		
		// B8 -> B26
		result.otRestAppCom = otRestAppComFinder.findByAppType();
		result.otRestApp7 = otRestAppComFinder.findByApp7();
		// A16_14, A16_15
		result.contentMail = contentMailFinder.findByCom();
		// A16_17
		result.url = url.findByComId();
		// A8_36 -> A8_43
		result.listDplReason = dplReason.findByCom();
		return result;
	}
}
