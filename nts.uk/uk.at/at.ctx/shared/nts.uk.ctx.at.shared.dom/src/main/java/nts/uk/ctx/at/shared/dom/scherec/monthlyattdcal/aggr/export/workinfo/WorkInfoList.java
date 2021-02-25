package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.export.workinfo;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;

/**
 * 勤務情報リスト
 * @author shuichu_ishida
 */
public class WorkInfoList {
	/** 日別実績の勤務情報マップ */
	private Map<GeneralDate, WorkInfoOfDailyAttendance> workInfoOfDailyMap;
	
	/**
	 * コンストラクタ
	 * @param employeeId 社員ID
	 * @param period 期間
	 */
	public WorkInfoList(RequireM1 require, String employeeId, DatePeriod period){
		
		this.workInfoOfDailyMap = require.dailyWorkInfo(employeeId, period);
	}
	
	public static interface RequireM1 {
		
		Map<GeneralDate, WorkInfoOfDailyAttendance> dailyWorkInfo(String employeeId, DatePeriod datePeriod);
	}

	/**
	 * コンストラクタ
	 * @param workInfoOfDailys 日別実績の勤務情報リスト
	 */
	public WorkInfoList(
			Map<GeneralDate, WorkInfoOfDailyAttendance> workInfoOfDailys){
	
		this.workInfoOfDailyMap = workInfoOfDailys;
	}
	
	/**
	 * 実績の勤務情報を取得する
	 * @param ymd 年月日
	 * @return 勤務情報
	 */
	public Optional<WorkInformation> getRecord(GeneralDate ymd) {
		if (!this.workInfoOfDailyMap.containsKey(ymd)) {
			return Optional.empty();
		}

		WorkInformation record = this.workInfoOfDailyMap.get(ymd).getRecordInfo();

		if (record == null) {
			return Optional.empty();
		}

		return Optional.of(record.clone());
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
}
