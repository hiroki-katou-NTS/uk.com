package nts.uk.ctx.at.shared.dom.monthly.roundingset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;

/**
 * 時間外超過の時間丸め
 * @author shuichu_ishida
 */
@Getter
@AllArgsConstructor
public class TimeRoundingOfExcessOutsideTime {
	
	private String companyId;
	/** 丸め単位 */
	private Unit roundingUnit;
	/** 端数処理 */
	private RoundingProcessOfExcessOutsideTime roundingProcess;
	
	/**
	 * コンストラクタ
	 */
	public TimeRoundingOfExcessOutsideTime(String companyId){
		this.companyId = companyId;
		this.roundingUnit = Unit.ROUNDING_TIME_1MIN;
		this.roundingProcess = RoundingProcessOfExcessOutsideTime.FOLLOW_ELEMENTS;
	}
	
	/**
	 * ファクトリー
	 * @param roundingUnit 丸め単位
	 * @param roundingProcess 端数処理
	 * @return 時間外超過の時間丸め
	 */
	public static TimeRoundingOfExcessOutsideTime of(String companyId,
			Unit roundingUnit,
			RoundingProcessOfExcessOutsideTime roundingProcess){

		TimeRoundingOfExcessOutsideTime domain = new TimeRoundingOfExcessOutsideTime(companyId);
		domain.roundingUnit = roundingUnit;
		domain.roundingProcess = roundingProcess;
		return domain;
	}
	
	/**
	 * Create from Java Type of Rounding Process
	 * 
	 * @param companyId
	 * @param unit
	 * @param rounding
	 * @return
	 */
	public static TimeRoundingOfExcessOutsideTime createFromJavaType(String companyId, int unit, int rounding) {
		return new TimeRoundingOfExcessOutsideTime(companyId, EnumAdaptor.valueOf(unit, Unit.class), EnumAdaptor.valueOf(rounding, RoundingProcessOfExcessOutsideTime.class));
	}
}
