package nts.uk.ctx.at.request.app.find.application.approvalstatus;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.i18n.I18NText;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.applist.service.OverTimeFrame;
import nts.uk.ctx.at.request.dom.application.applist.service.detail.AppAbsenceFull;
import nts.uk.ctx.at.request.dom.application.applist.service.detail.AppDetailInfoRepository;
import nts.uk.ctx.at.request.dom.application.applist.service.detail.AppGoBackInfoFull;
import nts.uk.ctx.at.request.dom.application.applist.service.detail.AppHolidayWorkFull;
import nts.uk.ctx.at.request.dom.application.applist.service.detail.AppOverTimeInfoFull;
import nts.uk.ctx.at.request.dom.application.applist.service.detail.AppWorkChangeFull;
import nts.uk.ctx.at.request.dom.application.approvalstatus.ApprovalStatusMailTemp;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.ApprovalStatusService;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApplicationsListOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApprovalStatusEmployeeOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApprovalSttAppDetail;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApprovalSttAppOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApproverOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.DailyStatusOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.SendMailResultOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.WorkplaceInfor;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalBehaviorAtrImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseStateImport_New;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.HdAppSet;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting.HolidayAppType;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ApprovalComfirmDto;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ClosureHistoryForComDto;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ClosuresDto;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
/**
 * 承認状況メールテンプレート
 * 
 * @author dat.lh
 */
public class ApprovalStatusFinder {

	@Inject
	private ClosureService closureService;

	/** The repository. */
	@Inject
	private ClosureRepository repository;

	@Inject
	ClosureEmploymentRepository closureEmpRepo;

	@Inject
	private ApprovalStatusService appSttService;

	@Inject
	private AppDetailInfoRepository appDetailInfoRepo;

	/**
	 * アルゴリズム「承認状況本文起動」を実行する
	 */
	public List<ApprovalStatusMailTempDto> getMailTemp() {
		// 会社ID
		String cid = AppContexts.user().companyId();
		List<ApprovalStatusMailTempDto> listMail = new ArrayList<ApprovalStatusMailTempDto>();

		listMail.add(this.getApprovalStatusMailTemp(cid, 0));
		listMail.add(this.getApprovalStatusMailTemp(cid, 1));
		listMail.add(this.getApprovalStatusMailTemp(cid, 2));
		listMail.add(this.getApprovalStatusMailTemp(cid, 3));
		listMail.add(this.getApprovalStatusMailTemp(cid, 4));
		return listMail;
	}

	private ApprovalStatusMailTempDto getApprovalStatusMailTemp(String cid, int mailType) {
		// アルゴリズム「承認状況メール本文取得」を実行する
		ApprovalStatusMailTemp domain = appSttService.getApprovalStatusMailTemp(mailType);
		// ドメインの取得
		if (Objects.isNull(domain)) {
			// ドメインが取得できなかった場合
			// 画面モード ＝ 新規
			return new ApprovalStatusMailTempDto(mailType, 1, 1, 1, "", "", 0);
		}
		// ドメインが取得できた場合(lấy được)
		// 画面モード ＝ 更新
		return ApprovalStatusMailTempDto.fromDomain(domain);
	}

	/**
	 * 承認状況メールテスト送信
	 */
	public String confirmSenderMail() {
		// アルゴリズム「承認状況送信者メール確認」を実行する
		return appSttService.confirmApprovalStatusMailSender();
	}

	public SendMailResultOutput sendTestMail(int mailType) {
		// アルゴリズム「承認状況メールテスト送信実行」を実行する
		return appSttService.sendTestMail(mailType);
	}

	/**
	 * アルゴリズム「承認状況指定締め日取得」を実行する Acquire approval situation designated closing
	 * date
	 * 
	 * @return approval situation
	 */
	public ApprovalComfirmDto findAllClosure() {
		// Get companyID.
		String companyId = AppContexts.user().companyId();
		GeneralDate startDate = null;
		GeneralDate endDate = null;
		int processingYm = 0;
		// ドメインモデル「就業締め日」を取得する <shared>
		List<Closure> closureList = this.repository.findAllUse(companyId);
		int selectedClosureId = 0;
		List<ClosuresDto> closureDto = closureList.stream().map(x -> {
			int closureId = x.getClosureId().value;
			List<ClosureHistoryForComDto> closureHistoriesList = x.getClosureHistories().stream().map(x1 -> {
				return new ClosureHistoryForComDto(x1.getClosureName().v(), x1.getClosureId().value,
						x1.getEndYearMonth().v().intValue(), x1.getClosureDate().getClosureDay().v().intValue(),
						x1.getStartYearMonth().v().intValue());
			}).collect(Collectors.toList());
			ClosureHistoryForComDto closureHistories = closureHistoriesList.stream()
					.filter(x2 -> x2.getClosureId() == closureId).findFirst().orElse(null);
			return new ClosuresDto(closureId, closureHistories.getCloseName(), closureHistories.getClosureDate());
		}).collect(Collectors.toList());

		// ユーザー固有情報「選択中の就業締め」を取得する
		// TODO neeed to get closureId init

		// 就業締め日（リスト）の先頭の締めIDを選択
		List<String> listEmpCode = new ArrayList<>();
		Optional<ClosuresDto> closure = closureDto.stream().findFirst();
		if (closure.isPresent()) {
			val closureId = closure.get().getClosureId();
			selectedClosureId = closureId;
			val closureOpt = this.repository.findById(companyId, closureId);
			if (closureOpt.isPresent()) {
				val closureItem = closureOpt.get();
				// 当月の期間を算出する
				val yearMonth = closureItem.getClosureMonth().getProcessingYm();
				processingYm = yearMonth.v();
				// アルゴリズム「承認状況指定締め期間設定」を実行する
				// アルゴリズム「当月の期間を算出する」を実行する
				DatePeriod closurePeriod = this.closureService.getClosurePeriod(closureId, yearMonth);
				startDate = closurePeriod.start();
				endDate = closurePeriod.end();
				// ドメインモデル「雇用に紐づく就業締め」より、雇用コードと締めIDを取得する
				List<ClosureEmployment> listEmployee = closureEmpRepo.findByClosureId(companyId, closureId);
				for (ClosureEmployment emp : listEmployee) {
					listEmpCode.add(emp.getEmploymentCD());
				}
			} else {
				throw new RuntimeException("Could not find closure");
			}
		}
		return new ApprovalComfirmDto(selectedClosureId, closureDto, startDate, endDate, processingYm, listEmpCode);
	}

	/**
	 * アルゴリズム「承認状況指定締め期間設定」を実行する
	 * 
	 * @param closureId
	 * @param closureDate
	 * @return
	 */
	public ApprovalStatusPeriorDto getApprovalStatusPerior(int closureId, int closureDate) {
		// Get companyID.
		String companyId = AppContexts.user().companyId();
		GeneralDate startDate = null;
		GeneralDate endDate = null;
		int processingYmNew = 0;
		// 当月の期間を算出する
		YearMonth processingYm = new YearMonth(closureDate);

		List<ClosureEmployment> listEmployee = new ArrayList<>();
		Optional<Closure> closure = repository.findById(companyId, closureId);
		if (!closure.isPresent()) {
			throw new RuntimeException("Could not find closure");
		}

		val yearMonth = closure.get().getClosureMonth().getProcessingYm();
		processingYmNew = yearMonth.v();
		// アルゴリズム「当月の期間を算出する」を実行する
		DatePeriod closurePeriod = this.closureService.getClosurePeriod(closureId, processingYm);
		startDate = closurePeriod.start();
		endDate = closurePeriod.end();
		// ドメインモデル「雇用に紐づく就業締め」より、雇用コードと締めIDを取得する
		listEmployee = closureEmpRepo.findByClosureId(companyId, closureId);
		List<String> listEmpCode = new ArrayList<>();
		for (ClosureEmployment emp : listEmployee) {
			listEmpCode.add(emp.getEmploymentCD());
		}
		return new ApprovalStatusPeriorDto(startDate, endDate, listEmpCode, processingYmNew);
	}

	/**
	 * アルゴリズム「承認状況職場別起動」を実行する
	 * 
	 * @param appStatus
	 */
	public List<ApprovalSttAppOutput> getAppSttByWorkpace(ApprovalStatusActivityData appStatus) {
		List<ApprovalSttAppOutput> listAppSttApp = new ArrayList<>();
		GeneralDate startDate = appStatus.getStartDate();
		GeneralDate endDate = appStatus.getEndDate();
		List<WorkplaceInfor> listWorkPlaceInfor = appStatus.getListWorkplace();
		for (WorkplaceInfor wkp : listWorkPlaceInfor) {
			List<ApprovalStatusEmployeeOutput> listAppStatusEmp = appSttService
					.getApprovalStatusEmployee(wkp.getCode(), startDate, endDate, appStatus.getListEmpCd());
			ApprovalSttAppOutput approvalSttApp = appSttService.getApprovalSttApp(wkp, listAppStatusEmp);
			if(Objects.isNull(approvalSttApp)) {
				continue;
			}
			listAppSttApp.add(approvalSttApp);
		}

		return listAppSttApp;
	}

	/**
	 * アルゴリズム「承認状況未承認メール送信」を実行する
	 */
	public List<String> getAppSttSendingUnapprovedMail(List<ApprovalSttAppOutput> listAppSttApp) {
		return appSttService.getAppSttSendingUnapprovedMail(listAppSttApp);

	}

	/**
	 * アルゴリズム「承認状況未承認メール送信実行」を実行する
	 */
	public void exeSendUnconfirmedMail(UnAppMailTransmisDto unAppMail) {
		appSttService.exeSendUnconfirmedMail(unAppMail.getListWkpId(), unAppMail.getClosureStart(),
				unAppMail.getClosureEnd(), unAppMail.getListEmpCd());
	}

	/**
	 * アルゴリズム「承認状況社員別起動」を実行する
	 */
	public List<DailyStatusOutput> initApprovalSttByEmployee(ApprovalStatusByIdDto appSttById) {
		return appSttService.getApprovalSttById(appSttById.getSelectedWkpId(), appSttById.getListWkpId(),
				appSttById.getStartDate(), appSttById.getEndDate(), appSttById.getListEmpCode());
	}

	/**
	 * アルゴリズム「承認状況申請内容表示」を実行する
	 */
	public ApplicationsListDto initApprovalSttRequestContentDis(ApprovalSttRequestContentDis appSttContent) {
		String companyID = AppContexts.user().companyId();
		ApplicationsListOutput appList = appSttService.initApprovalSttRequestContentDis(
				appSttContent.getListStatusEmp());
		HdAppSet lstHdAppSet = appList.getLstHdAppSet();
		List<ApplicationDetailDto> listAppDetail = new ArrayList<>();
		GeneralDate appStartDate = GeneralDate.today();
		GeneralDate appEndDate = GeneralDate.today();
		List<Integer> approvalStatus = new ArrayList<>();
		List<Integer> approvalStatus2 = new ArrayList<>();
		List<Integer> approvalStatus3 = new ArrayList<>();
		List<Integer> approvalStatus4 = new ArrayList<>();
		for(int x = 0; x < 3; x++) {
			approvalStatus.add(x);
			approvalStatus2.add(x);
			approvalStatus3.add(x);
			approvalStatus4.add(x);
		}
		listAppDetail. add(new ApplicationDetailDto(0, "残業申請", true, appStartDate, appEndDate, "2-16③システム日付(2018/4/7)　<　申請日.終了日(3/31)+8　　ＯＫ", 2, approvalStatus, "Phase1", "Phase1", "Phase1", "Phase1", "Phase1"));
		listAppDetail. add(new ApplicationDetailDto(1, "残業申請", false, appStartDate, appEndDate, "2-16③システム日付(2018/4/7)　<　申請日.終了日(3/31)+8　　ＯＫ", 4, approvalStatus2, "Phase1", "Phase1", "Phase1", "Phase1", "Phase1"));
		listAppDetail. add(new ApplicationDetailDto(2, "残業申請", true, appStartDate, appEndDate, "2-16③システム日付(2018/4/7)　<　申請日.終了日(3/31)+8　　ＯＫ", 5, approvalStatus3, "Phase1", "Phase1", "Phase1", "Phase1", "Phase1"));
		listAppDetail. add(new ApplicationDetailDto(3, "残業申請", false, appStartDate, appEndDate, "2-16③システム日付(2018/4/7)　<　申請日.終了日(3/31)+8　　ＯＫ", 0, approvalStatus4, "Phase1", "Phase1", "Phase1", "Phase1", "Phase1"));
		listAppDetail. add(new ApplicationDetailDto(1, "残業申請",true, appStartDate, appEndDate, "2-16③システム日付(2018/4/7)　<　申請日.終了日(3/31)+8　　ＯＫ", 1, approvalStatus, "Phase1", "Phase1", "Phase1", "Phase1", "Phase1"));
		/*List<ApprovalSttAppDetail> listAppSttDetail = appList.getApprovalSttAppDetail();
		for (ApprovalSttAppDetail app : listAppSttDetail) {
			ApplicationDetailDto detail = new ApplicationDetailDto();

			ApplicationType appType = app.getAppDispName().getAppType();
			Application_New applicaton_N = app.getAppContent().getApplication();
			String appId = applicaton_N.getAppID();
			detail.setAppType(appType.value);
			detail.setAppStartDate(applicaton_N.getStartDate().orElseGet(null));
			if(applicaton_N.isAppAbsence() || applicaton_N.isAppBusinessTrip() || applicaton_N.isAppWkChange()) {
				detail.setAppEndDate(applicaton_N.getEndDate().orElseGet(null));
			}
			
			detail.setAppName(app.getAppDispName().getDispName().v());
			detail.setPrePostAtr(applicaton_N.getPrePostAtr().value);

			List<ApprovalPhaseStateImport_New> listApprovalPhase= app.getAppContent().getApprRootContentExport().getApprovalRootState().getListApprovalPhaseState();
			listApprovalPhase.sort((ApprovalPhaseStateImport_New x1, ApprovalPhaseStateImport_New x2) -> x1.getPhaseOrder() - x2.getPhaseOrder());
			List<Integer> appStatus = new ArrayList<>();
			for(ApprovalPhaseStateImport_New appPhase: listApprovalPhase) {
				ApprovalBehaviorAtrImport_New appBehavior = appPhase.getApprovalAtr();
				switch (appBehavior) {
				case UNAPPROVED:
					appStatus.add(0);
					break;
				case APPROVED:
					appStatus.add(1);
					break;
				case REMAND:
					appStatus.add(0);
					break;
				case DENIAL:
					appStatus.add(2);
					break;
				default:
					break;
				}
			}
			detail.setApprovalStatus(appStatus);
			List<ApproverOutput> listApprover = app.getListApprover();
			for (ApproverOutput approver : listApprover) {
				int phase = approver.getPhase();
				String others = approver.getEmpName() + I18NText.getText("KAF018_47", approver.getNumOfPeople().toString());
				switch (phase) {
				case 1:
					detail.setPhase1(others);
					break;
				case 2:
					detail.setPhase2(others);
					break;
				case 3:
					detail.setPhase3(others);
					break;
				case 4:
					detail.setPhase4(others);
					break;
				case 5:
					detail.setPhase5(others);
					break;
				default:
					break;
				}
			}
			String appContent = "";
			switch (appType) {
			// 残業申請
			case OVER_TIME_APPLICATION:
				appContent = getAppContentOverTime(companyID, appId);
				break;
			// 休暇申請
			case ABSENCE_APPLICATION:
				appContent = getAbsenceApplication(applicaton_N, companyID, appId);
				break;
			// 勤務変更申請
			case WORK_CHANGE_APPLICATION:
				appContent = getAppWorkChangeInfo(companyID, appId);
				// có endDate
				break;
			// 出張申請
			case BUSINESS_TRIP_APPLICATION:
				// TODO
				// có endDate
				break;
			// 直行直帰申請
			case GO_RETURN_DIRECTLY_APPLICATION:
				appContent = getAppGoBackInfo(companyID, appId);
				break;
			// 休出時間申請
			case BREAK_TIME_APPLICATION:
				appContent = getBreakTimeApp(applicaton_N, companyID, appId);
				break;
			// 打刻申請
			case STAMP_APPLICATION:
				// TODO
				break;
			// 時間年休申請
			case ANNUAL_HOLIDAY_APPLICATION:

				break;
			// 遅刻早退取消申請
			case EARLY_LEAVE_CANCEL_APPLICATION:
				// TODO
				break;
			// 振休振出申請
			case COMPLEMENT_LEAVE_APPLICATION:
				//Not use anymore
				//appContent = getAppCompltLeaveInfo(applicaton_N, companyID, appId);
				// Đơn xin đồng bộ
				break;
			// 打刻申請（NR形式）
			case STAMP_NR_APPLICATION:
				// TODO
				break;
			// 連続出張申請
			case LONG_BUSINESS_TRIP_APPLICATION:
				// TODO
				break;
			// 出張申請オフィスヘルパー
			case BUSINESS_TRIP_APPLICATION_OFFICE_HELPER:
				// TODO
				break;
			// ３６協定時間申請
			case APPLICATION_36:
				// TODO
				break;

			default:
				break;
			}
			detail.setAppContent(appContent);
			int reflectState = applicaton_N.getReflectionInformation().getStateReflectionReal().value;
			detail.setReflectState(reflectState);
			listAppDetail.add(detail);
		}*/

		return new ApplicationsListDto(listAppDetail, lstHdAppSet);
	}

/*	private String getAppCompltLeaveInfo(Application_New applicaton_N, String companyID, String appId) {
		AppCompltLeaveFull appComlt =  appDetailInfoRepo.getAppCompltLeaveInfo(companyID, appId, type);
		return null;
	}*/

	private String getBreakTimeApp(Application_New applicaton_N, String companyID, String appId) {
		String appContent = "";
		AppHolidayWorkFull appHoliday = appDetailInfoRepo.getAppHolidayWorkInfo(companyID, appId);
		appContent += I18NText.getText("KAF018_275") + appHoliday.getWorkTypeName() + appHoliday.getWorkTimeName();
		if (!Objects.isNull(appHoliday.getStartTime1()) && !Objects.isNull(appHoliday.getEndTime1())) {
			appContent += I18NText.getText("KAF018_220");
		}
		appContent += appHoliday.getEndTime1() + appHoliday.getStartTime2();
		if (!Objects.isNull(appHoliday.getStartTime2()) && !Objects.isNull(appHoliday.getEndTime2())) {
			appContent += I18NText.getText("KAF018_220");
		}
		appContent += appHoliday.getEndTime1();
		List<OverTimeFrame> lstFrame = appHoliday.getLstFrame();
		int totalTime = 0;
		for (OverTimeFrame overTime : lstFrame) {
			totalTime += overTime.getApplicationTime();
		}
		appContent += I18NText.getText("KAF018_276") + clockShorHm(totalTime);
		int countItem = 0;
		for (OverTimeFrame overFrame : lstFrame) {
			if (overFrame.getApplicationTime() != 0) {
				appContent += overFrame.getName() + " " + clockShorHm(overFrame.getApplicationTime());
				countItem++;
				if (countItem > 2) {
					break;
				}
			}
		}
		appContent +=  "\n" + applicaton_N.getAppReason();
		return appContent;
	}

	private String getAppGoBackInfo(String companyID, String appId) {
		String appContent = "";
		AppGoBackInfoFull appGoBackInfo = appDetailInfoRepo.getAppGoBackInfo(companyID, appId);
		appContent += I18NText.getText("KAF018_258") + I18NText.getText("KAF018_259");
		appContent += clockShorHm(appGoBackInfo.getGoWorkAtr1()) + appGoBackInfo.getWorkTimeStart1();
		appContent += I18NText.getText("KAF018_260");
		appContent += clockShorHm(appGoBackInfo.getBackHomeAtr1()) + appGoBackInfo.getWorkTimeEnd1();
		appContent += I18NText.getText("KAF018_259");
		appContent += clockShorHm(appGoBackInfo.getGoWorkAtr2()) + appGoBackInfo.getWorkTimeStart2();
		appContent += I18NText.getText("KAF018_260");
		appContent += clockShorHm(appGoBackInfo.getBackHomeAtr2()) + appGoBackInfo.getWorkTimeEnd2();
		return appContent;
	}

	private String getAppWorkChangeInfo(String companyID, String appId) {
		String appContent = "";
		AppWorkChangeFull appWorkChange = appDetailInfoRepo.getAppWorkChangeInfo(companyID, appId);
		appContent += I18NText.getText("KAF018_250");
		appContent += appWorkChange.getWorkTypeName() + appWorkChange.getWorkTimeName();
		if (!Objects.isNull(appWorkChange.getWorkTimeStart1()) && !Objects.isNull(appWorkChange.getWorkTimeEnd1())) {
			appContent += I18NText.getText("KAF018_252") + clockShorHm(appWorkChange.getGoWorkAtr1())
					+ appWorkChange.getWorkTimeStart1();
			appContent += I18NText.getText("KAF018_220");
			appContent += I18NText.getText("KAF018_252") + clockShorHm(appWorkChange.getBackHomeAtr1())
					+ appWorkChange.getWorkTimeEnd1();
		}
		if (!Objects.isNull(appWorkChange.getWorkTimeStart2()) && !Objects.isNull(appWorkChange.getWorkTimeEnd2())) {
			appContent += I18NText.getText("KAF018_252") + clockShorHm(appWorkChange.getGoWorkAtr2())
					+ appWorkChange.getWorkTimeStart2();
			appContent += I18NText.getText("KAF018_220");
			appContent += I18NText.getText("KAF018_252") + clockShorHm(appWorkChange.getBackHomeAtr2())
					+ appWorkChange.getWorkTimeEnd2();
		}
		if (!Objects.isNull(appWorkChange.getBreakTimeStart1()) && !Objects.isNull(appWorkChange.getBreakTimeEnd1())) {
			appContent += I18NText.getText("KAF018_251");
			appContent += appWorkChange.getBreakTimeStart1() + I18NText.getText("KAF018_220")
					+ appWorkChange.getBreakTimeEnd1();
		}
		return appContent;
	}

	private String getAppContentOverTime(String companyID, String appId) {
		String appContent = "";

		AppOverTimeInfoFull appOverTIme = appDetailInfoRepo.getAppOverTimeInfo(companyID, appId);
		appContent += I18NText.getText("KAF018_268");
		appContent += appOverTIme.getWorkClockFrom1();
		appContent += I18NText.getText("KAF018_220");
		appContent += appOverTIme.getWorkClockTo1();
		appContent += appOverTIme.getWorkClockFrom2() != null ? appOverTIme.getWorkClockFrom2() : "";
		appContent += I18NText.getText("KAF018_220");
		appContent += appOverTIme.getWorkClockTo2() != null ? appOverTIme.getWorkClockTo2() : "";
		//appContent += appOverTIme.getTotal();

		List<OverTimeFrame> lstFrame = appOverTIme.getLstFrame();
		Comparator<OverTimeFrame> sortList = Comparator.comparing(OverTimeFrame::getAttendanceType)
				.thenComparing(OverTimeFrame::getFrameNo);
		lstFrame.sort(sortList);
		int countItem = 0;
		int countRest = 0;
		for (OverTimeFrame overFrame : lstFrame) {
			if (overFrame.getApplicationTime() != 0) {
				appContent += overFrame.getName() + " " + clockShorHm(overFrame.getApplicationTime());
				appContent += I18NText.getText("KAF018_270") + appOverTIme.getOverTimeShiftNight();
				appContent += I18NText.getText("KAF018_271") + appOverTIme.getFlexExessTime();
				countItem++;
				if (countItem > 3) {
					countRest = lstFrame.size() - 3;
				}
			}
		}
		if (countRest > 0) {
			appContent += I18NText.getText("KAF018_231", String.valueOf(countRest));
		}
		return appContent;
	}

	private String getAbsenceApplication(Application_New applicaton_N, String companyID, String appId) {
		String appContent = "";
		AppAbsenceFull appabsence = appDetailInfoRepo.getAppAbsenceInfo(companyID, appId, 0);
		HolidayAppType holidayAppType = EnumAdaptor.valueOf(appabsence.getHolidayAppType(), HolidayAppType.class);
		String value = "";
		switch (holidayAppType) {
		case ABSENCE:
			value = "欠勤名称";
			break;
		case ANNUAL_PAID_LEAVE:
			value = "年休名称";
			break;
		case DIGESTION_TIME:
			value = " 時間消化名称";
			break;
		case HOLIDAY:
			value = "休日名称";
			break;
		case REST_TIME:
			value = "振休名称";
			break;
		case SUBSTITUTE_HOLIDAY:
			value = "代休名称";
			break;
		case SPECIAL_HOLIDAY:
			value = "";
			break;
		case YEARLY_RESERVE:
			value = "積立年休名称";
			break;
		default:
			break;
		}
		appContent = I18NText.getText("KAF018_279") + I18NText.getText("KAF018_248")
				+ I18NText.getText("CMM045_230", value) + "　" + "\n" + applicaton_N.getAppReason();
		return appContent;
	}

	private String clockShorHm(Integer minute) {
		return (minute / 60 + ":" + (minute % 60 < 10 ? "0" + minute % 60 : minute % 60));
	}
}
