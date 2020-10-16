package nts.uk.ctx.at.request.dom.application.lateleaveearly;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationApprovalService;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.EmploymentRootAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseStateImport_New;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.DetailAfterUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.DetailBeforeUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.init.DetailAppCommonSetService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.RegisterAtApproveReflectionInfoService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.after.NewAfterRegister;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.NewBeforeRegister;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.ConfirmMsgOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AchievementDetail;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AchievementEarly;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ActualContentDisplay;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.common.service.setting.CommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoNoDateOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoWithDateOutput;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.ArrivedLateLeaveEarlyInfoOutput;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateCancelation;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateEarlyDateChangeOutput;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrEarlyAtr;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrEarlyInfo;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.TimeReport;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationlatearrival.CancelAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationlatearrival.LateEarlyCancelAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationlatearrival.LateEarlyCancelAppSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.AppTypeSetting;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.TimeWithDayAttr;

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
	private NewBeforeRegister newBeforeRegister;

	@Inject
	private RegisterAtApproveReflectionInfoService registerService;

	@Inject
	private ArrivedLateLeaveEarlyRepository lateEarlyRepository;

	@Inject
	private ApplicationApprovalService applicationService;

	@Inject
	private NewAfterRegister newAfterRegister;

	@Inject
	private DetailBeforeUpdate updateService;

	@Inject
	private DetailAfterUpdate afterUpdateService;

	@Inject
	private DetailAppCommonSetService detailAppCommonSetService;

	@Inject
	private ApplicationRepository applicationRepository;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * nts.uk.ctx.at.request.dom.application.lateleaveearly.LateLeaveEarlyService#
	 * getLateLeaveEarlyInfo(java.lang.String)
	 */
	@Override
	public ArrivedLateLeaveEarlyInfoOutput getLateLeaveEarlyInfo(int appId, List<String> appDates,
			AppDispInfoStartupOutput appDispInfoStartupOutput) {
		String companyId = AppContexts.user().companyId();

		// ApplicationType applicationType = EnumAdaptor.valueOf(appId,
		// ApplicationType.class);

		List<String> sIds = new ArrayList<String>();
		sIds.add(AppContexts.user().employeeId());
		List<GeneralDate> appDatesLst = new ArrayList<GeneralDate>();

		if (!appDates.isEmpty()) {
			for (String appDate : appDates) {
				appDatesLst.add(GeneralDate.fromString(appDate, this.DATE_FORMAT));
			}
		}

		// // 起動時の申請表示情報を取得する
		// AppDispInfoStartupOutput appDispInfoStartupOutput =
		// common.getAppDispInfoStart(companyId, applicationType, sIds,
		// appDatesLst, true, Optional.empty(), Optional.empty());

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

		Optional<List<ActualContentDisplay>> actualContentDisplay = appDisplayInfo.getAppDispInfoWithDateOutput()
				.getOpActualContentDisplayLst();

		// // AnhNM Mock data: START
		// AchievementEarly mockAchiveEarly = new AchievementEarly(new
		// TimeWithDayAttr(510), new TimeWithDayAttr(990),
		// new TimeWithDayAttr(900), new TimeWithDayAttr(1320));
		//// AchievementDetail mockAchive = new AchievementDetail(null, null, null,
		// null, null, null, mockAchiveEarly,
		//// Optional.of(1320), Optional.empty(), Optional.empty(), Optional.of(510),
		// Optional.of(900),
		//// Optional.empty(), Optional.of(960), Optional.empty(), Optional.empty(),
		// Optional.empty(),
		//// Optional.empty(), Optional.empty());
		// AchievementDetail mockAchive = null;
		// ActualContentDisplay mockDisplay = new
		// ActualContentDisplay(GeneralDate.fromString("2020/09/03", DATE_FORMAT),
		// Optional.of(mockAchive));
		// // AnhNM Mock data: END

		List<LateOrEarlyInfo> lateOrEarlyInfos = new ArrayList<>();
		if (actualContentDisplay.isPresent()) {
			// 取り消す初期情報
			if (!actualContentDisplay.get().isEmpty()) {
				lateOrEarlyInfos = this.initialInfo(listAppSet,
						actualContentDisplay.get().get(0).getOpAchievementDetail(),
						appDisplayInfo.getAppDispInfoNoDateOutput().isManagementMultipleWorkCycles());
			} else {
				lateOrEarlyInfos = this.initialInfo(listAppSet, Optional.empty(),
						appDisplayInfo.getAppDispInfoNoDateOutput().isManagementMultipleWorkCycles());
			}
		}

		// // AnhNM Mock data: START
		// lateOrEarlyInfos = this.initialInfo(listAppSet, Optional.of(mockAchive),
		// appDisplayInfo.getAppDispInfoNoDateOutput().isManagementMultipleWorkCycles());
		// // AnhNM Mock data: END

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
						&& !lateEarlyActualResults.get().getScheAttendanceTime2().isPresent()
						&& lateEarlyActualResults.get().getScheDepartureTime1() == null
						&& !lateEarlyActualResults.get().getScheDepartureTime2().isPresent()) {
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
					Optional.ofNullable(opAchievementDetail.get().getAchievementEarly().getScheAttendanceTime1()),
					Optional.of(opAchievementDetail.get().getOpWorkTime().get()), listAppSet.getCancelAtr(),
					new LateOrEarlyInfo(false, 1, false, true, LateOrEarlyAtr.LATE));

			// 「OUTPUT.取り消す初期情報＜List＞」に取得した「取り消す初期情報」を追加する （出勤１）
			infoLst.add(attend1);
		}

		if (opAchievementDetail.get().getOpLeaveTime().isPresent()) {
			// 退勤１データの状態のチェック
			LateOrEarlyInfo leave1 = this.checkDataStatus(
					Optional.ofNullable(opAchievementDetail.get().getAchievementEarly().getScheDepartureTime1()),
					Optional.of(opAchievementDetail.get().getOpLeaveTime().get()), listAppSet.getCancelAtr(),
					new LateOrEarlyInfo(false, 1, false, true, LateOrEarlyAtr.EARLY));

			// 「OUTPUT.取り消す初期情報＜List＞」に取得した「取り消す初期情報」を追加する（退勤１）
			infoLst.add(leave1);
		}

		if (multipleTimes) {
			if (opAchievementDetail.get().getOpWorkTime2().isPresent()) {
				// 出勤２のデータの状態のチェック
				LateOrEarlyInfo attend2 = this.checkDataStatus(
						opAchievementDetail.get().getAchievementEarly().getScheAttendanceTime2(),
						Optional.of(opAchievementDetail.get().getOpWorkTime2().get()), listAppSet.getCancelAtr(),
						new LateOrEarlyInfo(false, 2, false, true, LateOrEarlyAtr.LATE));

				// 「OUTPUT.取り消す初期情報＜List＞」に取得した「取り消す初期情報」を追加する （出勤２）
				infoLst.add(attend2);
			}

			if (opAchievementDetail.get().getOpDepartureTime2().isPresent()) {
				// 退勤２のデータの状態のチェック
				LateOrEarlyInfo leave2 = this.checkDataStatus(
						opAchievementDetail.get().getAchievementEarly().getScheDepartureTime2(),
						Optional.ofNullable(opAchievementDetail.get().getOpDepartureTime2().get()), listAppSet.getCancelAtr(),
						new LateOrEarlyInfo(false, 2, false, true, LateOrEarlyAtr.EARLY));

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
	private LateOrEarlyInfo checkDataStatus(Optional<TimeWithDayAttr> scheTime, Optional<Integer> actualTime,
			CancelAtr cancelAtr, LateOrEarlyInfo info) {
		if (scheTime.isPresent() && actualTime.isPresent() && actualTime.get() > scheTime.get().rawHour()) {
			info.setIsActive(true);
		}
		if (cancelAtr.value == 2) {
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
			AppDispInfoNoDateOutput appDispNoDate, AppDispInfoWithDateOutput appDispWithDate,
			LateEarlyCancelAppSet listAppSet) {
		String companyId = AppContexts.user().companyId();
		List<GeneralDate> dateLst = new ArrayList<>();
		List<LateOrEarlyInfo> earlyInfos = new ArrayList<>();

		for (String date : appDates) {
			dateLst.add(GeneralDate.fromString(date, this.DATE_FORMAT));
		}
		ApplicationType applicationType = EnumAdaptor.valueOf(appType, ApplicationType.class);
		Optional<AchievementEarly> lateEarlyActualResults = Optional.empty();

		// 申請日を変更する
		AppDispInfoWithDateOutput appDispInfoWithDateOutput = this.common.changeAppDateProcess(companyId, dateLst,
				applicationType, appDispNoDate, appDispWithDate, Optional.empty());

		if (appDispInfoWithDateOutput.getOpActualContentDisplayLst().isPresent()
				&& appDispInfoWithDateOutput.getOpActualContentDisplayLst().get().get(0).getOpAchievementDetail()
						.isPresent()
				&& appDispInfoWithDateOutput.getOpActualContentDisplayLst().get().get(0).getOpAchievementDetail().get()
						.getAchievementEarly() != null) {
			lateEarlyActualResults = Optional.of(appDispInfoWithDateOutput.getOpActualContentDisplayLst().get().get(0)
					.getOpAchievementDetail().get().getAchievementEarly());
		}

		Optional<GeneralDate> appDate = Optional.empty();
		if (!appDates.isEmpty()) {
			appDate = Optional.of(GeneralDate.fromString(appDates.get(0), DATE_FORMAT));
		}

		// 遅刻早退実績のチェック処理
		Optional<String> errorInfo = this.checkLateEarlyResult(lateEarlyActualResults, appDate);

		// エラー情報がない AND 申請対象日 ≠ Empty(ko có "errorInfo" AND appDate ≠ Empty
		if (!errorInfo.isPresent() && baseDate != null) {
			Optional<AchievementDetail> opAchieve = Optional.empty();

			if (appDispInfoWithDateOutput.getOpActualContentDisplayLst().isPresent()
					&& appDispInfoWithDateOutput.getOpActualContentDisplayLst().get().size() > 0) {
				opAchieve = appDispInfoWithDateOutput.getOpActualContentDisplayLst().get().get(0)
						.getOpAchievementDetail();
			}

			earlyInfos = this.initialInfo(listAppSet, opAchieve, appDispNoDate.isManagementMultipleWorkCycles());
		}

		LateEarlyDateChangeOutput output = new LateEarlyDateChangeOutput(appDispInfoWithDateOutput, earlyInfos);
		output.setErrorInfo(errorInfo);

		return output;
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

		return listMsg.stream().map(item -> item.getMsgID()).collect(Collectors.toList());
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
		List<ConfirmMsgOutput> listMsg = new ArrayList<>();
		List<TimeReport> timeReportsTemp;
		List<LateCancelation> cancelTemp;
		Integer attendTime = null;
		Integer leaveTime = null;
		Integer attendTime2 = null;
		Integer leaveTime2 = null;
		LateCancelation cancelAttend = null;
		LateCancelation cancelLeave = null;
		LateCancelation cancelAttend2 = null;
		LateCancelation cancelLeave2 = null;



			// Get attend time by workno = 1 && classification = 0
			timeReportsTemp = infoOutput.getArrivedLateLeaveEarly().get().getLateOrLeaveEarlies().stream().map(x -> {
				if (x.getWorkNo() == 1 && x.getLateOrEarlyClassification().value == 0) {
					return x;
				}
				return null;
			}).filter(m -> (m != null)).collect(Collectors.toList());

		attendTime = timeReportsTemp.isEmpty() ? null : timeReportsTemp.get(0).getTimeWithDayAttr().v();

			// Get leave time by workno = 1 && classification = 1
			timeReportsTemp = infoOutput.getArrivedLateLeaveEarly().get().getLateOrLeaveEarlies().stream().map(x -> {
				if (x.getWorkNo() == 1 && x.getLateOrEarlyClassification().value == 1) {
					return x;
				}
				return null;
			}).filter(m -> (m != null)).collect(Collectors.toList());

		leaveTime = timeReportsTemp.isEmpty() ? null : timeReportsTemp.get(0).getTimeWithDayAttr().v();

			// Get attend time 2 by workno = 2 && classification = 0
			timeReportsTemp = infoOutput.getArrivedLateLeaveEarly().get().getLateOrLeaveEarlies().stream().map(x -> {
				if (x.getWorkNo() == 2 && x.getLateOrEarlyClassification().value == 0) {
					return x;
				}
				return null;
			}).filter(m -> (m != null)).collect(Collectors.toList());

		attendTime2 = timeReportsTemp.isEmpty() ? null : timeReportsTemp.get(0).getTimeWithDayAttr().v();

			// Get attend time 2 by workno = 2 && classification = 1
			timeReportsTemp = infoOutput.getArrivedLateLeaveEarly().get().getLateOrLeaveEarlies().stream().map(x -> {
			if (x.getWorkNo() == 2 && x.getLateOrEarlyClassification().value == 1) {
					return x;
				}
				return null;
			}).filter(m -> (m != null)).collect(Collectors.toList());

		leaveTime2 = timeReportsTemp.isEmpty() ? null : timeReportsTemp.get(0).getTimeWithDayAttr().v();

			// Get attend time by workno = 1 && classification = 0
			cancelTemp = infoOutput.getArrivedLateLeaveEarly().get().getLateCancelation().stream().map(x -> {
				if (x.getWorkNo() == 1 && x.getLateOrEarlyClassification().value == 0) {
					return x;
				}
				return null;
			}).filter(m -> (m != null)).collect(Collectors.toList());

			cancelAttend = cancelTemp.isEmpty() ? null : cancelTemp.get(0);

			// Get attend time by workno = 1 && classification = 1
			cancelTemp = infoOutput.getArrivedLateLeaveEarly().get().getLateCancelation().stream().map(x -> {
				if (x.getWorkNo() == 1 && x.getLateOrEarlyClassification().value == 1) {
					return x;
				}
				return null;
			}).filter(m -> (m != null)).collect(Collectors.toList());

			cancelLeave = cancelTemp.isEmpty() ? null : cancelTemp.get(0);

			// Get attend time by workno = 2 && classification = 0
			cancelTemp = infoOutput.getArrivedLateLeaveEarly().get().getLateCancelation().stream().map(x -> {
				if (x.getWorkNo() == 2 && x.getLateOrEarlyClassification().value == 0) {
					return x;
				}
				return null;
			}).filter(m -> (m != null)).collect(Collectors.toList());

			cancelAttend2 = cancelTemp.isEmpty() ? null : cancelTemp.get(0);

			// Get attend time by workno = 2 && classification = 1
			cancelTemp = infoOutput.getArrivedLateLeaveEarly().get().getLateCancelation().stream().map(x -> {
				if (x.getWorkNo() == 2 && x.getLateOrEarlyClassification().value == 1) {
					return x;
				}
				return null;
			}).filter(m -> (m != null)).collect(Collectors.toList());

			cancelLeave = cancelTemp.isEmpty() ? null : cancelTemp.get(0);

		if (attendTime != null && leaveTime != null && attendTime >= leaveTime) {
			throw new BusinessException("Msg_1677");
		}

		if (leaveTime != null && attendTime2 != null && leaveTime >= attendTime2) {
			throw new BusinessException("Msg_1677");
		}

		if (attendTime2 != null && leaveTime2 != null && attendTime2 >= leaveTime2) {
				throw new BusinessException("Msg_1677");
			}

		if (attendTime != null && leaveTime2 != null && attendTime >= leaveTime2) {
			throw new BusinessException("Msg_1677");
		}

		if (leaveTime != null && leaveTime2 != null && leaveTime >= leaveTime2) {
			throw new BusinessException("Msg_1677");
		}

		if (attendTime != null && attendTime2 != null && attendTime >= attendTime2) {
			throw new BusinessException("Msg_1677");
		}

		if (application.getPrePostAtr().value == 0) {
			if (attendTime == null && attendTime2 == null && leaveTime == null && leaveTime2 == null) {
				throw new BusinessException("Msg_1681");
			}
		} else {
			if (attendTime == null && attendTime2 == null && leaveTime == null && leaveTime2 == null
					&& cancelAttend == null
					&& cancelAttend2 == null && cancelLeave == null && cancelLeave2 == null) {
				throw new BusinessException("Msg_1681");
			}
			}


		if (isNew) {
			// 2-1.新規画面登録前の処理
			listMsg = this.newBeforeRegister.processBeforeRegister_New(companyID, EmploymentRootAtr.APPLICATION,
					agentAtr, application, null,
					infoOutput.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getOpErrorFlag().get(),
					null,
					infoOutput.getAppDispInfoStartupOutput());
		} else {
			// 4-1.詳細画面登録前の処理
			this.updateService.processBeforeDetailScreenRegistration(companyID, application.getEmployeeID(),
					application.getAppDate().getApplicationDate(), EmploymentRootAtr.APPLICATION.value,
					application.getAppID(), application.getPrePostAtr(), application.getVersion(), null, null,
					infoOutput.getAppDispInfoStartupOutput());
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
	public ProcessResult register(int appType, ArrivedLateLeaveEarlyInfoOutput infoOutput, Application application) {
		String employeeId = AppContexts.user().employeeId();
		ProcessResult processResult = new ProcessResult();
		processResult.setProcessDone(true);
		processResult.setAppID(application.getAppID());

		// ドメインモデル「遅刻早退取消申請」の新規登録する (đăng ký mới domain 「遅刻早退取消申請」)
		this.registerDomain(application, infoOutput);


		// 2-2.新規画面登録時承認反映情報の整理
		this.registerService.newScreenRegisterAtApproveInfoReflect(employeeId, application);

		// TODO: 申請設定 domain has changed!
		AppTypeSetting appTypeSetting = infoOutput.getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput().getApplicationSetting()
				.getAppTypeSettings().stream().filter(x -> x.getAppType()==application.getAppType()).findAny().orElse(null);
		if (appTypeSetting.isSendMailWhenRegister()) {
			// 「新規登録時に自動でメールを送信する」がする(chọn auto send mail 「新規登録時に自動でメールを送信する」)
			// TODO: 申請設定 domain has changed!
			processResult = this.newAfterRegister.processAfterRegister(application.getAppID(),
					appTypeSetting,
					infoOutput.getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput().isMailServerSet());
		}

		return processResult;
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

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * nts.uk.ctx.at.request.dom.application.lateleaveearly.LateLeaveEarlyService#
	 * getInitB(java.lang.String)
	 */
	@Override
	public ArrivedLateLeaveEarlyInfoOutput getInitB(String appId, AppDispInfoStartupOutput infoStartupOutput) {

		String companyId = AppContexts.user().companyId();

		// 14-1.詳細画面起動前申請共通設定を取得する
		AppDispInfoStartupOutput appDispInfoStartupOutput = this.detailAppCommonSetService
				.getCommonSetBeforeDetail(companyId, appId);

		// 遅刻早退取消初期（詳細）
		ArrivedLateLeaveEarlyInfoOutput info = this.initLateEarlyDetail(companyId, appId, appDispInfoStartupOutput);

		return info;
	}

	/**
	 * 遅刻早退取消初期（詳細）
	 *
	 * @param companyId
	 * @param appid
	 * @param infoStartupOutput
	 * @return
	 */
	private ArrivedLateLeaveEarlyInfoOutput initLateEarlyDetail(String companyId, String appid,
			AppDispInfoStartupOutput infoStartupOutput) {
		ArrivedLateLeaveEarlyInfoOutput output = new ArrivedLateLeaveEarlyInfoOutput();
		Application application = infoStartupOutput.getAppDetailScreenInfo().get().getApplication();

		// ドメインモデル「遅刻早退取消申請」を取得する
		// (Lấy domain 「遅刻早退取消申請」)
		ArrivedLateLeaveEarly arrivedLateLeaveEarly = this.lateEarlyRepository.getLateEarlyApp(companyId, appid,
				application);

		// ドメインモデル「遅刻早退取消申請設定」を取得する
		// (Lấy domain 「遅刻早退取消申請設定」)
		LateEarlyCancelAppSet lateEarlyCancelAppSet = this.cancelAppSetRepository.getByCId(companyId);

		Optional<AchievementDetail> opAchieve = Optional.empty();

		if (infoStartupOutput.getAppDispInfoWithDateOutput().getOpActualContentDisplayLst().isPresent()
				&& infoStartupOutput.getAppDispInfoWithDateOutput().getOpActualContentDisplayLst().get().size() > 0) {
			opAchieve = infoStartupOutput.getAppDispInfoWithDateOutput().getOpActualContentDisplayLst().get().get(0)
					.getOpAchievementDetail();
		}

		// // AnhNM Mock data: START
		// AchievementEarly mockAchiveEarly = new AchievementEarly(new
		// TimeWithDayAttr(510), new TimeWithDayAttr(990),
		// new TimeWithDayAttr(900), new TimeWithDayAttr(1320));
		//// AchievementDetail mockAchive = new AchievementDetail(null, null, null,
		// null, null, null, mockAchiveEarly,
		//// Optional.of(1320), Optional.empty(), Optional.empty(), Optional.of(510),
		// Optional.of(900),
		//// Optional.empty(), Optional.of(960), Optional.empty(), Optional.empty(),
		// Optional.empty(),
		//// Optional.empty(), Optional.empty());
		// AchievementDetail mockAchive = null;
		// ActualContentDisplay mockDisplay = new
		// ActualContentDisplay(GeneralDate.fromString("2020/09/03", DATE_FORMAT),
		// Optional.of(mockAchive));
		// // AnhNM Mock data: END

		// 取り消す初期情報
		List<LateOrEarlyInfo> listInfo = this.initialInfo(lateEarlyCancelAppSet, opAchieve,
				infoStartupOutput.getAppDispInfoNoDateOutput().isManagementMultipleWorkCycles());

		// // AnhNM Mock data: START
		// listInfo = this.initialInfo(lateEarlyCancelAppSet, Optional.of(mockAchive),
		// infoStartupOutput.getAppDispInfoNoDateOutput().isManagementMultipleWorkCycles());
		// // AnhNM Mock data: END

		output.setEarlyInfos(listInfo);
		output.setArrivedLateLeaveEarly(Optional.of(arrivedLateLeaveEarly));
		output.setLateEarlyCancelAppSet(lateEarlyCancelAppSet);
		output.setAppDispInfoStartupOutput(infoStartupOutput);

		return output;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * nts.uk.ctx.at.request.dom.application.lateleaveearly.LateLeaveEarlyService#
	 * update(java.lang.String, nts.uk.ctx.at.request.dom.application.Application,
	 * nts.uk.ctx.at.request.dom.application.lateorleaveearly.ArrivedLateLeaveEarly)
	 */
	@Override
	public ProcessResult update(String companyId, Application application,
			ArrivedLateLeaveEarly arrivedLateLeaveEarly, AppDispInfoStartupOutput infoStartupOutput) {
		this.updateDomain(application, arrivedLateLeaveEarly);

		// 4-2.詳細画面登録後の処理
		ProcessResult result = this.afterUpdateService.processAfterDetailScreenRegistration(companyId,
				application.getAppID(), infoStartupOutput);
		return result;
	}

	/**
	 * ドメインモデル「遅刻早退取消申請」の更新する (Update domain 「遅刻早退取消申請」)
	 *
	 * @param application
	 * @param arrivedLateLeaveEarly
	 */
	private void updateDomain(Application application, ArrivedLateLeaveEarly arrivedLateLeaveEarly) {
		String companyId = AppContexts.user().companyId();

		this.lateEarlyRepository.updateLateLeaveEarly(companyId, application, arrivedLateLeaveEarly);

		this.applicationRepository.update(application);
	}
}
