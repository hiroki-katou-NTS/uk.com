package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.export.totaltimes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.actualworkinghours.repository.AttendanceTimeRepository;
import nts.uk.ctx.at.record.dom.attendanceitem.util.AttendanceItemConvertFactory;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.export.attdstatus.AttendanceStatusList;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.export.workinfo.WorkInfoList;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimes;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesResult;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.UseAtr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 日別実績から回数集計結果を取得する
 * @author shuichu_ishida
 */
public class TotalTimesFromDailyRecord {

	/** 出勤状態リスト */
	private AttendanceStatusList attendanceStatusList;
	/** 勤務情報リスト */
	private WorkInfoList workInfoList;
	/** 日別実績の勤怠時間リスト */
	private Map<GeneralDate, AttendanceTimeOfDailyPerformance> attendanceTimeOfDailyMap;
	/** 勤務種類リスト */
	private Map<String, WorkType> workTypeMap;

	/**
	 * コンストラクタ
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param dailysPeriod 日別実績の期間
	 * @param attendanceTimeOfDailyRepo 日別実績の勤怠時間リポジトリ
	 * @param timeLeavingOfDailyRepo 日別実績の出退勤リポジトリ
	 * @param workInfoOfDailyRepo 日別実績の勤務情報リポジトリ
	 * @param workTypeRepo 勤務種類リポジトリ
	 */
	public TotalTimesFromDailyRecord(
			String companyId,
			String employeeId,
			DatePeriod dailysPeriod,
			AttendanceTimeRepository attendanceTimeOfDailyRepo,
			TimeLeavingOfDailyPerformanceRepository timeLeavingOfDailyRepo,
			WorkInformationRepository workInfoOfDailyRepo,
			WorkTypeRepository workTypeRepo){
		
		this.attendanceStatusList = new AttendanceStatusList(
				employeeId, dailysPeriod, attendanceTimeOfDailyRepo, timeLeavingOfDailyRepo);
		this.workInfoList = new WorkInfoList(employeeId, dailysPeriod, workInfoOfDailyRepo);
		this.setData(
				attendanceTimeOfDailyRepo.findByPeriodOrderByYmd(employeeId, dailysPeriod),
				workTypeRepo.findByCompanyId(companyId));
	}

	/**
	 * コンストラクタ
	 * @param dailysPeriod 日別実績の期間
	 * @param attendanceTimeOfDailys 日別実績の勤怠時間リスト
	 * @param timeLeavingOfDailys 日別実績の出退勤リスト
	 * @param workInfoOfDailys 日別実績の勤務情報リスト
	 * @param workTypeList 勤務種類リスト
	 */
	public TotalTimesFromDailyRecord(
			DatePeriod dailysPeriod,
			List<AttendanceTimeOfDailyPerformance> attendanceTimeOfDailys,
			List<TimeLeavingOfDailyPerformance> timeLeavingOfDailys,
			List<WorkInfoOfDailyPerformance> workInfoOfDailys,
			List<WorkType> workTypeList){
		
		this.attendanceStatusList = new AttendanceStatusList(attendanceTimeOfDailys, timeLeavingOfDailys);
		this.workInfoList = new WorkInfoList(workInfoOfDailys);
		this.setData(attendanceTimeOfDailys, workTypeList);
	}
	
	/**
	 * データ設定
	 * @param attendanceTimeOfDailys 日別実績の勤怠時間リスト
	 * @param workTypeList 勤務種類リスト
	 */
	private void setData(
			List<AttendanceTimeOfDailyPerformance> attendanceTimeOfDailys,
			List<WorkType> workTypeList){
		
		this.attendanceTimeOfDailyMap = new HashMap<>();
		this.workTypeMap = new HashMap<>();
		for (val attendanceTimeOfDaily : attendanceTimeOfDailys){
			val ymd = attendanceTimeOfDaily.getYmd();
			this.attendanceTimeOfDailyMap.putIfAbsent(ymd, attendanceTimeOfDaily);
		}
		for (val workType : workTypeList){
			val workTypeCode = workType.getWorkTypeCode();
			this.workTypeMap.putIfAbsent(workTypeCode.v(), workType);
		}
	}

	/**
	 * 回数集計結果情報を取得する
	 * @param totalTimesList 回数集計
	 * @param period 期間
	 * @param attendanceItemConverter 勤怠項目値変換
	 * @return 回数集計結果情報
	 */
	public Optional<TotalTimesResult> getResult(TotalTimes totalTimes, DatePeriod period,
			AttendanceItemConvertFactory attendanceItemConverter) {
		
		List<TotalTimes> totalTimesList = new ArrayList<>();
		totalTimesList.add(totalTimes);
		
		val results = this.getResults(totalTimesList, period, attendanceItemConverter);
		val totalCountNo = totalTimes.getTotalCountNo();
		if (!results.containsKey(totalCountNo)) return Optional.empty();
		return Optional.of(results.get(totalCountNo));
	}
	
	/**
	 * 回数集計結果情報を取得する
	 * @param totalTimesList 回数集計リスト
	 * @param period 期間
	 * @param attendanceItemConverter 勤怠項目値変換
	 * @return 回数集計結果情報マップ（回数集計NO別）
	 */
	public Map<Integer, TotalTimesResult> getResults(List<TotalTimes> totalTimesList, DatePeriod period,
			AttendanceItemConvertFactory attendanceItemConverter) {
		
		Map<Integer, TotalTimesResult> results = new HashMap<>();

		// 結果情報マップを初期化する
		for (val totalTimes : totalTimesList){
			if (totalTimes.getUseAtr() == UseAtr.NotUse) continue;
			val totalCountNo = totalTimes.getTotalCountNo();
			results.put(totalCountNo, new TotalTimesResult());
		}
		
		// 「期間」を取得
		GeneralDate procDate = period.start();
		for ( ; procDate.beforeOrEquals(period.end()); procDate = procDate.addDays(1)){

			// 勤務情報が取得できない日は、集計しない
			val workInfoOpt = this.workInfoList.getRecord(procDate);
			if (!workInfoOpt.isPresent()) continue;
			val workInfo = workInfoOpt.get();
			
			// 勤務種類が取得できない日は、集計しない
			if (workInfo.getWorkTypeCode() == null) continue;
			String workTypeCd = workInfo.getWorkTypeCode().v();
			if (!this.workTypeMap.containsKey(workTypeCd)) continue;
			val workType = this.workTypeMap.get(workTypeCd);

			// 就業時間帯を確認する
			String workTimeCd = "";
			if (workInfo.getWorkTimeCode() != null) workTimeCd = workInfo.getWorkTimeCode().v();
			
			// 「日別実績の勤怠時間」を取得
			if (!this.attendanceTimeOfDailyMap.containsKey(procDate)) continue;
			val attendanceTimeOfDaily = this.attendanceTimeOfDailyMap.get(procDate);
			
			for (val totalTimes : totalTimesList){
				if (totalTimes.getUseAtr() == UseAtr.NotUse) continue;
				val totalCountNo = totalTimes.getTotalCountNo();
				val totalCondition = totalTimes.getTotalCondition();
				val result = results.get(totalCountNo);
				
				// 勤務情報の判断
				boolean isTargetWork = false;
				if (totalTimes.getSummaryList().isPresent()){
					val summaryList = totalTimes.getSummaryList().get();
					switch (totalTimes.getSummaryAtr()){
					case COMBINATION:
						boolean isExistWorkType = false;
						for (val sumWorkType : summaryList.getWorkTypeCodes()){
							if (sumWorkType.compareTo(workTypeCd) == 0){
								isExistWorkType = true;
								break;
							}
						}
						boolean isExistWorkTime = false;
						for (val sumWorkTime : summaryList.getWorkTimeCodes()){
							if (sumWorkTime.compareTo(workTimeCd) == 0){
								isExistWorkTime = true;
								break;
							}
						}
						if (isExistWorkType && isExistWorkTime) isTargetWork = true;
						break;
					case WORKINGTIME:
						for (val sumWorkTime : summaryList.getWorkTimeCodes()){
							if (sumWorkTime.compareTo(workTimeCd) == 0){
								isTargetWork = true;
								break;
							}
						}
						break;
					case DUTYTYPE:
						for (val sumWorkType : summaryList.getWorkTypeCodes()){
							if (sumWorkType.compareTo(workTypeCd) == 0){
								isTargetWork = true;
								break;
							}
						}
						break;
					}
				}
				if (!isTargetWork) continue;
				
				// 出勤状態の判断
				boolean isTargetAttendance = false;
				switch (totalTimes.getSummaryAtr()){
				case COMBINATION:
				case WORKINGTIME:
					// 出勤状態を判断する
					if (this.attendanceStatusList.isAttendanceDay(procDate)) isTargetAttendance = true;
					break;
				case DUTYTYPE:
					isTargetAttendance = true;
					break;
				}
				if (!isTargetAttendance) continue;
				
				if (totalTimes.getTotalCondition().getAtdItemId() != null){
					
					// 日別実績を回数集計用のクラスに変換
					val dailyConverter = attendanceItemConverter.createDailyConverter();
					val dailyItems = dailyConverter.withAttendanceTime(attendanceTimeOfDaily);
					
					// 勤務時間の判断
					val itemValOpt = dailyItems.convert(totalCondition.getAtdItemId());
					if (!itemValOpt.isPresent()) continue;
					if (!itemValOpt.get().getValueType().isInteger()) continue;
					Integer itemVal = itemValOpt.get().value();
					boolean isTargetValue = true;
					if (totalCondition.getLowerLimitSettingAtr() == UseAtr.Use){
						// 下限未満なら、集計しない
						if (totalCondition.getThresoldLowerLimit() != null){
							if (itemVal < totalCondition.getThresoldLowerLimit().v()) isTargetValue = false;
						}
					}
					if (totalCondition.getUpperLimitSettingAtr() == UseAtr.Use){
						// 上限以上なら、集計しない
						if (totalCondition.getThresoldUpperLimit() != null){
							if (itemVal >= totalCondition.getThresoldUpperLimit().v()) isTargetValue = false;
						}
					}
					if (!isTargetValue) continue;
				}

				// 回数を取得
				double count = 1.0;
				switch (workType.getAttendanceHolidayAttr()){
				case MORNING:
				case AFTERNOON:
					count += 0.5;
					break;
				default:
					break;
				}
				switch (totalTimes.getCountAtr()){
				case ONEDAY:
					// カウント区分＝1回なら、0.5日も1回としてカウントする
					if (count > 0.0) count = 1.0;
					break;
				case HALFDAY:
					// カウント区分＝0.5回なら、日数をそのまま回数とする
					break;
				}
				if (count == 0.0) continue;
				
				// 回数・時間を加算する
				result.addCount(count);
				result.addTime(this.attendanceStatusList.getTotalTime(procDate).v());
			}
		}
		
		// 回数集計結果情報を返す
		return results;
	}
}
