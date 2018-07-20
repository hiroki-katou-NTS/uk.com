package nts.uk.ctx.exio.dom.exo.dataformat.dataformatsetting;

import java.math.BigDecimal;

import lombok.Getter;
import nts.uk.ctx.exio.dom.exo.dataformat.init.NumberDataFmSet;

/**
 * 数値型データ形式設定
 */
@Getter
public class NumberDataFmSetting extends NumberDataFmSet {

	/**
	 * 出力項目コード
	 */
	private OutputItemCode outputItemCode;

	/**
	 * 条件設定コード
	 */
	private ConditionSettingCode conditionSettingCode;

	public NumberDataFmSetting(int itemType, String cid, int nullValueReplace, String valueOfNullValueReplace,
			int outputMinusAsZero, int fixedValue, String valueOfFixedValue, int fixedValueOperation,
			BigDecimal fixedCalculationValue, int fixedValueOperationSymbol, int fixedLengthOutput,
			Integer fixedLengthIntegerDigit, int fixedLengthEditingMethod, Integer decimalDigit,
			int decimalPointClassification, int decimalFraction, int formatSelection, String conditionSettingCode,
			String outputItemCode) {
		super(itemType, cid, nullValueReplace, valueOfNullValueReplace, outputMinusAsZero, fixedValue,
				valueOfFixedValue, fixedValueOperation, fixedCalculationValue, fixedValueOperationSymbol,
				fixedLengthOutput, fixedLengthIntegerDigit, fixedLengthEditingMethod, decimalDigit,
				decimalPointClassification, decimalFraction, formatSelection);
		this.outputItemCode = new OutputItemCode(outputItemCode);
		this.conditionSettingCode = new ConditionSettingCode(conditionSettingCode);
	}

}
