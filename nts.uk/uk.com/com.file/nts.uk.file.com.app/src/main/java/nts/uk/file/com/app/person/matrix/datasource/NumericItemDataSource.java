package nts.uk.file.com.app.person.matrix.datasource;

import java.math.BigDecimal;

import lombok.Getter;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;

/**
 * NumericItemDataSource
 * 
 * @author lanlt
 *
 */
@Getter
public class NumericItemDataSource extends DataTypeStateDataSource {

	private int numericItemMinus;
	private int numericItemAmount;
	private int integerPart;
	private Integer decimalPart;
	private BigDecimal NumericItemMin;
	private BigDecimal NumericItemMax;

	private NumericItemDataSource(int numericItemMinus, int numericItemAmount, int integerPart, Integer decimalPart,
			BigDecimal numericItemMin, BigDecimal numericItemMax) {
		super();
		this.dataTypeValue = DataTypeValue.NUMERIC.value;
		this.numericItemMinus = numericItemMinus;
		this.numericItemAmount = numericItemAmount;
		this.integerPart = integerPart;
		this.decimalPart = decimalPart;
		this.NumericItemMin = numericItemMin;
		this.NumericItemMax = numericItemMax;
	}

	public static NumericItemDataSource createFromJavaType(int numericItemMinus, int numericItemAmount, int integerPart,
			Integer decimalPart, BigDecimal numericItemMin, BigDecimal numericItemMax) {
		return new NumericItemDataSource(numericItemMinus, numericItemAmount, integerPart, decimalPart, numericItemMin,
				numericItemMax);
	}
}
