package nts.uk.ctx.hr.notice.dom.report.valueImported;

import java.math.BigDecimal;

import lombok.Getter;

@Getter
public class NumericButtonImport extends DataTypeStateImport{
	private int numericItemMinus;
	private int numericItemAmount;
	private int integerPart;
	private int decimalPart;
	private BigDecimal NumericItemMin;
	private BigDecimal NumericItemMax;
	
	private NumericButtonImport( int numericItemMinus, int numericItemAmount, int integerPart, int decimalPart,
			BigDecimal numericItemMin, BigDecimal numericItemMax) {
		super();
		this.dataTypeValue = DataTypeValueImport.NUMBERIC_BUTTON.value;
		this.numericItemMinus = numericItemMinus;
		this.numericItemAmount = numericItemAmount;
		this.integerPart = integerPart;
		this.decimalPart =decimalPart;
		this.NumericItemMin =numericItemMin;
		this.NumericItemMax = numericItemMax;
	}

	public static NumericButtonImport createFromJavaType(int numericItemMinus, int numericItemAmount, int integerPart,
			int decimalPart, BigDecimal numericItemMin, BigDecimal numericItemMax) {
		return new NumericButtonImport(numericItemMinus, numericItemAmount, integerPart, decimalPart, numericItemMin,
				numericItemMax);
	}
}
