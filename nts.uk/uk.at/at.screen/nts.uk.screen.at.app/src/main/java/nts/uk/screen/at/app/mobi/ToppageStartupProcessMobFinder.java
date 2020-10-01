/**
 * 
 */
package nts.uk.screen.at.app.mobi;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.management.RuntimeErrorException;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.auth.app.find.employmentrole.InitDisplayPeriodSwitchSetFinder;
import nts.uk.ctx.at.auth.app.find.employmentrole.dto.InitDisplayPeriodSwitchSetDto;
import nts.uk.ctx.at.function.dom.adapter.widgetKtg.AnnualLeaveRemainingNumberImport;
import nts.uk.ctx.at.function.dom.adapter.widgetKtg.KTGRsvLeaveInfoImport;
import nts.uk.ctx.at.function.dom.adapter.widgetKtg.NextAnnualLeaveGrantImport;
import nts.uk.ctx.at.function.dom.adapter.widgetKtg.NumAnnLeaReferenceDateImport;
import nts.uk.ctx.at.function.dom.adapter.widgetKtg.OptionalWidgetAdapter;
import nts.uk.ctx.at.function.dom.employmentfunction.checksdailyerror.ChecksDailyPerformanceErrorRepository;
import nts.uk.ctx.at.record.dom.monthly.agreement.export.GetAgreementTimeOfMngPeriod;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementOperationSettingRepository;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.ReflectedState;
import nts.uk.ctx.at.shared.app.query.workrule.closure.ClosureResultModel;
import nts.uk.ctx.at.shared.app.query.workrule.closure.WorkClosureQueryProcessor;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employment.BsEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsenceReruitmentMngInPeriodQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffMngInPeriodQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.ComplileInPeriodOfSpecialLeaveParam;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.InPeriodOfSpecialLeave;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.SpecialLeaveGrantDetails;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.SpecialLeaveManagementService;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.setting.AgreementOperationSetting;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHoliday;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.ctx.at.shared.pub.workrule.closure.PresentClosingPeriodExport;
import nts.uk.ctx.at.shared.pub.workrule.closure.ShClosurePub;
import nts.uk.ctx.sys.portal.dom.smartphonetoppageset.NotificationDetailSet;
import nts.uk.ctx.sys.portal.dom.smartphonetoppageset.NotificationDisplayItem;
import nts.uk.ctx.sys.portal.dom.smartphonetoppageset.NotificationType;
import nts.uk.ctx.sys.portal.dom.smartphonetoppageset.SPTopPageSet;
import nts.uk.ctx.sys.portal.dom.smartphonetoppageset.SPTopPageSetRepository;
import nts.uk.ctx.sys.portal.dom.smartphonetoppageset.TimeStatusDetailsSet;
import nts.uk.ctx.sys.portal.dom.smartphonetoppageset.TimeStatusDisplayItem;
import nts.uk.ctx.sys.portal.dom.smartphonetoppageset.TimeStatusType;
import nts.uk.ctx.sys.portal.dom.smartphonetoppageset.Type;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalRootStateRepository;
import nts.uk.screen.at.app.ktgwidget.find.dto.AgreementTimeList36;
import nts.uk.screen.at.app.ktgwidget.find.dto.AgreementTimeOfMonthlyDto;
import nts.uk.screen.at.app.ktgwidget.find.dto.DatePeriodDto;
import nts.uk.screen.at.app.ktgwidget.find.dto.OptionalWidgetInfoMobileDto;
import nts.uk.screen.at.app.ktgwidget.find.dto.OvertimeHours;
import nts.uk.screen.at.app.ktgwidget.find.dto.OvertimeHoursDto;
import nts.uk.screen.at.app.ktgwidget.find.dto.RemainingNumber;
import nts.uk.screen.at.app.ktgwidget.find.dto.TimeOT;
import nts.uk.screen.at.app.ktgwidget.find.dto.YearlyHoliday;
import nts.uk.screen.at.app.ktgwidget.find.dto.YearlyHolidayInfo;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author hieult
 *
 */
@Stateless
public class ToppageStartupProcessMobFinder {
	@Inject
	private SPTopPageSetRepository sPTopPageSetRepository;
	@Inject
	private ShareEmploymentAdapter shareEmploymentAdapter;
//	@Inject
//	private KTG002QueryProcessor kTG002QueryProcessor;
//	@Inject
//	private OptionalWidgetKtgFinder ktg029;
	@Inject
	private ChecksDailyPerformanceErrorRepository checksDailyPerformanceErrorRepo;
//	@Inject
//	private KTG027QueryProcessor kTG027QueryProcessor;
	@Inject
	private OptionalWidgetAdapter optionalWidgetAdapter;
	@Inject
	private SpecialHolidayRepository specialHolidayRepository;
	@Inject
	private ClosureRepository closureRepo;
	@Inject
	private ClosureEmploymentRepository closureEmploymentRepo;
	@Inject
	private EmpEmployeeAdapter empEmployeeAdapter;
	@Inject
	private ShClosurePub shClosurePub;
	@Inject
	private WorkClosureQueryProcessor workClosureQueryProcessor;
//	@Inject
//	private DailyPerformanceAdapter dailyPerformanceAdapter;
	@Inject
	private ApprovalRootStateRepository approvalRootStateRepository;
	@Inject
	private ApplicationRepository applicationRepository;
	@Inject
	private AgreementOperationSettingRepository agreementOperationSettingRepository;
	@Inject
	private InitDisplayPeriodSwitchSetFinder displayPeriodfinder;
	
	@Inject
	private RecordDomRequireService requireService;

	public ToppageStartupDto startupProcessMob() {
		String companyID = AppContexts.user().companyId();
		ToppageStartupDto toppageStartupDto = new ToppageStartupDto();
		// アルゴリズム「会社で表示するトップページ種別を取得する」を実行する
		// Thực hiện thuật toán "Lấy type top page hiển thị trong công ty"
		List<SPTopPageSet> listSPTopPageSet = sPTopPageSetRepository.findAll(companyID);

		for (SPTopPageSet spTopPageSet : listSPTopPageSet) {

			if (spTopPageSet.getSmartPhoneTopPageType().getType() == Type.NOTIFICATION) {
				toppageStartupDto.displayNotifiDto = new DisplayNotifiDto(false, false,
						spTopPageSet.getDisplayAtr() == NotUseAtr.USE);
			}
			if (spTopPageSet.getSmartPhoneTopPageType().getType() == Type.TIME_STATUS) {
				// -----------------------Start Date ???? End Date
				// ?????----------------------------------------------------
				toppageStartupDto.ktg029 = new ToppageOptionalWidgetInfoDto(null,
						spTopPageSet.getDisplayAtr() == NotUseAtr.USE);

			}
			if (spTopPageSet.getSmartPhoneTopPageType().getType() == Type.OVERTIME_WORK) {
				toppageStartupDto.overtimeHoursDto = new ToppageOvertimeHoursDto(null,
						spTopPageSet.getDisplayAtr() == NotUseAtr.USE);
			}
		}
		Closure closure = ClosureService.getClosureDataByEmployee(
				ClosureService.createRequireM3(closureRepo, closureEmploymentRepo, shareEmploymentAdapter),
				new CacheCarrier(),
				AppContexts.user().employeeId(), GeneralDate.today());
		toppageStartupDto.closureID = closure.getClosureId().value;
		toppageStartupDto.closureYearMonth = closure.getClosureMonth().getProcessingYm().v();
		
		return toppageStartupDto;

	}

	public DisplayNotifiDto getDisplayNotif() {
		String companyID = AppContexts.user().companyId();
		DisplayNotifiDto result = displayNoti();

		SPTopPageSet setting = sPTopPageSetRepository.getTopPageSetByCompanyAndType(companyID, Type.NOTIFICATION.value);
		if (setting != null && setting.getSmartPhoneTopPageType().getType() == Type.NOTIFICATION) {
			result.setVisible(setting.getDisplayAtr() == NotUseAtr.USE);
		}
		return result;
	}

	public ToppageOptionalWidgetInfoDto getKTG029() {
		String companyID = AppContexts.user().companyId();
		// DatePeriodDto datePeriod = getCurrentMonth();
		/*
		 * GeneralDate start = datePeriod.getStrCurrentMonth(); GeneralDate end
		 * = datePeriod.getEndCurrentMonth().addMonths(1);
		 */
		boolean view = false;
		String companyId = AppContexts.user().companyId();
		DateRangeDto dateRange = getDateRangeByClsId(companyId);
		OptionalWidgetInfoMobileDto result = getKTG029(dateRange.getStart(), dateRange.getEnd());
		SPTopPageSet setting = sPTopPageSetRepository.getTopPageSetByCompanyAndType(companyID, Type.TIME_STATUS.value);

		if (setting != null && setting.getSmartPhoneTopPageType().getType() == Type.TIME_STATUS) {
			view = setting.getDisplayAtr() == NotUseAtr.USE;
		}

		return new ToppageOptionalWidgetInfoDto(result, view);
	}

	private DateRangeDto getDateRangeByClsId(String companyId) {
		String employmentCode = this.getEmploymentCode();
		Integer closureId = this.getClosureId();
		LocalDate todaydate = LocalDate.now();
		GeneralDate startDate = GeneralDate.localDate(todaydate.withDayOfMonth(1));
		GeneralDate endDate = GeneralDate.localDate(todaydate.with(TemporalAdjusters.lastDayOfMonth()));
		if (!employmentCode.isEmpty() && closureId != null) {
			Optional<Closure> closure = closureRepo.findById(companyId, closureId);
			YearMonth yearmonth = closure.get().getClosureMonth().getProcessingYm();
			// アルゴリズム「当月の期間を算出する」を実行する
			// 検索当月＝当月＋１ヵ月
			DatePeriod datePeriod1 = ClosureService.getClosurePeriod(closureId, yearmonth, closure);
			startDate = datePeriod1.start();
			endDate = datePeriod1.end();
		}

		return new DateRangeDto(startDate, endDate);
	}
	
	public ToppageOvertimeData getOvertimeToppage() {
		String companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		Integer closureId = this.getClosureId();
		List<AgreementTimeToppage> agreementTimeLst = new ArrayList<>();
		Integer dataStatus = 0;
		boolean visible = false;
		SPTopPageSet setting = sPTopPageSetRepository.getTopPageSetByCompanyAndType(companyID, Type.OVERTIME_WORK.value);

		if (setting != null) {
			visible = setting.getDisplayAtr() == NotUseAtr.USE;
		}
		
		Closure closure = closureRepo.findById(companyID, closureId).get();
		YearMonth targetMonth = closure.getClosureMonth().getProcessingYm();
		
		// [RQ609]ログイン社員のシステム日時点の処理対象年月を取得する
		InitDisplayPeriodSwitchSetDto rq609 = displayPeriodfinder.targetDateFromLogin();
		
		if (closureId == null) {
			throw new BusinessException("Msg_1134");
		}
		//取得した「処理対象年月List」．締めIDが、ログイン者の締めIDと一致する「締め情報」を取得する
		YearMonth targetMonth_A = rq609.getListDateProcessed().stream()
				.filter(c -> c.getClosureID() == closure.getClosureId().value)
				.collect(Collectors.toList()).get(0).getTargetDate();
		// 【NO.333】36協定時間の取得(【NO.333】lấy thời gian hiệp định 36)
		/** TODO: 36協定時間対応により、コメントアウトされた */
//		List<AgreementTimeDetail> listAgreementTimeDetail = GetAgreementTime.get(
//				requireService.createRequire(), new CacheCarrier(), companyID, Arrays.asList(employeeID), targetMonth_A, ClosureId.valueOf(closureId));
//
//		if (listAgreementTimeDetail.isEmpty()) {
//			throw new RuntimeException("ListAgreementTimeDetailRQ333 Empty");
//		}
//		for (AgreementTimeDetail agreementTimeDetail : listAgreementTimeDetail) {
//			if (agreementTimeDetail.getErrorMessage().isPresent()) {
//				throw new BusinessException(new RawErrorMessage(agreementTimeDetail.getErrorMessage().get()));
//			}
//		}
//		agreementTimeLst.add(new AgreementTimeToppage(String.valueOf(targetMonth_A), 
//				AgreementTimeOfMonthlyDto.fromAgreementTimeOfMonthly(listAgreementTimeDetail.get(0).getConfirmed().get())));
		
		// 過去の時間外労働時間の取得処理
		int currentOrNextMonth = rq609.getCurrentOrNextMonth();
		
		// アルゴリズム「年月を指定して、36協定期間の年度を取得する」を実行する
		AgreementOperationSetting agreeOpSet = agreementOperationSettingRepository.find(companyID).get();
		YearMonth yearMonth = closure.getClosureMonth().getProcessingYm();
		Year year = new Year(yearMonth.year());
		if (yearMonth.month() < (agreeOpSet.getStartingMonth().value + 1)) {
			year = new Year(yearMonth.year() - 1);
		}
		/** TODO: 36協定時間対応により、コメントアウトされた */
		// 年度から36協定の年月期間を取得
//		YearMonthPeriod yearMonthPeriod = agreeOpSet.getYearMonthPeriod(year, closure);
//		YearMonthPeriod ymPeriodPast = new YearMonthPeriod(yearMonthPeriod.start(), yearMonth.previousMonth());
		
		// Parameter．当月翌月区分をチェックする
		if(currentOrNextMonth == 1) {
//			if(yearMonthPeriod.start().lessThan(yearMonth)){
//				// [NO.612]年月期間を指定して管理期間の36協定時間を取得する
//				List<AgreementTimeToppage> agreementTimeToppageLst = 
//						getAgreementTimeOfMngPeriod.getAgreementTimeByMonths(Arrays.asList(employeeID), ymPeriodPast).stream()
//						.map(x -> {
//							AgreementTimeOfMonthlyDto agreementTimeOfMonthlyDto = AgreementTimeOfMonthlyDto
//									.fromAgreementTimeOfMonthly(x.getAgreementTime().getAgreementTime());
//							return new AgreementTimeToppage(x.getYearMonth().toString(), agreementTimeOfMonthlyDto);
//						}).collect(Collectors.toList());
//				agreementTimeLst.addAll(agreementTimeToppageLst);
//				dataStatus = AgreementPastStatus.NORMAL.value;
//			} else {
//				dataStatus = AgreementPastStatus.PRESENT.value;
//			}
		} else {//翌月(NextMonth)
			/** TODO: 36協定時間対応により、コメントアウトされた */
			// 【NO.333】36協定時間の取得: lay data thang hien tai
//			List<AgreementTimeDetail> listAgreementTimeCur = GetAgreementTime.get(
//					requireService.createRequire(), new CacheCarrier(), companyID, Arrays.asList(employeeID), targetMonth, closure.getClosureId());
//			if (listAgreementTimeCur.isEmpty()) {
//				throw new RuntimeException("ListAgreementTimeDetailRQ333 Empty");
//			}
//			Optional<AgreementTimeDetail> agreementTimeDetailError = listAgreementTimeCur.stream().filter(x -> x.getErrorMessage().isPresent()).findAny();
//			if(agreementTimeDetailError.isPresent()) {
//				dataStatus = AgreementPastStatus.ERROR.value;
//			} else {
//				agreementTimeLst.add(new AgreementTimeToppage(String.valueOf(targetMonth), 
//						AgreementTimeOfMonthlyDto.fromAgreementTimeOfMonthly(listAgreementTimeCur.get(0).getConfirmed().get())));
//				if(yearMonthPeriod.start().lessThan(targetMonth)) {
//					// // [NO.612]年月期間を指定して管理期間の36協定時間を取得する: lay data thang qua khu
//					List<AgreementTimeToppage> agreementTimeToppageLst = 
//							getAgreementTimeOfMngPeriod.getAgreementTimeByMonths(Arrays.asList(employeeID), ymPeriodPast).stream()
//							.map(x -> {
//								AgreementTimeOfMonthlyDto agreementTimeOfMonthlyDto = AgreementTimeOfMonthlyDto
//										.fromAgreementTimeOfMonthly(x.getAgreementTime().getAgreementTime());
//								return new AgreementTimeToppage(x.getYearMonth().toString(), agreementTimeOfMonthlyDto);
//							}).collect(Collectors.toList());
//					agreementTimeLst.addAll(agreementTimeToppageLst);
//					dataStatus = AgreementPastStatus.NORMAL.value;
//				} else {
//					dataStatus = AgreementPastStatus.NORMAL.value;
//				}
//			}
		}
		/** TODO: 36協定時間対応により、コメントアウトされた */
		return new ToppageOvertimeData(convertAgreementTimeLst(agreementTimeLst, new YearMonthPeriod(targetMonth_A, targetMonth_A)),
//		return new ToppageOvertimeData(convertAgreementTimeLst(agreementTimeLst, new YearMonthPeriod(yearMonthPeriod.start(), targetMonth_A)),
				dataStatus, visible, targetMonth_A.v());
	}
	
	// xử lý output với các tháng không có dữ liệu
	private List<AgreementTimeToppage> convertAgreementTimeLst(List<AgreementTimeToppage> agreementTimeLst, YearMonthPeriod ymPeriodPast) {
		List<AgreementTimeToppage> convertLst = new ArrayList<>();
		YearMonth loopYM = ymPeriodPast.start();
		while(loopYM.lessThanOrEqualTo(ymPeriodPast.end())) {
			Optional<AgreementTimeToppage> loopItem = getLoopItem(agreementTimeLst, loopYM);
			if(loopItem.isPresent()) {
				convertLst.add(loopItem.get());
			} else {
				convertLst.add(new AgreementTimeToppage(
						loopYM.toString(), 
						new AgreementTimeOfMonthlyDto(0, 0, 0, 0, 0, 0)));
			}
			loopYM = loopYM.nextMonth();
		}
		convertLst.sort(Comparator.comparing(AgreementTimeToppage::getYearMonth).reversed());
		return convertLst;
	}
	
	private Optional<AgreementTimeToppage> getLoopItem(List<AgreementTimeToppage> agreementTimeLst, YearMonth yearMonth){
		return agreementTimeLst.stream().filter(x -> x.yearMonth.equals(yearMonth.toString())).findAny();
	}

	public ToppageOvertimeHoursDto getDisplayOvertime(int targetMonth) {
		String companyID = AppContexts.user().companyId();
		boolean view = false;
		SPTopPageSet setting = sPTopPageSetRepository.getTopPageSetByCompanyAndType(companyID, Type.TIME_STATUS.value);

		if (setting != null && setting.getSmartPhoneTopPageType().getType() == Type.TIME_STATUS) {
			view = setting.getDisplayAtr() == NotUseAtr.USE;
		}
		return new ToppageOvertimeHoursDto(displayOvertime(targetMonth), view);
	}

	private DisplayNotifiDto displayNoti() {
		// アルゴリズム「会社で表示する通知を取得する」を実行する_
		// Thực hiện thuật toán "Lấy thông báo hiển thị tại công ty "
		String companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		DateRangeDto dateRange = getDateRangeByClsId(companyID);
		DisplayNotifiDto result = new DisplayNotifiDto();
		// OptionalWidgetInfoDto dto = new OptionalWidgetInfoDto();
		// ドメインモデル「通知詳細設定」を取得する
		// _Lấy domain model"Cài đặt chi tiết thông báo"
		// nếu type =0 thì nó là Notification
		// còn type = 1 thì là TimeStatus
		Optional<NotificationDetailSet> notiDetailSet = sPTopPageSetRepository.getNotificationDetailSet(companyID,
				Type.NOTIFICATION.value);
		if (notiDetailSet.isPresent()) {
			List<NotificationDisplayItem> listNotificationDisplayItem = notiDetailSet.get().getDetailedItem();
			for (NotificationDisplayItem notificationDisplayItem : listNotificationDisplayItem) {
				if (notificationDisplayItem.getDetailType().value == NotificationType.APPLICATION_FOR_APPROVED.value
						&& notificationDisplayItem.getDisplayAtr() == NotUseAtr.USE) {
					result.KTG002 = existenceDataApproved();
				}
				if (notificationDisplayItem.getDetailType().value == NotificationType.DAILY_ACTUALS_ERROR.value
						&& notificationDisplayItem.getDisplayAtr() == NotUseAtr.USE) {
					// アルゴリズム「08.日別実績のエラー有無表示」を実行する_
					// Thực hiện thuật toán "Hiển thị hoặc không hiển thị erro
					// thực tích các ngày 08"
					result.checkDailyErrorA2_2 = checksDailyPerformanceErrorRepo.checked(employeeID,
							dateRange.getStart(), dateRange.getEnd());
				}

			}

			return result;
		} else {
			return result;
		}

	}

	public OptionalWidgetInfoMobileDto getKTG029(GeneralDate startDate, GeneralDate endDate) {
		GeneralDate systemDate = GeneralDate.today();
		String employeeId = AppContexts.user().employeeId();
		String companyId = AppContexts.user().companyId();
		DatePeriod datePeriod = new DatePeriod(startDate, endDate);
		OptionalWidgetInfoMobileDto dataKTG029 = new OptionalWidgetInfoMobileDto();
		Optional<TimeStatusDetailsSet> optTimeStatusDetailsSet = sPTopPageSetRepository
				.getTimeStatusDetailsSet(companyId, Type.TIME_STATUS.value);

		if (!optTimeStatusDetailsSet.isPresent()) {
			return null;
		}

		// アルゴリズム「選択ウィジェット画面表示前処理」を実行する
		// _Thực hiện thuật toán "Xử lý trước khi hiển thị màn hình Selection
		// widget"
		List<TimeStatusDisplayItem> listDetailedItem = optTimeStatusDetailsSet.get().getDetailedItem();
		for (TimeStatusDisplayItem timeStatusDisplayItem : listDetailedItem) {
			if ((timeStatusDisplayItem.getDetailType() == TimeStatusType.NUMBER_REMAINING_YEARS
					|| timeStatusDisplayItem.getDetailType() == TimeStatusType.HALF_DAY_ANNUAL_REST_COUNT
					|| timeStatusDisplayItem.getDetailType() == TimeStatusType.HOURLY_ANNUAL_HOLIDAY_AVAI_LIMIT)
					&& timeStatusDisplayItem.getDisplayAtr() == NotUseAtr.USE) {
				// アルゴリズム「15.年休残数表示」を実行する_ THực hiện thuật toán "15. Hiển thị
				// nghỉ phép năm còn lại "
				// Xử lý 15
				dataKTG029.setYearlyHoliday(setYearlyHoliday(companyId, employeeId, systemDate, datePeriod));
			} else if (timeStatusDisplayItem.getDetailType() == TimeStatusType.ACCUMULATED_ANNUAL_REST
					&& timeStatusDisplayItem.getDisplayAtr() == NotUseAtr.USE) {
				// アルゴリズム「16.積立年休残数表示」を実行する_Thực hiện thuật toán "16.HIển thị
				// nghỉ phép năm cộng dồn"
				// Xử lý 16
				KTGRsvLeaveInfoImport KTGRsvLeaveInfoImport = optionalWidgetAdapter
						.getNumberOfReservedYearsRemain(employeeId, systemDate);
				boolean showAfter = false;
				if (KTGRsvLeaveInfoImport.getGrantDay() != null) {
					showAfter = startDate.beforeOrEquals(KTGRsvLeaveInfoImport.getGrantDay())
							&& endDate.afterOrEquals(KTGRsvLeaveInfoImport.getGrantDay());
				}
				dataKTG029.setReservedYearsRemainNo(new RemainingNumber("", KTGRsvLeaveInfoImport.getRemainingDays(),
						KTGRsvLeaveInfoImport.getAftRemainDay(), KTGRsvLeaveInfoImport.getGrantDay(), showAfter));
			} else if (timeStatusDisplayItem.getDetailType() == TimeStatusType.NUMBER_DAYS_LEFT
					&& timeStatusDisplayItem.getDisplayAtr() == NotUseAtr.USE) {
				// アルゴリズム「18.代休残数表示」を実行する_Thực hiện thuật toán"18.Hiển thị nghỉ
				// bù"
				// Xử lý 18
				Double remain = BreakDayOffMngInPeriodQuery.getBreakDayOffMngRemain(
						requireService.createRequire(), new CacheCarrier(), employeeId, systemDate);
				dataKTG029.setRemainAlternationNoDay(remain != null ? remain : 0.0);
			} else if (timeStatusDisplayItem.getDetailType() == TimeStatusType.REMNANT_NUMBER
					&& timeStatusDisplayItem.getDisplayAtr() == NotUseAtr.USE) {
				// アルゴリズム「19.振休残数表示」を実行する_ THực hiện thuật toán "19.Hiển thị
				// nghỉ bù ngày lễ không nghỉ"
				// Xử lý 19
				Double remainLeft = AbsenceReruitmentMngInPeriodQuery.getAbsRecMngRemain(
						requireService.createRequire(), new CacheCarrier(), employeeId, systemDate).getRemainDays();
				dataKTG029.setRemainsLeft(remainLeft != null ? remainLeft : 0.0);
			} else if (timeStatusDisplayItem.getDetailType() == TimeStatusType.REMAINING_HOLIDAY
					&& timeStatusDisplayItem.getDisplayAtr() == NotUseAtr.USE) {
				// sử lý 23
				// requestList 208(期間内の特別休暇残を集計する)
				List<RemainingNumber> sPHDRamainNos = new ArrayList<>();
				List<SpecialHoliday> specialHolidays = specialHolidayRepository.findByCompanyId(companyId);
				DatePeriodDto datePeriodDto = getCurrentMonth();
				for (SpecialHoliday specialHoliday : specialHolidays) {
					// get request list 208 rồi trả về
					// ・上書きフラグ ← falseを渡してください(muto)
					// ・上書き用の暫定管理データ ← 空（null or Empty）で渡してください
					ComplileInPeriodOfSpecialLeaveParam param = new ComplileInPeriodOfSpecialLeaveParam(companyId,
							employeeId,
							new DatePeriod(datePeriodDto.getStrCurrentMonth(),
									datePeriodDto.getStrCurrentMonth().addYears(1).addDays(-1)),
							false, systemDate, specialHoliday.getSpecialHolidayCode().v(), false, false,
							new ArrayList<>(), new ArrayList<>(), Optional.empty());
					InPeriodOfSpecialLeave inPeriodOfSpecialLeave = SpecialLeaveManagementService
							.complileInPeriodOfSpecialLeave(
									requireService.createRequire(), new CacheCarrier(), param).getAggSpecialLeaveResult();
					boolean showAfter = false;
					GeneralDate date = GeneralDate.today();
					List<SpecialLeaveGrantDetails> lstSpeLeaveGrantDetails = inPeriodOfSpecialLeave
							.getLstSpeLeaveGrantDetails();
					for (SpecialLeaveGrantDetails items : lstSpeLeaveGrantDetails) {
						if (items.getGrantDate().afterOrEquals(startDate)
								&& items.getGrantDate().beforeOrEquals(endDate)) {
							date = items.getGrantDate();
							showAfter = true;
						}
					}
					double before = inPeriodOfSpecialLeave.getRemainDays().getGrantDetailBefore().getRemainDays();
					double after = inPeriodOfSpecialLeave.getRemainDays().getGrantDetailAfter().isPresent()
							? inPeriodOfSpecialLeave.getRemainDays().getGrantDetailAfter().get().getRemainDays() : 0.0;
					sPHDRamainNos.add(new RemainingNumber(specialHoliday.getSpecialHolidayName().v(), before, after,
							date, showAfter));
				}
				dataKTG029.setSPHDRamainNo(sPHDRamainNos);
			}

		}

		return dataKTG029;
	}

	public DatePeriodDto getCurrentMonth() {
		String companyId = AppContexts.user().companyId();
		Integer closureId = this.getClosureId();

		Optional<Closure> closure = closureRepo.findById(companyId, closureId);
		if (!closure.isPresent())
			return null;

		YearMonth processingDate = closure.get().getClosureMonth().getProcessingYm();

		DatePeriod currentMonth = ClosureService.getClosurePeriod(closureId, processingDate, closure);

		DatePeriod nextMonth = ClosureService.getClosurePeriod(requireService.createRequire(), closureId, processingDate.addMonths(1));

		DatePeriodDto dto = new DatePeriodDto(currentMonth.start(), currentMonth.end(), nextMonth.start(),
				nextMonth.end());

		return dto;
	}

	private Integer getClosureId() {
		String companyId = AppContexts.user().companyId();
		String employmentCode = this.getEmploymentCode();
		Optional<ClosureEmployment> closureEmployment = closureEmploymentRepo.findByEmploymentCD(companyId,
				employmentCode);
		if (!closureEmployment.isPresent())
			return null;
		return closureEmployment.get().getClosureId();
	}

	private String getEmploymentCode() {
		String companyId = AppContexts.user().companyId();
		String employeeId = AppContexts.user().employeeId();

		Optional<BsEmploymentHistoryImport> EmploymentHistoryImport = shareEmploymentAdapter
				.findEmploymentHistory(companyId, employeeId, GeneralDate.today());
		if (!EmploymentHistoryImport.isPresent())
			throw new RuntimeException("Not found EmploymentHistory by closureID");
		String employmentCode = EmploymentHistoryImport.get().getEmploymentCode();
		return employmentCode;
	}

	public OvertimeHoursDto displayOvertime(int targetMonth) {
		String empLoginCode = AppContexts.user().employeeCode();
		String companyID = AppContexts.user().companyId();
		Integer closureId = this.getClosureId();
		List<AgreementTimeList36> data = new ArrayList<>();
		// OvertimeHoursDto overtimeHoursDto =
		// kTG027QueryProcessor.initialActivationArocess(targetMonth);
		List<String> employeeId = new ArrayList<>();
		employeeId.add(AppContexts.user().employeeId());
		// RQ 333
		if (closureId == null) {
			throw new BusinessException("Msg_1134");
		}
		/** TODO: 36協定時間対応により、コメントアウトされた */
//		List<AgreementTimeDetail> listAgreementTimeDetail = GetAgreementTime.get(
//				requireService.createRequire(), new CacheCarrier(), companyID, employeeId,
//				YearMonth.of(targetMonth), ClosureId.valueOf(closureId));
//
//		if (listAgreementTimeDetail.isEmpty()) {
//			throw new RuntimeException("ListAgreementTimeDetailRQ333 Empty");
//		}
//		for (AgreementTimeDetail agreementTimeDetail : listAgreementTimeDetail) {
//			if (agreementTimeDetail.getErrorMessage().isPresent()) {
//				throw new BusinessException(new RawErrorMessage(agreementTimeDetail.getErrorMessage().get()));
//			}
//		}
		// (Set thông tin công việc ngoài giờ đã lấy)
//		List<String> lstEmpID = listAgreementTimeDetail.stream().map(c -> c.getEmployeeId())
//				.collect(Collectors.toList());
		// Lay Request61
//		List<PersonEmpBasicInfoImport> listEmpBasicInfoImport = empEmployeeAdapter.getPerEmpBasicInfo(lstEmpID);
//		for (AgreementTimeDetail agreementTimeDetail : listAgreementTimeDetail) {
//			Optional<PersonEmpBasicInfoImport> personInfor = listEmpBasicInfoImport.stream()
//					.filter(c -> c.getEmployeeId().equals(agreementTimeDetail.getEmployeeId())).findFirst();
//			if (!personInfor.isPresent()) {
//				break;
//			}
//			AgreementTimeList36 agreementTimeList36 = new AgreementTimeList36(personInfor.get().getEmployeeCode(),
//					personInfor.get().getBusinessName(), null,
//					new AgreementTimeOfMonthlyDto(
//							!agreementTimeDetail.getConfirmed().isPresent() ? 0
//									: agreementTimeDetail.getConfirmed().get().getAgreementTime().v(),
//							!agreementTimeDetail.getConfirmed().isPresent() ? 0
//									: agreementTimeDetail.getConfirmed().get().getLimitErrorTime().v(),
//							!agreementTimeDetail.getConfirmed().isPresent() ? 0
//									: agreementTimeDetail.getConfirmed().get().getLimitAlarmTime().v(),
//							!agreementTimeDetail.getConfirmed().isPresent() ? 0
//									: (!agreementTimeDetail.getConfirmed().get().getExceptionLimitErrorTime()
//											.isPresent()
//													? agreementTimeDetail.getConfirmed().get().getLimitErrorTime().v()
//													: agreementTimeDetail.getConfirmed().get()
//															.getExceptionLimitErrorTime().get().v()),
//							!agreementTimeDetail.getConfirmed().isPresent() ? 0
//									: (!agreementTimeDetail.getConfirmed().get().getExceptionLimitAlarmTime()
//											.isPresent()
//													? 0
//													: agreementTimeDetail.getConfirmed().get()
//															.getExceptionLimitAlarmTime().get().v()),
//							!agreementTimeDetail.getConfirmed().isPresent() ? 0
//									: agreementTimeDetail.getConfirmed().get().getStatus().value),
//					new AgreementTimeOfMonthlyDto(
//							!agreementTimeDetail.getAfterAppReflect().isPresent() ? 0
//									: agreementTimeDetail.getAfterAppReflect().get().getAgreementTime().v(),
//							!agreementTimeDetail.getAfterAppReflect().isPresent() ? 0
//									: agreementTimeDetail.getAfterAppReflect().get().getLimitErrorTime().v(),
//							!agreementTimeDetail.getAfterAppReflect().isPresent() ? 0
//									: agreementTimeDetail.getAfterAppReflect().get().getLimitAlarmTime().v(),
//							!agreementTimeDetail.getAfterAppReflect().isPresent() ? 0
//									: (!agreementTimeDetail.getAfterAppReflect().get().getExceptionLimitErrorTime()
//											.isPresent()
//													? 0
//													: agreementTimeDetail.getAfterAppReflect().get()
//															.getExceptionLimitErrorTime().get().v()),
//							!agreementTimeDetail.getAfterAppReflect().isPresent() ? 0
//									: (!agreementTimeDetail.getAfterAppReflect().get().getExceptionLimitAlarmTime()
//											.isPresent()
//													? 0
//													: agreementTimeDetail.getAfterAppReflect().get()
//															.getExceptionLimitAlarmTime().get().v()),
//							!agreementTimeDetail.getAfterAppReflect().isPresent() ? 0
//									: agreementTimeDetail.getAfterAppReflect().get().getStatus().value));
//			data.add(agreementTimeList36);
//		}

		data.sort((a, b) -> {
			if (a.getAfterAppReflect().getAgreementTime() == b.getAfterAppReflect().getAgreementTime()) {
				return a.getEmployeeCD().compareTo(b.getEmployeeCD());
			} else {
				return b.getAfterAppReflect().getAgreementTime() - a.getAfterAppReflect().getAgreementTime();
			}
		});
		OvertimeHours over = new OvertimeHours(null, data);
		OvertimeHoursDto overtimeHoursDto = new OvertimeHoursDto(closureId, null, over);
		List<AgreementTimeList36> filtered = overtimeHoursDto.getOvertimeHours().getOvertimeLaborInfor().stream()
				.filter(x -> x.getEmployeeCD().equalsIgnoreCase(empLoginCode)).collect(Collectors.toList());
		overtimeHoursDto.getOvertimeHours().setOvertimeLaborInfor(filtered);

		return overtimeHoursDto;
	}

	private YearlyHoliday setYearlyHoliday(String cID, String employeeId, GeneralDate date, DatePeriod datePeriod) {
		YearlyHoliday yearlyHoliday = new YearlyHoliday();
		// lấy request list 210
		List<NextAnnualLeaveGrantImport> listNextAnnualLeaveGrant = optionalWidgetAdapter
				.acquireNextHolidayGrantDate(cID, employeeId, date);
		if (!listNextAnnualLeaveGrant.isEmpty()) {
			NextAnnualLeaveGrantImport NextAnnualLeaveGrant = listNextAnnualLeaveGrant.get(0);
			yearlyHoliday.setNextTime(NextAnnualLeaveGrant.getGrantDate());
			yearlyHoliday.setNextGrantDate(NextAnnualLeaveGrant.getGrantDate());
			yearlyHoliday.setGrantedDaysNo(NextAnnualLeaveGrant.getGrantDays());
			if (datePeriod.contains(NextAnnualLeaveGrant.getGrantDate())) {
				yearlyHoliday.setShowGrantDate(true);
			}
		}
		// lấy request 198
		NumAnnLeaReferenceDateImport reNumAnnLeaReferenceDate = optionalWidgetAdapter
				.getReferDateAnnualLeaveRemainNumber(employeeId, date);

		AnnualLeaveRemainingNumberImport remainingNumber = reNumAnnLeaReferenceDate.getAnnualLeaveRemainNumberImport();
		yearlyHoliday.setNextTimeInfo(new YearlyHolidayInfo(remainingNumber.getAnnualLeaveGrantPreDay(),
				new TimeOT(remainingNumber.getAnnualLeaveGrantPreTime().intValue() / 60,
						remainingNumber.getAnnualLeaveGrantPreTime().intValue() % 60),
				remainingNumber.getNumberOfRemainGrantPre(),
				new TimeOT(remainingNumber.getTimeAnnualLeaveWithMinusGrantPre().intValue() / 60,
						remainingNumber.getTimeAnnualLeaveWithMinusGrantPre().intValue() % 60)));
		/*
		 * yearlyHoliday.setNextGrantDateInfo(new
		 * YearlyHolidayInfo(remainingNumber.getAnnualLeaveGrantPreDay(), new
		 * TimeOT(remainingNumber.getAnnualLeaveGrantPreTime().intValue()/60,
		 * remainingNumber.getAnnualLeaveGrantPreTime().intValue()%60),
		 * remainingNumber.getNumberOfRemainGrantPre(), new
		 * TimeOT(remainingNumber.getTimeAnnualLeaveWithMinusGrantPre().intValue
		 * ()/60,remainingNumber.getTimeAnnualLeaveWithMinusGrantPre().intValue(
		 * )%60))); yearlyHoliday.setAfterGrantDateInfo(new
		 * YearlyHolidayInfo(remainingNumber.getAnnualLeaveGrantPostDay(), new
		 * TimeOT(remainingNumber.getAnnualLeaveGrantPostTime().intValue()/60,
		 * remainingNumber.getAnnualLeaveGrantPostTime().intValue()%60),
		 * remainingNumber.getNumberOfRemainGrantPost(), new
		 * TimeOT(remainingNumber.getTimeAnnualLeaveWithMinusGrantPost().
		 * intValue()/60,remainingNumber.getTimeAnnualLeaveWithMinusGrantPost().
		 * intValue()%60)));
		 */
		/*
		 * yearlyHoliday.setAttendanceRate(remainingNumber.getAttendanceRate());
		 * yearlyHoliday.setWorkingDays(remainingNumber.getWorkingDays());
		 * yearlyHoliday.setCalculationMethod(optionalWidgetAdapter.
		 * getGrantHdTblSet(cID, employeeId));
		 */
		return yearlyHoliday;
	}

	public void displayPreProcessKTG029() {
		String companyId = AppContexts.user().companyId();
		String employmentCode = this.getEmploymentCode();
		Integer closureId = this.getClosureId();
		if (!employmentCode.isEmpty()) {
			Optional<Closure> closure = closureRepo.findById(companyId, closureId);
			YearMonth yearmonth = closure.get().getClosureMonth().getProcessingYm();
			// アルゴリズム「当月の期間を算出する」を実行する
			// 検索当月＝当月＋１ヵ月
			DatePeriod datePeriod = ClosureService.getClosurePeriod(
					ClosureService.createRequireM1(closureRepo, closureEmploymentRepo),
					closureId, yearmonth.addMonths(1));
			GeneralDate startDate = datePeriod.start();
			YearlyHoliday yearlyHoliday = new YearlyHoliday();
		}
	}

	// Get the existence of application data to be approved
	public boolean existenceDataApproved() {
		String cid = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		List<GeneralDate> listDate = new ArrayList<>();
		// アルゴリズム「会社の締めを取得する」を実行する
		// Lấy RQ 140
		List<ClosureResultModel> rq140 = workClosureQueryProcessor.findClosureByReferenceDate(GeneralDate.today());
		List<Integer> listClosureID = rq140.stream().map(c -> c.getClosureId()).collect(Collectors.toList());
		// 取得した締めIDのリストでループする
		for (Integer integer : listClosureID) {
			// アルゴリズム「処理年月と締め期間を取得する」を実行する
			// (Thực thi xử lý lấy thời gian quyết toán và tháng năm xử lý)
			Optional<PresentClosingPeriodExport> presentClosingPeriod = shClosurePub.find(cid, integer);
			if (presentClosingPeriod.isPresent()) {
				listDate.add(presentClosingPeriod.get().getClosureStartDate());
			}
		}
		Optional<GeneralDate> startDate = listDate.stream().min(Comparator.comparing(GeneralDate::date));
		if (!startDate.isPresent()) {
			throw new RuntimeErrorException(new Error(), "Can't get Start Date");
		} else {
			// ・年月日（開始日） ＜＝ 締め開始日（取得した一番小さい締め開始日）
			// ・年月日（終了日） ＜＝ 締め開始日（取得した一番小さい締め開始日） + ２年 - １日
			GeneralDate endDate = startDate.get().addYears(2).addDays(-1);
			List<String> listApplicationID = approvalRootStateRepository.resultKTG002Mobile(startDate.get(), endDate,
					employeeID, 0, cid);
			// アルゴリズム「申請IDを使用して申請一覧を取得する」を実行する
			List<Application> listApplication = applicationRepository.findByListID(cid, listApplicationID);
			/* 「申請」．申請種類＝Input．申請種類 & 「申請」．実績反映状態<>差し戻し に該当する申請が存在するかチェックする */
			List<Application> listApplicationFilter = listApplication.stream()
					.filter(c -> (c.getAppType() == ApplicationType.OVER_TIME_APPLICATION)
							&& c.getAppReflectedState() != ReflectedState.REMAND)
					.collect(Collectors.toList());
			if (listApplicationFilter.isEmpty()) {
				return false;
			} else {
				return true;
			}

		}
	}
}
