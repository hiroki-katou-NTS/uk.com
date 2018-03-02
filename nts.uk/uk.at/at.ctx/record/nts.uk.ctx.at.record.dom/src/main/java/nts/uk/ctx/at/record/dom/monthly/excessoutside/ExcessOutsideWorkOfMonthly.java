package nts.uk.ctx.at.record.dom.monthly.excessoutside;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.monthly.calc.actualworkingtime.IrregularWorkingTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.excessoutside.TotalTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

/**
 * 月別実績の時間外超過
 * @author shuichu_ishida
 */
@Getter
public class ExcessOutsideWorkOfMonthly {

	/** 週割増合計時間 */
	private AttendanceTimeMonth weeklyTotalPremiumTime;
	/** 月割増合計時間 */
	private AttendanceTimeMonth monthlyTotalPremiumTime;
	/** 変形繰越時間 */
	private AttendanceTimeMonth deformationCarryforwardTime;
	/** 時間 */
	private Map<Integer, ExcessOutSideWorkEachBreakdown> time;
	
	/**
	 * コンストラクタ
	 */
	public ExcessOutsideWorkOfMonthly(){
		
		this.weeklyTotalPremiumTime = new AttendanceTimeMonth(0);
		this.monthlyTotalPremiumTime = new AttendanceTimeMonth(0);
		this.deformationCarryforwardTime = new AttendanceTimeMonth(0);
		this.time = new HashMap<>();
	}
	
	/**
	 * ファクトリー
	 * @param weeklyTotalPremiumTime 週割増合計時間
	 * @param monthlyTotalPremiumTime 月割増合計時間
	 * @param deformationCarryforwardTime 変形繰越時間
	 * @param timeList 時間リスト
	 * @return 月別実績の時間外超過
	 */
	public static ExcessOutsideWorkOfMonthly of(
			AttendanceTimeMonth weeklyTotalPremiumTime,
			AttendanceTimeMonth monthlyTotalPremiumTime,
			AttendanceTimeMonth deformationCarryforwardTime,
			List<ExcessOutsideWork> timeList){
		
		ExcessOutsideWorkOfMonthly domain = new ExcessOutsideWorkOfMonthly();
		domain.weeklyTotalPremiumTime = weeklyTotalPremiumTime;
		domain.monthlyTotalPremiumTime = monthlyTotalPremiumTime;
		domain.deformationCarryforwardTime = deformationCarryforwardTime;
		for (val excessOutsideWork : timeList){
			val breakdownNo = excessOutsideWork.getBreakdownNo();
			val excessNo = excessOutsideWork.getExcessNo();
			domain.time.putIfAbsent(breakdownNo, new ExcessOutSideWorkEachBreakdown(breakdownNo));
			val breakdown = domain.time.get(breakdownNo);
			breakdown.getBreakdown().putIfAbsent(excessNo, excessOutsideWork);
		}
		return domain;
	}
	
	/**
	 * 時間が存在するか
	 * @param breakdownNo 内訳NO
	 * @param excessNo 超過NO
	 * @return true：存在する、false：存在しない
	 */
	public boolean containsTime(int breakdownNo, int excessNo){
		if (!this.time.containsKey(breakdownNo)) return false;
		if (!this.time.get(breakdownNo).getBreakdown().containsKey(excessNo)) return false;
		return true;
	}
	
	/**
	 * 週割増合計時間に分を加算する
	 * @param minutes 分
	 */
	public void addMinutesToWeeklyTotalPremiumTime(int minutes){
		this.weeklyTotalPremiumTime = this.weeklyTotalPremiumTime.addMinutes(minutes);
	}
	
	/**
	 * 月割増合計時間に分を加算する
	 * @param minutes 分
	 */
	public void addMinutesToMonthlyTotalPremiumTime(int minutes){
		this.monthlyTotalPremiumTime = this.monthlyTotalPremiumTime.addMinutes(minutes);
	}
	
	/**
	 * 変形繰越時間に分を加算する
	 * @param minutes 分
	 */
	public void addMinutesToDeformationCarryforwardTime(int minutes){
		this.deformationCarryforwardTime = this.deformationCarryforwardTime.addMinutes(minutes);
	}
	
	/**
	 * 集計時間から各時間を設定する
	 * @param totalTime 合計時間
	 * @param irregularWork 月別実績の変形労働時間
	 */
	public void setFromAggregateTime(TotalTime totalTime, IrregularWorkingTimeOfMonthly irregularWork){
		
		// 丸め後合計時間をコピーする
		this.weeklyTotalPremiumTime = totalTime.getWeeklyTotalPremiumTime();
		this.monthlyTotalPremiumTime = totalTime.getMonthlyTotalPremiumTime();
		
		// 変形労働時間から変形期間繰越時間をコピーする
		this.deformationCarryforwardTime = irregularWork.getIrregularPeriodCarryforwardTime();
	}
}
