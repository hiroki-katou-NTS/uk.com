package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.excessoutside.ExcessOutsideWorkMng;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.totalworkingtime.AggregateTotalWorkingTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.weekly.AttendanceTimeOfWeekly;

/**
 * 戻り値：月別実績を集計する
 * @author shuichu_ishida
 */
@Getter
@Setter
public class AggregateMonthlyValue {

	/** 集計総労働時間 */
	private AggregateTotalWorkingTime aggregateTotalWorkingTime;
	/** 時間外超過管理 */
	private ExcessOutsideWorkMng excessOutsideWorkMng;
	/** 週別実績の勤怠時間 */
	private List<AttendanceTimeOfWeekly> attendanceTimeWeeks;

	/**
	 * コンストラクタ
	 */
	public AggregateMonthlyValue(){
		
		this.aggregateTotalWorkingTime = null;
		this.excessOutsideWorkMng = null;
		this.attendanceTimeWeeks = new ArrayList<>();
	}
	
	/**
	 * ファクトリー
	 * @param aggregateTotalWorkingTime 集計総労働時間
	 * @param excessOutsideWorkMng 時間外超過管理
	 * @param attendanceTimeWeeks 週別実績の勤怠時間リスト
	 * @return 戻り値：月別実績を集計する
	 */
	public static AggregateMonthlyValue of(
			AggregateTotalWorkingTime aggregateTotalWorkingTime,
			ExcessOutsideWorkMng excessOutsideWorkMng,
			List<AttendanceTimeOfWeekly> attendanceTimeWeeks){
		
		AggregateMonthlyValue returnClass = new AggregateMonthlyValue();
		returnClass.aggregateTotalWorkingTime = aggregateTotalWorkingTime;
		returnClass.excessOutsideWorkMng = excessOutsideWorkMng;
		returnClass.attendanceTimeWeeks = attendanceTimeWeeks;
		return returnClass;
	}
}
