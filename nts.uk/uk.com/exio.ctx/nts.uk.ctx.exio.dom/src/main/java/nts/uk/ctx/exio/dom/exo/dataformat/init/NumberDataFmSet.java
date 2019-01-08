package nts.uk.ctx.exio.dom.exo.dataformat.init;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.exio.dom.exo.base.ItemType;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 数値型データ形式設定
 */
@Getter
public class NumberDataFmSet extends DataFormatSetting {

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * マイナス値を0で出力
	 */
	private NotUseAtr outputMinusAsZero;

	/**
	 * 固定値演算
	 */
	private NotUseAtr fixedValueOperation;

	/**
	 * 固定値演算値
	 */
	private Optional<DataFormatFixedValueOperation> fixedCalculationValue;

	/**
	 * 固定値演算符号
	 */
	private FixedValueOperationSymbol fixedValueOperationSymbol;

	/**
	 * 固定長出力
	 */
	private NotUseAtr fixedLengthOutput;

	/**
	 * 固定長整数桁
	 */
	private Optional<DataFormatIntegerDigit> fixedLengthIntegerDigit;

	/**
	 * 固定長編集方法
	 */
	private FixedLengthEditingMethod fixedLengthEditingMethod;

	/**
	 * 小数桁
	 */
	private Optional<DataFormatDecimalDigit> decimalDigit;

	/**
	 * 小数点区分
	 */
	private DecimalPointClassification decimalPointClassification;

	/**
	 * 小数端数
	 */
	private Rounding decimalFraction;

	/**
	 * 形式選択
	 */
	private DecimalDivision formatSelection;

	public NumberDataFmSet(int itemType, String cid, int nullValueReplace, String valueOfNullValueReplace,
			int outputMinusAsZero, int fixedValue, String valueOfFixedValue, int fixedValueOperation,
			BigDecimal fixedCalculationValue, int fixedValueOperationSymbol, int fixedLengthOutput,
			Integer fixedLengthIntegerDigit, int fixedLengthEditingMethod, Integer decimalDigit,
			int decimalPointClassification, int decimalFraction, int formatSelection) {
		super(itemType, fixedValue, valueOfFixedValue, nullValueReplace, valueOfNullValueReplace);
		this.cid = cid;
		this.outputMinusAsZero = EnumAdaptor.valueOf(outputMinusAsZero, NotUseAtr.class);
		this.fixedValueOperation = EnumAdaptor.valueOf(fixedValueOperation, NotUseAtr.class);
		this.fixedCalculationValue = Optional.ofNullable(
				fixedCalculationValue != null ? new DataFormatFixedValueOperation(fixedCalculationValue) : null);
		this.fixedValueOperationSymbol = EnumAdaptor.valueOf(fixedValueOperationSymbol,
				FixedValueOperationSymbol.class);
		this.fixedLengthOutput = EnumAdaptor.valueOf(fixedLengthOutput, NotUseAtr.class);
		this.fixedLengthIntegerDigit = Optional.ofNullable(
				fixedLengthIntegerDigit != null ? new DataFormatIntegerDigit(fixedLengthIntegerDigit) : null);
		this.fixedLengthEditingMethod = EnumAdaptor.valueOf(fixedLengthEditingMethod, FixedLengthEditingMethod.class);
		this.decimalDigit = Optional.ofNullable(decimalDigit != null ? new DataFormatDecimalDigit(decimalDigit) : null);
		this.decimalPointClassification = EnumAdaptor.valueOf(decimalPointClassification,
				DecimalPointClassification.class);
		this.decimalFraction = EnumAdaptor.valueOf(decimalFraction, Rounding.class);
		this.formatSelection = EnumAdaptor.valueOf(formatSelection, DecimalDivision.class);
	}

	public NumberDataFmSet(ItemType itemType, String cid, NotUseAtr nullValueReplace,
			Optional<DataFormatNullReplacement> valueOfNullValueReplace, NotUseAtr outputMinusAsZero,
			NotUseAtr fixedValue, Optional<DataTypeFixedValue> valueOfFixedValue, NotUseAtr fixedValueOperation,
			Optional<DataFormatFixedValueOperation> fixedCalculationValue,
			FixedValueOperationSymbol fixedValueOperationSymbol, NotUseAtr fixedLengthOutput,
			Optional<DataFormatIntegerDigit> fixedLengthIntegerDigit, FixedLengthEditingMethod fixedLengthEditingMethod,
			Optional<DataFormatDecimalDigit> decimalDigit, DecimalPointClassification decimalPointClassification,
			Rounding decimalFraction, DecimalDivision formatSelection) {
		super(itemType, fixedValue, valueOfFixedValue, nullValueReplace, valueOfNullValueReplace);
		this.cid = cid;
		this.outputMinusAsZero = outputMinusAsZero;
		this.fixedValueOperation = fixedValueOperation;
		this.fixedCalculationValue = fixedCalculationValue;
		this.fixedValueOperationSymbol = fixedValueOperationSymbol;
		this.fixedLengthOutput = fixedLengthOutput;
		this.fixedLengthIntegerDigit = fixedLengthIntegerDigit;
		this.fixedLengthEditingMethod = fixedLengthEditingMethod;
		this.decimalDigit = decimalDigit;
		this.decimalPointClassification = decimalPointClassification;
		this.decimalFraction = decimalFraction;
		this.formatSelection = formatSelection;
	}

}
