package nts.uk.ctx.at.record.dom.monthly.roundingset;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
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
	
	/**
	 * Of.
	 *
	 * @param roundingUnit the rounding unit
	 * @param roundingProcess the rounding process
	 * @return the time rounding of excess outside time
	 */
	public static TimeRoundingOfExcessOutsideTime of(
			int roundingUnit,
			int roundingProcess){

		TimeRoundingOfExcessOutsideTime domain = new TimeRoundingOfExcessOutsideTime();
		domain.roundingUnit = EnumAdaptor.valueOf(roundingUnit, Unit.class);
		domain.roundingProcess = EnumAdaptor.valueOf(roundingProcess, RoundingProcessOfExcessOutsideTime.class); ;
		return domain;
	}
}
