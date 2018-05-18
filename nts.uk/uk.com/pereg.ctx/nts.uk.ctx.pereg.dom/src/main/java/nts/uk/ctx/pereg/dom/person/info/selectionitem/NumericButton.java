package nts.uk.ctx.pereg.dom.person.info.selectionitem;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.pereg.dom.person.info.numericitem.DecimalPart;
import nts.uk.ctx.pereg.dom.person.info.numericitem.IntegerPart;
import nts.uk.ctx.pereg.dom.person.info.numericitem.NumericItemAmount;
import nts.uk.ctx.pereg.dom.person.info.numericitem.NumericItemMax;
import nts.uk.ctx.pereg.dom.person.info.numericitem.NumericItemMin;
import nts.uk.ctx.pereg.dom.person.info.numericitem.NumericItemMinus;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeState;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;
@Getter
public class NumericButton extends DataTypeState {
	private NumericItemMinus numericItemMinus;
	private NumericItemAmount numericItemAmount;
	private IntegerPart integerPart;
	private DecimalPart decimalPart;
	private NumericItemMin numericItemMin;
	private NumericItemMax numericItemMax;

	private NumericButton(int numericItemMinus, int numericItemAmount, int integerPart, int decimalPart,
			BigDecimal numericItemMin, BigDecimal numericItemMax) {
		super();
		this.dataTypeValue = DataTypeValue.NUMBERIC_BUTTON;
		this.numericItemMinus = EnumAdaptor.valueOf(numericItemMinus, NumericItemMinus.class);
		this.numericItemAmount = EnumAdaptor.valueOf(numericItemAmount, NumericItemAmount.class);
		this.integerPart = new IntegerPart(integerPart);
		this.decimalPart = new DecimalPart(decimalPart);
		this.numericItemMin = numericItemMin != null ? new NumericItemMin(numericItemMin) : null;
		this.numericItemMax = numericItemMax != null ? new NumericItemMax(numericItemMax) : null;
	}

	public static NumericButton createFromJavaType(int numericItemMinus, int numericItemAmount, int integerPart,
			int decimalPart, BigDecimal numericItemMin, BigDecimal numericItemMax) {
		return new NumericButton(numericItemMinus, numericItemAmount, integerPart, decimalPart, numericItemMin,
				numericItemMax);
	}
}
