package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.export.totaltimes;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.actualworkinghours.repository.AttendanceTimeRepository;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.attdstatus.GetAttendanceStatus;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimes;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesResult;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.UseAtr;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.algorithm.GetTimeAndCountFromDailyRecord;
import nts.uk.ctx.at.shared.dom.scherec.workinfo.GetWorkInfo;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 実装：日別実績から回数集計結果を取得する
 * @author shuichu_ishida
 */
@Stateless
public class GetTimeAndCountFromDailyRecordImpl implements GetTimeAndCountFromDailyRecord {

	/** 出勤状態を取得する */
	@Inject
	private GetAttendanceStatus getAttendanceStatus;
	/** 勤務情報を取得する */
	@Inject
	private GetWorkInfo getWorkInfo;
	/** 日別実績の勤怠時間 */
	@Inject
	private AttendanceTimeRepository attendanceTimeRepo;
	/** 日別実績と勤怠項目の相互変換 */
	@Inject
	private DailyRecordToAttendanceItemConverter dailyToAttendanceItem;
	/** 勤務種類の取得 */
	@Inject
	private WorkTypeRepository workTypeRepo;

	/** 期間 */
	private DatePeriod period;
	/** 日別実績の勤怠時間リスト */
	private Map<GeneralDate, AttendanceTimeOfDailyPerformance> attendanceTimeOfDailyMap;
	/** 勤務種類リスト */
	private Map<String, WorkType> workTypeMap;
	
	private GetTimeAndCountFromDailyRecordImpl() {
		this.period = new DatePeriod(GeneralDate.min(), GeneralDate.min());
		this.attendanceTimeOfDailyMap = new HashMap<>();
		this.workTypeMap = new HashMap<>();
	}
	
	/** データ設定 */
	@Override
	public GetTimeAndCountFromDailyRecord setData(String companyId, String employeeId, DatePeriod period) {
		
		this.period = period;
		this.getAttendanceStatus = this.getAttendanceStatus.setData(employeeId, period);
		this.getWorkInfo = this.getWorkInfo.setData(employeeId, period);
		for (val attendanceTimeOfDaily : this.attendanceTimeRepo.findByPeriodOrderByYmd(employeeId, period)){
			val ymd = attendanceTimeOfDaily.getYmd();
			this.attendanceTimeOfDailyMap.putIfAbsent(ymd, attendanceTimeOfDaily);
		}
		for (val workType : this.workTypeRepo.findByCompanyId(companyId)){
			val workTypeCode = workType.getWorkTypeCode();
			this.workTypeMap.putIfAbsent(workTypeCode.v(), workType);
		}
		return this;
	}
	
	/** 時間・回数を取得 */
	@Override
	public TotalTimesResult getResult(TotalTimes totalTimes) {
		
		TotalTimesResult result = new TotalTimesResult();
		
		// 勤怠項目IDがない時、0:00、0回で返す
		val totalCondition = totalTimes.getTotalCondition();
		if (totalCondition.getAtdItemId() == null) return result;
		
		// 「期間」を取得
		GeneralDate procDate = this.period.start();
		for ( ; procDate.before(this.period.end()); procDate = procDate.addDays(1)){

			// 勤務情報が取得できない日は、集計しない
			val workInfoOpt = this.getWorkInfo.getRecord(procDate);
			if (!workInfoOpt.isPresent()) continue;
			val workInfo = workInfoOpt.get();
			String workTypeCd = workInfo.getWorkTypeCode().v();
			
			// 勤務種類が取得できない日は、集計しない
			if (!this.workTypeMap.containsKey(workTypeCd)) continue;
			val workType = this.workTypeMap.get(workTypeCd);
			
			// 「日別実績の勤怠時間」を取得
			if (!this.attendanceTimeOfDailyMap.containsKey(procDate)) continue;
			val attendanceTimeOfDaily = this.attendanceTimeOfDailyMap.get(procDate);
			
			// 勤務情報の判断
			boolean isTargetWork = false;
			/*
			val totalSubjects = totalTimes.getTotalSubjects();
			switch (totalTimes.getSummaryAtr()){
			case COMBINATION:
				for (val totalSubject : totalSubjects){
					//*****（未）　就業時間帯コードに不備があるため、修正された後、コード判定を追加する
					if (totalSubject.getWorkTypeCode() == null) continue;
					if (totalSubject.getWorkTypeCode().v().compareTo(workInfo.getWorkTypeCode().v()) == 0){
						isTargetWork = true;
						break;
					}
				}
				break;
			case WORKINGTIME:
				break;
			case DUTYTYPE:
				for (val totalSubject : totalSubjects){
					if (totalSubject.getWorkTypeCode() == null) continue;
					if (totalSubject.getWorkTypeCode().v().compareTo(workInfo.getWorkTypeCode().v()) == 0){
						isTargetWork = true;
						break;
					}
				}
				break;
			}
			*/
			if (!isTargetWork) continue;
			
			// 出勤状態の判断
			boolean isTargetAttendance = false;
			switch (totalTimes.getSummaryAtr()){
			case COMBINATION:
			case WORKINGTIME:
				// 出勤状態を判断する
				if (this.getAttendanceStatus.isAttendanceDay(procDate)) isTargetAttendance = true;
				break;
			case DUTYTYPE:
				isTargetAttendance = true;
				break;
			}
			if (!isTargetAttendance) continue;
			
			// 日別実績を回数集計用のクラスに変換
			val dailyItems = this.dailyToAttendanceItem.withAttendanceTime(attendanceTimeOfDaily);
			
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

			// 回数を取得
			double count = 0.0;
			if (workType.isOneDay()){
				// 1日出勤系なら、1.0日を加算
				if (workType.isAttendanceOrShootingOrHolidayWork(workType.getDailyWork().getOneDay())){
					count += 1.0;
				}
			}
			else {
				// 午前出勤系・午後出勤系なら、それぞれ0.5日を加算
				if (workType.isAttendanceOrShootingOrHolidayWork(workType.getDailyWork().getMorning())){
					count += 0.5;
				}
				if (workType.isAttendanceOrShootingOrHolidayWork(workType.getDailyWork().getAfternoon())){
					count += 0.5;
				}
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
			result.addTime(this.getAttendanceStatus.getTotalTime(procDate).v());
		}
		
		// 回数集計結果情報を返す
		return result;
	}
}
