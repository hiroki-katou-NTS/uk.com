package nts.uk.ctx.at.request.dom.application.common.service.other;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.Year;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.agreement.AgreMaxTimeOfMonthExport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.agreement.AgreePeriodYMDExport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.agreement.AgreeTimeOfMonthExport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.agreement.AgreementExcessInfoImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.agreement.AgreementPeriodByYMDAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.agreement.AgreementTimeAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.agreement.AgreementTimeImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.agreement.AgreementTimeStatusAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.agreement.ExcessTimesYearAdapter;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AppTimeItem;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.Time36ErrorOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.Time36UpperLimitCheckResult;
import nts.uk.ctx.at.request.dom.application.overtime.AppOvertimeDetail;
import nts.uk.ctx.at.request.dom.application.overtime.time36.Time36Agree;
import nts.uk.ctx.at.request.dom.application.overtime.time36.Time36AgreeAnnual;
import nts.uk.ctx.at.request.dom.application.overtime.time36.Time36AgreeMonth;
import nts.uk.ctx.at.request.dom.application.overtime.time36.Time36AgreeUpperLimit;
import nts.uk.ctx.at.request.dom.application.overtime.time36.Time36AgreeUpperLimitAverage;
import nts.uk.ctx.at.request.dom.application.overtime.time36.Time36AgreeUpperLimitMonth;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.Time36AgreeCheckRegister;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeYear;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SEmpHistoryImport;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SysEmploymentHisAdapter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreMaxAverageTimeMulti;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeYear;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.service.MonthlyItems;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.service.OutsideOTSettingService;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.service.Time36AgreementTargetItem;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosurePeriod;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;

@Stateless
public class Time36UpperLimitCheckImpl implements Time36UpperLimitCheck {

//	@Inject
//	private ClosureService closureService;

	@Inject
	SysEmploymentHisAdapter sysEmploymentHisAdapter;

//	@Inject
//	private ClosureEmploymentRepository closureEmploymentRepository;

	@Inject
	private AgreementTimeAdapter agreementTimeAdapter;

	@Inject
	private ExcessTimesYearAdapter excessTimesYearAdapter;

//	@Inject
//	private OutsideOTSettingService outsideOTSettingService;

	@Inject
	private AgreementTimeStatusAdapter agreementTimeStatusAdapter;
	
//	@Inject
//	private ClosureRepository closureRepository;
	
	@Inject
	private OvertimeRestAppCommonSetRepository overtimeRestAppCommonSetRepository;
	
//	@Inject
//	private AgreementPeriodByYMDAdapter agreementPeriodByYMDAdapter;

	@Override
	public Time36UpperLimitCheckResult checkRegister(String companyID, String employeeID, GeneralDate appDate,
			ApplicationType appType, List<AppTimeItem> appTimeItems) {
		List<Time36ErrorOutput> errorFlg = new ArrayList<Time36ErrorOutput>();
		// 「時間外時間の詳細」をクリア
		AppOvertimeDetail appOvertimeDetail = new AppOvertimeDetail();
		appOvertimeDetail.setCid(companyID);
		
		// 時間外時間の詳細を作成
		this.createAppOvertimeDetail(appOvertimeDetail, employeeID, appDate, appType, appTimeItems);
		
		// 残業休出申請の時間外時間の詳細をセット
		this.createDetailFromInput();
		
		// 登録不可３６協定チェック区分を取得
		Time36AgreeCheckRegister time36AgreeCheckRegister = this.getTime36AgreeCheckRegister(companyID, appType);
		
		if(time36AgreeCheckRegister==Time36AgreeCheckRegister.NOT_CHECK){
			return new Time36UpperLimitCheckResult(errorFlg, Optional.ofNullable(appOvertimeDetail));
		}
		
		// 36協定時間のエラーチェック処理
		errorFlg.addAll(this.agree36CheckError(time36AgreeCheckRegister, appOvertimeDetail.getTime36Agree(), appOvertimeDetail.getYearMonth()));
		
		// 36協定上限時間のエラーチェック
		errorFlg.addAll(this.agree36UpperLimitCheckError(companyID, employeeID, appDate, appOvertimeDetail.getTime36AgreeUpperLimit()));
		
		return new Time36UpperLimitCheckResult(errorFlg, Optional.ofNullable(appOvertimeDetail));
	}

	@Override
	public Time36UpperLimitCheckResult checkUpdate(String companyID, Optional<AppOvertimeDetail> appOvertimeDetailOpt,
			String employeeID, GeneralDate appDate, ApplicationType appType, List<AppTimeItem> appTimeItems) {
		
		List<Time36ErrorOutput> errorFlg = new ArrayList<Time36ErrorOutput>();

		if (!appOvertimeDetailOpt.isPresent()) {
			return new Time36UpperLimitCheckResult(errorFlg, appOvertimeDetailOpt);
		}
		AppOvertimeDetail appOvertimeDetail = appOvertimeDetailOpt.get();
		
		// 画面から36協定対象時間を取得
		this.getTime36FromScreen(appOvertimeDetail, appType, appTimeItems);
		
		// 登録不可３６協定チェック区分を取得
		Time36AgreeCheckRegister time36AgreeCheckRegister = this.getTime36AgreeCheckRegister(companyID, appType);
		
		if(time36AgreeCheckRegister==Time36AgreeCheckRegister.NOT_CHECK){
			return new Time36UpperLimitCheckResult(errorFlg, Optional.ofNullable(appOvertimeDetail));
		}
		
		// 36協定時間のエラーチェック処理
		errorFlg.addAll(this.agree36CheckError(time36AgreeCheckRegister, appOvertimeDetail.getTime36Agree(), appOvertimeDetail.getYearMonth()));
		
		// 36協定上限時間のエラーチェック
		errorFlg.addAll(this.agree36UpperLimitCheckError(companyID, employeeID, appDate, appOvertimeDetail.getTime36AgreeUpperLimit()));
		
		return new Time36UpperLimitCheckResult(errorFlg, Optional.ofNullable(appOvertimeDetail));
	}

	private int calcOvertimeAppTime(List<AppTimeItem> appTimeItems, Time36AgreementTargetItem targetItem) {
		int sumAppTime = 0;
		List<AppTimeItem> appItemFrame = appTimeItems.stream()
				.filter(x -> x.getFrameNo() <= MonthlyItems.OVERTIME_10.frameNo).collect(Collectors.toList());
		for (AppTimeItem appTime : appItemFrame) {
			Optional<Integer> frameOtp = targetItem.getOvertimeFrNo().stream().filter(x -> x == appTime.getFrameNo())
					.findFirst();
			if (frameOtp.isPresent()) {
				sumAppTime += appTime.getAppTime();
			}
		}
		if (targetItem.isTargetFlex()) {
			sumAppTime += appTimeItems.stream().filter(x -> x.getFrameNo() > MonthlyItems.OVERTIME_10.frameNo)
					.mapToInt(x -> x.getAppTime()).sum();
		}
		return sumAppTime;
	}

	private int calcBreakAppTime(List<AppTimeItem> appTimeItems, Time36AgreementTargetItem targetItem) {
		int sumAppTime = 0;
		List<AppTimeItem> appItemFrame = appTimeItems.stream()
				.filter(x -> x.getFrameNo() <= MonthlyItems.BREAKTIME_10.frameNo).collect(Collectors.toList());
		for (AppTimeItem appTime : appItemFrame) {
			Optional<Integer> frameOtp = targetItem.getBreakFrNo().stream().filter(x -> x == appTime.getFrameNo())
					.findFirst();
			if (frameOtp.isPresent()) {
				sumAppTime += appTime.getAppTime();
			}
		}
		return sumAppTime;
	}
	
	// 時間外時間の詳細を作成
	private AppOvertimeDetail createAppOvertimeDetail(AppOvertimeDetail appOvertimeDetail, String employeeID, GeneralDate appDate,
			ApplicationType appType, List<AppTimeItem> appTimeItems){
//		String companyID = appOvertimeDetail.getCid();
//		String employmentCD = StringUtils.EMPTY;
//		// 社員所属雇用履歴を取得
//		Optional<SEmpHistoryImport> empHistOtp = sysEmploymentHisAdapter.findSEmpHistBySid(companyID, employeeID,
//				GeneralDate.today());
//		if (empHistOtp.isPresent()) {
//			employmentCD = empHistOtp.get().getEmploymentCode();
//		}
//		
//		// 雇用に紐づく締めを取得する
//		Optional<ClosureEmployment> closureEmpOtp = closureEmploymentRepository.findByEmploymentCD(companyID,
//				employmentCD);
//		Optional<Closure> opClosureSystem = closureRepository.findById(companyID, closureEmpOtp.get().getClosureId());
//		if(!opClosureSystem.isPresent()){
//			throw new RuntimeException("khong co closure");
//		}
//		Closure closureSystem = opClosureSystem.get();
//		
//		// 指定した年月日時点の締め期間を取得する
//		Closure closure = closureService.getClosureDataByEmployee(employeeID, appDate);
//		Optional<ClosurePeriod> closurePeriodOpt = closure.getClosurePeriodByYmd(appDate);
//		appOvertimeDetail.setYearMonth(closurePeriodOpt.get().getYearMonth());
//		
//		// 年月日を指定して、36協定期間を取得する
//		AgreePeriodYMDExport agreePeriodYMDExport = agreementPeriodByYMDAdapter.getAgreementPeriod(companyID, appDate, closureSystem.getClosureId());
//		
//		// 反映処理
//		// chưa đối ứng
//		
//		// 画面から36協定対象時間を取得
//		this.getTime36FromScreen(appOvertimeDetail, appType, appTimeItems);
//		
//		// 36協定時間の取得
//		List<AgreementTimeImport> agreementTimeList = Collections.emptyList();
//		agreementTimeList = agreementTimeAdapter.getAgreementTime(companyID, Arrays.asList(employeeID),
//				agreePeriodYMDExport.getDateTime(), closureSystem.getClosureId());
//		AgreementTimeImport agreementTime = agreementTimeList.get(0);
//		
//		// 36協定上限複数月平均時間と36協定年間時間の取得
//		AgreementTimeOutput agreementTimeOutput = this.getTime36YearAndAverage(companyID, employeeID, agreePeriodYMDExport.getDateTime(), appDate, ScheRecAtr.SCHEDULE);
//		
//		// 36協定時間の作成
//		this.createTime36Agree(appOvertimeDetail, agreementTime.getConfirmed(), employeeID, appDate, agreementTimeOutput.getAgreementTimeYear());
//		
//		// 36協定上限時間の作成
//		this.createTime36AgreeUpperLimit(appOvertimeDetail, agreementTime.getConfirmedMax(), employeeID, appDate, agreementTimeOutput.getAgreMaxAverageTimeMulti());
//		
//		return appOvertimeDetail;
		return null;
	}
	
	// 36協定上限複数月平均時間と36協定年間時間の取得
	/** TODO: 36協定時間対応により、コメントアウトされた */
//	private AgreementTimeOutput getTime36YearAndAverage(String companyID, String employeeID, YearMonth yearMonth, GeneralDate appDate, ScheRecAtr scheRecAtr){
//
//// 2019.5.30 DEL shuichi_ishida Redmine #107910　（NO.579は、NO.599内で呼ぶように仕様変更）
////		// [NO.579]指定日を含む年期間を取得
////		YearMonthPeriod period = agreementTimeAdapter.containsDate(companyID, appDate).get();
//	
//		// [NO.599]36協定上限複数月平均時間と年間時間の取得
//		return agreementTimeAdapter.getAverageAndYear(companyID, employeeID, yearMonth, appDate, scheRecAtr);
//		
//	}
	
	// 残業休出申請の時間外時間の詳細をセット
	private void createDetailFromInput(){
		
	}
	
	// 登録不可３６協定チェック区分を取得
	private Time36AgreeCheckRegister getTime36AgreeCheckRegister(String companyID, ApplicationType appType){
		Optional<OvertimeRestAppCommonSetting> overtimeSetingOtp = overtimeRestAppCommonSetRepository
				.getOvertimeRestAppCommonSetting(companyID, appType.value);
		OvertimeRestAppCommonSetting overtimeSeting = overtimeSetingOtp.get();
		return EnumAdaptor.valueOf(overtimeSeting.getExtratimeExcessAtr().value, Time36AgreeCheckRegister.class); 
	}
	
	// 画面から36協定対象時間を取得
	private void getTime36FromScreen(AppOvertimeDetail appOvertimeDetail, ApplicationType appType, List<AppTimeItem> appTimeItems){
//		String companyID = appOvertimeDetail.getCid();
//		Integer appTime = 0;
//		Integer appUpperLimitTime = 0;
//		Integer statutoryInternalTime = 0;
//		
//		// 36協定対象項目一覧を取得
//		Time36AgreementTargetItem targetItem = outsideOTSettingService.getTime36AgreementTargetItem(companyID);
//		// INPUT．残業休出区分をチェックする
//		if (ApplicationType.OVER_TIME_APPLICATION.equals(appType)) {
//			// INPUT．時間外時間一覧の36協定時間対象の枠を合計する
//			appTime = this.calcOvertimeAppTime(appTimeItems, targetItem);
//		} else if (ApplicationType.HOLIDAY_WORK_APPLICATION.equals(appType)) {
//			// INPUT．時間外時間一覧の36協定時間対象の枠を合計する
//			appTime = this.calcBreakAppTime(appTimeItems, targetItem);
//			// 法定内休出の勤怠項目IDを全て取得
//			List<Integer> attendanceItems = outsideOTSettingService.getAllAttendanceItemIdsForLegalBreak(companyID);
//			// INPUT．法定内休出の勤怠項目IDの枠を合計する
//			List<Integer> breakFrNo = new ArrayList<>();
//			List<MonthlyItems> breakTimeItems = MonthlyItems.findBreakTime();
//			boolean targetFlex = false;
//			for (Integer itemId : attendanceItems) {
//				Optional<MonthlyItems> itemBreakTimeOtp = breakTimeItems
//						.stream().filter(x -> x.itemId == itemId)
//						.findFirst();
//				if (itemBreakTimeOtp.isPresent()) {
//					if (!breakFrNo.contains(itemBreakTimeOtp.get().frameNo)) {
//						breakFrNo.add(itemBreakTimeOtp.get().frameNo);
//					}
//				}
//			}
//			Optional<Integer> flexExessOtp = attendanceItems.stream()
//				.filter(x -> x == MonthlyItems.FLEX_EXCESS_TIME.itemId)
//				.findFirst();
//			if(flexExessOtp.isPresent()){
//				targetFlex = true;
//			}
//			List<Integer> uniqueLst = new ArrayList<>();
//			for(Integer legalBreakNo : breakFrNo){
//				if(targetItem.getBreakFrNo().isEmpty()||!targetItem.getBreakFrNo().contains(legalBreakNo)){
//					uniqueLst.add(legalBreakNo);
//				}
//			}
//			Time36AgreementTargetItem legalBreakTimes = new Time36AgreementTargetItem(new ArrayList<>(), uniqueLst, targetFlex);
//			statutoryInternalTime = this.calcBreakAppTime(appTimeItems, legalBreakTimes);
//		}
//		appUpperLimitTime = appTime + statutoryInternalTime;
//		appOvertimeDetail.getTime36Agree().updateAppTime(appTime);
//		appOvertimeDetail.getTime36AgreeUpperLimit().updateAppTime(appUpperLimitTime);
	}
	
	// 36協定時間の作成
	private void createTime36Agree(AppOvertimeDetail appOvertimeDetail, Optional<AgreeTimeOfMonthExport> confirmed, 
			String employeeID, GeneralDate appDate, Optional<AgreementTimeYear> opAgreementTimeYear){
		// 月間時間の作成
		this.createMonthly(appOvertimeDetail, confirmed, employeeID);
		// 年間時間の作成
		this.createAnnual(appOvertimeDetail, employeeID, appDate, opAgreementTimeYear);
	}
	
	// 月間時間の作成
	private void createMonthly(AppOvertimeDetail appOvertimeDetail, Optional<AgreeTimeOfMonthExport> confirmed, String employeeID){
		// 項目を移送する
		appOvertimeDetail.getTime36Agree().getAgreeMonth().setActualTime(confirmed.map(x -> x.getAgreementTime()).orElse(0));
		appOvertimeDetail.getTime36Agree().getAgreeMonth().setLimitAlarmTime(confirmed.map(x -> x.getLimitAlarmTime()).orElse(0));
		appOvertimeDetail.getTime36Agree().getAgreeMonth().setLimitErrorTime(confirmed.map(x -> x.getLimitErrorTime()).orElse(0));
		appOvertimeDetail.getTime36Agree().getAgreeMonth().setExceptionLimitAlarmTime(confirmed.map(x -> x.getExceptionLimitAlarmTime().orElse(null)).orElse(null));
		appOvertimeDetail.getTime36Agree().getAgreeMonth().setExceptionLimitErrorTime(confirmed.map(x -> x.getExceptionLimitErrorTime().orElse(null)).orElse(null));
		// 超過回数を取得する
		Year year = new Year(appOvertimeDetail.getYearMonth().year());
		AgreementExcessInfoImport agreeInfo = excessTimesYearAdapter.getExcessTimesYear(employeeID, year);
		// 「時間外時間の詳細」．36年間超過回数 = 返した回数、「時間外時間の詳細」．36年間超過月.Add(返した超過の年月)
		appOvertimeDetail.getTime36Agree().getAgreeMonth().setYear36OverMonth(agreeInfo.getYearMonths());
		appOvertimeDetail.getTime36Agree().getAgreeMonth().setNumOfYear36Over(agreeInfo.getExcessTimes());
	}
	
	// 年間時間の作成
	private void createAnnual(AppOvertimeDetail appOvertimeDetail, String employeeID, GeneralDate appDate, Optional<AgreementTimeYear> opAgreementTimeYear){
		/** TODO: 36協定時間対応により、コメントアウトされた */
//		appOvertimeDetail.getTime36Agree().getAgreeAnnual().setActualTime(opAgreementTimeYear.map(x -> x.getRecordTime()).orElse(new AttendanceTimeYear(0)));
//		appOvertimeDetail.getTime36Agree().getAgreeAnnual().setLimitTime(opAgreementTimeYear.map(x -> x.getLimitTime()).orElse(new AgreementOneYearTime(0)));
	}
	
	// 36協定上限時間の作成
	private void createTime36AgreeUpperLimit(AppOvertimeDetail appOvertimeDetail, Optional<AgreMaxTimeOfMonthExport> confirmedMax, 
			String employeeID, GeneralDate appDate, Optional<AgreMaxAverageTimeMulti> opAgreMaxAverageTimeMulti){
		// 月間時間の作成
		this.createMonthlyUpperLimit(appOvertimeDetail, confirmedMax);
		// 複数月平均時間の作成
		this.createMonthlyAverage(appOvertimeDetail, employeeID, appDate, opAgreMaxAverageTimeMulti);
	}
	
	// 月間時間の作成
	private void createMonthlyUpperLimit(AppOvertimeDetail appOvertimeDetail, Optional<AgreMaxTimeOfMonthExport> confirmedMax){
		// 項目移送する
		appOvertimeDetail.getTime36AgreeUpperLimit().getAgreeUpperLimitMonth().updateOverTime(confirmedMax.map(x -> x.getAgreementTime()).orElse(0));
		appOvertimeDetail.getTime36AgreeUpperLimit().getAgreeUpperLimitMonth().updateUpperLimitTime(confirmedMax.map(x -> x.getMaxTime()).orElse(0));
	}
	
	// 複数月平均時間の作成
	private void createMonthlyAverage(AppOvertimeDetail appOvertimeDetail, String employeeID, GeneralDate referenceDate, Optional<AgreMaxAverageTimeMulti> opAgreMaxAverageTimeMulti){
		AgreMaxAverageTimeMulti agreMaxAverageTimeMulti = opAgreMaxAverageTimeMulti.orElse(new AgreMaxAverageTimeMulti());
		// 上限時間の項目移送
		/** TODO: 36協定時間対応により、コメントアウトされた */
//		appOvertimeDetail.getTime36AgreeUpperLimit().getAgreeUpperLimitAverage().setUpperLimitTime(agreMaxAverageTimeMulti.getMaxTime());
//		List<Time36AgreeUpperLimitPerMonth> averageTimeLst = new ArrayList<>();
//		for(AgreMaxAverageTime agreMaxAverageTime : agreMaxAverageTimeMulti.getAverageTimeList()){
//			averageTimeLst.add(new Time36AgreeUpperLimitPerMonth(
//					agreMaxAverageTime.getPeriod(), 
//					agreMaxAverageTime.getAverageTime(), 
//					agreMaxAverageTime.getTotalTime()));
//		}
//		appOvertimeDetail.getTime36AgreeUpperLimit().getAgreeUpperLimitAverage().setAverageTimeLst(averageTimeLst);
	}
	
	// 36協定時間のエラーチェック処理
	private List<Time36ErrorOutput> agree36CheckError(Time36AgreeCheckRegister time36AgreeCheckRegister, Time36Agree time36Agree, YearMonth yearMonth){
		List<Time36ErrorOutput> time36ErrorLst = new ArrayList<>();
		// 登録不可３６協定チェック区分をチェック
		if(time36AgreeCheckRegister==Time36AgreeCheckRegister.CHECK_ONLY_UPPER_OVERTIME){
			return time36ErrorLst;
		}
		// 月間のチェック
		List<Time36ErrorOutput> monthlyError = this.monthlyCheck(time36Agree.getAgreeMonth(), time36Agree.getApplicationTime(), yearMonth);
		// 年間のチェック
		List<Time36ErrorOutput> annualError = this.annualCheck(time36Agree.getAgreeAnnual(), time36Agree.getApplicationTime());
		time36ErrorLst.addAll(monthlyError);
		time36ErrorLst.addAll(annualError);
		return time36ErrorLst;
	} 
	
	// 月間のチェック
	private List<Time36ErrorOutput> monthlyCheck(Time36AgreeMonth agreeMonth, AttendanceTimeMonth apptime, YearMonth yearMonth){
		List<Time36ErrorOutput> time36ErrorLst = new ArrayList<>();
		// 36協定時間の状態チェック
		/** TODO: 36協定時間対応により、コメントアウトされた */
//		AgreementTimeStatusOfMonthly checkAgreement = agreementTimeStatusAdapter.checkAgreementTimeStatus(
//				new AttendanceTimeMonth(apptime.v()+agreeMonth.getActualTime().v()),
//				agreeMonth.getLimitAlarmTime(), 
//				agreeMonth.getLimitErrorTime(),
//				agreeMonth.getExceptionLimitAlarmTime(), 
//				agreeMonth.getExceptionLimitErrorTime());
		
//		if (AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ERROR.equals(checkAgreement)
//				|| AgreementTimeStatusOfMonthly.EXCESS_EXCEPTION_LIMIT_ERROR.equals(checkAgreement)) {
//			// エラー情報一覧に「月間エラー」を追加
//			String realTime = Integer.toString(agreeMonth.getActualTime().v() + apptime.v());
//			String limitTime = "";
//			if(agreeMonth.getExceptionLimitErrorTime().isPresent()){	
//				limitTime = agreeMonth.getExceptionLimitErrorTime().get().toString();
//			} else {
//				limitTime = agreeMonth.getLimitErrorTime().toString();
//			}
//			time36ErrorLst.add(new Time36ErrorOutput(Time36ErrorFlg.MONTH, realTime, limitTime, "", ""));
//			if(!(agreeMonth.getYear36OverMonth().stream().filter(x -> x.equals(yearMonth)).count() > 0)){
//				// 「時間外時間の詳細」．36年間超過回数 += 1、「時間外時間の詳細」．36年間超過月.Add(「時間外時間の詳細」．年月)
//				agreeMonth.setNumOfYear36Over(agreeMonth.getNumOfYear36Over().v()+1);
//				List<YearMonth> oldLst = agreeMonth.getYear36OverMonth();
//				oldLst.add(yearMonth);
//				agreeMonth.setYear36OverMonth(oldLst);
//			}
//		} else if(AgreementTimeStatusOfMonthly.EXCESS_EXCEPTION_LIMIT_ALARM.equals(checkAgreement)
//				|| AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ERROR_SP.equals(checkAgreement)){
//			if(!(agreeMonth.getYear36OverMonth().stream().filter(x -> x.equals(yearMonth)).count() > 0)){
//				// 「時間外時間の詳細」．36年間超過回数 += 1、「時間外時間の詳細」．36年間超過月.Add(「時間外時間の詳細」．年月)
//				agreeMonth.setNumOfYear36Over(agreeMonth.getNumOfYear36Over().v()+1);
//				List<YearMonth> oldLst = agreeMonth.getYear36OverMonth();
//				oldLst.add(yearMonth);
//				agreeMonth.setYear36OverMonth(oldLst);
//			}
//		}
		return time36ErrorLst;
	}
	
	// 年間のチェック
	private List<Time36ErrorOutput> annualCheck(Time36AgreeAnnual agreeAnnual, AttendanceTimeMonth applicationTime){
		List<Time36ErrorOutput> time36ErrorLst = new ArrayList<>();
		/** TODO: 36協定時間対応により、コメントアウトされた */
//		AgreementTimeYear agreementTimeYear = AgreementTimeYear.of(agreeAnnual.getLimitTime(), agreeAnnual.getActualTime(), AgreTimeYearStatusOfMonthly.NORMAL);
		Optional<AttendanceTimeYear> requestTimeOpt = Optional.ofNullable(new AttendanceTimeYear(applicationTime.v()));
		// [NO.545]36協定年間時間の状態チェック
//		AgreTimeYearStatusOfMonthly yearStatus = agreementTimeAdapter.timeYear(agreementTimeYear, requestTimeOpt);
//		if(yearStatus==AgreTimeYearStatusOfMonthly.EXCESS_LIMIT){
//			String realTime = Integer.toString(agreeAnnual.getActualTime().v() + applicationTime.v());
//			String limitTime = agreeAnnual.getLimitTime().toString();
//			time36ErrorLst.add(new Time36ErrorOutput(Time36ErrorFlg.YEAR, realTime, limitTime, "", ""));
//		}
		return time36ErrorLst;
	}
	
	// 36協定上限時間のエラーチェック
	private List<Time36ErrorOutput> agree36UpperLimitCheckError(String companyID, String employeeID, GeneralDate appDate, Time36AgreeUpperLimit time36AgreeUpperLimit){
		List<Time36ErrorOutput> time36ErrorLst = new ArrayList<>();
		// 月間のチェック
		List<Time36ErrorOutput> monthlyUpperError = this.monthlyUpperLimitCheck(time36AgreeUpperLimit.getAgreeUpperLimitMonth(), time36AgreeUpperLimit.getApplicationTime());
		// 複数月平均のチェック
		List<Time36ErrorOutput> monthlyAverageError = this.monthlyAverageCheck(companyID, employeeID, appDate, 
				time36AgreeUpperLimit.getAgreeUpperLimitAverage(), time36AgreeUpperLimit.getApplicationTime());
		time36ErrorLst.addAll(monthlyUpperError);
		time36ErrorLst.addAll(monthlyAverageError);
		return time36ErrorLst;
	} 
	
	// 月間のチェック
	private List<Time36ErrorOutput> monthlyUpperLimitCheck(Time36AgreeUpperLimitMonth agreeUpperLimitMonth, AttendanceTimeMonth applicationTime){
		List<Time36ErrorOutput> time36ErrorLst = new ArrayList<>();
		// [NO.540]36協定上限時間の状態チェック
		/** TODO: 36協定時間対応により、コメントアウトされた */
//		AgreMaxTimeStatusOfMonthly maxTimeStatus = agreementTimeAdapter.maxTime(
//				new AttendanceTimeMonth(agreeUpperLimitMonth.getOverTime().v() + applicationTime.v()), 
//				agreeUpperLimitMonth.getUpperLimitTime(), 
//				Optional.empty());
//		if(maxTimeStatus==AgreMaxTimeStatusOfMonthly.ALARM_OVER){
//			// エラー情報一覧に「上限月間時間エラー」を追加
//			String realTime = Integer.toString(agreeUpperLimitMonth.getOverTime().v() + applicationTime.v());
//			String limitTime = agreeUpperLimitMonth.getUpperLimitTime().toString();
//			time36ErrorLst.add(new Time36ErrorOutput(Time36ErrorFlg.MAX_MONTH, realTime, limitTime, "", ""));
//		}
		return time36ErrorLst;
	}
	
	// 複数月平均のチェック
	private List<Time36ErrorOutput> monthlyAverageCheck(String companyID, String employeeID, GeneralDate appDate,
			Time36AgreeUpperLimitAverage agreeUpperLimitAverage, AttendanceTimeMonth applicationTime){
		List<Time36ErrorOutput> time36ErrorLst = new ArrayList<>();
		// 36協定上限複数月平均時間の状態チェック
		/** TODO: 36協定時間対応により、コメントアウトされた */
//		AgreMaxAverageTimeMulti agreMaxAverageTimeMulti = agreementTimeAdapter.maxAverageTimeMulti(
//				companyID, 
//				AgreMaxAverageTimeMulti.of(
//						agreeUpperLimitAverage.getUpperLimitTime(), 
//						agreeUpperLimitAverage.getAverageTimeLst()
//							.stream().map(x -> AgreMaxAverageTime.of(
//									x.getPeriod(), 
//									x.getTotalTime(), 
//									AgreMaxTimeStatusOfMonthly.NORMAL))
//							.collect(Collectors.toList())), 
//				Optional.ofNullable(new AttendanceTime(applicationTime.v())), 
//				Optional.ofNullable(appDate));
		/** TODO: 36協定時間対応により、コメントアウトされた */
//		for(AgreMaxAverageTime agreMaxAverageTime : agreMaxAverageTimeMulti.getAverageTimeList()){
//			if(agreMaxAverageTime.getStatus()==AgreMaxTimeStatusOfMonthly.ALARM_OVER){
//				// エラー情報一覧に「上限複数月平均時間エラー」を追加
//				String yearMonthStart = agreMaxAverageTime.getPeriod().start().toString();
//				String yearMonthEnd = agreMaxAverageTime.getPeriod().end().toString();
//				String realTime = agreMaxAverageTime.getAverageTime().toString();
//				String limitTime = agreMaxAverageTimeMulti.getMaxTime().toString();
//				time36ErrorLst.add(new Time36ErrorOutput(Time36ErrorFlg.AVERAGE_MONTH, realTime, limitTime, yearMonthStart, yearMonthEnd));
//			}
//		}
		return time36ErrorLst;
	}
	
}
