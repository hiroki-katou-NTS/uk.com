package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work;

import java.util.Random;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.breakorgoout.enums.GoingOutReason;
import nts.uk.ctx.at.record.dom.monthly.AttendanceDaysMonth;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimesMonth;
import nts.uk.ctx.at.record.dom.monthly.TimeMonthWithCalculation;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.hdwkandcompleave.AggregateHolidayWorkTime;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.overtime.AggregateOverTime;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.leave.AggregateLeaveDays;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.leave.AnyLeave;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.specificdays.AggregateSpecificDays;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays.AggregateAbsenceDays;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.bonuspaytime.AggregateBonusPayTime;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.divergencetime.AggregateDivergenceTime;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.divergencetime.DivergenceAtrOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.goout.AggregateGoOut;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.medicaltime.MedicalTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.premiumtime.AggregatePremiumTime;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.excessoutside.ExcessOutsideWorkMng;
import nts.uk.ctx.at.record.dom.raisesalarytime.primitivevalue.SpecificDateItemNo;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeImport;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkTimeNightShift;
import nts.uk.ctx.at.shared.dom.worktype.CloseAtr;

/**
 * ドメインサービス：月別実績を集計する
 * @author shuichi_ishida
 */
@Stateless
public class AggregateMonthlyRecordServiceImpl implements AggregateMonthlyRecordService {

	/** 労働条件項目 */
	@Inject
	private WorkingConditionItemRepository workingConditionItemRepository;
	/** 労働条件 */
	@Inject
	private WorkingConditionRepository workingConditionRepository;
	/** 社員 */
	@Inject
	private EmpEmployeeAdapter empEmployeeAdapter;

	/** 月別集計が必要とするリポジトリ */
	@Inject
	private RepositoriesRequiredByMonthlyAggr repositories;
	
	/**
	 * 集計処理　（アルゴリズム）
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日付
	 * @param datePeriod 期間
	 * @return 集計結果
	 */
	@Override
	public AggregateMonthlyRecordValue aggregate(String companyId, String employeeId, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate, DatePeriod datePeriod) {
		
		val returnValue = new AggregateMonthlyRecordValue();
		
		// 「労働条件項目」を取得
		val workingConditionItems = this.workingConditionItemRepository.getBySidAndPeriodOrderByStrD(
				employeeId, datePeriod);
		
		// 社員を取得する
		EmployeeImport employee = null;
		employee = this.empEmployeeAdapter.findByEmpId(employeeId);
		if (employee == null){
			String errMsg = "社員データが見つかりません。　社員ID：" + employeeId;
			throw new BusinessException(new RawErrorMessage(errMsg));
		}
		
		// 項目の数だけループ
		for (val workingConditionItem : workingConditionItems){

			// 「労働条件」の該当履歴から期間を取得
			val historyId = workingConditionItem.getHistoryId();
			val workingConditionOpt = this.workingConditionRepository.getByHistoryId(historyId);
			if (!workingConditionOpt.isPresent()) continue;
			val workingCondition = workingConditionOpt.get();

			// 処理期間を計算　（処理期間と労働条件履歴期間の重複を確認する）
			val dateHistoryItems = workingCondition.getDateHistoryItem();
			if (dateHistoryItems.isEmpty()) continue;
			val term = dateHistoryItems.get(0).span();
			DatePeriod procPeriod = this.confirmProcPeriod(datePeriod, term);
			if (procPeriod == null) {
				// 履歴の期間と重複がない時
				continue;
			}
			
			// 退職月か確認する　（変形労働勤務の月単位集計：精算月判定に利用）
			boolean isRetireMonth = false;
			if (procPeriod.contains(employee.getRetiredDate())) isRetireMonth = true;
			
			// 入社前、退職後を期間から除く
			val termInOffice = new DatePeriod(employee.getEntryDate(), employee.getRetiredDate());
			procPeriod = this.confirmProcPeriod(procPeriod, termInOffice);
			if (procPeriod == null) {
				// 処理期間全体が、入社前または退職後の時
				continue;
			}
			
			// 労働制を確認する
			val workingSystem = workingConditionItem.getLaborSystem();
			
			// 月別実績の勤怠時間　初期設定
			val attendanceTime = new AttendanceTimeOfMonthly(employeeId, yearMonth, closureId, closureDate, procPeriod);
			attendanceTime.prepareAggregation(companyId, procPeriod, workingSystem, isRetireMonth, this.repositories);
			
			// 月の計算
			val monthlyCalculation = attendanceTime.getMonthlyCalculation();
			monthlyCalculation.aggregate(this.repositories);
			
			// 縦計
			val verticalTotal = attendanceTime.getVerticalTotal();
			verticalTotal.verticalTotal(companyId, employeeId, procPeriod, workingSystem, this.repositories);
			
			// 時間外超過
			ExcessOutsideWorkMng excessOutsideWorkMng = new ExcessOutsideWorkMng(monthlyCalculation);
			excessOutsideWorkMng.aggregate(this.repositories);
			attendanceTime.setExcessOutsideWork(excessOutsideWorkMng.getExcessOutsideWork());

			// 計算結果を戻り値に蓄積
			returnValue.getAttendanceTimes().add(attendanceTime);
		}
		
		//*****start（テスト shuichi_ishida）　2017.12 検収用。仮データ設定。
		/*
		Random random = new Random();
		val randomVal = random.nextInt(9) + 1;		// 1～9の乱数発生
		val attendanceTime = new AttendanceTimeOfMonthly(employeeId, yearMonth,
				closureId, closureDate, datePeriod);
		val monthlyCalculation = attendanceTime.getMonthlyCalculation();
		val totalWorkingTime = monthlyCalculation.getTotalWorkingTime();
		totalWorkingTime.getWorkTime().setWorkTime(new AttendanceTimeMonth(450 + randomVal));
		val aggrOvertime1 =	AggregateOverTime.of(
				new OverTimeFrameNo(1),
				new TimeMonthWithCalculation(
						new AttendanceTimeMonth(118),
						new AttendanceTimeMonth(120 + randomVal)),
				new AttendanceTimeMonth(0),
				TimeMonthWithCalculation.ofSameTime(0),
				new AttendanceTimeMonth(0),
				new AttendanceTimeMonth(0));
		val aggrOvertime2 =	AggregateOverTime.of(
				new OverTimeFrameNo(2),
				new TimeMonthWithCalculation(
						new AttendanceTimeMonth(236),
						new AttendanceTimeMonth(240 + randomVal)),
				new AttendanceTimeMonth(0),
				TimeMonthWithCalculation.ofSameTime(0),
				new AttendanceTimeMonth(0),
				new AttendanceTimeMonth(0));
		totalWorkingTime.getOverTime().getAggregateOverTimeMap().put(
				aggrOvertime1.getOverTimeFrameNo(), aggrOvertime1);
		if (randomVal >= 5){
			totalWorkingTime.getOverTime().getAggregateOverTimeMap().put(
					aggrOvertime2.getOverTimeFrameNo(), aggrOvertime2);
		}
		totalWorkingTime.getOverTime().setTotalOverTime(new TimeMonthWithCalculation(
				new AttendanceTimeMonth(440 + randomVal), new AttendanceTimeMonth(0)));
		val aggrHdwktime1 = AggregateHolidayWorkTime.of(
				new HolidayWorkFrameNo(1),
				new TimeMonthWithCalculation(
						new AttendanceTimeMonth(178),
						new AttendanceTimeMonth(180 + randomVal)),
				new AttendanceTimeMonth(0),
				TimeMonthWithCalculation.ofSameTime(0),
				new AttendanceTimeMonth(0),
				new AttendanceTimeMonth(0));
		val aggrHdwktime2 = AggregateHolidayWorkTime.of(
				new HolidayWorkFrameNo(2),
				new TimeMonthWithCalculation(
						new AttendanceTimeMonth(296),
						new AttendanceTimeMonth(300 + randomVal)),
				new AttendanceTimeMonth(0),
				TimeMonthWithCalculation.ofSameTime(0),
				new AttendanceTimeMonth(0),
				new AttendanceTimeMonth(0));
		totalWorkingTime.getHolidayWorkTime().getAggregateHolidayWorkTimeMap().put(
				aggrHdwktime1.getHolidayWorkFrameNo(), aggrHdwktime1);
		if (randomVal >= 6){
			totalWorkingTime.getHolidayWorkTime().getAggregateHolidayWorkTimeMap().put(
					aggrHdwktime2.getHolidayWorkFrameNo(), aggrHdwktime2);
		}
		val actualWorkingTime = monthlyCalculation.getActualWorkingTime();
		actualWorkingTime.setWeeklyTotalPremiumTime(new AttendanceTimeMonth(540 + randomVal));
		actualWorkingTime.setMonthlyTotalPremiumTime(new AttendanceTimeMonth(2460 + randomVal));
		
		// 縦計分追加　2018.2.8
		val verticalTotal = attendanceTime.getVerticalTotal();
		val vWorkTime = verticalTotal.getWorkTime();
		val vBonusPayTime = vWorkTime.getBonusPayTime();
		val vBonusPayTimeMap = vBonusPayTime.getBonusPayTime();
		val aggrBonusPayTime01 = AggregateBonusPayTime.of(1,
				new AttendanceTimeMonth(3110 + randomVal),
				new AttendanceTimeMonth(0),
				new AttendanceTimeMonth(0),
				new AttendanceTimeMonth(0));
		val aggrBonusPayTime02 = AggregateBonusPayTime.of(1,
				new AttendanceTimeMonth(3120 + randomVal),
				new AttendanceTimeMonth(0),
				new AttendanceTimeMonth(0),
				new AttendanceTimeMonth(0));
		vBonusPayTimeMap.put(1, aggrBonusPayTime01);
		if (randomVal >= 6) vBonusPayTimeMap.put(2, aggrBonusPayTime02);
		val vDivergenceTime = vWorkTime.getDivergenceTime();
		val vDivergenceTimeMap = vDivergenceTime.getDivergenceTimeList();
		val aggrDivergenceTime01 = AggregateDivergenceTime.of(1,
				new AttendanceTimeMonth(3210 + randomVal),
				new AttendanceTimeMonth(0),
				new AttendanceTimeMonth(0),
				DivergenceAtrOfMonthly.NORMAL);
		val aggrDivergenceTime02 = AggregateDivergenceTime.of(2,
				new AttendanceTimeMonth(3220 + randomVal),
				new AttendanceTimeMonth(0),
				new AttendanceTimeMonth(0),
				DivergenceAtrOfMonthly.NORMAL);
		vDivergenceTimeMap.put(1, aggrDivergenceTime01);
		if (randomVal >= 6) vDivergenceTimeMap.put(2, aggrDivergenceTime02);
		val vGoOut = vWorkTime.getGoOut();
		val vGoOuts = vGoOut.getGoOuts();
		val aggrGoOut01 = AggregateGoOut.of(GoingOutReason.PRIVATE,
				new AttendanceTimesMonth(10 + randomVal),
				TimeMonthWithCalculation.ofSameTime(0),
				TimeMonthWithCalculation.ofSameTime(0),
				TimeMonthWithCalculation.ofSameTime(0));
		val aggrGoOut02 = AggregateGoOut.of(GoingOutReason.PUBLIC,
				new AttendanceTimesMonth(20 + randomVal),
				TimeMonthWithCalculation.ofSameTime(0),
				TimeMonthWithCalculation.ofSameTime(0),
				TimeMonthWithCalculation.ofSameTime(0));
		vGoOuts.put(GoingOutReason.PRIVATE, aggrGoOut01);
		if (randomVal >= 6) vGoOuts.put(GoingOutReason.PUBLIC, aggrGoOut02);
		val vPremiumTime = vWorkTime.getPremiumTime();
		val vPremiumTimeMap = vPremiumTime.getPremiumTime();
		val aggrPremiumTime01 = AggregatePremiumTime.of(1, new AttendanceTimeMonth(3410 + randomVal));
		val aggrPremiumTime02 = AggregatePremiumTime.of(2, new AttendanceTimeMonth(3420 + randomVal));
		vPremiumTimeMap.put(1, aggrPremiumTime01);
		if (randomVal >= 6) vPremiumTimeMap.put(2, aggrPremiumTime02);
		val medicalTime = vWorkTime.getMedicalTime();
		val medicalTime01 = MedicalTimeOfMonthly.of(WorkTimeNightShift.DAY_SHIFT,
				new AttendanceTimeMonth(3510 + randomVal),
				new AttendanceTimeMonth(0),
				new AttendanceTimeMonth(0));
		val medicalTime02 = MedicalTimeOfMonthly.of(WorkTimeNightShift.NIGHT_SHIFT,
				new AttendanceTimeMonth(3520 + randomVal),
				new AttendanceTimeMonth(0),
				new AttendanceTimeMonth(0));
		medicalTime.put(WorkTimeNightShift.DAY_SHIFT, medicalTime01);
		if (randomVal >= 6) medicalTime.put(WorkTimeNightShift.NIGHT_SHIFT, medicalTime02);
		val vWorkDays = verticalTotal.getWorkDays();
		val vAbsenceDays = vWorkDays.getAbsenceDays();
		val vAbsenceDaysMap = vAbsenceDays.getAbsenceDaysList();
		val aggrAbsenceDays01 = AggregateAbsenceDays.of(1, new AttendanceDaysMonth(10.0 + randomVal));
		val aggrAbsenceDays02 = AggregateAbsenceDays.of(2, new AttendanceDaysMonth(20.0 + randomVal));
		vAbsenceDaysMap.put(1, aggrAbsenceDays01);
		if (randomVal >= 6) vAbsenceDaysMap.put(2, aggrAbsenceDays02);
		val vSpecificDays = vWorkDays.getSpecificDays();
		val vSpecificDaysMap = vSpecificDays.getSpecificDays();
		val specificDays01 = AggregateSpecificDays.of(new SpecificDateItemNo(1),
				new AttendanceDaysMonth(30.0 + randomVal),
				new AttendanceDaysMonth(0.0));
		val specificDays02 = AggregateSpecificDays.of(new SpecificDateItemNo(2),
				new AttendanceDaysMonth(40.0 + randomVal),
				new AttendanceDaysMonth(0.0));
		vSpecificDaysMap.put(new SpecificDateItemNo(1), specificDays01);
		if (randomVal >= 6) vSpecificDaysMap.put(new SpecificDateItemNo(2), specificDays02);
		val vLeave = vWorkDays.getLeave();
		val fixLeaveDays = vLeave.getFixLeaveDays();
		val anyLeaveDays = vLeave.getAnyLeaveDays();
		val aggrLeaveDays01 = AggregateLeaveDays.of(CloseAtr.PRENATAL, new AttendanceDaysMonth(10.0 + randomVal));
		val aggrLeaveDays02 = AggregateLeaveDays.of(CloseAtr.POSTPARTUM, new AttendanceDaysMonth(20.0 + randomVal));
		val anyLeave01 = AnyLeave.of(1, new AttendanceDaysMonth(30.0 + randomVal));
		val anyLeave02 = AnyLeave.of(2, new AttendanceDaysMonth(40.0 + randomVal));
		fixLeaveDays.put(CloseAtr.PRENATAL, aggrLeaveDays01);
		if (randomVal >= 6) fixLeaveDays.put(CloseAtr.POSTPARTUM, aggrLeaveDays02);
		anyLeaveDays.put(0, anyLeave01);
		if (randomVal >= 6) anyLeaveDays.put(1, anyLeave02);
		vWorkDays.getWorkDays().setDays(new AttendanceDaysMonth(20.0 +  randomVal));
		
		returnValue.getAttendanceTimes().add(attendanceTime);
		*/
		//*****end（テスト shuichi_ishida）　2017.12 検収用。仮データ設定。
		
		//*****（テスト 2017.12 shuichi_ichida）　集計設定読み込み
		/*
		val aggrSet = this.repositories.getAggrSettingMonthly().get("TESTCMP", "TESTWKP", "XM", "XESTSYA");
		val otlist = aggrSet.getRegularWork().getAggregateTimeSet().getTreatOverTimeOfLessThanCriteriaPerDay().getAutoExcludeOverTimeFrames();
		*/
		
		return returnValue;
	}	
	
	/**
	 * 処理期間との重複を確認する　（重複期間を取り出す）
	 * @param target 処理期間
	 * @param comparison 比較対象期間
	 * @return 重複期間　（null = 重複なし）
	 */
	private DatePeriod confirmProcPeriod(DatePeriod target, DatePeriod comparison){

		DatePeriod overlap = null;		// 重複期間
		
		// 開始前
		if (target.isBefore(comparison)) return overlap;
		
		// 終了後
		if (target.isAfter(comparison)) return overlap;
		
		// 重複あり
		overlap = target;
		
		// 開始日より前を除外
		if (overlap.contains(comparison.start())){
			overlap = overlap.cutOffWithNewStart(comparison.start());
		}
		
		// 終了日より後を除外
		if (overlap.contains(comparison.end())){
			overlap = overlap.cutOffWithNewEnd(comparison.end());
		}

		return overlap;
	}
}
