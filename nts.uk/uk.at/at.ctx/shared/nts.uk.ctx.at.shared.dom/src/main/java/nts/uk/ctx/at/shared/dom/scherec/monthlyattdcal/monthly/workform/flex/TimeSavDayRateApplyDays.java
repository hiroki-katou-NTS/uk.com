package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.workform.flex;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMantissaMaxLength;
import nts.arc.primitive.constraint.DecimalRange;

/**
 * 時短日割適用日数
 * @author shuichu_ishida
 */
@DecimalMantissaMaxLength(2)
@DecimalRange(min = "0", max = "999.99")
public class TimeSavDayRateApplyDays extends DecimalPrimitiveValue<TimeSavDayRateApplyDays> {

	private static final long serialVersionUID = 1L;

	/**
	 * コンストラクタ
	 * @param days 日数
	 */
	public TimeSavDayRateApplyDays(Double days){
		super(BigDecimal.valueOf(days));
	}
	
	/**
	 * 日数を加算する
	 * @param days 日数
	 * @return 加算後の時短日割適用日数
	 */
	public TimeSavDayRateApplyDays addDays(Double days){
		return new TimeSavDayRateApplyDays(this.v().doubleValue() + days);
	}
}
