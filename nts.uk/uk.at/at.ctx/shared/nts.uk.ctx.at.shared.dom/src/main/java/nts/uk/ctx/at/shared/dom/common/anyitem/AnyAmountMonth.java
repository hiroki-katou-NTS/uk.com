package nts.uk.ctx.at.shared.dom.common.anyitem;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * 月次任意金額
 * @author shuichi_ishida
 */
@IntegerRange(min = -999999999, max = 999999999)
public class AnyAmountMonth extends IntegerPrimitiveValue<AnyAmountMonth> {

	private static final long serialVersionUID = 1L;

	/**
	 * コンストラクタ
	 * @param amount 金額
	 */
	public AnyAmountMonth(Integer amount){
		super(amount);
	}
	
	/**
	 * 金額を加算する
	 * @param amount 金額
	 * @return 加算後の月次任意金額
	 */
	public AnyAmountMonth addAmount(Integer amount){
		return new AnyAmountMonth(this.v() + amount);
	}
	
	@Override
	protected Integer reviseRawValue(Integer rawValue) {
		if (rawValue == null) return super.reviseRawValue(rawValue);
		if (rawValue > 999999999) rawValue = 999999999;
		if (rawValue < -999999999) rawValue = -999999999;
		return super.reviseRawValue(rawValue);
	}
}
