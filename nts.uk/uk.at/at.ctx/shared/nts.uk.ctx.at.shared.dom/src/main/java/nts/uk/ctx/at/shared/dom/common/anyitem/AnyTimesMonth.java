package nts.uk.ctx.at.shared.dom.common.anyitem;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMantissaMaxLength;
import nts.arc.primitive.constraint.DecimalRange;

/**
 * 月次任意回数
 * @author shuichu_ishida
 */
@DecimalMantissaMaxLength(2)
@DecimalRange(min = "-99999.99", max = "99999.99")
public class AnyTimesMonth extends DecimalPrimitiveValue<AnyTimesMonth> {

	private static final long serialVersionUID = 1L;

	/**
	 * コンストラクタ
	 * @param times 回数
	 */
	public AnyTimesMonth(Double times){
		super(BigDecimal.valueOf(times));
	}
	
	/**
	 * 回数を加算する
	 * @param times 回数
	 * @return 加算後の月次任意回数
	 */
	public AnyTimesMonth addTimes(Double times){
		return new AnyTimesMonth(this.v().doubleValue() + times);
	}
}
