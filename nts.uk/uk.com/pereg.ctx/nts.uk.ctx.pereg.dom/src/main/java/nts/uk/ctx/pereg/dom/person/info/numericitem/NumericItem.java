package nts.uk.ctx.pereg.dom.person.info.numericitem;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeState;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;

@Getter
public class NumericItem extends DataTypeState {

	private NumericItemMinus numericItemMinus;
	private NumericItemAmount numericItemAmount;
	private IntegerPart integerPart;
	private DecimalPart decimalPart;
	private NumericItemMin numericItemMin;
	private NumericItemMax numericItemMax;

	private NumericItem(int numericItemMinus, int numericItemAmount, int integerPart, Integer decimalPart,
			BigDecimal numericItemMin, BigDecimal numericItemMax) {
		super();
		this.dataTypeValue = DataTypeValue.NUMERIC;
		this.numericItemMinus = EnumAdaptor.valueOf(numericItemMinus, NumericItemMinus.class);
		this.numericItemAmount = EnumAdaptor.valueOf(numericItemAmount, NumericItemAmount.class);
		this.integerPart = new IntegerPart(integerPart);
		this.decimalPart = decimalPart == null? null : new DecimalPart(decimalPart);
		this.numericItemMin = numericItemMin != null ? new NumericItemMin(numericItemMin) : null;
		this.numericItemMax = numericItemMax != null ? new NumericItemMax(numericItemMax) : null;
	}

	public static NumericItem createFromJavaType(int numericItemMinus, int numericItemAmount, int integerPart,
			Integer decimalPart, BigDecimal numericItemMin, BigDecimal numericItemMax) {
		return new NumericItem(numericItemMinus, numericItemAmount, integerPart, decimalPart, numericItemMin,
				numericItemMax);
	}

}
