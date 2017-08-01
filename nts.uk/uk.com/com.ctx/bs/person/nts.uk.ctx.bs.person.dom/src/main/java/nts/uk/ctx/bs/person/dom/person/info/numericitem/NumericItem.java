package nts.uk.ctx.bs.person.dom.person.info.numericitem;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.bs.person.dom.person.info.singleitem.DataTypeObject;
import nts.uk.ctx.bs.person.dom.person.info.singleitem.DataTypeState;

@Getter
public class NumericItem extends DataTypeObject {

	private NumericItemMinus numericItemMinus;
	private NumericItemAmount numericItemAmount;
	private IntegerPart integerPart;
	private DecimalPart decimalPart;
	private BigDecimal NumericItemMin;
	private BigDecimal NumericItemMax;

	public NumericItem(int numericItemMinus, int numericItemAmount, int integerPart, int decimalPart,
			BigDecimal numericItemMin, BigDecimal numericItemMax) {
		super();
		this.dataTypeState = DataTypeState.NUMERIC;
		this.numericItemMinus = EnumAdaptor.valueOf(numericItemMinus, NumericItemMinus.class);
		this.numericItemAmount = EnumAdaptor.valueOf(numericItemAmount, NumericItemAmount.class);
		this.integerPart = new IntegerPart(integerPart);
		this.decimalPart = new DecimalPart(decimalPart);
		this.NumericItemMin = numericItemMin;
		this.NumericItemMax = numericItemMax;
	}

}
