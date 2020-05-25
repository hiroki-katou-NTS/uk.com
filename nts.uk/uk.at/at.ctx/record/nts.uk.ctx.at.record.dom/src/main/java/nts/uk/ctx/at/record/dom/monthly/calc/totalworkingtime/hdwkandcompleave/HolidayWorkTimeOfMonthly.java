package nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.hdwkandcompleave;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.daily.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.record.dom.daily.holidayworktime.HolidayWorkFrameTime;
import nts.uk.ctx.at.record.dom.monthly.TimeMonthWithCalculation;
import nts.uk.ctx.at.record.dom.monthly.calc.MonthlyAggregateAtr;
import nts.uk.ctx.at.record.dom.monthly.calc.flex.FlexTime;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.legaltransferorder.LegalHolidayWorkTransferOrderOfAggrMonthly;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonAggrEmployeeSettings;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.RepositoriesRequiredByMonthlyAggr;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.SettingRequiredByFlex;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.ExcessOutsideTimeSetReg;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.DailyUnit;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameRole;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.subholtransferset.HolidayWorkAndTransferAtr;
import nts.uk.ctx.at.shared.dom.worktype.HolidayAtr;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * 月別実績の休出時間
 * @author shuichi_ishida
 */
@Getter
public class HolidayWorkTimeOfMonthly implements Cloneable {

	/** 休出合計時間 */
	private TimeMonthWithCalculation totalHolidayWorkTime;
	/** 事前休出時間 */
	private AttendanceTimeMonth beforeHolidayWorkTime;
	/** 振替合計時間 */
	private TimeMonthWithCalculation totalTransferTime;
	/** 集計休出時間 */
	private Map<HolidayWorkFrameNo, AggregateHolidayWorkTime> aggregateHolidayWorkTimeMap;
	
	/**
	 * コンストラクタ
	 */
	public HolidayWorkTimeOfMonthly(){
		
		this.totalHolidayWorkTime = TimeMonthWithCalculation.ofSameTime(0);
		this.beforeHolidayWorkTime = new AttendanceTimeMonth(0);
		this.totalTransferTime = TimeMonthWithCalculation.ofSameTime(0);
		
		this.aggregateHolidayWorkTimeMap = new HashMap<>();
	}

	/**
	 * ファクトリー
	 * @param totalHolidayWorkTime 休出合計時間
	 * @param beforeHolidayWorkTime 事前休出時間
	 * @param totalTransferTime 振替合計時間
	 * @param aggregateHolidayWorkTimeList 集計休出時間
	 * @return 月別実績の休出時間
	 */
	public static HolidayWorkTimeOfMonthly of(
			TimeMonthWithCalculation totalHolidayWorkTime,
			AttendanceTimeMonth beforeHolidayWorkTime,
			TimeMonthWithCalculation totalTransferTime,
			List<AggregateHolidayWorkTime> aggregateHolidayWorkTimeList){

		val domain = new HolidayWorkTimeOfMonthly();
		domain.totalHolidayWorkTime = totalHolidayWorkTime;
		domain.beforeHolidayWorkTime = beforeHolidayWorkTime;
		domain.totalTransferTime = totalTransferTime;
		for (AggregateHolidayWorkTime aggregateHolidayWorkTime : aggregateHolidayWorkTimeList){
			val holidayWorkFrameNo = aggregateHolidayWorkTime.getHolidayWorkFrameNo();
			domain.aggregateHolidayWorkTimeMap.putIfAbsent(holidayWorkFrameNo, aggregateHolidayWorkTime);
		}
		return domain;
	}
	
	@Override
	public HolidayWorkTimeOfMonthly clone() {
		HolidayWorkTimeOfMonthly cloned = new HolidayWorkTimeOfMonthly();
		try {
			cloned.totalHolidayWorkTime = new TimeMonthWithCalculation(
					new AttendanceTimeMonth(this.totalHolidayWorkTime.getTime().v()),
					new AttendanceTimeMonth(this.totalHolidayWorkTime.getCalcTime().v()));
			cloned.beforeHolidayWorkTime = new AttendanceTimeMonth(this.beforeHolidayWorkTime.v());
			cloned.totalTransferTime = new TimeMonthWithCalculation(
					new AttendanceTimeMonth(this.totalTransferTime.getTime().v()),
					new AttendanceTimeMonth(this.totalTransferTime.getCalcTime().v()));
			for (val aggrHolidayWorkTime : this.aggregateHolidayWorkTimeMap.entrySet()){
				cloned.aggregateHolidayWorkTimeMap.putIfAbsent(
						aggrHolidayWorkTime.getKey(), aggrHolidayWorkTime.getValue().clone());
			}
		}
		catch (Exception e){
			throw new RuntimeException("HolidayWorkTimeOfMonthly clone error.");
		}
		return cloned;
	}
	
	/**
	 * 対象の集計休出時間を取得する
	 * @param holidayWorkFrameNo 休出枠NO
	 * @return 対象の集計休出時間
	 */
	private AggregateHolidayWorkTime getTargetAggregateHolidayWorkTime(HolidayWorkFrameNo holidayWorkFrameNo){

		this.aggregateHolidayWorkTimeMap.putIfAbsent(holidayWorkFrameNo, new AggregateHolidayWorkTime(holidayWorkFrameNo));
		return this.aggregateHolidayWorkTimeMap.get(holidayWorkFrameNo);
	}
	
	/**
	 * 集計する　（通常・変形労働時間勤務用）
	 * @param attendanceTimeOfDaily 日別実績の勤怠時間
	 * @param companyId 会社ID
	 * @param workplaceId 職場ID
	 * @param employmentCd 雇用コード
	 * @param workingSystem 労働制
	 * @param aggregateAtr 集計区分
	 * @param workInfo 勤務情報
	 * @param legalHolidayWorkTransferOrder 法定内休出振替順
	 * @param excessOutsideTimeSet 割増集計方法
	 * @param roleHolidayWorkFrameMap 休出枠の役割
	 * @param autoExceptHolidayWorkFrames 自動的に除く休出枠
	 * @param companySets 月別集計で必要な会社別設定
	 * @param employeeSets 月別集計で必要な社員別設定
	 * @param repositories 月次集計が必要とするリポジトリ
	 */
	public void aggregateForRegAndIrreg(AttendanceTimeOfDailyPerformance attendanceTimeOfDaily,
			String companyId, String workplaceId, String employmentCd, WorkingSystem workingSystem,
			MonthlyAggregateAtr aggregateAtr, WorkInformation workInfo,
			LegalHolidayWorkTransferOrderOfAggrMonthly legalHolidayWorkTransferOrder,
			ExcessOutsideTimeSetReg excessOutsideTimeSet,
			Map<Integer, WorkdayoffFrameRole> roleHolidayWorkFrameMap,
			List<Integer> autoExceptHolidayWorkFrames,
			MonAggrCompanySettings companySets,
			MonAggrEmployeeSettings employeeSets,
			RepositoriesRequiredByMonthlyAggr repositories){
		
		// 「休出枠」を取得する　（「休出枠の役割」を確認する）
		if (roleHolidayWorkFrameMap.size() > 0) {
			
			// 自動計算して休出時間を集計する
			this.aggregateByAutoCalc(attendanceTimeOfDaily, companyId, workplaceId, employmentCd, workingSystem,
					aggregateAtr,  workInfo, legalHolidayWorkTransferOrder, excessOutsideTimeSet,
					roleHolidayWorkFrameMap, companySets, employeeSets, repositories);
		}
		
		// 法定内休出の計算休出を休出時間の計算休出へ移送
		for (val entry : this.aggregateHolidayWorkTimeMap.entrySet()){
			for (val workEntry : entry.getValue().getTimeSeriesWorks().entrySet()){
				workEntry.getValue().addCalcLegalHWTimeToCalcHWTime();
			}
		}
	}
	
	/**
	 * 自動計算して集計する
	 * @param attendanceTimeOfDaily 日別実績の勤怠時間
	 * @param companyId 会社ID
	 * @param placeId 職場ID
	 * @param employmentCd 雇用コード
	 * @param workingSystem 労働制
	 * @param aggregateAtr 集計区分
	 * @param workInfo 勤務情報
	 * @param legalHolidayWorkTransferOrder 法定内休出振替順
	 * @param excessOutsideTimeSet 割増集計方法
	 * @param roleHolidayWorkFrameMap 休出枠の役割
	 * @param companySets 月別集計で必要な会社別設定
	 * @param employeeSets 月別集計で必要な社員別設定
	 * @param repositories 月次集計が必要とするリポジトリ
	 */
	private void aggregateByAutoCalc(AttendanceTimeOfDailyPerformance attendanceTimeOfDaily,
			String companyId, String placeId, String employmentCd, WorkingSystem workingSystem,
			MonthlyAggregateAtr aggregateAtr,
			WorkInformation workInfo,
			LegalHolidayWorkTransferOrderOfAggrMonthly legalHolidayWorkTransferOrder,
			ExcessOutsideTimeSetReg excessOutsideTimeSet,
			Map<Integer, WorkdayoffFrameRole> roleHolidayWorkFrameMap,
			MonAggrCompanySettings companySets,
			MonAggrEmployeeSettings employeeSets,
			RepositoriesRequiredByMonthlyAggr repositories){
		
		// 法定内休出にできる時間を計算する
		AttendanceTime canLegalHolidayWork = this.calcLegalHolidayWork(attendanceTimeOfDaily,
				companyId, placeId, employmentCd, workingSystem, companySets, employeeSets, repositories);
		
		// 「休出枠時間」を取得する
		val actualWorkingTimeOfDaily = attendanceTimeOfDaily.getActualWorkingTimeOfDaily();
		val totalWorkingTime = actualWorkingTimeOfDaily.getTotalWorkingTime();
		val excessOfStatutoryTimeOfDaily = totalWorkingTime.getExcessOfStatutoryTimeOfDaily();
		val holidayWorkTimeOfDaily = excessOfStatutoryTimeOfDaily.getWorkHolidayTime();
		// 休出時間がない時、集計しない
		if (!holidayWorkTimeOfDaily.isPresent()) return;
		val holidayWorkFrameTimeSrcs = holidayWorkTimeOfDaily.get().getHolidayWorkFrameTime();
			
		// 休出枠時間リストをマップに組み換え　（枠での検索用）
		Map<HolidayWorkFrameNo, HolidayWorkFrameTime> holidayWorkFrameTimes = new HashMap<>();
		for (val holidayWorkFrameTimeSrc : holidayWorkFrameTimeSrcs){
			holidayWorkFrameTimes.putIfAbsent(holidayWorkFrameTimeSrc.getHolidayFrameNo(), holidayWorkFrameTimeSrc);
		}
	
		// 休出・振替の処理順序を取得する
		if (workInfo.getWorkTimeCode() == null) return;
		val workTimeCode = workInfo.getWorkTimeCode().v();
		val holidayWorkAndTransferAtrs = repositories.getHolidayWorkAndTransferOrder().get(
				companyId, companySets.getWorkTimeCommonSetMap(workTimeCode, repositories), false);
		
		// 休出・振替のループ
		for (val holidayWorkAndTransferAtr : holidayWorkAndTransferAtrs){
		
			// 休出枠時間のループ処理
			canLegalHolidayWork = this.holidayWorkFrameTimeProcess(holidayWorkAndTransferAtr,
					legalHolidayWorkTransferOrder, excessOutsideTimeSet, canLegalHolidayWork,
					roleHolidayWorkFrameMap, holidayWorkFrameTimes, attendanceTimeOfDaily.getYmd(),
					aggregateAtr, workInfo, companySets, repositories);
		}
	}
	
	/**
	 * 法定内休出に出来る時間を計算する
	 * @param attendanceTimeOfDaily 日別実績の勤怠時間
	 * @param companyId 会社ID
	 * @param placeId 職場ID
	 * @param employmentCd 雇用コード
	 * @param workingSystem 労働制
	 * @param companySets 月別集計で必要な会社別設定
	 * @param employeeSets 月別集計で必要な社員別設定
	 * @param repositories 月次集計が必要とするリポジトリ
	 */
	private AttendanceTime calcLegalHolidayWork(AttendanceTimeOfDailyPerformance attendanceTimeOfDaily,
			String companyId, String placeId, String employmentCd, WorkingSystem workingSystem,
			MonAggrCompanySettings companySets,
			MonAggrEmployeeSettings employeeSets,
			RepositoriesRequiredByMonthlyAggr repositories){
		
		// 日の法定労働時間を取得する
		DailyUnit dailyUnit = DailyUnit.zero();
		val workTimeSetOpt = companySets.getWorkingTimeSetting(employmentCd,
				employeeSets.getWorkplacesToRoot(attendanceTimeOfDaily.getYmd()),
				workingSystem, employeeSets, repositories);
		if (workTimeSetOpt.isPresent()){
			if (workTimeSetOpt.get().getDailyTime() != null){
				dailyUnit = workTimeSetOpt.get().getDailyTime();
			}
		}
		
		// 法定内休出にできる時間
		AttendanceTime canLegalHolidayWork = new AttendanceTime(dailyUnit.getDailyTime().v());
		return canLegalHolidayWork;
	}
	
	/**
	 * 休出枠時間のループ処理
	 * @param holidayWorkAndTransferAtr 休出振替区分
	 * @param legalHolidayWorkTransferOrderOfAggrMonthly 法定内休出振替順
	 * @param excessOutsideTimeSet 割増集計方法
	 * @param canLegalHolidayWork 法定内休出に出来る時間
	 * @param roleHolidayWorkFrameMap 休出枠の役割
	 * @param holidayWorkFrameTimeMap 休出枠時間　（日別実績より取得）
	 * @param ymd 年月日
	 * @param aggregateAtr 集計区分
	 * @param workInfo 勤務情報
	 * @param companySets 月別集計で必要な会社別設定
	 * @param repositories 月次集計が必要とするリポジトリ
	 * @return 法定内休出に出来る時間　（計算後）
	 */
	private AttendanceTime holidayWorkFrameTimeProcess(
			HolidayWorkAndTransferAtr holidayWorkAndTransferAtr,
			LegalHolidayWorkTransferOrderOfAggrMonthly legalHolidayWorkTransferOrderOfAggrMonthly,
			ExcessOutsideTimeSetReg excessOutsideTimeSet,
			AttendanceTime canLegalHolidayWork,
			Map<Integer, WorkdayoffFrameRole> roleHolidayWorkFrameMap,
			Map<HolidayWorkFrameNo, HolidayWorkFrameTime> holidayWorkFrameTimeMap,
			GeneralDate ymd,
			MonthlyAggregateAtr aggregateAtr,
			WorkInformation workInfo,
			MonAggrCompanySettings companySets,
			RepositoriesRequiredByMonthlyAggr repositories){
		
		AttendanceTime timeAfterCalc = canLegalHolidayWork;
		
		// 休出枠時間分ループ
		for (val legalHolidayWorkTransferOrder : legalHolidayWorkTransferOrderOfAggrMonthly.getLegalHolidayWorkTransferOrders()){
			val holidayWorkFrameNo = legalHolidayWorkTransferOrder.getHolidayWorkFrameNo();
			
			// 該当する（日別実績の）休出枠時間がなければ、次の枠へ
			if (!holidayWorkFrameTimeMap.containsKey(holidayWorkFrameNo)) continue;
			val holidayWorkFrameTime = holidayWorkFrameTimeMap.get(holidayWorkFrameNo);
			
			// 対象の時系列ワークを確認する
			val targetHolidayWorkTime = this.getTargetAggregateHolidayWorkTime(holidayWorkFrameNo);
			val timeSeriesWork = targetHolidayWorkTime.getAndPutTimeSeriesWork(ymd);

			boolean isStatutory = false;	// 法定内処理かどうか　（false=法定外処理）	
			boolean isAddHdwk = true;		// 休出時間に入れるかどうか
			
			// 休出枠．役割を取得する
			if (!roleHolidayWorkFrameMap.containsKey(holidayWorkFrameNo.v())) continue;			// 取得できない場合
			val roleHolidayWorkFrame = roleHolidayWorkFrameMap.get(holidayWorkFrameNo.v());
			switch (roleHolidayWorkFrame){
			case MIX_WITHIN_OUTSIDE_STATUTORY:		// 法定内・外混在
				// 「日別実績の勤務情報」を取得する
				if (workInfo.getWorkTypeCode() != null){
					// 勤務種類から法定内休日か判断する
					val workType = companySets.getWorkTypeMap(workInfo.getWorkTypeCode().v(), repositories);
					if (workType != null){
						val workTypeSet = workType.getWorkTypeSet();
						if (workTypeSet != null){
							val holidayAtr = workTypeSet.getHolidayAtr();
							// 法定内休日なら、法定内処理をする　（以外なら、法定外処理になる）
							if (holidayAtr == HolidayAtr.STATUTORY_HOLIDAYS) isStatutory = true;
						}
					}
				}
				break;
				
			case STATUTORY_HOLIDAYS:		// 法定内休出
				isStatutory = true;
				break;
				
			case NON_STATUTORY_HOLIDAYS:	// 法定外休出
				isStatutory = false;
				break;
			}

			// 入れる条件の確認
			if (isStatutory){		// 法定内
				if (aggregateAtr == MonthlyAggregateAtr.EXCESS_OUTSIDE_WORK){		// 時間外超過
					// 勤務種類が法内休出の日を除く」を確認　→　true（除く）なら入れない
					if (excessOutsideTimeSet.getExceptLegalHdwk() == true) isAddHdwk = false;
				}
			}
			else{					// 法定外
				// 「法定外休出を含める」を確認　→　false（含めない）なら法定内処理にする
				if (excessOutsideTimeSet.getLegalHoliday() == false) isStatutory = true;
			}
			
			// 休出時間を入れる
			if (isAddHdwk){
				if (isStatutory){		// 法定内
					// 取得した休出枠時間を「集計休出時間」に入れる（法定内）
					switch (holidayWorkAndTransferAtr){
					case HOLIDAY_WORK:
						timeSeriesWork.addHolidayWorkTimeInHolidayWorkTime(holidayWorkFrameTime.getHolidayWorkTime().get());
						break;
					case TRANSFER:
						timeSeriesWork.addTransferTimeInHolidayWorkTime(holidayWorkFrameTime.getTransferTime().get());
						break;
					}
				}
				else {					// 法定外
					// 取得した休出枠時間を集計休出時間に入れる（法定外）
					// →　入れた時間分を「法定内休出にできる時間」から引く
					switch (holidayWorkAndTransferAtr){
					case HOLIDAY_WORK:
						AttendanceTime legalHolidayWorkTime =
							new AttendanceTime(holidayWorkFrameTime.getHolidayWorkTime().get().getTime().v());
						AttendanceTime holidayWorkTime = new AttendanceTime(0);
						if (legalHolidayWorkTime.lessThanOrEqualTo(timeAfterCalc.v())){
							// 休出時間が法定内休出にできる時間以下の時
							timeAfterCalc = timeAfterCalc.minusMinutes(legalHolidayWorkTime.v());
						}
						else {
							// 休出時間が法定内休出にできる時間を超える時
							holidayWorkTime = new AttendanceTime(legalHolidayWorkTime.v());
							holidayWorkTime = holidayWorkTime.minusMinutes(timeAfterCalc.v());
							legalHolidayWorkTime = new AttendanceTime(timeAfterCalc.v());
							timeAfterCalc = new AttendanceTime(0);
						}
						timeSeriesWork.addHolidayWorkTimeInLegalHolidayWorkTime(TimeDivergenceWithCalculation.createTimeWithCalculation(
								legalHolidayWorkTime, new AttendanceTime(0)));
						timeSeriesWork.addHolidayWorkTimeInHolidayWorkTime(TimeDivergenceWithCalculation.createTimeWithCalculation(
								holidayWorkTime, holidayWorkFrameTime.getHolidayWorkTime().get().getCalcTime()));
						break;
					case TRANSFER:
						AttendanceTime legalTransferTimeWork =
							new AttendanceTime(holidayWorkFrameTime.getTransferTime().get().getTime().v());
						AttendanceTime transferTimeWork = new AttendanceTime(0);
						if (legalTransferTimeWork.lessThanOrEqualTo(timeAfterCalc.v())){
							// 振替時間が法定内休出にできる時間以下の時
							timeAfterCalc = timeAfterCalc.minusMinutes(legalTransferTimeWork.v());
						}
						else {
							// 振替時間が法定内休出にできる時間を超える時
							transferTimeWork = new AttendanceTime(legalTransferTimeWork.v());
							transferTimeWork = transferTimeWork.minusMinutes(timeAfterCalc.v());
							legalTransferTimeWork = new AttendanceTime(timeAfterCalc.v());
							timeAfterCalc = new AttendanceTime(0);
						}
						timeSeriesWork.addTransferTimeInLegalHolidayWorkTime(TimeDivergenceWithCalculation.createTimeWithCalculation(
								legalTransferTimeWork, new AttendanceTime(0)));
						timeSeriesWork.addTransferTimeInHolidayWorkTime(TimeDivergenceWithCalculation.createTimeWithCalculation(
								transferTimeWork, holidayWorkFrameTime.getTransferTime().get().getCalcTime()));
						break;
					}
				}
			}
			
			// 取得した休出枠時間の「事前申請時間」を入れる
			if (holidayWorkAndTransferAtr == HolidayWorkAndTransferAtr.HOLIDAY_WORK){
				timeSeriesWork.addBeforeAppTimeInHolidayWorkTime(holidayWorkFrameTime.getBeforeApplicationTime().get());
			}
		}
		
		// 「法定内休出に出来る時間」を返す
		return timeAfterCalc;
	}
	
	/**
	 * 集計する　（フレックス時間勤務用）
	 * @param attendanceTimeOfDaily 日別実績の勤怠時間
	 * @param companyId 会社ID
	 * @param aggregateAtr 集計区分
	 * @param flexTime フレックス時間
	 * @param settingsByFlex フレックス勤務が必要とする設定
	 * @param workInfo 勤務情報
	 * @param companySets 月別集計で必要な会社別設定
	 * @param repositories 月次集計が必要とするリポジトリ
	 */
	public FlexTime aggregateForFlex(AttendanceTimeOfDailyPerformance attendanceTimeOfDaily,
			String companyId, MonthlyAggregateAtr aggregateAtr,
			FlexTime flexTime, SettingRequiredByFlex settingsByFlex,
			WorkInformation workInfo,
			MonAggrCompanySettings companySets,
			RepositoriesRequiredByMonthlyAggr repositories){

		val flexAggrSet = settingsByFlex.getFlexAggrSet();
		val roleHolidayWorkFrameMap = settingsByFlex.getRoleHolidayWorkFrameMap();
		
		// 「休出枠時間」を取得する（日別）
		val actualWorkingTimeOfDaily = attendanceTimeOfDaily.getActualWorkingTimeOfDaily();
		val totalWorkingTime = actualWorkingTimeOfDaily.getTotalWorkingTime();
		val excessOfStatutoryTimeOfDaily = totalWorkingTime.getExcessOfStatutoryTimeOfDaily();
		val holidayWorkTimeOfDaily = excessOfStatutoryTimeOfDaily.getWorkHolidayTime();
		// 休出時間がない時、集計しない
		if (!holidayWorkTimeOfDaily.isPresent()) return flexTime;
		
		val ymd = attendanceTimeOfDaily.getYmd();
		
		val holidayWorkFrameTimeSrcs = holidayWorkTimeOfDaily.get().getHolidayWorkFrameTime();
		for (val holidayWorkFrameTimeSrc : holidayWorkFrameTimeSrcs){
			val holidayWorkFrameNo = holidayWorkFrameTimeSrc.getHolidayFrameNo();

			// 対象の時系列ワークを確認する
			val targetHolidayWorkTime = this.getTargetAggregateHolidayWorkTime(holidayWorkFrameNo);
			val timeSeriesWork = targetHolidayWorkTime.getAndPutTimeSeriesWork(ymd);
			
			boolean isStatutory = false;	// 法定内処理かどうか　（false=法定外処理）	
			boolean isAddHdwk = true;		// 休出時間に入れるかどうか
			
			// 「休出枠．役割」を取得する
			if (!roleHolidayWorkFrameMap.containsKey(holidayWorkFrameNo.v())) continue;			// 取得できない場合
			val roleHolidayWorkFrame = roleHolidayWorkFrameMap.get(holidayWorkFrameNo.v());
			switch (roleHolidayWorkFrame){
			case MIX_WITHIN_OUTSIDE_STATUTORY:		// 法定内・外混在
				// 「日別実績の勤務情報」を取得する
				if (workInfo.getWorkTypeCode() != null){
					// 勤務種類から法定内休日か判断する
					val workType = companySets.getWorkTypeMap(workInfo.getWorkTypeCode().v(), repositories);
					if (workType != null){
						val workTypeSet = workType.getWorkTypeSet();
						if (workTypeSet != null){
							val holidayAtr = workTypeSet.getHolidayAtr();
							// 法定内休日なら、法定内処理をする　（以外なら、法定外処理になる）
							if (holidayAtr == HolidayAtr.STATUTORY_HOLIDAYS) isStatutory = true;
						}
					}
				}
				break;
				
			case STATUTORY_HOLIDAYS:		// 法定内休出
				isAddHdwk = false;
				break;
				
			case NON_STATUTORY_HOLIDAYS:	// 法定外休出
				isStatutory = false;
				break;
			}

			// 入れる条件の確認
			if (isStatutory == false){		// 法定外
				// 「フレックス時間の扱い．法定外休出時間をフレックス時間に含める」を取得する　→　false（含めない）なら法定内処理にする
				if (flexAggrSet.getIncludeIllegalHdwk() == false) isStatutory = true;
			}
			
			// 休出時間を入れる
			if (isAddHdwk){
				if (isStatutory){		// 法定内
					// 取得した休出枠時間を「集計休出時間」に入れる
					timeSeriesWork.addHolidayWorkTimeInHolidayWorkTime(holidayWorkFrameTimeSrc.getHolidayWorkTime().get());
					timeSeriesWork.addTransferTimeInHolidayWorkTime(holidayWorkFrameTimeSrc.getTransferTime().get());
				}
				else {					// 法定外
					// 取得した休出枠時間を「集計休出時間」に入れる　（法定内休出時間）
					timeSeriesWork.addHolidayWorkTimeInLegalHolidayWorkTime(holidayWorkFrameTimeSrc.getHolidayWorkTime().get());
					timeSeriesWork.addTransferTimeInLegalHolidayWorkTime(holidayWorkFrameTimeSrc.getTransferTime().get());
					
					// 取得した休出枠時間を「フレックス時間」に入れる
					flexTime.addHolidayWorkTimeFrameTime(ymd, holidayWorkFrameTimeSrc);
				}
			}
			
			// 取得した休出枠時間の「事前申請時間」を入れる
			timeSeriesWork.addBeforeAppTimeInHolidayWorkTime(holidayWorkFrameTimeSrc.getBeforeApplicationTime().get());
		}
		
		return flexTime;
	}
	
	/**
	 * 休日出勤の集計　（期間別集計用）
	 * @param datePeriod 期間
	 * @param attendanceTimeOfDailyMap 日別実績の勤怠時間リスト
	 * @param roleHolidayWorkFrameMap 休出枠の役割
	 */
	public void aggregateForByPeriod(
			DatePeriod datePeriod,
			Map<GeneralDate, AttendanceTimeOfDailyPerformance> attendanceTimeOfDailyMap,
			Map<Integer, WorkdayoffFrameRole> roleHolidayWorkFrameMap){
		
		// 休出時間を縦計する
		for (val attendanceTimeOfDaily : attendanceTimeOfDailyMap.values()) {
			val ymd = attendanceTimeOfDaily.getYmd();
			
			// 期間外はスキップする
			if (!datePeriod.contains(ymd)) continue;
			
			// 「休出枠時間」を取得する
			val actualWorkingTimeOfDaily = attendanceTimeOfDaily.getActualWorkingTimeOfDaily();
			val totalWorkingTime = actualWorkingTimeOfDaily.getTotalWorkingTime();
			val excessOfStatutoryTimeOfDaily = totalWorkingTime.getExcessOfStatutoryTimeOfDaily();
			val holidayWorkTimeOfDailyOpt = excessOfStatutoryTimeOfDaily.getWorkHolidayTime();
			if (!holidayWorkTimeOfDailyOpt.isPresent()) continue;
			val holidayWorkTimeFrames = holidayWorkTimeOfDailyOpt.get().getHolidayWorkFrameTime();
			
			// 取得した「休出枠時間」を「集計休出時間」に入れる
			for (val holidayWorkTimeFrame : holidayWorkTimeFrames){
				int frameNo = holidayWorkTimeFrame.getHolidayFrameNo().v();
				val target = this.getTargetAggregateHolidayWorkTime(new HolidayWorkFrameNo(frameNo));
				target.addHolidayWorkTimeInTimeSeriesWork(ymd, holidayWorkTimeFrame);
			}
		}
		
		// 休出合計時間を集計する
		this.aggregateTotal(datePeriod);
	}
	
	/**
	 * 法定内休出時間を取得する
	 * @param datePeriod 期間
	 * @return 法定内休出時間
	 */
	public AttendanceTimeMonth getLegalHolidayWorkTime(DatePeriod datePeriod){

		AttendanceTimeMonth returnTime = new AttendanceTimeMonth(0);

		// 法定内休出時間．休出時間　＋　法定内休出時間．振替時間　の合計
		for (val aggregateHolidayWorkTime : this.aggregateHolidayWorkTimeMap.values()){
			for (val timeSeriesWork : aggregateHolidayWorkTime.getTimeSeriesWorks().values()){
				if (!datePeriod.contains(timeSeriesWork.getYmd())) continue;
				val legalHolidayWorkTime = timeSeriesWork.getLegalHolidayWorkTime();
				returnTime = returnTime.addMinutes(legalHolidayWorkTime.getHolidayWorkTime().get().getTime().v());
				returnTime = returnTime.addMinutes(legalHolidayWorkTime.getTransferTime().get().getTime().v());
			}
		}
		
		return returnTime;
	}
	
	/**
	 * 休出合計時間を集計する
	 * @param datePeriod 期間
	 */
	public void aggregateTotal(DatePeriod datePeriod){

		this.totalHolidayWorkTime = TimeMonthWithCalculation.ofSameTime(0);
		this.beforeHolidayWorkTime = new AttendanceTimeMonth(0);
		this.totalTransferTime = TimeMonthWithCalculation.ofSameTime(0);

		for (val aggregateHolidayWorkTime : this.aggregateHolidayWorkTimeMap.values()){
			aggregateHolidayWorkTime.aggregate(datePeriod);
			this.totalHolidayWorkTime = this.totalHolidayWorkTime.addMinutes(
					aggregateHolidayWorkTime.getHolidayWorkTime().getTime().v(),
					aggregateHolidayWorkTime.getHolidayWorkTime().getCalcTime().v());
			this.beforeHolidayWorkTime = this.beforeHolidayWorkTime.addMinutes(
					aggregateHolidayWorkTime.getBeforeHolidayWorkTime().v());
			this.totalTransferTime = this.totalTransferTime.addMinutes(
					aggregateHolidayWorkTime.getTransferTime().getTime().v(),
					aggregateHolidayWorkTime.getTransferTime().getCalcTime().v());
		}
	}
	
	/**
	 * 休出合計時間を集計する　（再計算用）
	 */
	public void recalcTotal(){

		this.totalHolidayWorkTime = TimeMonthWithCalculation.ofSameTime(0);
		this.beforeHolidayWorkTime = new AttendanceTimeMonth(0);
		this.totalTransferTime = TimeMonthWithCalculation.ofSameTime(0);

		for (val aggregateHolidayWorkTime : this.aggregateHolidayWorkTimeMap.values()){
			this.totalHolidayWorkTime = this.totalHolidayWorkTime.addMinutes(
					aggregateHolidayWorkTime.getHolidayWorkTime().getTime().v(),
					aggregateHolidayWorkTime.getHolidayWorkTime().getCalcTime().v());
			this.beforeHolidayWorkTime = this.beforeHolidayWorkTime.addMinutes(
					aggregateHolidayWorkTime.getBeforeHolidayWorkTime().v());
			this.totalTransferTime = this.totalTransferTime.addMinutes(
					aggregateHolidayWorkTime.getTransferTime().getTime().v(),
					aggregateHolidayWorkTime.getTransferTime().getCalcTime().v());
		}
	}
	
	/**
	 * 総労働対象時間の取得
	 * @return 総労働対象時間
	 */
	public AttendanceTimeMonth getTotalWorkingTargetTime(){
		
		return new AttendanceTimeMonth(this.totalHolidayWorkTime.getTime().v() +
				this.totalTransferTime.getTime().v());
	}
	
	/**
	 * 合算する
	 * @param target 加算対象
	 */
	public void sum(HolidayWorkTimeOfMonthly target){
		
		this.totalHolidayWorkTime = this.totalHolidayWorkTime.addMinutes(
				target.totalHolidayWorkTime.getTime().v(), target.totalHolidayWorkTime.getCalcTime().v());
		this.beforeHolidayWorkTime = this.beforeHolidayWorkTime.addMinutes(target.beforeHolidayWorkTime.v());
		this.totalTransferTime = this.totalTransferTime.addMinutes(
				target.totalTransferTime.getTime().v(), target.totalTransferTime.getCalcTime().v());
		
		for (val aggrHolidayWorkTime : this.aggregateHolidayWorkTimeMap.values()){
			val frameNo = aggrHolidayWorkTime.getHolidayWorkFrameNo();
			if (target.aggregateHolidayWorkTimeMap.containsKey(frameNo)){
				aggrHolidayWorkTime.sum(target.aggregateHolidayWorkTimeMap.get(frameNo));
			}
		}
		for (val targetAggrHolidayWorkTime : target.aggregateHolidayWorkTimeMap.values()){
			val frameNo = targetAggrHolidayWorkTime.getHolidayWorkFrameNo();
			this.aggregateHolidayWorkTimeMap.putIfAbsent(frameNo, targetAggrHolidayWorkTime);
		}
	}
}
