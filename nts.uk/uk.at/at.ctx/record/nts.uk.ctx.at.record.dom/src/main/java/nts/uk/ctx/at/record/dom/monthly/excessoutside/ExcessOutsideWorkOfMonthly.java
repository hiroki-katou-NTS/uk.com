package nts.uk.ctx.at.record.dom.monthly.excessoutside;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.val;
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
	private Map<Integer, ExcessOutsideWork> time;
	
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
		for (val time : timeList){
			domain.time.putIfAbsent(time.getBreakdownNo(), time);
		}
		return domain;
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
}
