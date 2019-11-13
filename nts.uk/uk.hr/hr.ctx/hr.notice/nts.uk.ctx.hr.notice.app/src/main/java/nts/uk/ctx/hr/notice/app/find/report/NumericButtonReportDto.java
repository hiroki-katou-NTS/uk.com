package nts.uk.ctx.hr.notice.app.find.report;

import java.math.BigDecimal;

import lombok.Getter;

@Getter
public class NumericButtonReportDto extends DataTypeStateReportDto {
	private int numericItemMinus;
	private int numericItemAmount;
	private int integerPart;
	private int decimalPart;
	private BigDecimal NumericItemMin;
	private BigDecimal NumericItemMax;

	private NumericButtonReportDto(int numericItemMinus, int numericItemAmount, int integerPart, int decimalPart,
			BigDecimal numericItemMin, BigDecimal numericItemMax) {
		super();
		this.dataTypeValue = DataTypeValueReport.NUMBERIC_BUTTON.value;
		this.numericItemMinus = numericItemMinus;
		this.numericItemAmount = numericItemAmount;
		this.integerPart = integerPart;
		this.decimalPart = decimalPart;
		this.NumericItemMin = numericItemMin;
		this.NumericItemMax = numericItemMax;
	}

	public static NumericButtonReportDto createFromJavaType(int numericItemMinus, int numericItemAmount,
			int integerPart, int decimalPart, BigDecimal numericItemMin, BigDecimal numericItemMax) {
		return new NumericButtonReportDto(numericItemMinus, numericItemAmount, integerPart, decimalPart, numericItemMin,
				numericItemMax);
	}
}
