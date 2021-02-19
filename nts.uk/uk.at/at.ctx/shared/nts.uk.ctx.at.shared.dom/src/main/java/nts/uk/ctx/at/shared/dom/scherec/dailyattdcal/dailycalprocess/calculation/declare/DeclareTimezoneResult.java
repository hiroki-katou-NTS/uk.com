package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.declare;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.CalculationRangeOfOneDay;

/**
 * 申告時間帯作成結果
 * @author shuichi_ishida
 */
@Getter
@Setter
public class DeclareTimezoneResult {

	/** 1日の計算範囲 */
	private Optional<CalculationRangeOfOneDay> calcRangeOfOneDay;
	/** 申告計算範囲 */
	private Optional<DeclareCalcRange> declareCalcRange;

	/**
	 * コンストラクタ
	 */
	public DeclareTimezoneResult(){
		this.calcRangeOfOneDay = Optional.empty();
		this.declareCalcRange = Optional.empty();
	}
}
