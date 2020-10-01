package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.reservation;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/** 注文数 */
@IntegerRange(min = 0, max = 99999)
public class OrderNumberMonthly extends IntegerPrimitiveValue<OrderNumberMonthly> {

	public OrderNumberMonthly(Integer rawValue) {
		super(rawValue);
	}

	/***/
	private static final long serialVersionUID = 1L;

	public OrderNumberMonthly add(int order) {
		return new OrderNumberMonthly(this.v() + order);
	}
}
