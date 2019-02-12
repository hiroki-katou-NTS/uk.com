package nts.uk.file.com.app.person.matrix.datasource;

import java.math.BigDecimal;

import lombok.Getter;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;
/**
 * NumericButtonDataSource
 * @author lanlt
 *
 */
@Getter
public class NumericButtonDataSource extends DataTypeStateDataSource {
	private int numericItemMinus;
	private int numericItemAmount;
	private int integerPart;
	private int decimalPart;
	private BigDecimal NumericItemMin;
	private BigDecimal NumericItemMax;

	private NumericButtonDataSource(int numericItemMinus, int numericItemAmount, int integerPart, int decimalPart,
			BigDecimal numericItemMin, BigDecimal numericItemMax) {
		super();
		this.dataTypeValue = DataTypeValue.NUMBERIC_BUTTON.value;
		this.numericItemMinus = numericItemMinus;
		this.numericItemAmount = numericItemAmount;
		this.integerPart = integerPart;
		this.decimalPart = decimalPart;
		this.NumericItemMin = numericItemMin;
		this.NumericItemMax = numericItemMax;
	}

	public static NumericButtonDataSource createFromJavaType(int numericItemMinus, int numericItemAmount,
			int integerPart, int decimalPart, BigDecimal numericItemMin, BigDecimal numericItemMax) {
		return new NumericButtonDataSource(numericItemMinus, numericItemAmount, integerPart, decimalPart,
				numericItemMin, numericItemMax);
	}
}