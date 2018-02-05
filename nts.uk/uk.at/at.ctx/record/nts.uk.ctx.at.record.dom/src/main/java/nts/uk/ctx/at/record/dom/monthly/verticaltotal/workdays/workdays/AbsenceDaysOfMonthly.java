package nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.monthly.AttendanceDaysMonth;
import nts.uk.ctx.at.record.dom.monthly.WorkTypeDaysCountTable;

/**
 * 月別実績の欠勤日数
 * @author shuichu_ishida
 */
@Getter
public class AbsenceDaysOfMonthly {

	/** 欠勤合計日数 */
	private AttendanceDaysMonth totalAbsenceDays;
	/** 欠勤日数 */
	private Map<Integer, AggregateAbsenceDays> absenceDaysList;
	
	/**
	 * コンストラクタ
	 */
	public AbsenceDaysOfMonthly(){
		
		this.totalAbsenceDays = new AttendanceDaysMonth(0.0);
		this.absenceDaysList = new HashMap<>();
	}
	
	/**
	 * ファクトリー
	 * @param totalAbsenceDays 欠勤合計日数
	 * @param absenceDaysList 欠勤日数
	 * @return 月別実績の欠勤日数
	 */
	public static AbsenceDaysOfMonthly of(
			AttendanceDaysMonth totalAbsenceDays,
			List<AggregateAbsenceDays> absenceDaysList){
		
		val domain = new AbsenceDaysOfMonthly();
		domain.totalAbsenceDays = totalAbsenceDays;
		for (val absenceDays : absenceDaysList){
			val absenceFrameNo = Integer.valueOf(absenceDays.getAbsenceFrameNo());
			if (domain.absenceDaysList.containsKey(absenceFrameNo)) continue;
			domain.absenceDaysList.put(absenceFrameNo, AggregateAbsenceDays.of(
					absenceFrameNo, new AttendanceDaysMonth(absenceDays.getDays().v())));
		}
		return domain;
	}
	
	/**
	 * 集計
	 * @param workTypeDaysCountTable 勤務種類の日数カウント表
	 */
	public void aggregate(WorkTypeDaysCountTable workTypeDaysCountTable){

		if (workTypeDaysCountTable == null) return;
		
		for (val aggrAbsenceDays : workTypeDaysCountTable.getAbsenceDaysMap().values()){
			
			// 欠勤枠日数の集計
			if (aggrAbsenceDays.getDays().greaterThan(0.0)){
				val absenceFrameNo = Integer.valueOf(aggrAbsenceDays.getAbsenceFrameNo());
				if (!this.absenceDaysList.containsKey(absenceFrameNo)){
					this.absenceDaysList.put(absenceFrameNo, new AggregateAbsenceDays(absenceFrameNo));
				}
				val targetAbsenceDays = this.absenceDaysList.get(absenceFrameNo);
				targetAbsenceDays.addDays(aggrAbsenceDays.getDays().v());
			}
			
			// 欠勤合計日数の集計
			this.totalAbsenceDays = this.totalAbsenceDays.addDays(aggrAbsenceDays.getDays().v());
		}
	}
}
