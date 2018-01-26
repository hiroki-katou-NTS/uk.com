package nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.monthly.AttendanceDaysMonth;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * 月別実績の出勤日数
 * @author shuichu_ishida
 */
@Getter
public class AttendanceDaysOfMonthly {

	/** 日数 */
	private AttendanceDaysMonth days;
	
	/**
	 * コンストラクタ
	 */
	public AttendanceDaysOfMonthly(){
		
		this.days = new AttendanceDaysMonth(0.0);
	}
	
	/**
	 * ファクトリー
	 * @param days 日数
	 * @return 月別実績の出勤日数
	 */
	public static AttendanceDaysOfMonthly of(AttendanceDaysMonth days){
		
		val domain = new AttendanceDaysOfMonthly();
		domain.days = days;
		return domain;
	}
	
	/**
	 * 集計
	 * @param workInfoOfDailys 日別実績の勤務情報リスト
	 * @param workTypeMap 勤務種類マップ
	 */
	public void aggregate(
			List<WorkInfoOfDailyPerformance> workInfoOfDailys,
			Map<String, WorkType> workTypeMap){
		
		this.days = new AttendanceDaysMonth(0.0);
		for (val workInfoOfDaily : workInfoOfDailys){
			val recordWorkInfo = workInfoOfDaily.getRecordWorkInformation();
			val workTypeCd = recordWorkInfo.getWorkTypeCode();
			if (!workTypeMap.containsKey(workTypeCd.v())) continue;
			val workType = workTypeMap.get(workTypeCd.v());
			
			// 勤務種類を判断しカウント数を取得する
			//*****（未）　カウントの確認方法の確認要。
			val workTypeSet = workType.getWorkTypeSet();
			
			// 出勤状態を判断する
			
			// 出勤日数に加算する
			this.days = this.days.addDays(0.0);
		}
	}
}
