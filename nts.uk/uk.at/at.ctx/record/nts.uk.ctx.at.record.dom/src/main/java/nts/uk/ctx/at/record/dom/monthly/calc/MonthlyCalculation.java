package nts.uk.ctx.at.record.dom.monthly.calc;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.monthly.calc.actualworkingtime.RegularAndIrregularTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.flex.FlexTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.AggregateTotalWorkingTime;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.AggrSettingMonthly;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.AggrSettingMonthlyOfFlx;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.legaltransferorder.LegalTransferOrderSetOfAggrMonthly;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.LegalAggrSetOfIrg;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.LegalAggrSetOfReg;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.RepositoriesRequiredByMonthlyAggr;
import nts.uk.ctx.at.record.dom.workinformation.WorkInformation;
import nts.uk.ctx.at.shared.dom.calculation.holiday.HolidayAddtion;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
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
	/** 総労働時間 */
	private AggregateTotalWorkingTime totalWorkingTime;
	/** 総拘束時間 */
	private AggregateTotalTimeSpentAtWork totalTimeSpentAtWork;

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
	private DatePeriod datePeriod;
	/** 労働制 */
	private WorkingSystem workingSystem;
	/** 職場ID */
	private String workplaceId;
	/** 雇用コード */
	private String employmentCd;
	/** 退職月度がどうか */
	private boolean isRetireMonth;
	/** 月別実績集計設定 */
	private AggrSettingMonthly aggrSettingMonthly;
	/** 月次集計の法定内振替順設定 */
	private LegalTransferOrderSetOfAggrMonthly legalTransferOrderSet;
	/** 休暇加算時間設定 */
	private Optional<HolidayAddtion> holidayAdditionOpt;
	/** 日別実績の勤怠時間リスト */
	private Map<GeneralDate, AttendanceTimeOfDailyPerformance> attendanceTimeOfDailyMap;
	/** 日別実績の勤務情報リスト */
	private Map<GeneralDate, WorkInformation> workInformationOfDailyMap;
	/** 週間法定労働時間 */
	private AttendanceTimeMonth statutoryWorkingTimeWeek;
	/** 月間法定労働時間 */
	private AttendanceTimeMonth statutoryWorkingTimeMonth;
	/** 週間所定労働時間 */
	private AttendanceTimeMonth prescribedWorkingTimeWeek;
	/** 月間所定労働時間 */
	private AttendanceTimeMonth prescribedWorkingTimeMonth;
	
	/**
	 * コンストラクタ
	 */
	public MonthlyCalculation(){

		this.actualWorkingTime = new RegularAndIrregularTimeOfMonthly();
		this.flexTime = new FlexTimeOfMonthly();
		this.statutoryWorkingTime = new AttendanceTimeMonth(0);
		this.totalWorkingTime = new AggregateTotalWorkingTime();
		this.totalTimeSpentAtWork = new AggregateTotalTimeSpentAtWork();
		
		this.companyId = "empty";
		this.employeeId = "empty";
		this.yearMonth = new YearMonth(0);
		this.closureId = ClosureId.RegularEmployee;
		this.closureDate = new ClosureDate(0, true);
		this.datePeriod = new DatePeriod(GeneralDate.today(), GeneralDate.today());
		this.workingSystem = WorkingSystem.REGULAR_WORK;
		this.workplaceId = "empty";
		this.employmentCd = "empty";
		this.isRetireMonth = false;
		this.aggrSettingMonthly = AggrSettingMonthly.of(
				new LegalAggrSetOfReg(), new LegalAggrSetOfIrg(), new AggrSettingMonthlyOfFlx());
		this.legalTransferOrderSet = new LegalTransferOrderSetOfAggrMonthly("empty");
		this.holidayAdditionOpt = Optional.empty();
		this.attendanceTimeOfDailyMap = new HashMap<>();
		this.workInformationOfDailyMap = new HashMap<>();
		this.statutoryWorkingTimeWeek = new AttendanceTimeMonth(0);
		this.statutoryWorkingTimeMonth = new AttendanceTimeMonth(0);
		this.prescribedWorkingTimeWeek = new AttendanceTimeMonth(0);
		this.prescribedWorkingTimeMonth = new AttendanceTimeMonth(0);
	}

	/**
	 * ファクトリー
	 * @param actualWorkingTime 実働時間
	 * @param flexTime フレックス時間
	 * @param statutoryWorkingTime 法定労働時間
	 * @param totalWorkingTime 総労働時間
	 * @param totalTimeSpentAtWork 総拘束時間
	 * @return 月別実績の月の計算
	 */
	public static MonthlyCalculation of(
			RegularAndIrregularTimeOfMonthly actualWorkingTime,
			FlexTimeOfMonthly flexTime,
			AttendanceTimeMonth statutoryWorkingTime,
			AggregateTotalWorkingTime totalWorkingTime,
			AggregateTotalTimeSpentAtWork totalTimeSpentAtWork){
		
		val domain = new MonthlyCalculation();
		domain.actualWorkingTime = actualWorkingTime;
		domain.flexTime = flexTime;
		domain.statutoryWorkingTime = statutoryWorkingTime;
		domain.totalWorkingTime = totalWorkingTime;
		domain.totalTimeSpentAtWork = totalTimeSpentAtWork;
		return domain;
	}
	
	/**
	 * 集計準備
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日付
	 * @param datePeriod 期間
	 * @param workingSystem 労働制
	 * @param isRetireMonth 退職月度かどうか
	 * @param repositories 月次集計が必要とするリポジトリ
	 */
	public void prepareAggregation(
			String companyId, String employeeId,YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate,
			DatePeriod datePeriod, WorkingSystem workingSystem,
			boolean isRetireMonth, RepositoriesRequiredByMonthlyAggr repositories){
		
		this.companyId = companyId;
		this.employeeId = employeeId;
		this.yearMonth = yearMonth;
		this.closureId = closureId;
		this.closureDate = closureDate;
		this.datePeriod = datePeriod;
		this.workingSystem = workingSystem;
		this.isRetireMonth = isRetireMonth;
		
		// 期間終了日時点の職場コードを取得する
		val affWorkplaceOpt = repositories.getAffWorkplaceAdapter().findBySid(employeeId, datePeriod.end());
		if (affWorkplaceOpt.isPresent()){
			this.workplaceId = affWorkplaceOpt.get().getWorkplaceId();
		}
		
		// 期間終了日時点の雇用コードを取得する
		val syEmploymentOpt = repositories.getSyEmployment().findByEmployeeId(
				companyId, employeeId, datePeriod.end());
		if (syEmploymentOpt.isPresent()){
			this.employmentCd = syEmploymentOpt.get().getEmploymentCode();
		}
		
		// 月別実績集計設定　取得　（基準：期間終了日）　（設定確認不可時は、空設定を適用）
		val aggrSettingMonthlyOpt = repositories.getAggrSettingMonthly().get(
				companyId, this.workplaceId, this.employmentCd, employeeId);
		if (aggrSettingMonthlyOpt.isPresent()){
			this.aggrSettingMonthly = aggrSettingMonthlyOpt.get();
		}
		
		// 法定内振替順設定　取得
		val legalTransferOrderSetOpt = repositories.getLegalTransferOrderSetOfAggrMonthly().find(companyId);
		this.legalTransferOrderSet = new LegalTransferOrderSetOfAggrMonthly(companyId);
		if (legalTransferOrderSetOpt.isPresent()){
			legalTransferOrderSet = legalTransferOrderSetOpt.get();
		}

		// 休暇加算時間設定　取得
		this.holidayAdditionOpt = repositories.getHolidayAddition().findByCId(companyId);
		
		// 日別実績の勤怠時間　取得
		// ※　取得期間を　開始日-6日～終了日　とする　（開始週の集計のため）
		DatePeriod findPeriod = new DatePeriod(datePeriod.start().addDays(-6), datePeriod.end());
		val attendanceTimeOfDailys =
				repositories.getAttendanceTimeOfDaily().findByPeriodOrderByYmd(employeeId, findPeriod);
		for (val attendanceTimeOfDaily : attendanceTimeOfDailys){
			this.attendanceTimeOfDailyMap.putIfAbsent(attendanceTimeOfDaily.getYmd(), attendanceTimeOfDaily);
		}
		
		// 日別実績の勤務情報　取得
		val workInformationOfDailys =
				repositories.getWorkInformationOfDaily().findByPeriodOrderByYmd(employeeId, findPeriod);
		for (val workInformationOfDaily : workInformationOfDailys){
			val workInfo = workInformationOfDaily.getRecordWorkInformation();
			this.workInformationOfDailyMap.putIfAbsent(workInformationOfDaily.getYmd(), workInfo);
		}

		// 週間、月間法定・所定労働時間　取得
		//*****（未）　日次での実装位置を確認して、合わせて実装する。
		//*****（未）　参考（日次用）。このクラスか、別のクラスに、月・週用のメソッドを追加。仮に0設定。
		//*****（未）　フレックスの場合、労働制を判断して、Month側だけに対象時間を入れる。
		/*
		repositories.getGetOfStatutoryWorkTime().getDailyTimeFromStaturoyWorkTime(WorkingSystem.RegularWork,
				companyId, workplaceId, employmentCd, employeeId, datePeriod.end());
		*/
		this.statutoryWorkingTimeWeek = new AttendanceTimeMonth(0);
		this.statutoryWorkingTimeMonth = new AttendanceTimeMonth(0);
		this.prescribedWorkingTimeWeek = new AttendanceTimeMonth(0);
		this.prescribedWorkingTimeMonth = new AttendanceTimeMonth(0);
	}
	
	/**
	 * 履歴ごとに月別実績を集計する
	 * @param repositories 月次集計が必要とするリポジトリ
	 */
	public void aggregate(RepositoriesRequiredByMonthlyAggr repositories){
		
		// 集計結果　初期化
		this.actualWorkingTime = new RegularAndIrregularTimeOfMonthly();
		this.flexTime = new FlexTimeOfMonthly();
		this.totalWorkingTime = new AggregateTotalWorkingTime();
		this.totalTimeSpentAtWork = new AggregateTotalTimeSpentAtWork();
		
		// 共有項目を集計する
		this.totalWorkingTime.aggregateSharedItem(this.datePeriod, this.attendanceTimeOfDailyMap);
		
		// 通常勤務　or　変形労働　の時
		if (this.workingSystem == WorkingSystem.REGULAR_WORK ||
				this.workingSystem == WorkingSystem.VARIABLE_WORKING_TIME_WORK){
			
			// 通常・変形労働勤務の月別実績を集計する
			val aggrValue = this.actualWorkingTime.aggregateMonthly(this.companyId, this.employeeId,
					this.yearMonth, this.datePeriod, this.workingSystem, MonthlyAggregateAtr.MONTHLY,
					this.aggrSettingMonthly, this.legalTransferOrderSet, this.holidayAdditionOpt,
					this.attendanceTimeOfDailyMap, this.workInformationOfDailyMap,
					this.statutoryWorkingTimeWeek, this.totalWorkingTime, null, repositories);
			this.totalWorkingTime = aggrValue.getAggregateTotalWorkingTime();
			
			// 通常・変形労働勤務の月単位の時間を集計する
			this.actualWorkingTime.aggregateMonthlyHours(this.companyId, this.employeeId,
					this.yearMonth, this.closureId, this.closureDate, this.datePeriod, this.workingSystem,
					MonthlyAggregateAtr.MONTHLY, this.isRetireMonth, this.workplaceId, this.employmentCd,
					this.aggrSettingMonthly, this.holidayAdditionOpt, this.totalWorkingTime,
					this.statutoryWorkingTimeMonth, repositories);
		}
		// フレックス時間勤務　の時
		else if (this.workingSystem == WorkingSystem.FLEX_TIME_WORK){
			
			// フレックス集計方法を取得する
			val aggrSetOfFlex = this.aggrSettingMonthly.getFlexWork();
			val flexAggrMethod = aggrSetOfFlex.getAggregateMethod();

			// フレックス勤務の月別実績を集計する
			val aggrValue = this.flexTime.aggregateMonthly(this.companyId, this.employeeId,
					this.yearMonth, this.datePeriod, this.workingSystem, MonthlyAggregateAtr.MONTHLY, flexAggrMethod,
					aggrSetOfFlex, this.attendanceTimeOfDailyMap, this.totalWorkingTime, null,
					this.prescribedWorkingTimeMonth, this.statutoryWorkingTimeMonth, repositories);
			this.totalWorkingTime = aggrValue.getAggregateTotalWorkingTime();
			
			// フレックス勤務の月単位の時間を集計する
			this.flexTime.aggregateMonthlyHours(this.companyId, this.employeeId, this.yearMonth, this.datePeriod, flexAggrMethod,
					this.workplaceId, this.employmentCd, aggrSetOfFlex, this.holidayAdditionOpt,
					this.totalWorkingTime, this.prescribedWorkingTimeMonth, this.statutoryWorkingTimeMonth,
					repositories);
		}

		// 実働時間の集計
		this.totalWorkingTime.aggregateActualWorkingTime(this.datePeriod, this.workingSystem,
				this.actualWorkingTime, this.flexTime);
	}
}
