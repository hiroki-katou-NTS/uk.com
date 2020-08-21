package nts.uk.screen.at.app.kaf022.find;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.app.find.application.applicationlist.AppTypeBfFinder;
import nts.uk.ctx.at.request.app.find.application.common.ApplicationSettingFinder;
import nts.uk.ctx.at.request.app.find.application.requestofearch.GetDataAppCfDetailFinder;
import nts.uk.ctx.at.request.app.find.application.triprequestsetting.TripRequestSetFinder;
import nts.uk.ctx.at.request.app.find.application.workchange.AppWorkChangeFinder;
import nts.uk.ctx.at.request.app.find.applicationreflect.AppReflectExeConditionDto;
import nts.uk.ctx.at.request.app.find.setting.applicationapprovalsetting.appovertime.AppOvertimeSettingFinder;
import nts.uk.ctx.at.request.app.find.setting.applicationapprovalsetting.hdapplicationsetting.TimeHdAppSetFinder;
import nts.uk.ctx.at.request.app.find.setting.applicationapprovalsetting.hdworkapplicationsetting.WithdrawalAppSetFinder;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.applicationlatearrival.LateEarlyRequestFinder;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.applicationsetting.ApplicationSettingDto;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.applicationsetting.DisplayReasonDto;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.appovertime.OvertimeAppSetDto;
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
import nts.uk.ctx.at.request.app.find.setting.request.application.ApplicationDeadlineDto;
import nts.uk.ctx.at.request.app.find.setting.request.gobackdirectlycommon.GoBackDirectlyCommonSettingFinder;
import nts.uk.ctx.at.request.dom.applicationreflect.AppReflectExeConditionRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.ApplicationSettingRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.DisplayReasonRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime.OvertimeAppSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.HolidayApplicationSettingRepository;
import nts.uk.ctx.at.shared.app.find.ot.frame.OvertimeWorkFrameFindDto;
import nts.uk.ctx.at.shared.app.find.ot.frame.OvertimeWorkFrameFinder;
import nts.uk.ctx.at.shared.app.find.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.overtimeworkapplycation.OvertimeWorkApplicationReflectDto;
import nts.uk.ctx.at.shared.app.find.workrule.closure.ClosureHistoryFinder;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ClosureHistoryFindDto;
import nts.uk.ctx.sys.portal.pub.standardmenu.StandardMenuNameExport;
import nts.uk.ctx.sys.portal.pub.standardmenu.StandardMenuNameQuery;
import nts.uk.ctx.sys.portal.pub.standardmenu.StandardMenuPub;
import nts.uk.ctx.workflow.app.find.approvermanagement.setting.ApprovalSettingFinder;
import nts.uk.ctx.workflow.app.find.approvermanagement.setting.JobAssignSettingFinder;
import nts.uk.shr.com.context.AppContexts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class FinderDtoKaf022 {
	
	@Inject
	private ClosureHistoryFinder closureHistoryFinder;
	
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
	AppWorkChangeFinder appWorkFinder;
	
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

	@Inject
	private StandardMenuPub menuPub;

	@Inject
	private GetDataAppCfDetailFinder getDataAppCfDetailFinder;

	@Inject
	private ApplicationSettingRepository appSettingRepo;

	@Inject
	private DisplayReasonRepository displayReasonRepo;

	@Inject
	private OvertimeAppSetRepository overtimeAppSetRepo;

	@Inject
    private OvertimeWorkFrameFinder overtimeFrameFinder;

	@Inject
    private HolidayApplicationSettingRepository hdAppSettingRepo;

	@Inject
    private AppReflectExeConditionRepository appReflectConditionRepo;
	
	public DtoKaf022 findDtoKaf022() {
		String companyId = AppContexts.user().companyId();
		// TODO: get Application Settings
		ApplicationSettingDto applicationSetting = appSettingRepo.findByCompanyId(companyId).map(ApplicationSettingDto::fromDomain).orElse(null);
		List<DisplayReasonDto> displayReasons = displayReasonRepo.findByCompanyId(companyId).stream().map(DisplayReasonDto::fromDomain).collect(Collectors.toList());
		OvertimeAppSetDto overTimeAppSetting = overtimeAppSetRepo.findSettingByCompanyId(companyId).map(OvertimeAppSetDto::fromDomain).orElse(null);
		List<OvertimeWorkFrameFindDto> overtimeFrames = overtimeFrameFinder.findAll();

		// TODO: get Reflection Settings
        AppReflectExeConditionDto appReflectCondition = appReflectConditionRepo.findByCompanyId(companyId).map(AppReflectExeConditionDto::fromDomain).orElse(null);
        OvertimeWorkApplicationReflectDto overtimeAppReflect = overtimeAppSetRepo.findReflectByCompanyId(companyId).map(OvertimeWorkApplicationReflectDto::fromDomain).orElse(null);

		// TODO: get menu
		List<StandardMenuNameQuery> queries = new ArrayList<>();
		queries.add(new StandardMenuNameQuery("KAF005", "A", Optional.of("overworkatr=0")));
		queries.add(new StandardMenuNameQuery("KAF005", "A", Optional.of("overworkatr=1")));
		queries.add(new StandardMenuNameQuery("KAF005", "A", Optional.of("overworkatr=2")));
		queries.add(new StandardMenuNameQuery("KAF006", "A", Optional.empty()));
		queries.add(new StandardMenuNameQuery("KAF007", "A", Optional.empty()));
		queries.add(new StandardMenuNameQuery("KAF008", "A", Optional.empty()));
		queries.add(new StandardMenuNameQuery("KAF009", "A", Optional.empty()));
		queries.add(new StandardMenuNameQuery("KAF010", "A", Optional.empty()));
		queries.add(new StandardMenuNameQuery("KAF012", "A", Optional.empty()));
		queries.add(new StandardMenuNameQuery("KAF004", "A", Optional.empty()));
		queries.add(new StandardMenuNameQuery("KAF002", "A", Optional.empty()));
		queries.add(new StandardMenuNameQuery("KAF002", "C", Optional.empty()));
		queries.add(new StandardMenuNameQuery("KAF011", "A", Optional.empty()));
		queries.add(new StandardMenuNameQuery("KAF020", "A", Optional.empty()));
		List<StandardMenuNameExport> menuList = menuPub.getMenuDisplayName(companyId, queries);

		DtoKaf022 result = new DtoKaf022();
		result.allClosure = closureHistoryFinder.findAll();
//		result.appSet = appSetFind.getAppRef();
//		result.appCommon = appCommonSetFinder.findByCom();
//		result.proxy = proxyAppSetFinder.findAll();
//		result.mailHd = mailHdInstructionFinder.findByComId();
//		result.mailOt = otFinder.findByComId();
//		result.appTemp = tempFinder.findByComId();
//		result.appliSet = appSetFind.finder();
//		result.appName = dispFinder.findByCom();
		result.stampReq = stampRequestSettingFinder.findByCompanyID();
		result.goBack = goBackSettingFinder.findGoBackDirectlyCommonSettingbyAppID();
		result.appOt = overTimeFinder.findByCom();
		result.hdSet = hdAppFinder.findByApp();
		result.appChange = appWorkFinder.findByCompany();
		result.tripReq = tripFinder.findByCid();
		result.wdApp = withFinder.findByCid();
		result.timeHd = timeFinder.findByCid();
		result.wdReq = withDrawalReqSetFinder.findByCompanyID();
		result.lateEarly = lateEarlyRequestFinder.findByCompanyID();
		// a7, 8
//		result.appBf = bfreqFinder.findByCom();
		// A14

		
		// B8 -> B26

		result.otRestApp7 = otRestAppComFinder.findByApp7();
		// A16_14, A16_15
//		result.contentMail = contentMailFinder.findByCom();
		// A16_17
//		result.url = url.findByComId();
		// A8_36 -> A8_43
//		result.listDplReason = dplReason.findByCom();

		// refactor 4
		// A
		result.setApplicationSetting(applicationSetting);
		result.setReasonDisplaySettings(displayReasons);
		result.setMenus(menuList);
		result.setAppReflectCondition(appReflectCondition);
//		result.setOvertimeWorkFrames(overtimeFrames);
		result.setNightOvertimeReflectAtr(appSettingRepo.getNightOvertimeReflectAtr(companyId));
		result.jobAssign = jobFinder.findApp();
		result.approvalSettingDto = approvalSettingFinder.findApproSet();

		// B
        result.setOvertimeAppSetting(overTimeAppSetting);
        result.setOvertimeAppReflect(overtimeAppReflect);

		return result;
	}
}
