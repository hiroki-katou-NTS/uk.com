package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.excessoutside;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonthWithMinus;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.excessoutside.TotalTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.actualworkingtime.IrregularWorkingTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.Overtime;

/**
 * 月別実績の時間外超過
 * @author shuichi_ishida
 */
@Getter
public class ExcessOutsideWorkOfMonthly implements Serializable{

	/** Serializable */
	private static final long serialVersionUID = 1L;

	/** 週割増合計時間 */
	@Setter
	private AttendanceTimeMonth weeklyTotalPremiumTime;
	/** 月割増合計時間 */
	@Setter
	private AttendanceTimeMonth monthlyTotalPremiumTime;
	/** 変形繰越時間 */
	@Setter
	private AttendanceTimeMonthWithMinus deformationCarryforwardTime;
	/** 時間 */
	private Map<Integer, ExcessOutSideWorkEachBreakdown> time;
	
	/**
	 * コンストラクタ
	 */
	public ExcessOutsideWorkOfMonthly(){
		
		this.weeklyTotalPremiumTime = new AttendanceTimeMonth(0);
		this.monthlyTotalPremiumTime = new AttendanceTimeMonth(0);
		this.deformationCarryforwardTime = new AttendanceTimeMonthWithMinus(0);
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
			AttendanceTimeMonthWithMinus deformationCarryforwardTime,
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
	 * 分を時間に加算する
	 * @param breakdownNo 内訳NO
	 * @param excessNo 超過NO
	 * @param minutes 分
	 */
	public void addMinutesToTime(int breakdownNo, int excessNo, int minutes){
		this.time.putIfAbsent(breakdownNo, new ExcessOutSideWorkEachBreakdown(breakdownNo));
		val breakdown = this.time.get(breakdownNo).getBreakdown();
		breakdown.putIfAbsent(excessNo, new ExcessOutsideWork(breakdownNo, excessNo));
		breakdown.get(excessNo).addMinutesToExcessTime(minutes);
	}
	
	/**
	 * 時間外超過（内訳）の合計を取得する
	 * @return 合計時間　（時間外超過）
	 */
	public AttendanceTimeMonth getTotalBreakdownTime(){
		AttendanceTimeMonth totalTime = new AttendanceTimeMonth(0);
		for (val breakdown : this.time.values()){
			for (val excessOutsideWork : breakdown.getBreakdown().values()){
				totalTime = totalTime.addMinutes(excessOutsideWork.getExcessTime().v());
			}
		}
		return totalTime;
	}
	
	/**
	 * 内訳項目時間を（時間外超過）時間に加算する
	 * @param totalExcessOutside 時間外超過累積時間
	 * @param breakdownItemTime 内訳項目時間
	 * @param nowOverTime 処理中超過時間
	 * @param nextOverTime 次の超過時間
	 * @param breakdownItemNo 内訳項目NO
	 */
	public void addTimeFromBreakdownItemTime(
			AttendanceTimeMonth totalExcessOutside, AttendanceTimeMonth breakdownItemTime,
			Overtime nowOverTime, Overtime nextOverTime, int breakdownItemNo){
		
		val nowOverMinutes = nowOverTime.getOvertime().v();
		boolean isOverNext = false;
		int nextOverMinutes = 0;
		if (nextOverTime != null){
			
			// 時間外超過累積時間と次の超過時間を比較する
			nextOverMinutes = nextOverTime.getOvertime().v();
			if (totalExcessOutside.v() > nextOverMinutes) return;
				
			// 時間外超過累積時間＋内訳項目時間と次の超過時間を比較する
			if (totalExcessOutside.v() + breakdownItemTime.v() > nextOverMinutes) isOverNext = true;
		}
		int addMinutes = 0;
		if (isOverNext){
			// 時間外超過累積時間＋内訳項目時間　>　次の超過時間　の時
			if (totalExcessOutside.v() <= nowOverMinutes){
				// 時間外超過累積時間　<= 処理中の超過時間　の時、次の超過時間－処理中の超過時間　を加算する
				addMinutes = nextOverMinutes - nowOverMinutes;
			}
			else {
				// 時間外超過累積時間　> 処理中の超過時間　の時、次の超過時間－時間外超過累積時間　を加算する
				addMinutes = nextOverMinutes - totalExcessOutside.v();
			}
		}
		else {
			// 時間外超過累積時間＋内訳項目時間　<=　次の超過時間　の時　（次の超過時間がない時も含む）
			if (totalExcessOutside.v() <= nowOverMinutes){
				// 時間外超過累積時間　<= 処理中の超過時間　の時、時間外超過累積時間＋内訳項目時間－処理中の超過時間　を加算する
				addMinutes = totalExcessOutside.v() + breakdownItemTime.v() - nowOverMinutes;
			}
			else {
				// 時間外超過累積時間　> 処理中の超過時間　の時、内訳項目時間　を加算する
				addMinutes = breakdownItemTime.v();
			}
		}
		this.addMinutesToTime(breakdownItemNo, nowOverTime.getOvertimeNo().value, addMinutes);
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
	 * 集計した時間（合計時間）から各時間を設定する
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
	
	/**
	 * 合算する
	 * @param target 加算対象
	 */
	public void sum(ExcessOutsideWorkOfMonthly target){
		
		this.weeklyTotalPremiumTime = this.weeklyTotalPremiumTime.addMinutes(target.weeklyTotalPremiumTime.v());
		this.monthlyTotalPremiumTime = this.monthlyTotalPremiumTime.addMinutes(target.monthlyTotalPremiumTime.v());
		this.deformationCarryforwardTime = this.deformationCarryforwardTime.addMinutes(target.deformationCarryforwardTime.v());
		
		for (val breakdown : this.time.values()){
			val breakdownNo = breakdown.getBreakdownNo();
			if (target.time.containsKey(breakdownNo)){
				val targetBreakdown = target.time.get(breakdownNo);
				for (val excess : breakdown.getBreakdown().values()){
					val excessNo = excess.getExcessNo();
					if (targetBreakdown.getBreakdown().containsKey(excessNo)){
						excess.sum(targetBreakdown.getBreakdown().get(excessNo));
					}
				}
			}
		}
		for (val targetBreakdown : target.time.values()){
			val breakdownNo = targetBreakdown.getBreakdownNo();
			if (this.time.containsKey(breakdownNo)){
				val breakdown = this.time.get(breakdownNo);
				for (val targetExcess : targetBreakdown.getBreakdown().values()){
					val excessNo = targetExcess.getExcessNo();
					breakdown.getBreakdown().putIfAbsent(excessNo, targetExcess);
				}
			}
			else {
				this.time.putIfAbsent(breakdownNo, targetBreakdown);
			}
		}
	}
}
