package nts.uk.ctx.at.request.dom.application.lateleaveearly;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationApprovalService;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.EmploymentRootAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseStateImport_New;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.RegisterAtApproveReflectionInfoService_New;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.NewBeforeRegister_New;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.ConfirmMsgOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AchievementDetail;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AchievementEarly;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ActualContentDisplay;
import nts.uk.ctx.at.request.dom.application.common.service.setting.CommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoNoDateOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoWithDateOutput;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.ArrivedLateLeaveEarlyInfoOutput;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateEarlyDateChangeOutput;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrEarlyClassification;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrEarlyInfo;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationlatearrival.CancelAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationlatearrival.LateEarlyCancelAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationlatearrival.LateEarlyCancelAppSetRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author anhnm
 *
 */
@Stateless
public class LateLeaveEarlyServiceImp implements LateLeaveEarlyService {
	private final String DATE_FORMAT = "yyyy/MM/dd";

	@Inject
	private LateEarlyCancelAppSetRepository cancelAppSetRepository;

	@Inject
	private CommonAlgorithm common;

	@Inject
	private NewBeforeRegister_New newBeforeRegister;

	@Inject
	private RegisterAtApproveReflectionInfoService_New registerService;

	@Inject
	private LateLeaveEarlyRepository lateEarlyRepository;

	@Inject
	private ApplicationApprovalService applicationService;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * nts.uk.ctx.at.request.dom.application.lateleaveearly.LateLeaveEarlyService#
	 * getLateLeaveEarlyInfo(java.lang.String)
	 */
	@Override
	public ArrivedLateLeaveEarlyInfoOutput getLateLeaveEarlyInfo(int appId, List<String> appDates) {
		String companyId = AppContexts.user().companyId();

		ApplicationType applicationType = EnumAdaptor.valueOf(appId, ApplicationType.class);

		List<String> sIds = new ArrayList<String>();
		sIds.add(AppContexts.user().employeeId());
		List<GeneralDate> appDatesLst = new ArrayList<GeneralDate>();

		if(!appDates.isEmpty()) {
			for(String appDate : appDates) {
				appDatesLst.add(GeneralDate.fromString(appDate, this.DATE_FORMAT));
			}
		}

		// 起動時の申請表示情報を取得する
		AppDispInfoStartupOutput appDispInfoStartupOutput = common.getAppDispInfoStart(companyId, applicationType, sIds,
				appDatesLst, true, Optional.empty(), Optional.empty());

		// 遅刻早退取消初期（新規）
		ArrivedLateLeaveEarlyInfoOutput displayInfo = this.initCancelLateEarlyApp(companyId, sIds, appDatesLst,
				appDispInfoStartupOutput);

		return displayInfo;
	}

	/**
	 * 遅刻早退取消初期（新規）
	 *
	 * @param companyId
	 * @param employmentLst
	 * @param appDates
	 * @param appDisplayInfo
	 * @return
	 */
	private ArrivedLateLeaveEarlyInfoOutput initCancelLateEarlyApp(String companyId, List<String> employmentLst,
			List<GeneralDate> appDates, AppDispInfoStartupOutput appDisplayInfo) {
		// 遅刻早退取消申請起動時の表示情報
		ArrivedLateLeaveEarlyInfoOutput displayInfo = new ArrivedLateLeaveEarlyInfoOutput();

		Optional<GeneralDate> appDate;
		Optional<AchievementEarly> achieveEarly = Optional.empty();

		// ドメインモデル「遅刻早退取消申請設定」を取得する
		LateEarlyCancelAppSet listAppSet = this.cancelAppSetRepository.getByCId(companyId);

		if (!appDisplayInfo.getAppDispInfoWithDateOutput().getOpActualContentDisplayLst().isPresent()) {
			achieveEarly = Optional.empty();
		} else {
			Optional<List<ActualContentDisplay>> actualContentDisplay = appDisplayInfo.getAppDispInfoWithDateOutput()
					.getOpActualContentDisplayLst();

			if (actualContentDisplay.isPresent() && !actualContentDisplay.get().isEmpty()
					&& !actualContentDisplay.get().isEmpty()) {

				Optional<AchievementDetail> achieve = actualContentDisplay.get().get(0).getOpAchievementDetail();
				if (achieve.isPresent()) {
					achieveEarly = Optional
							.of(actualContentDisplay.get().get(0).getOpAchievementDetail().get().getAchievementEarly());
				}
			}
		}

		if (appDates.isEmpty()) {
			appDate = Optional.empty();
		} else {
			appDate = Optional.of(appDates.get(0));
		}

		// 遅刻早退実績のチェック処理
		Optional<String> info = this.checkLateEarlyResult(achieveEarly, appDate);

		Optional<List<ActualContentDisplay>> actualContentDisplay = appDisplayInfo
				.getAppDispInfoWithDateOutput().getOpActualContentDisplayLst();

		List<LateOrEarlyInfo> lateOrEarlyInfos = new ArrayList<>();
		if (actualContentDisplay.isPresent()) {
			// 取り消す初期情報
			if (!actualContentDisplay.get().isEmpty()) {
				lateOrEarlyInfos = this.initialInfo(listAppSet,
						actualContentDisplay.get().get(0).getOpAchievementDetail(),
						appDisplayInfo.getAppDispInfoNoDateOutput()
							.isManagementMultipleWorkCycles());
			} else {
				lateOrEarlyInfos = this.initialInfo(listAppSet, Optional.empty(),
						appDisplayInfo.getAppDispInfoNoDateOutput().isManagementMultipleWorkCycles());
			}
		}

		displayInfo.setAppDispInfoStartupOutput(appDisplayInfo);
		displayInfo.setLateEarlyCancelAppSet(listAppSet);
		displayInfo.setInfo(info);
		displayInfo.setEarlyInfos(lateOrEarlyInfos);

		return displayInfo;
	}

	/**
	 * 遅刻早退実績のチェック処理
	 *
	 * @param lateEarlyActualResults
	 *            Optional
	 * @param appDate
	 *            Optional
	 * @return info Optional<String>
	 */
	private Optional<String> checkLateEarlyResult(Optional<AchievementEarly> lateEarlyActualResults,
			Optional<GeneralDate> appDate) {
		Optional<String> errorMsg = Optional.empty();

		if (appDate.isPresent()) {
			if (!lateEarlyActualResults.isPresent()) {
				errorMsg = Optional.of("Msg_1707");
			} else {
				if (lateEarlyActualResults.get().getScheAttendanceTime1() == null
						&& lateEarlyActualResults.get().getScheAttendanceTime2() == null
						&& lateEarlyActualResults.get().getScheDepartureTime1() == null
						&& lateEarlyActualResults.get().getScheDepartureTime2() == null) {
					errorMsg = Optional.of("Msg_1707");
				}
			}
		}

		return errorMsg;
	}

	/**
	 * 取り消す初期情報
	 *
	 * @param listAppSet
	 * @param opAchievementDetail
	 * @param multipleTimes
	 */
	private List<LateOrEarlyInfo> initialInfo(LateEarlyCancelAppSet listAppSet,
			Optional<AchievementDetail> opAchievementDetail, boolean multipleTimes) {
		List<LateOrEarlyInfo> infoLst = new ArrayList<>();

		// INPUT.「遅刻早退取消申請設定」.取り消す設定をチェックする
		if (listAppSet.getCancelAtr().value == 0 || !opAchievementDetail.isPresent()) {
			return infoLst;
		}

		if (opAchievementDetail.get().getOpWorkTime().isPresent()) {
			// 出勤１のデータの状態のチェック
			LateOrEarlyInfo attend1 = this.checkDataStatus(
					Optional.of(opAchievementDetail.get().getAchievementEarly().getScheAttendanceTime1().rawHour()),
					Optional.of(opAchievementDetail.get().getOpWorkTime().get()), listAppSet.getCancelAtr(),
					new LateOrEarlyInfo(false, 1, false, false, LateOrEarlyClassification.LATE));

			// 「OUTPUT.取り消す初期情報＜List＞」に取得した「取り消す初期情報」を追加する （出勤１）
			infoLst.add(attend1);
		}

		if (opAchievementDetail.get().getOpLeaveTime().isPresent()) {
			// 退勤１データの状態のチェック
			LateOrEarlyInfo leave1 = this.checkDataStatus(
					Optional.of(opAchievementDetail.get().getAchievementEarly().getScheDepartureTime1().rawHour()),
					Optional.of(opAchievementDetail.get().getOpLeaveTime().get()), listAppSet.getCancelAtr(),
					new LateOrEarlyInfo(false, 1, false, false, LateOrEarlyClassification.EARLY));

			// 「OUTPUT.取り消す初期情報＜List＞」に取得した「取り消す初期情報」を追加する（退勤１）
			infoLst.add(leave1);
		}

		if (multipleTimes) {
			if (opAchievementDetail.get().getOpWorkTime2().isPresent()) {
				// 出勤２のデータの状態のチェック
				LateOrEarlyInfo attend2 = this.checkDataStatus(
						Optional.of(opAchievementDetail.get().getAchievementEarly().getScheAttendanceTime2().rawHour()),
						Optional.of(opAchievementDetail.get().getOpWorkTime2().get()), listAppSet.getCancelAtr(),
						new LateOrEarlyInfo(false, 2, false, false, LateOrEarlyClassification.LATE));

				// 「OUTPUT.取り消す初期情報＜List＞」に取得した「取り消す初期情報」を追加する （出勤２）
				infoLst.add(attend2);
			}

			if (opAchievementDetail.get().getOpDepartureTime2().isPresent()) {
				// 退勤２のデータの状態のチェック
				LateOrEarlyInfo leave2 = this.checkDataStatus(
						Optional.of(opAchievementDetail.get().getAchievementEarly().getScheDepartureTime2().rawHour()),
						Optional.of(opAchievementDetail.get().getOpDepartureTime2().get()), listAppSet.getCancelAtr(),
						new LateOrEarlyInfo(false, 2, false, false, LateOrEarlyClassification.EARLY));

				// 「OUTPUT.取り消す初期情報＜List＞」に取得した「取り消す初期情報」を追加する （退勤２）
				infoLst.add(leave2);
			}
		}

		return infoLst;
	}

	/**
	 * データの状態のチェック
	 *
	 * @param scheTime
	 * @param actualTime
	 * @param cancelAtr
	 * @param info
	 * @return
	 */
	private LateOrEarlyInfo checkDataStatus(Optional<Integer> scheTime, Optional<Integer> actualTime,
			CancelAtr cancelAtr, LateOrEarlyInfo info) {
		if (scheTime.isPresent() && actualTime.isPresent() && actualTime.get() > scheTime.get()
				&& cancelAtr.value == 2) {
			info.setIsCheck(true);
		}

		return info;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * nts.uk.ctx.at.request.dom.application.lateleaveearly.LateLeaveEarlyService#
	 * getChangeAppDate(int, java.util.List, java.lang.String)
	 */
	@Override
	public LateEarlyDateChangeOutput getChangeAppDate(int appType, List<String> appDates, String baseDate,
			AppDispInfoNoDateOutput appDispNoDate, AppDispInfoWithDateOutput appDispWithDate) {
		String companyId = AppContexts.user().companyId(); List<GeneralDate> dateLst
		= new ArrayList<>(); for(String date : appDates) {
		dateLst.add(GeneralDate.fromString(date, this.DATE_FORMAT)); }
		ApplicationType applicationType = EnumAdaptor.valueOf(appType,
		ApplicationType.class); Optional<AchievementEarly> lateEarlyActualResults =
		Optional.empty();

		// 申請日を変更する // not finish yet AppDispInfoWithDateOutput
		AppDispInfoStartupOutput appDispInfoWithDateOutput = this.common.changeAppDateProcess(companyId,
		dateLst, applicationType, appDispNoDate, appDispWithDate, Optional.empty());

		if (appDispWithDate.getOpActualContentDisplayLst().isPresent() ||
		appDispWithDate.getOpActualContentDisplayLst().get().get(0).
		getOpAchievementDetail().isPresent() ||
		appDispWithDate.getOpActualContentDisplayLst().get().get(0).
		getOpAchievementDetail().get() .getAchievementEarly() == null) {
		lateEarlyActualResults =
		Optional.of(appDispWithDate.getOpActualContentDisplayLst().get().get(0)
		.getOpAchievementDetail().get().getAchievementEarly()); }

		// 遅刻早退実績のチェック処理 this.checkLateEarlyResult(lateEarlyActualResults,
		Optional.of(GeneralDate.fromString(baseDate, DATE_FORMAT)));


		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * nts.uk.ctx.at.request.dom.application.lateleaveearly.LateLeaveEarlyService#
	 * getMessageList(int, java.util.List, java.lang.String,
	 * nts.uk.ctx.at.request.dom.application.common.service.setting.output.
	 * AppDispInfoNoDateOutput,
	 * nts.uk.ctx.at.request.dom.application.common.service.setting.output.
	 * AppDispInfoWithDateOutput)
	 */
	@Override
	public List<String> getMessageList(int appType, boolean agentAtr, boolean isNew,
			ArrivedLateLeaveEarlyInfoOutput infoOutput, Application application) {
		String companyID = AppContexts.user().companyId();

		List<ConfirmMsgOutput> listMsg = this.checkError(companyID, appType, agentAtr, isNew, infoOutput, application);
		List<String> listMsgStr = new ArrayList<>();

		for (ConfirmMsgOutput msgOutput : listMsg) {
			listMsgStr.add(msgOutput.getMsgID());
		}

		return listMsgStr;
	}

	/**
	 * 共通登録前のエラーチェック処理
	 *
	 * @param appType
	 *            int
	 * @param agentAtr
	 *            boolean
	 * @param isNew
	 *            boolean
	 * @param infoOutput
	 *            ArrivedLateLeaveEarlyInfoOutput
	 * @param application
	 *            Application
	 * @return listError List<String>
	 */
	private List<ConfirmMsgOutput> checkError(String companyID, int appType, boolean agentAtr, boolean isNew,
			ArrivedLateLeaveEarlyInfoOutput infoOutput, Application application) {
		List<ConfirmMsgOutput> listMsg;

		// 事前制約をチェックする (Kiểm tra các ràng buộc trước)
		if (isNew) {
			// 事前モード：
			// 「出勤時刻・退勤時刻・出勤時刻２・退勤時刻２」か１つは入力必須
			if (infoOutput.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getOpActualContentDisplayLst()
					.get().get(0).getOpAchievementDetail().get().getAchievementEarly().getScheAttendanceTime1() == null
					&& infoOutput.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput()
							.getOpActualContentDisplayLst().get().get(0).getOpAchievementDetail().get()
							.getAchievementEarly().getScheAttendanceTime2() == null
					&& infoOutput.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput()
							.getOpActualContentDisplayLst().get().get(0).getOpAchievementDetail().get()
							.getAchievementEarly().getScheDepartureTime1() == null
					&& infoOutput.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput()
							.getOpActualContentDisplayLst().get().get(0).getOpAchievementDetail().get()
							.getAchievementEarly().getScheDepartureTime2() == null) {
				throw new BusinessException("Msg_1681");
			}
		} else {
			// 事後モード：
			// 「4つ時刻・4つ取り消す」のいずれか１つは設定必須

		}

		// 出勤時刻 ＜ 退勤時刻
		// 出勤時刻2 ＜ 退勤時刻2
		// 退勤時刻 ＜ 出勤時刻2
		if (infoOutput.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getOpActualContentDisplayLst().get()
				.get(0).getOpAchievementDetail().get().getAchievementEarly().getScheAttendanceTime1()
				.rawHour() < infoOutput.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput()
						.getOpActualContentDisplayLst().get().get(0).getOpAchievementDetail().get()
						.getAchievementEarly().getScheDepartureTime1().rawHour()
				|| infoOutput.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput()
						.getOpActualContentDisplayLst().get().get(0).getOpAchievementDetail().get()
						.getAchievementEarly().getScheAttendanceTime2().rawHour() < infoOutput
								.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput()
								.getOpActualContentDisplayLst().get().get(0).getOpAchievementDetail().get()
								.getAchievementEarly().getScheDepartureTime2().rawHour()
				|| infoOutput.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput()
						.getOpActualContentDisplayLst().get().get(0).getOpAchievementDetail().get()
						.getAchievementEarly().getScheDepartureTime1().rawHour() < infoOutput
								.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput()
								.getOpActualContentDisplayLst().get().get(0).getOpAchievementDetail().get()
								.getAchievementEarly().getScheAttendanceTime2().rawHour()) {
			throw new BusinessException("Msg_1677");
		}

		if (isNew) {
			// 2-1.新規画面登録前の処理
			listMsg = this.newBeforeRegister.processBeforeRegister_New(companyID, EmploymentRootAtr.APPLICATION,
					agentAtr, application, null,
					infoOutput.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getOpErrorFlag().get(),
					null);
		} else {
			// 4-1.詳細画面登録前の処理
			listMsg = null;
		}

		return listMsg;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * nts.uk.ctx.at.request.dom.application.lateleaveearly.LateLeaveEarlyRepository
	 * #register(int, boolean, boolean,
	 * nts.uk.ctx.at.request.dom.application.lateorleaveearly.
	 * ArrivedLateLeaveEarlyInfoOutput,
	 * nts.uk.ctx.at.request.dom.application.Application)
	 */
	@Override
	public void register(int appType, ArrivedLateLeaveEarlyInfoOutput infoOutput, Application application) {
		String employeeId = AppContexts.user().employeeId();

		// 2-2.新規画面登録時承認反映情報の整理
		this.registerService.newScreenRegisterAtApproveInfoReflect(employeeId, application);

		// ドメインモデル「遅刻早退取消申請」の新規登録する (đăng ký mới domain 「遅刻早退取消申請」)
		this.registerDomain(application, infoOutput);
	}

	/**
	 * ドメインモデル「遅刻早退取消申請」の新規登録する (đăng ký mới domain 「遅刻早退取消申請」)
	 *
	 * @param application
	 * @param infoOutput
	 */
	public void registerDomain(Application application, ArrivedLateLeaveEarlyInfoOutput infoOutput) {
		String cID = AppContexts.user().companyId();
		lateEarlyRepository.registerLateLeaveEarly(cID, application, infoOutput);
		this.registerApplication(application, infoOutput);
	}

	/**
	 * @param cID
	 * @param application
	 * @param infoOutput
	 */
	public void registerApplication(Application application, ArrivedLateLeaveEarlyInfoOutput infoOutput) {
		Optional<List<ApprovalPhaseStateImport_New>> opListApproval = infoOutput.getAppDispInfoStartupOutput()
				.getAppDispInfoWithDateOutput().getOpListApprovalPhaseState();

		this.applicationService.insertApp(application, opListApproval.get());
	}
}
