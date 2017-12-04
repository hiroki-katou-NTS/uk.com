package nts.uk.ctx.bs.person.dom.person.personinfoctgdata.item.datastate;

import java.math.BigDecimal;

import nts.uk.ctx.bs.person.dom.person.personinfoctgdata.item.DataState;
import nts.uk.ctx.bs.person.dom.person.personinfoctgdata.item.DataStateType;


public class NumberValue extends DataState {

	public BigDecimal value;

	private NumberValue(BigDecimal value) {
		super();
		this.dataStateType = DataStateType.Numeric;
		this.value = value;

	}

	public static NumberValue createFromJavaType(BigDecimal value) {

		return new NumberValue(value);

	}
}
