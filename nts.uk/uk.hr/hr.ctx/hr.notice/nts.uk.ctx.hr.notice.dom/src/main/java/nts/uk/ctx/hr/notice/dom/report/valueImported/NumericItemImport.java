package nts.uk.ctx.hr.notice.dom.report.valueImported;

import java.math.BigDecimal;

import lombok.Getter;

@Getter
public class NumericItemImport extends DataTypeStateImport{

	private int numericItemMinus;
	private int numericItemAmount;
	private int integerPart;
	private Integer decimalPart;
	private BigDecimal NumericItemMin;
	private BigDecimal NumericItemMax;
	
	private NumericItemImport(int numericItemMinus, int numericItemAmount, int integerPart, Integer decimalPart,
			BigDecimal numericItemMin, BigDecimal numericItemMax) {
		super();
		this.dataTypeValue = DataTypeValueImport.NUMERIC.value;
		this.numericItemMinus = numericItemMinus;
		this.numericItemAmount = numericItemAmount;
		this.integerPart = integerPart;
		this.decimalPart = decimalPart;
		this.NumericItemMin =numericItemMin;
		this.NumericItemMax = numericItemMax;
	}

	public static NumericItemImport createFromJavaType(int numericItemMinus, int numericItemAmount, int integerPart,
			Integer decimalPart, BigDecimal numericItemMin, BigDecimal numericItemMax) {
		return new NumericItemImport(numericItemMinus, numericItemAmount, integerPart, decimalPart, numericItemMin,
				numericItemMax);
	}
}
