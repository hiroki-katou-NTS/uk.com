package nts.uk.ctx.pereg.pub.person.info.item;

import java.math.BigDecimal;

import lombok.Getter;

@Getter
public class NumericItemExport extends DataTypeStateExport{

	private int numericItemMinus;
	private int numericItemAmount;
	private int integerPart;
	private Integer decimalPart;
	private BigDecimal NumericItemMin;
	private BigDecimal NumericItemMax;
	
	private NumericItemExport(int numericItemMinus, int numericItemAmount, int integerPart, Integer decimalPart,
			BigDecimal numericItemMin, BigDecimal numericItemMax) {
		super();
		this.dataTypeValue = DataTypeValueExport.NUMERIC.value;
		this.numericItemMinus = numericItemMinus;
		this.numericItemAmount = numericItemAmount;
		this.integerPart = integerPart;
		this.decimalPart = decimalPart;
		this.NumericItemMin =numericItemMin;
		this.NumericItemMax = numericItemMax;
	}

	public static NumericItemExport createFromJavaType(int numericItemMinus, int numericItemAmount, int integerPart,
			Integer decimalPart, BigDecimal numericItemMin, BigDecimal numericItemMax) {
		return new NumericItemExport(numericItemMinus, numericItemAmount, integerPart, decimalPart, numericItemMin,
				numericItemMax);
	}
}
