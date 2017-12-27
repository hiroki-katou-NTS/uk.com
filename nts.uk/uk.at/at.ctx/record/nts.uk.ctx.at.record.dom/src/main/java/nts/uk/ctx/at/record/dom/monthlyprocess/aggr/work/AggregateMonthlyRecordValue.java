package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthly;

/*
 * 戻り値：ドメインサービス：月別実績を集計する．集計処理
 */
@Getter
public class AggregateMonthlyRecordValue {

	/** 月別実績の勤怠時間 */
	private List<AttendanceTimeOfMonthly> attendanceTimes;

	/*
	 * コンストラクタ
	 */
	public AggregateMonthlyRecordValue(){
		
		this.attendanceTimes = new ArrayList<>();
	}
	
	/**
	 * リスト：月別実績の勤怠時間　データ追加
	 * @param attendanceTime 月別実績の勤怠時間
	 */
	public void addAttendanceTime(AttendanceTimeOfMonthly attendanceTime){
		
		this.attendanceTimes.add(attendanceTime);
	}
}
