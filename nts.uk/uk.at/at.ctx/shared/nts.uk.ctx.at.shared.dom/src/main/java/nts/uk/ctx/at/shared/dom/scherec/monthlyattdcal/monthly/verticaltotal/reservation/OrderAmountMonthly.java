package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.reservation;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/** 注文金額 */
@IntegerRange(min = 0, max = 99999999)
public class OrderAmountMonthly extends IntegerPrimitiveValue<OrderAmountMonthly> {

	public OrderAmountMonthly(Integer rawValue) {
		super(rawValue);
	}

	/***/
	private static final long serialVersionUID = 1L;

	public OrderAmountMonthly add(OrderAmountMonthly amount) {
		return new OrderAmountMonthly(this.v() + amount.v());
	}
	
	public OrderAmountMonthly add(int amount) {
		return new OrderAmountMonthly(this.v() + amount);
	}
}
