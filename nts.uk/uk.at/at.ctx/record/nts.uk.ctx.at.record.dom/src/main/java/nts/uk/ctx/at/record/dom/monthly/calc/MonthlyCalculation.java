package nts.uk.ctx.at.record.dom.monthly.calc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.monthly.AttendanceDaysMonth;
import nts.uk.ctx.at.record.dom.monthly.AttendanceItemOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.agreement.AgreementTimeOfManagePeriod;
import nts.uk.ctx.at.record.dom.monthly.agreement.AgreementTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.actualworkingtime.RegularAndIrregularTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.flex.FlexTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.AggregateTotalWorkingTime;
import nts.uk.ctx.at.record.dom.monthly.roundingset.RoundingSetOfMonthly;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.MonthlyAggregationErrorInfo;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.RepositoriesRequiredByMonthlyAggr;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.SettingRequiredByDefo;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.SettingRequiredByFlex;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.SettingRequiredByReg;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageContent;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeImport;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.workrecord.monthlyresults.roleofovertimework.RoleOvertimeWorkEnum;
import nts.uk.ctx.at.shared.dom.workrecord.monthlyresults.roleopenperiod.RoleOfOpenPeriodEnum;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.at.shared.dom.workrule.closure.UseClassification;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 月別実績の月の計算
 * @author shuichi_ishida
 */
@Getter
public class MonthlyCalculation {

	/** 実働時間 */
	private RegularAndIrregularTimeOfMonthly actualWorkingTime;
	/** フレックス時間 */
	private FlexTimeOfMonthly flexTime;
	/** 法定労働時間 */
	private AttendanceTimeMonth statutoryWorkingTime;
	/** 集計時間 */
	private AggregateTotalWorkingTime aggregateTime;
	/** 総労働時間 */
	private AttendanceTimeMonth totalWorkingTime;
	/** 総拘束時間 */
	private AggregateTotalTimeSpentAtWork totalTimeSpentAtWork;
	/** 36協定時間 */
	@Setter
	private AgreementTimeOfMonthly agreementTime;

	/** 会社ID */
	private String companyId;
	/** 社員ID */
	private String employeeId;
	/** 年月 */
	private YearMonth yearMonth;
	/** 締めID */
	private ClosureId closureId;
	/** 締め日付 */
	private ClosureDate closureDate;
	/** 期間 */
	private DatePeriod procPeriod;
	/** 労働条件項目 */
	private WorkingConditionItem workingConditionItem;
	/** 労働制 */
	private WorkingSystem workingSystem;
	/** 職場ID */
	private String workplaceId;
	/** 雇用コード */
	private String employmentCd;
	/** 退職月度がどうか */
	private boolean isRetireMonth;
	/** 締め */
	private Optional<Closure> closureOpt;

	/** 通常勤務が必要とする設定 */
	private SettingRequiredByReg settingsByReg;
	/** 変形労働勤務が必要とする設定 */
	private SettingRequiredByDefo settingsByDefo;
	/** フレックス勤務が必要とする設定 */
	private SettingRequiredByFlex settingsByFlex;
	
	/** 日別実績の勤怠時間リスト */
	private Map<GeneralDate, AttendanceTimeOfDailyPerformance> attendanceTimeOfDailyMap;
	/** 日別実績の勤務情報リスト */
	private Map<GeneralDate, WorkInformation> workInformationOfDailyMap;
	/** 月別実績の勤怠時間　（集計前） */
	private Optional<AttendanceTimeOfMonthly> originalData;
	
	/** 年度 */
	private Year year;
	/** 管理期間の36協定時間 */
	private AgreementTimeOfManagePeriod agreementTimeOfManagePeriod;
	/** エラー情報 */
	private List<MonthlyAggregationErrorInfo> errorInfos;
	
	/**
	 * コンストラクタ
	 */
	public MonthlyCalculation(){

		this.actualWorkingTime = new RegularAndIrregularTimeOfMonthly();
		this.flexTime = new FlexTimeOfMonthly();
		this.statutoryWorkingTime = new AttendanceTimeMonth(0);
		this.aggregateTime = new AggregateTotalWorkingTime();
		this.totalWorkingTime = new AttendanceTimeMonth(0);
		this.totalTimeSpentAtWork = new AggregateTotalTimeSpentAtWork();
		this.agreementTime = new AgreementTimeOfMonthly();
		
		this.companyId = "empty";
		this.employeeId = "empty";
		this.yearMonth = new YearMonth(0);
		this.closureId = ClosureId.RegularEmployee;
		this.closureDate = new ClosureDate(0, true);
		this.procPeriod = new DatePeriod(GeneralDate.today(), GeneralDate.today());
		this.workingConditionItem = null;
		this.workingSystem = WorkingSystem.REGULAR_WORK;
		this.workplaceId = "empty";
		this.employmentCd = "empty";
		this.isRetireMonth = false;
		this.closureOpt = Optional.empty();
		this.settingsByReg = new SettingRequiredByReg(this.companyId);
		this.settingsByDefo = new SettingRequiredByDefo(this.companyId);
		this.settingsByFlex = new SettingRequiredByFlex();
		
		this.attendanceTimeOfDailyMap = new HashMap<>();
		this.workInformationOfDailyMap = new HashMap<>();
		this.originalData = null;

		this.year = new Year(0);
		this.agreementTimeOfManagePeriod = new AgreementTimeOfManagePeriod(this.employeeId, this.yearMonth);
		this.errorInfos = new ArrayList<>();
	}

	/**
	 * ファクトリー
	 * @param actualWorkingTime 実働時間
	 * @param flexTime フレックス時間
	 * @param statutoryWorkingTime 法定労働時間
	 * @param aggregateTime 集計時間
	 * @param totalWorkingTime 総労働時間
	 * @param totalTimeSpentAtWork 総拘束時間
	 * @param agreementTime 36協定時間
	 * @return 月別実績の月の計算
	 */
	public static MonthlyCalculation of(
			RegularAndIrregularTimeOfMonthly actualWorkingTime,
			FlexTimeOfMonthly flexTime,
			AttendanceTimeMonth statutoryWorkingTime,
			AggregateTotalWorkingTime aggregateTime,
			AttendanceTimeMonth totalWorkingTime,
			AggregateTotalTimeSpentAtWork totalTimeSpentAtWork,
			AgreementTimeOfMonthly agreementTime){
		
		val domain = new MonthlyCalculation();
		domain.actualWorkingTime = actualWorkingTime;
		domain.flexTime = flexTime;
		domain.statutoryWorkingTime = statutoryWorkingTime;
		domain.aggregateTime = aggregateTime;
		domain.totalWorkingTime = totalWorkingTime;
		domain.totalTimeSpentAtWork = totalTimeSpentAtWork;
		domain.agreementTime = agreementTime;
		return domain;
	}
	
	/**
	 * 集計準備
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日付
	 * @param procPeriod 期間
	 * @param workingConditionItem 労働条件項目
	 * @param attendanceTimeOfDailysOpt 日別実績の勤怠時間リスト
	 * @param repositories 月次集計が必要とするリポジトリ
	 */
	public void prepareAggregation(
			String companyId, String employeeId,YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate,
			DatePeriod procPeriod, WorkingConditionItem workingConditionItem,
			Optional<List<AttendanceTimeOfDailyPerformance>> attendanceTimeOfDailysOpt,
			RepositoriesRequiredByMonthlyAggr repositories){
		
		this.companyId = companyId;
		this.employeeId = employeeId;
		this.yearMonth = yearMonth;
		this.closureId = closureId;
		this.closureDate = closureDate;
		this.procPeriod = procPeriod;
		this.workingConditionItem = workingConditionItem;
		this.workingSystem = workingConditionItem.getLaborSystem();
		
		this.settingsByReg = new SettingRequiredByReg(companyId);
		this.settingsByDefo = new SettingRequiredByDefo(companyId);
		this.settingsByFlex = new SettingRequiredByFlex();
		
		// 社員を取得する
		EmployeeImport employee = repositories.getEmpEmployee().findByEmpId(employeeId);
		if (employee == null){
			this.errorInfos.add(new MonthlyAggregationErrorInfo(
					"002", new ErrMessageContent(TextResource.localize("Msg_1156"))));
			return;
		}
		
		// 退職月か確認する　（変形労働勤務の月単位集計：精算月判定に利用）
		this.isRetireMonth = false;
		if (procPeriod.contains(employee.getRetiredDate())) this.isRetireMonth = true;
		
		// 期間終了日時点の雇用コードを取得する
		val syEmploymentOpt = repositories.getSyEmployment().findByEmployeeId(
				companyId, employeeId, procPeriod.end());
		if (syEmploymentOpt.isPresent()){
			this.employmentCd = syEmploymentOpt.get().getEmploymentCode();
		}
		
		// 期間終了日時点の職場コードを取得する
		val affWorkplaceOpt = repositories.getAffWorkplace().findBySid(employeeId, procPeriod.end());
		if (affWorkplaceOpt.isPresent()){
			this.workplaceId = affWorkplaceOpt.get().getWorkplaceId();
		}
		
		// 「締め」　取得
		this.closureOpt = repositories.getClosure().findById(companyId, closureId.value);
		if (closureOpt.isPresent()){
			val closure = closureOpt.get();
			if (closure.getUseClassification() != UseClassification.UseClass_Use) this.closureOpt = Optional.empty();
		}
		
		// 通常勤務月別実績集計設定　取得　（基準：期間終了日）
		val regularAggrSetOpt = repositories.getRegularAggrSet().get(
				companyId, this.workplaceId, this.employmentCd, employeeId, procPeriod.end());
		if (!regularAggrSetOpt.isPresent()){
			this.errorInfos.add(new MonthlyAggregationErrorInfo(
					"002", new ErrMessageContent("通常勤務月別実績集計設定が取得できません。")));
			return;
		}
		this.settingsByReg.setRegularAggrSet(regularAggrSetOpt.get());

		// 変形労働月別実績集計設定　取得　（基準：期間終了日）
		val deforAggrSetOpt = repositories.getDeforAggrSet().get(
				companyId, this.workplaceId, this.employmentCd, employeeId, procPeriod.end());
		if (!deforAggrSetOpt.isPresent()){
			this.errorInfos.add(new MonthlyAggregationErrorInfo(
					"002", new ErrMessageContent("変形労働月別実績集計設定が取得できません。")));
			return;
		}
		this.settingsByDefo.setDeforAggrSet(deforAggrSetOpt.get());

		// フレックス月別実績集計設定　取得　（基準：期間終了日）
		val flexAggrSetOpt = repositories.getFlexAggrSet().get(
				companyId, this.workplaceId, this.employmentCd, employeeId, procPeriod.end());
		if (!flexAggrSetOpt.isPresent()){
			this.errorInfos.add(new MonthlyAggregationErrorInfo(
					"002", new ErrMessageContent("フレックス月別実績集計設定が取得できません。")));
			return;
		}
		this.settingsByFlex.setFlexAggrSet(flexAggrSetOpt.get());
		
		// 法定内振替順設定　取得
		val legalTransferOrderSetOpt = repositories.getLegalTransferOrderSetOfAggrMonthly().find(companyId);
		if (legalTransferOrderSetOpt.isPresent()){
			this.settingsByReg.setLegalTransferOrderSet(legalTransferOrderSetOpt.get());
			this.settingsByDefo.setLegalTransferOrderSet(legalTransferOrderSetOpt.get());
		}
		else {
			this.errorInfos.add(new MonthlyAggregationErrorInfo(
					"002", new ErrMessageContent(TextResource.localize("Msg_1232"))));
			return;
		}

		// 残業枠の役割　取得
		val roleOverTimeFrameList = repositories.getRoleOverTimeFrame().findByCID(companyId);
		for (val roleOverTimeFrame : roleOverTimeFrameList){
			this.settingsByReg.getRoleOverTimeFrameMap().putIfAbsent(
					roleOverTimeFrame.getOvertimeFrNo().v(), roleOverTimeFrame);
			this.settingsByDefo.getRoleOverTimeFrameMap().putIfAbsent(
					roleOverTimeFrame.getOvertimeFrNo().v(), roleOverTimeFrame);
			
			// 自動的に除く残業枠　取得
			if (roleOverTimeFrame.getRoleOTWorkEnum() != RoleOvertimeWorkEnum.MIX_IN_OUT_STATUTORY) continue;
			this.settingsByReg.getAutoExceptOverTimeFrames().add(roleOverTimeFrame);
			this.settingsByDefo.getAutoExceptOverTimeFrames().add(roleOverTimeFrame);
		}
		
		// 休出枠の役割　取得
		val roleHolidayWorkFrameList = repositories.getRoleHolidayWorkFrame().findByCID(companyId);
		for (val roleHolidayWorkFrame : roleHolidayWorkFrameList){
			this.settingsByReg.getRoleHolidayWorkFrameMap().putIfAbsent(
					roleHolidayWorkFrame.getBreakoutFrNo().v(), roleHolidayWorkFrame);
			this.settingsByDefo.getRoleHolidayWorkFrameMap().putIfAbsent(
					roleHolidayWorkFrame.getBreakoutFrNo().v(), roleHolidayWorkFrame);
			
			// 自動的に除く休出枠　取得
			if (roleHolidayWorkFrame.getRoleOfOpenPeriodEnum() != RoleOfOpenPeriodEnum.MIX_WITHIN_OUTSIDE_STATUTORY) continue;
			this.settingsByReg.getAutoExceptHolidayWorkFrames().add(roleHolidayWorkFrame);
			this.settingsByDefo.getAutoExceptHolidayWorkFrames().add(roleHolidayWorkFrame);
		}
		
		// 休暇加算時間設定　取得
		val holidayAdditionMap = repositories.getHolidayAddition().findByCompanyId(companyId);
		this.settingsByReg.getHolidayAdditionMap().putAll(holidayAdditionMap);
		this.settingsByDefo.getHolidayAdditionMap().putAll(holidayAdditionMap);
		this.settingsByFlex.getHolidayAdditionMap().putAll(holidayAdditionMap);
		
		// フレックス勤務の月別集計設定
		this.settingsByFlex.setMonthlyAggrSetOfFlexOpt(repositories.getMonthlyAggrSetOfFlex().find(companyId));
		if (!this.settingsByFlex.getMonthlyAggrSetOfFlexOpt().isPresent()){
			this.errorInfos.add(new MonthlyAggregationErrorInfo(
					"002", new ErrMessageContent(TextResource.localize("Msg_1238"))));
			return;
		}
		
		// フレックス勤務所定労働時間取得
		this.settingsByFlex.setGetFlexPredWorkTimeOpt(repositories.getFlexPredWorktime().find(companyId));
		if (!this.settingsByFlex.getGetFlexPredWorkTimeOpt().isPresent()){
			this.errorInfos.add(new MonthlyAggregationErrorInfo(
					"002", new ErrMessageContent(TextResource.localize("Msg_1243"))));
			return;
		}
		
		// 通常の取得期間を　開始日-6日～終了日　とする　（開始週の集計のため）
		DatePeriod findPeriod = new DatePeriod(procPeriod.start().addDays(-6), procPeriod.end());

		// 日別実績の勤怠時間　取得
		if (attendanceTimeOfDailysOpt.isPresent()){
			for (val attendanceTimeOfDaily : attendanceTimeOfDailysOpt.get()){
				this.attendanceTimeOfDailyMap.putIfAbsent(attendanceTimeOfDaily.getYmd(), attendanceTimeOfDaily);
			}
			// 日別実績を渡された時は、渡された期間を適用する
			findPeriod = procPeriod;
		}
		else {
			val attendanceTimeOfDailys =
					repositories.getAttendanceTimeOfDaily().findByPeriodOrderByYmd(employeeId, findPeriod);
			for (val attendanceTimeOfDaily : attendanceTimeOfDailys){
				this.attendanceTimeOfDailyMap.putIfAbsent(attendanceTimeOfDaily.getYmd(), attendanceTimeOfDaily);
			}
		}
		
		// 日別実績の勤務情報　取得
		val workInformationOfDailys =
				repositories.getWorkInformationOfDaily().findByPeriodOrderByYmd(employeeId, findPeriod);
		for (val workInformationOfDaily : workInformationOfDailys){
			val workInfo = workInformationOfDaily.getRecordInfo();
			this.workInformationOfDailyMap.putIfAbsent(workInformationOfDaily.getYmd(), workInfo);
		}

		// 週間、月間法定・所定労働時間　取得
		switch (this.workingSystem){
		case REGULAR_WORK:
		case VARIABLE_WORKING_TIME_WORK:
			val monAndWeekStatTimeOpt = repositories.getMonthlyStatutoryWorkingHours().getMonAndWeekStatutoryTime(
					companyId, this.employmentCd, employeeId, procPeriod.end(), yearMonth, this.workingSystem);
			if (!monAndWeekStatTimeOpt.isPresent()){
				this.errorInfos.add(new MonthlyAggregationErrorInfo(
						"002", new ErrMessageContent("法定労働時間が取得できません。")));
				break;
			}
			val monAndWeekStatTime = monAndWeekStatTimeOpt.get();
			int weekMinutes = monAndWeekStatTime.getWeeklyEstimateTime().v();
			int monthMinutes = monAndWeekStatTime.getMonthlyEstimateTime().v();
			this.statutoryWorkingTime = new AttendanceTimeMonth(monthMinutes);
			this.settingsByReg.setStatutoryWorkingTimeWeek(new AttendanceTimeMonth(weekMinutes));
			this.settingsByReg.setStatutoryWorkingTimeMonth(new AttendanceTimeMonth(monthMinutes));
			this.settingsByDefo.setStatutoryWorkingTimeWeek(new AttendanceTimeMonth(weekMinutes));
			this.settingsByDefo.setStatutoryWorkingTimeMonth(new AttendanceTimeMonth(monthMinutes));
			break;
		case FLEX_TIME_WORK:
			val flexMonAndWeekStatTime = repositories.getMonthlyStatutoryWorkingHours().getFlexMonAndWeekStatutoryTime(
					companyId, this.employmentCd, employeeId, procPeriod.end(), yearMonth);
			int statMinutes = flexMonAndWeekStatTime.getStatutorySetting().v();
			int predMinutes = flexMonAndWeekStatTime.getSpecifiedSetting().v();
			this.statutoryWorkingTime = new AttendanceTimeMonth(statMinutes);
			this.settingsByFlex.setStatutoryWorkingTimeMonth(new AttendanceTimeMonth(statMinutes));
			this.settingsByFlex.setPrescribedWorkingTimeMonth(new AttendanceTimeMonth(predMinutes));
			break;
		default:
			this.statutoryWorkingTime = new AttendanceTimeMonth(0);
			break;
		}
		
		// 月別実績の勤怠時間　既存データ　取得
		this.originalData = repositories.getAttendanceTimeOfMonthly().find(
				employeeId, yearMonth, closureId, closureDate);
		
		// 年度　設定
		this.year = new Year(this.yearMonth.year());
	}
	
	/**
	 * 履歴ごとに月別実績を集計する
	 * @param aggrPeriod 集計期間
	 * @param aggrAtr 集計区分
	 * @param annualLeaveDeductDays 年休控除日数
	 * @param absenceDeductTime 欠勤控除時間
	 * @param repositories 月次集計が必要とするリポジトリ
	 */
	public void aggregate(DatePeriod aggrPeriod, MonthlyAggregateAtr aggrAtr,
			Optional<AttendanceDaysMonth> annualLeaveDeductDays,
			Optional<AttendanceTimeMonth> absenceDeductTime,
			RepositoriesRequiredByMonthlyAggr repositories){
		
		// 集計結果　初期化
		this.actualWorkingTime = new RegularAndIrregularTimeOfMonthly();
		this.flexTime = new FlexTimeOfMonthly();
		this.aggregateTime = new AggregateTotalWorkingTime();
		this.totalTimeSpentAtWork = new AggregateTotalTimeSpentAtWork();

		// 既存データの復元
		this.restoreOriginalData(annualLeaveDeductDays, absenceDeductTime);

		// 不正呼び出しの時、集計しない
		if (this.workingConditionItem == null) return;
		
		// 共有項目を集計する
		this.aggregateTime.aggregateSharedItem(aggrPeriod, this.attendanceTimeOfDailyMap);
		
		// 通常勤務　or　変形労働　の時
		if (this.workingSystem == WorkingSystem.REGULAR_WORK ||
				this.workingSystem == WorkingSystem.VARIABLE_WORKING_TIME_WORK){
			
			// 通常・変形労働勤務の月別実績を集計する
			val aggrValue = this.actualWorkingTime.aggregateMonthly(this.companyId, this.employeeId,
					this.yearMonth, aggrPeriod, this.workingSystem, this.closureOpt, aggrAtr,
					this.settingsByReg, this.settingsByDefo,
					this.attendanceTimeOfDailyMap, this.workInformationOfDailyMap,
					this.aggregateTime, null, repositories);
			this.aggregateTime = aggrValue.getAggregateTotalWorkingTime();
			
			// 通常・変形労働勤務の月単位の時間を集計する
			this.actualWorkingTime.aggregateMonthlyHours(this.companyId, this.employeeId,
					this.yearMonth, this.closureId, this.closureDate, aggrPeriod, this.workingSystem,
					aggrAtr, this.isRetireMonth, this.workplaceId, this.employmentCd,
					this.settingsByReg, this.settingsByDefo, this.aggregateTime, repositories);
		}
		// フレックス時間勤務　の時
		else if (this.workingSystem == WorkingSystem.FLEX_TIME_WORK){
			
			// フレックス集計方法を取得する
			val flexAggrMethod = this.settingsByFlex.getFlexAggrSet().getAggrMethod();

			// フレックス勤務の月別実績を集計する
			val aggrValue = this.flexTime.aggregateMonthly(this.companyId, this.employeeId,
					this.yearMonth, aggrPeriod, this.workingSystem, aggrAtr, flexAggrMethod,
					this.settingsByFlex, this.attendanceTimeOfDailyMap, this.aggregateTime, null,
					repositories);
			this.aggregateTime = aggrValue.getAggregateTotalWorkingTime();
			
			// フレックス勤務の月単位の時間を集計する
			this.flexTime.aggregateMonthlyHours(this.companyId, this.employeeId,
					this.yearMonth, aggrPeriod, flexAggrMethod, this.workingConditionItem,
					this.workplaceId, this.employmentCd,
					this.settingsByFlex, this.aggregateTime,
					repositories);
		}

		// 実働時間の集計
		this.aggregateTime.aggregateActualWorkingTime(aggrPeriod, this.workingSystem,
				this.actualWorkingTime, this.flexTime);
		
		// フレックス時間勤務の時
		if (this.workingSystem == WorkingSystem.FLEX_TIME_WORK){
			
			// 年休使用時間に加算する
			this.addAnnualLeaveUseTime();
			
			// 控除時間が余分に入れられていないか確認する
			this.checkDeductTime();
		}
		
		// 総労働時間を計算
		this.calcTotalWorkingTime();
		
		// 管理期間の36協定時間の作成
		this.agreementTimeOfManagePeriod = new AgreementTimeOfManagePeriod(this.employeeId, this.yearMonth);
		this.agreementTimeOfManagePeriod.aggregate(this.companyId, this.year, aggrPeriod.end(),
				aggrAtr, this, repositories);
		
		// 月別実績の36協定へ値を移送
		this.agreementTime = this.agreementTimeOfManagePeriod.getAgreementTime();
	}
	
	/**
	 * 既存データの復元
	 * @param annualDeductDays 年休控除日数
	 * @param absenceDeductTime 欠勤控除時間
	 */
	private void restoreOriginalData(
			Optional<AttendanceDaysMonth> annualDeductDays,
			Optional<AttendanceTimeMonth> absenceDeductTime){
		
		// 年休控除日数・欠勤控除時間
		AttendanceDaysMonth applyAnnualDeductDays = new AttendanceDaysMonth(0.0);
		AttendanceTimeMonth applyAbsenceDeductTime = new AttendanceTimeMonth(0);
		if (annualDeductDays.isPresent() || absenceDeductTime.isPresent()){
			if (annualDeductDays.isPresent()) applyAnnualDeductDays = annualDeductDays.get();
			if (absenceDeductTime.isPresent()) applyAbsenceDeductTime = absenceDeductTime.get();
		}
		else if (this.originalData.isPresent()){
			val monthlyCalculation = this.originalData.get().getMonthlyCalculation();
			val flexShortDeductTime = monthlyCalculation.getFlexTime().getFlexShortDeductTime();
			applyAnnualDeductDays = flexShortDeductTime.getAnnualLeaveDeductDays();
			applyAbsenceDeductTime = flexShortDeductTime.getAbsenceDeductTime();
		}
		this.flexTime.getFlexShortDeductTime().setAnnualLeaveDeductDays(applyAnnualDeductDays);
		this.flexTime.getFlexShortDeductTime().setAbsenceDeductTime(applyAbsenceDeductTime);
	}
	
	/**
	 * 年休使用時間に加算する
	 */
	private void addAnnualLeaveUseTime(){
		
		// 控除後の結果にエラーがある時、加算しない
		val afterDeduct = this.flexTime.getDeductDaysAndTime();
		if (afterDeduct.getErrorInfos().size() > 0) return;
		if (!afterDeduct.getPredetermineTimeSetOfWeekDay().isPresent()) return;

		// 控除前の年休控除時間を取得する
		val beforeDeductTime = this.flexTime.getAnnualLeaveTimeBeforeDeduct();
		
		// 控除前の年休控除時間を年休使用時間に加算する
		val annualLeave = this.aggregateTime.getVacationUseTime().getAnnualLeave();
		annualLeave.addMinuteToUseTime(beforeDeductTime.v());
	}
	
	/**
	 * 控除時間が余分に入れられていないか確認する
	 */
	public void checkDeductTime(){
		
		// 控除後の結果にエラーがある時、確認しない
		val afterDeduct = this.flexTime.getDeductDaysAndTime();
		if (afterDeduct.getErrorInfos().size() > 0) return;
		if (!afterDeduct.getPredetermineTimeSetOfWeekDay().isPresent()) return;
		
		// 控除時間が余分に入力されていないか確認する
		val predetermineTimeSet = afterDeduct.getPredetermineTimeSetOfWeekDay().get();
		val predAddTimeAM = predetermineTimeSet.getPredTime().getAddTime().getMorning();
		boolean isExtraTime = false;
		if (afterDeduct.getAnnualLeaveDeductTime().greaterThanOrEqualTo(predAddTimeAM.v())){
			isExtraTime = true;
		}
		else if (afterDeduct.getAbsenceDeductTime().greaterThan(0)){
			isExtraTime = true;
		}
		if (isExtraTime){
			
			// 「余分な控除時間のエラーフラグ」をtrueにする
			this.flexTime.getFlexShortDeductTime().setErrorAtrOfExtraDeductTime(true);
		}
	}
	
	/**
	 * 総労働時間の計算
	 * @param datePeriod 期間
	 */
	private void calcTotalWorkingTime(){

		this.totalWorkingTime = new AttendanceTimeMonth(this.aggregateTime.getTotalWorkingTargetTime().v() +
				this.actualWorkingTime.getTotalWorkingTargetTime().v() +
				this.flexTime.getTotalWorkingTargetTime().v());
	}
	
	/**
	 * 36協定時間の集計
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日付
	 * @param procPeriod 期間
	 * @param workingConditionItem 労働条件項目
	 * @param isRetireMonth 退職月度かどうか
	 * @param attendanceTimeOfDailysOpt 日別実績の勤怠時間リスト
	 * @param repositories 月次集計が必要とするリポジトリ
	 */
	public Optional<AgreementTimeOfManagePeriod> aggregateAgreementTime(
			String companyId, String employeeId,YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate,
			DatePeriod procPeriod, WorkingConditionItem workingConditionItem,
			Optional<List<AttendanceTimeOfDailyPerformance>> attendanceTimeOfDailysOpt,
			RepositoriesRequiredByMonthlyAggr repositories){
		
		// 36協定運用設定を取得
		val agreementOperationSetOpt = repositories.getAgreementOperationSet().find(companyId);
		if (!agreementOperationSetOpt.isPresent()) return Optional.empty();
		val agreementOperationSet = agreementOperationSetOpt.get();
		
		// 集計期間を取得
		val aggrPeriod = agreementOperationSet.getAggregatePeriod(procPeriod);
		
		// 履歴ごとに月別実績を集計する
		this.prepareAggregation(companyId, employeeId, aggrPeriod.getYearMonth(), closureId, closureDate,
				aggrPeriod.getPeriod(), workingConditionItem, attendanceTimeOfDailysOpt, repositories);
		for (val errorInfo : this.errorInfos){
			if (errorInfo.getResourceId().compareTo("002") == 0) return Optional.empty();
		}
		this.year = aggrPeriod.getYear();
		this.aggregate(aggrPeriod.getPeriod(), MonthlyAggregateAtr.EXCESS_OUTSIDE_WORK,
				Optional.empty(), Optional.empty(), repositories);

		// 管理時間の36協定時間を返す
		return Optional.of(this.agreementTimeOfManagePeriod);
	}
	
	/**
	 * 勤怠項目IDに対応する時間を取得する　（丸め処理付き）
	 * @param attendanceItemId 勤怠項目ID
	 * @param roundingSet 月別実績の丸め設定
	 * @return 勤怠月間時間
	 */
	public AttendanceTimeMonth getTimeOfAttendanceItemId(int attendanceItemId, RoundingSetOfMonthly roundingSet){

		AttendanceTimeMonth notExistTime = new AttendanceTimeMonth(0);

		val overTimeMap = this.aggregateTime.getOverTime().getAggregateOverTimeMap();
		val hdwkTimeMap = this.aggregateTime.getHolidayWorkTime().getAggregateHolidayWorkTimeMap();
		
		// 就業時間
		if (attendanceItemId == AttendanceItemOfMonthly.WORK_TIME.value){
			val workTime = this.aggregateTime.getWorkTime().getWorkTime();
			return roundingSet.itemRound(attendanceItemId, workTime);
		}
		
		// 残業時間
		if (attendanceItemId >= AttendanceItemOfMonthly.OVER_TIME_01.value &&
			attendanceItemId <= AttendanceItemOfMonthly.OVER_TIME_10.value){
			val overTimeFrameNo = new OverTimeFrameNo(
					attendanceItemId - AttendanceItemOfMonthly.OVER_TIME_01.value + 1);
			if (!overTimeMap.containsKey(overTimeFrameNo)) return notExistTime;
			return roundingSet.itemRound(attendanceItemId,
					overTimeMap.get(overTimeFrameNo).getOverTime().getTime());
		}
		
		// 計算残業時間
		if (attendanceItemId >= AttendanceItemOfMonthly.CALC_OVER_TIME_01.value &&
			attendanceItemId <= AttendanceItemOfMonthly.CALC_OVER_TIME_10.value){
			val overTimeFrameNo = new OverTimeFrameNo(
					attendanceItemId - AttendanceItemOfMonthly.CALC_OVER_TIME_01.value + 1);
			if (!overTimeMap.containsKey(overTimeFrameNo)) return notExistTime;
			return roundingSet.itemRound(attendanceItemId,
					overTimeMap.get(overTimeFrameNo).getOverTime().getCalcTime());
		}
		
		// 振替残業時間
		if (attendanceItemId >= AttendanceItemOfMonthly.TRANSFER_OVER_TIME_01.value &&
			attendanceItemId <= AttendanceItemOfMonthly.TRANSFER_OVER_TIME_10.value){
			val overTimeFrameNo = new OverTimeFrameNo(
					attendanceItemId - AttendanceItemOfMonthly.TRANSFER_OVER_TIME_01.value + 1);
			if (!overTimeMap.containsKey(overTimeFrameNo)) return notExistTime;
			return roundingSet.itemRound(attendanceItemId,
					overTimeMap.get(overTimeFrameNo).getTransferOverTime().getTime());
		}
		
		// 計算振替残業時間
		if (attendanceItemId >= AttendanceItemOfMonthly.CALC_TRANSFER_OVER_TIME_01.value &&
			attendanceItemId <= AttendanceItemOfMonthly.CALC_TRANSFER_OVER_TIME_10.value){
			val overTimeFrameNo = new OverTimeFrameNo(
					attendanceItemId - AttendanceItemOfMonthly.CALC_TRANSFER_OVER_TIME_01.value + 1);
			if (!overTimeMap.containsKey(overTimeFrameNo)) return notExistTime;
			return roundingSet.itemRound(attendanceItemId,
					overTimeMap.get(overTimeFrameNo).getTransferOverTime().getCalcTime());
		}
		
		// 休出時間
		if (attendanceItemId >= AttendanceItemOfMonthly.HOLIDAY_WORK_TIME_01.value &&
			attendanceItemId <= AttendanceItemOfMonthly.HOLIDAY_WORK_TIME_10.value){
			val holidayWorkTimeFrameNo = new HolidayWorkFrameNo(
					attendanceItemId - AttendanceItemOfMonthly.HOLIDAY_WORK_TIME_01.value + 1);
			if (!hdwkTimeMap.containsKey(holidayWorkTimeFrameNo)) return notExistTime;
			return roundingSet.itemRound(attendanceItemId,
					hdwkTimeMap.get(holidayWorkTimeFrameNo).getHolidayWorkTime().getTime());
		}
		
		// 計算休出時間
		if (attendanceItemId >= AttendanceItemOfMonthly.CALC_HOLIDAY_WORK_TIME_01.value &&
			attendanceItemId <= AttendanceItemOfMonthly.CALC_HOLIDAY_WORK_TIME_10.value){
			val holidayWorkTimeFrameNo = new HolidayWorkFrameNo(
					attendanceItemId - AttendanceItemOfMonthly.CALC_HOLIDAY_WORK_TIME_01.value + 1);
			if (!hdwkTimeMap.containsKey(holidayWorkTimeFrameNo)) return notExistTime;
			return roundingSet.itemRound(attendanceItemId,
					hdwkTimeMap.get(holidayWorkTimeFrameNo).getHolidayWorkTime().getCalcTime());
		}
		
		// 振替時間
		if (attendanceItemId >= AttendanceItemOfMonthly.TRANSFER_TIME_01.value &&
			attendanceItemId <= AttendanceItemOfMonthly.TRANSFER_TIME_10.value){
			val holidayWorkTimeFrameNo = new HolidayWorkFrameNo(
					attendanceItemId - AttendanceItemOfMonthly.TRANSFER_TIME_01.value + 1);
			if (!hdwkTimeMap.containsKey(holidayWorkTimeFrameNo)) return notExistTime;
			return roundingSet.itemRound(attendanceItemId,
					hdwkTimeMap.get(holidayWorkTimeFrameNo).getTransferTime().getTime());
		}
		
		// 計算振替時間
		if (attendanceItemId >= AttendanceItemOfMonthly.CALC_TRANSFER_TIME_01.value &&
			attendanceItemId <= AttendanceItemOfMonthly.CALC_TRANSFER_TIME_10.value){
			val holidayWorkTimeFrameNo = new HolidayWorkFrameNo(
					attendanceItemId - AttendanceItemOfMonthly.CALC_TRANSFER_TIME_01.value + 1);
			if (!hdwkTimeMap.containsKey(holidayWorkTimeFrameNo)) return notExistTime;
			return roundingSet.itemRound(attendanceItemId,
					hdwkTimeMap.get(holidayWorkTimeFrameNo).getTransferTime().getCalcTime());
		}
		
		// フレックス超過時間
		if (attendanceItemId == AttendanceItemOfMonthly.FLEX_EXCESS_TIME.value){
			val flexExcessTime = this.flexTime.getFlexExcessTime();
			return roundingSet.itemRound(attendanceItemId, flexExcessTime);
		}
		
		// 所定内割増時間
		if (attendanceItemId == AttendanceItemOfMonthly.WITHIN_PRESCRIBED_PREMIUM_TIME.value){
			val withinPrescribedPremiumTime = this.aggregateTime.getWorkTime().getWithinPrescribedPremiumTime();
			return roundingSet.itemRound(attendanceItemId, withinPrescribedPremiumTime);
		}
		
		// 週割増合計時間
		if (attendanceItemId == AttendanceItemOfMonthly.WEEKLY_TOTAL_PREMIUM_TIME.value){
			val weeklyTotalPremiumTime = this.actualWorkingTime.getWeeklyTotalPremiumTime();
			return roundingSet.itemRound(attendanceItemId, weeklyTotalPremiumTime);
		}
		
		// 月割増合計時間
		if (attendanceItemId == AttendanceItemOfMonthly.MONTHLY_TOTAL_PREMIUM_TIME.value){
			val monthlyTotalPremiumTime = this.actualWorkingTime.getMonthlyTotalPremiumTime();
			return roundingSet.itemRound(attendanceItemId, monthlyTotalPremiumTime);
		}
		
		return notExistTime;
	}
	
	/**
	 * エラー情報の取得
	 * @return エラー情報リスト
	 */
	public List<MonthlyAggregationErrorInfo> getErrorInfos(){
		
		this.errorInfos.addAll(this.actualWorkingTime.getErrorInfos());
		this.errorInfos.addAll(this.flexTime.getErrorInfos());
		return this.errorInfos;
	}
	
	/**
	 * 合算する
	 * @param target 加算対象
	 */
	public void sum(MonthlyCalculation target){
		
		this.actualWorkingTime.sum(target.actualWorkingTime);
		this.flexTime.sum(target.flexTime);
		this.aggregateTime.sum(target.aggregateTime);
		this.totalWorkingTime = this.totalWorkingTime.addMinutes(target.totalWorkingTime.v());
		this.totalTimeSpentAtWork.sum(target.totalTimeSpentAtWork);
		this.agreementTime.sum(target.agreementTime);
	}
}
