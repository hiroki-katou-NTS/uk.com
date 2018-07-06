package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.export.workinfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 勤務情報リスト
 * @author shuichu_ishida
 */
public class WorkInfoList {

	/** 日別実績の勤務情報 */
	private List<WorkInfoOfDailyPerformance> workInfoOfDailys;
	/** 日別実績の勤務情報マップ */
	private Map<GeneralDate, WorkInfoOfDailyPerformance> workInfoOfDailyMap;
	
	/**
	 * コンストラクタ
	 * @param employeeId 社員ID
	 * @param period 期間
	 * @param workInformationOfDaily 日別実績の勤務情報リポジトリ
	 */
	public WorkInfoList(
			String employeeId,
			DatePeriod period,
			WorkInformationRepository workInformationOfDaily){
		
		this.workInfoOfDailys = workInformationOfDaily.findByPeriodOrderByYmd(employeeId, period);
		this.setData();
	}

	/**
	 * コンストラクタ
	 * @param workInfoOfDailys 日別実績の勤務情報リスト
	 */
	public WorkInfoList(
			List<WorkInfoOfDailyPerformance> workInfoOfDailys){
	
		this.workInfoOfDailys = workInfoOfDailys;
		this.setData();
	}
	
	/**
	 * データ設定
	 */
	private void setData(){
		
		this.workInfoOfDailyMap = new HashMap<>();
		for (val workInfoOfDaily : this.workInfoOfDailys){
			val ymd = workInfoOfDaily.getYmd();
			this.workInfoOfDailyMap.putIfAbsent(ymd, workInfoOfDaily);
		}
	}
	
	/**
	 * 実績の勤務情報を取得する
	 * @param ymd 年月日
	 * @return 勤務情報
	 */
	public Optional<WorkInformation> getRecord(GeneralDate ymd) {

		if (!this.workInfoOfDailyMap.containsKey(ymd)) return Optional.empty();
		WorkInformation record = this.workInfoOfDailyMap.get(ymd).getRecordInfo();
		if (record == null) return Optional.empty();
		return Optional.of(new WorkInformation(record.getSiftCode(), record.getWorkTypeCode()));
	}
	
	/**
	 * 予定の勤務情報を取得する
	 * @param ymd 年月日
	 * @return 勤務情報
	 */
	public Optional<WorkInformation> getSchedule(GeneralDate ymd) {
		
		if (!this.workInfoOfDailyMap.containsKey(ymd)) return Optional.empty();
		WorkInformation schedule = this.workInfoOfDailyMap.get(ymd).getScheduleInfo();
		if (schedule == null) return Optional.empty();
		return Optional.of(new WorkInformation(schedule.getSiftCode(), schedule.getWorkTypeCode()));
	}
	
	/**
	 * 実績の勤務情報を取得する
	 * @return 勤務情報マップ
	 */
	public Map<GeneralDate, WorkInformation> getRecordMap() {
		
		Map<GeneralDate, WorkInformation> results = new HashMap<>();
		for (val entry : this.workInfoOfDailyMap.entrySet()){
			if (entry.getValue().getRecordInfo() == null) continue;
			results.put(entry.getKey(), entry.getValue().getRecordInfo());
		}
		return results;
	}
	
	/**
	 * 予定の勤務情報を取得する
	 * @return 勤務情報マップ
	 */
	public Map<GeneralDate, WorkInformation> getScheduleMap() {
		
		Map<GeneralDate, WorkInformation> results = new HashMap<>();
		for (val entry : this.workInfoOfDailyMap.entrySet()){
			if (entry.getValue().getScheduleInfo() == null) continue;
			results.put(entry.getKey(), entry.getValue().getScheduleInfo());
		}
		return results;
	}
}
