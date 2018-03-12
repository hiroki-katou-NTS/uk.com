package nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.goout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.breakorgoout.enums.GoingOutReason;
import nts.uk.ctx.at.shared.dom.shortworktime.ChildCareAtr;

/**
 * 月別実績の外出
 * @author shuichu_ishida
 */
@Getter
public class GoOutOfMonthly {

	/** 外出 */
	private Map<GoingOutReason, AggregateGoOut> goOuts;
	/** 育児外出 */
	private Map<ChildCareAtr, GoOutForChildCare> goOutForChildCares;
	
	/**
	 * コンストラクタ
	 */
	public GoOutOfMonthly(){
		
		this.goOuts = new HashMap<>();
		this.goOutForChildCares = new HashMap<>();
	}

	/**
	 * ファクトリー
	 * @param goOuts 外出
	 * @param goOutForChildCares 育児外出
	 * @return 月別実績の外出
	 */
	public static GoOutOfMonthly of(
			List<AggregateGoOut> goOuts,
			List<GoOutForChildCare> goOutForChildCares){
		
		val domain = new GoOutOfMonthly();
		for (val goOut : goOuts){
			val goOutReason = goOut.getGoOutReason();
			domain.goOuts.putIfAbsent(goOutReason, goOut);
		}
		for (val goOutForChildCare : goOutForChildCares){
			val childCareAtr = goOutForChildCare.getChildCareAtr();
			domain.goOutForChildCares.putIfAbsent(childCareAtr, goOutForChildCare);
		}
		return domain;
	}
	
	/**
	 * 集計
	 * @param attendanceTimeOfDaily 日別実績の勤怠時間
	 */
	public void aggregate(AttendanceTimeOfDailyPerformance attendanceTimeOfDaily){

		if (attendanceTimeOfDaily == null) return;
		
		val totalWorkingTime = attendanceTimeOfDaily.getActualWorkingTimeOfDaily().getTotalWorkingTime();
		//*****（未）　誤って外出時間帯クラスがメンバになっているので、その修正待ち。外出時間クラスも、まだ未実装。
		//val goOutTimeOfDailys = totalWorkingTime.getOutingTimeOfDailyPerformance();
		//*****（未）　短時間勤務時間のメンバが、まだ実装されていない。shortWorkTime
		
		// 日別実績の「外出時間・回数」を集計する
		
		// 日別実績の「短時間・回数」を集計する
	}
}
