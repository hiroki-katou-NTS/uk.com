package nts.uk.ctx.at.request.pubimp.application.infoterminal;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.auth.dom.adapter.login.IGetInfoForLogin;
import nts.uk.ctx.at.request.dom.adapter.employee.GetMngInfoAdapter;
import nts.uk.ctx.at.request.dom.adapter.employee.RQEmpDataImport;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationApprovalService;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.EmploymentRootAtr;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.appabsence.ApplyForLeave;
import nts.uk.ctx.at.request.dom.application.appabsence.ApplyForLeaveRepository;
import nts.uk.ctx.at.request.dom.application.appabsence.HolidayAppType;
import nts.uk.ctx.at.request.dom.application.common.adapter.employeemanage.EmployeeManageRQAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.infoterminal.EmpInfoTerminalAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.stamp.StampCardAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseStateImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootContentImport_New;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.NewBeforeRegister;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.ConfirmMsgOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.CollectAchievement;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ActualContentDisplay;
import nts.uk.ctx.at.request.dom.application.common.service.setting.CommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.MsgErrorOutput;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.EmpInfoTerminal;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.StampCard;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.log.TopPageAlarmEmpInfoTerRQ;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.log.TopPgAlTrAdapter;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.receive.AnnualHolidayReceptionData;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.receive.AppLateReceptionData;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.receive.AppOverTimeReceptionData.AppOverTimBuilder;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.receive.AppStampReceptionData.AppStampBuilder;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.receive.AppVacationReceptionData;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.receive.AppWorkChangeReceptionData;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.receive.AppWorkHolidayReceptionData.AppWorkHolidayBuilder;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.receive.ApplicationReceptionData;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.service.ConvertTimeRecordApplicationService;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWork;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWorkRepository;
import nts.uk.ctx.at.request.dom.application.lateleaveearly.ArrivedLateLeaveEarly;
import nts.uk.ctx.at.request.dom.application.lateleaveearly.ArrivedLateLeaveEarlyRepository;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTimeRepository;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeAppAtr;
import nts.uk.ctx.at.request.dom.application.stamp.AppRecordImage;
import nts.uk.ctx.at.request.dom.application.stamp.AppRecordImageRepository;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.TimeLeaveApplication;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.TimeLeaveApplicationRepository;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChange;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChangeRepository;
import nts.uk.ctx.at.request.pub.application.infoterminal.AnnualHolidayReceptionDataExport;
import nts.uk.ctx.at.request.pub.application.infoterminal.AppLateReceptionDataExport;
import nts.uk.ctx.at.request.pub.application.infoterminal.AppOverTimeReceptionDataExport;
import nts.uk.ctx.at.request.pub.application.infoterminal.AppStampReceptionDataExport;
import nts.uk.ctx.at.request.pub.application.infoterminal.AppVacationReceptionDataExport;
import nts.uk.ctx.at.request.pub.application.infoterminal.AppWorkChangeReceptionDataExport;
import nts.uk.ctx.at.request.pub.application.infoterminal.AppWorkHolidayReceptionDataExport;
import nts.uk.ctx.at.request.pub.application.infoterminal.ApplicationReceptionDataExport;
import nts.uk.ctx.at.request.pub.application.infoterminal.ConvertTRAppServicePub;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyAdapter;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainDataMngRegisterDateChange;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.TimeDigestionParam;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.loginuser.LoginUserContextManager;

@Stateless
public class ConvertTRAppServicePubImpl implements ConvertTRAppServicePub {

	@Inject
	private WorkTypeRepository workTypeRepository;

	@Inject
	private WorkingConditionItemRepository workingConditionItemRepository;

	@Inject
	private ApplicationRepository applicationRepository;

	@Inject
	private AppRecordImageRepository appRecordImageRepository;

	@Inject
	private AppWorkChangeRepository appWorkChangeRepository;

	@Inject
	private ArrivedLateLeaveEarlyRepository arrivedLateLeaveEarlyRepository;

	@Inject
	private CommonAlgorithm commonAlgorithm;

	@Inject
	private ApplicationApprovalService applicationApprovalService;

	@Inject
	private EmpInfoTerminalAdapter empInfoTerminalAdapter;

	@Inject
	private StampCardAdapter stampCardAdapter;

	@Inject
	private TopPgAlTrAdapter topPgAlTrAdapter;

	@Inject
	private EmployeeManageRQAdapter employeeManageRQAdapter;

	@Inject
	private TimeLeaveApplicationRepository timeLeaveApplicationRepo;

	@Inject
	private AppOverTimeRepository appOverTimeRepo;

	@Inject
	private AppHolidayWorkRepository appHolidayWorkRepo;

	@Inject
	private ApplyForLeaveRepository applyForLeaveRepo;

	@Inject
	private CompanyAdapter companyAdapter;

	@Inject
	private IGetInfoForLogin IGetInfoForLogin;

	@Inject
	private GetMngInfoAdapter getMngInfoAdapter;

	@Inject
	private LoginUserContextManager loginUserContextManager;

	@Inject
	private InterimRemainDataMngRegisterDateChange interimRemainDataMngRegisterDateChange;

	@Inject
	private CollectAchievement collectAchievement;

	@Inject
	private OtherCommonAlgorithm otherCommonAlgorithm;

	@Inject
	private NewBeforeRegister newBeforeRegister;

	@Override
	public <T extends ApplicationReceptionDataExport> Optional<AtomTask> converData(String empInfoTerCode,
			String contractCode, T recept) {
		RequireImpl impl = new RequireImpl(workTypeRepository, workingConditionItemRepository, applicationRepository,
				appRecordImageRepository, appWorkChangeRepository, arrivedLateLeaveEarlyRepository, commonAlgorithm,
				applicationApprovalService, empInfoTerminalAdapter, stampCardAdapter, topPgAlTrAdapter,
				employeeManageRQAdapter, timeLeaveApplicationRepo, appOverTimeRepo, appHolidayWorkRepo,
				applyForLeaveRepo, companyAdapter, IGetInfoForLogin, getMngInfoAdapter, loginUserContextManager,
				interimRemainDataMngRegisterDateChange, collectAchievement, otherCommonAlgorithm, newBeforeRegister);

		return ConvertTimeRecordApplicationService.converData(impl, empInfoTerCode, contractCode, covertTo(recept));
	}

	public ApplicationReceptionData covertTo(ApplicationReceptionDataExport data) {

		ApplicationReceptionData appDom = new ApplicationReceptionData(data.getIdNumber(),
				data.getApplicationCategory(), data.getYmd(), data.getTime());
		ApplicationCategoryPub cate = ApplicationCategoryPub.valueStringOf(data.getApplicationCategory());
		switch (cate) {

		// 打刻申請
		case STAMP:
			AppStampReceptionDataExport appStampData = (AppStampReceptionDataExport) data;
			return new AppStampBuilder(data.getIdNumber(), data.getApplicationCategory(), data.getYmd(), data.getTime(),
					appStampData.getGoOutCategory(), appStampData.getTypeBeforeAfter())
							.appTime(appStampData.getAppTime()).appYMD(appStampData.getAppYMD())
							.stampType(appStampData.getStampType()).reason(appStampData.getReason()).build();

		// 残業申請
		case OVERTIME:
			// AppOverTime
			AppOverTimeReceptionDataExport overTimeData = (AppOverTimeReceptionDataExport) data;
			return new AppOverTimBuilder(data.getIdNumber(), data.getApplicationCategory(), data.getYmd(),
					data.getTime(), overTimeData.getOvertimeHour1(), overTimeData.getOvertimeNo1())
							.overtimeHour2(overTimeData.getOvertimeHour2()).overtimeNo2(overTimeData.getOvertimeNo2())
							.overtimeHour3(overTimeData.getOvertimeHour3()).overtimeNo3(overTimeData.getOvertimeNo3())
							.typeBeforeAfter(overTimeData.getTypeBeforeAfter()).appYMD(overTimeData.getAppYMD())
							.reason(overTimeData.getReason()).build();

		// 休暇申請
		case VACATION:
			// ApplyForLeave
			AppVacationReceptionDataExport appVacData = (AppVacationReceptionDataExport) data;
			return new AppVacationReceptionData(
					new ApplicationReceptionData(data.getIdNumber(), data.getApplicationCategory(), data.getYmd(),
							data.getTime()),
					appVacData.getTypeBeforeAfter(), appVacData.getStartDate(), appVacData.getEndDate(),
					appVacData.getWorkType(), appVacData.getReason());

		// 勤務変更申請
		case WORK_CHANGE:
			AppWorkChangeReceptionDataExport appWorkData = (AppWorkChangeReceptionDataExport) data;
			return new AppWorkChangeReceptionData(appDom, appWorkData.getTypeBeforeAfter(), appWorkData.getStartDate(),
					appWorkData.getEndDate(), appWorkData.getWorkTime(), appWorkData.getReason());

		// 休日出勤時間申請
		case WORK_HOLIDAY:
			AppWorkHolidayReceptionDataExport appWorkHol = (AppWorkHolidayReceptionDataExport) data;
			return new AppWorkHolidayBuilder(data.getIdNumber(), data.getApplicationCategory(), data.getYmd(),
					data.getTime(), appWorkHol.getBreakTime1(), appWorkHol.getBreakNo1())
							.breakTime2(appWorkHol.getBreakTime2()).breakNo2(appWorkHol.getBreakNo2())
							.breakTime3(appWorkHol.getBreakTime3()).breakNo3(appWorkHol.getBreakNo3())
							.typeBeforeAfter(appWorkHol.getTypeBeforeAfter()).appYMD(appWorkHol.getAppYMD())
							.reason(appWorkHol.getReason()).build();
		// 遅刻早退取消申請
		case LATE:
			AppLateReceptionDataExport appLateData = (AppLateReceptionDataExport) data;
			return new AppLateReceptionData(appDom, appLateData.getTypeBeforeAfter(), appLateData.getAppYMD(),
					appLateData.getReasonLeave(), appLateData.getReason());

		// 時間年休申請
		case ANNUAL:
			AnnualHolidayReceptionDataExport annualHoli = (AnnualHolidayReceptionDataExport) data;
			return new AnnualHolidayReceptionData(
					new ApplicationReceptionData(data.getIdNumber(), data.getApplicationCategory(), data.getYmd(),
							data.getTime()),
					annualHoli.getAnnualHolidayType(), annualHoli.getAnnualHolidayTime(),
					annualHoli.getTypeBeforeAfter(), annualHoli.getAppYMD(), annualHoli.getReason());

		default:
			return null;
		}
	}

	@AllArgsConstructor
	public class RequireImpl implements ConvertTimeRecordApplicationService.Require {

		private final WorkTypeRepository workTypeRepository;

		private final WorkingConditionItemRepository workingConditionItemRepository;

		private final ApplicationRepository applicationRepository;

		private final AppRecordImageRepository appRecordImageRepository;

		private final AppWorkChangeRepository appWorkChangeRepository;

		private final ArrivedLateLeaveEarlyRepository arrivedLateLeaveEarlyRepository;

		private final CommonAlgorithm commonAlgorithm;

		private final ApplicationApprovalService applicationApprovalService;

		private final EmpInfoTerminalAdapter empInfoTerminalAdapter;

		private final StampCardAdapter stampCardAdapter;

		private final TopPgAlTrAdapter topPgAlTrAdapter;

		private final EmployeeManageRQAdapter employeeManageRQAdapter;

		private final TimeLeaveApplicationRepository timeLeaveApplicationRepo;

		private final AppOverTimeRepository appOverTimeRepo;

		private final AppHolidayWorkRepository appHolidayWorkRepo;

		private final ApplyForLeaveRepository applyForLeaveRepo;

		private final CompanyAdapter companyAdapter;

		private final IGetInfoForLogin IGetInfoForLogin;

		private final GetMngInfoAdapter getMngInfoAdapter;

		private final LoginUserContextManager loginUserContextManager;

		private final InterimRemainDataMngRegisterDateChange interimRemainDataMngRegisterDateChange;

		private final CollectAchievement collectAchievement;

		private final OtherCommonAlgorithm otherCommonAlgorithm;

		private final NewBeforeRegister newBeforeRegister;

		@Override
		public Optional<EmpInfoTerminal> getEmpInfoTerminal(String empInfoTerCode, String contractCode,
				Optional<String> leavCategory) {
			return empInfoTerminalAdapter.getEmpInfoTerminal(empInfoTerCode, contractCode, leavCategory);
		}

		@Override
		public Optional<WorkType> findByPK(String companyId, String workTypeCd) {
			return workTypeRepository.findByPK(companyId, workTypeCd);
		}

		@Override
		public Optional<WorkingConditionItem> getBySidAndStandardDate(String employeeId, GeneralDate baseDate) {
			return workingConditionItemRepository.getBySidAndStandardDate(employeeId, baseDate);
		}

		@Override
		public List<Application> getApplication(PrePostAtr prePostAtr, GeneralDateTime inputDate, GeneralDate appDate,
				ApplicationType appType, String employeeID) {
			return this.applicationRepository.getApplication(prePostAtr, inputDate, appDate, appType, employeeID);
		}

		@Override
		public Optional<StampCard> getByCardNoAndContractCode(String contractCode, String stampNumber) {
			return stampCardAdapter.getByCardNoAndContractCode(contractCode, stampNumber);
		}

		@Override
		public void insert(AppRecordImage appStamp) {
			this.appRecordImageRepository.addStamp(appStamp);
		}

		@Override
		public void insert(AppOverTime appOverTime) {
			this.appOverTimeRepo.add(appOverTime);
		}

		@Override
		public void insert(String companyId, String contractCode, ApplyForLeave appAbsence) {
			this.applyForLeaveRepo.insert(companyId, contractCode, appAbsence);
		}

		@Override
		public void insert(AppWorkChange appWorkChange) {
			this.appWorkChangeRepository.add(appWorkChange);
		}

		@Override
		public void insert(AppHolidayWork appHolidayWork) {
			this.appHolidayWorkRepo.add(appHolidayWork);
		}

		@Override
		public void insert(String cid, ArrivedLateLeaveEarly lateOrLeaveEarly) {
			this.arrivedLateLeaveEarlyRepository.add(cid, lateOrLeaveEarly);

		}

		@Override
		public void insert(TimeLeaveApplication timeLeav) {
			this.timeLeaveApplicationRepo.add(timeLeav);
		}

		@Override
		public ApprovalRootContentImport_New getApprovalRoot(String cid, String employeeID, EmploymentRootAtr rootAtr,
				ApplicationType appType, GeneralDate appDate) {
			return commonAlgorithm.getApprovalRoot(cid, employeeID, rootAtr, appType, appDate);
		}

		@Override
		public void insertApp(Application application, List<ApprovalPhaseStateImport_New> listApprovalPhaseState) {
			applicationApprovalService.insertApp(application, listApprovalPhaseState);
		}

		@Override
		public void insertLogAll(TopPageAlarmEmpInfoTerRQ alEmpInfo) {
			topPgAlTrAdapter.insertLogAll(alEmpInfo);
		}

		@Override
		public List<String> getListEmpID(String companyID, GeneralDate referenceDate) {
			return employeeManageRQAdapter.getListEmpID(companyID, referenceDate);
		}

		@Override
		public List<RQEmpDataImport> getEmpData(List<String> empIDList) {
			return getMngInfoAdapter.getEmpData(empIDList);
		}

		@Override
		public CompanyInfo getCompanyInfoById(String companyId) {
			return companyAdapter.getCompanyInfoById(companyId);
		}

		@Override
		public Optional<String> getUserIdFromLoginId(String perId) {
			return IGetInfoForLogin.getUserIdFromLoginId(perId);
		}

		@Override
		public void loggedInAsEmployee(String userId, String personId, String contractCode, String companyId,
				String companyCode, String employeeId, String employeeCode) {
			loginUserContextManager.loggedInAsEmployee(userId, personId, contractCode, companyId, companyCode,
					employeeId, employeeCode);
		}

		@Override
		public void loggedOut() {
			loginUserContextManager.loggedOut();
		}

		@Override
		public void registerDateChange(String cid, String sid, List<GeneralDate> lstDate) {
			interimRemainDataMngRegisterDateChange.registerDateChange(cid, sid, lstDate);
		}

		@Override
		public List<ActualContentDisplay> getAchievementContents(String companyID, String employeeID,
				List<GeneralDate> dateLst, ApplicationType appType) {
			return collectAchievement.getAchievementContents(companyID, employeeID, dateLst, appType);
		}

		@Override
		public List<GeneralDate> lstDateIsHoliday(String sid, DatePeriod dates,
				List<ActualContentDisplay> actualContentDisplayLst) {
			return otherCommonAlgorithm.lstDateIsHoliday(sid, dates, actualContentDisplayLst);
		}

		@Override
		public List<ConfirmMsgOutput> processBeforeRegister_New(String companyID, EmploymentRootAtr employmentRootAtr,
				boolean agentAtr, Application application, OvertimeAppAtr overtimeAppAtr,
				List<MsgErrorOutput> msgErrorLst, List<GeneralDate> lstDateHd,
				AppDispInfoStartupOutput appDispInfoStartupOutput, List<String> workTypeCds,
				Optional<TimeDigestionParam> timeDigestionUsageInfor, Optional<String> workTimeCode, boolean flag) {
			return newBeforeRegister.processBeforeRegister_New(companyID, employmentRootAtr, agentAtr, application,
					overtimeAppAtr, msgErrorLst, lstDateHd, appDispInfoStartupOutput, workTypeCds,
					timeDigestionUsageInfor, workTimeCode, flag);
		}

		@Override
		public AppDispInfoStartupOutput getAppDispInfoStart(String companyID, ApplicationType appType,
				List<String> applicantLst, List<GeneralDate> dateLst, boolean mode,
				Optional<HolidayAppType> opHolidayAppType, Optional<OvertimeAppAtr> opOvertimeAppAtr) {
			return commonAlgorithm.getAppDispInfoStart(companyID, appType, applicantLst, dateLst, mode,
					opHolidayAppType, opOvertimeAppAtr);
		}

	}
}
