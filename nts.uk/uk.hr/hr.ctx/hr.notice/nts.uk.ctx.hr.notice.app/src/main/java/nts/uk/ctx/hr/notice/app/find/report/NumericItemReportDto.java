package nts.uk.ctx.hr.notice.app.find.report;

import java.math.BigDecimal;

import lombok.Getter;
@Getter
public class NumericItemReportDto extends DataTypeStateReportDto{
	
	private int numericItemMinus;
	private int numericItemAmount;
	private int integerPart;
	private Integer decimalPart;
	private BigDecimal NumericItemMin;
	private BigDecimal NumericItemMax;
	
	private NumericItemReportDto(int numericItemMinus, int numericItemAmount, int integerPart, Integer decimalPart,
			BigDecimal numericItemMin, BigDecimal numericItemMax) {
		super();
		this.dataTypeValue = DataTypeValueReport.NUMERIC.value;
		this.numericItemMinus = numericItemMinus;
		this.numericItemAmount = numericItemAmount;
		this.integerPart = integerPart;
		this.decimalPart = decimalPart;
		this.NumericItemMin =numericItemMin;
		this.NumericItemMax = numericItemMax;
	}

	public static NumericItemReportDto createFromJavaType(int numericItemMinus, int numericItemAmount, int integerPart,
			Integer decimalPart, BigDecimal numericItemMin, BigDecimal numericItemMax) {
		return new NumericItemReportDto(numericItemMinus, numericItemAmount, integerPart, decimalPart, numericItemMin,
				numericItemMax);
	}
}
