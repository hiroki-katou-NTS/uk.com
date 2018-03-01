package nts.uk.ctx.at.record.dom.monthly.roundingset;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;

/**
 * 時間外超過の時間丸め
 * @author shuichu_ishida
 */
@Getter
public class TimeRoundingOfExcessOutsideTime {

	/** 丸め単位 */
	private Unit roundingUnit;
	/** 端数処理 */
	private RoundingProcessOfExcessOutsideTime roundingProcess;
	
	/**
	 * コンストラクタ
	 */
	public TimeRoundingOfExcessOutsideTime(){
		
		this.roundingUnit = Unit.ROUNDING_TIME_1MIN;
		this.roundingProcess = RoundingProcessOfExcessOutsideTime.FOLLOW_ELEMENTS;
	}
	
	/**
	 * ファクトリー
	 * @param roundingUnit 丸め単位
	 * @param roundingProcess 端数処理
	 * @return 時間外超過の時間丸め
	 */
	public static TimeRoundingOfExcessOutsideTime of(
			Unit roundingUnit,
			RoundingProcessOfExcessOutsideTime roundingProcess){

		TimeRoundingOfExcessOutsideTime domain = new TimeRoundingOfExcessOutsideTime();
		domain.roundingUnit = roundingUnit;
		domain.roundingProcess = roundingProcess;
		return domain;
	}
}
