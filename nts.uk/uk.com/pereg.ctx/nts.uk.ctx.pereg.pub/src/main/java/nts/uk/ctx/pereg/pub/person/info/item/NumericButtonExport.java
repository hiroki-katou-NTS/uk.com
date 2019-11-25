package nts.uk.ctx.pereg.pub.person.info.item;

import java.math.BigDecimal;

import lombok.Getter;

@Getter
public class NumericButtonExport extends DataTypeStateExport{
	private int numericItemMinus;
	private int numericItemAmount;
	private int integerPart;
	private int decimalPart;
	private BigDecimal NumericItemMin;
	private BigDecimal NumericItemMax;
	
	private NumericButtonExport( int numericItemMinus, int numericItemAmount, int integerPart, int decimalPart,
			BigDecimal numericItemMin, BigDecimal numericItemMax) {
		super();
		this.dataTypeValue = DataTypeValueExport.NUMBERIC_BUTTON.value;
		this.numericItemMinus = numericItemMinus;
		this.numericItemAmount = numericItemAmount;
		this.integerPart = integerPart;
		this.decimalPart =decimalPart;
		this.NumericItemMin =numericItemMin;
		this.NumericItemMax = numericItemMax;
	}

	public static NumericButtonExport createFromJavaType(int numericItemMinus, int numericItemAmount, int integerPart,
			int decimalPart, BigDecimal numericItemMin, BigDecimal numericItemMax) {
		return new NumericButtonExport(numericItemMinus, numericItemAmount, integerPart, decimalPart, numericItemMin,
				numericItemMax);
	}
}
