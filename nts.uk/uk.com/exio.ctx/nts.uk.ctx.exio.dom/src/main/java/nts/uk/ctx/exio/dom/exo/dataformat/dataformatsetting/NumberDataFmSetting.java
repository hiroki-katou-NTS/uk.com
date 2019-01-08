package nts.uk.ctx.exio.dom.exo.dataformat.dataformatsetting;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.exio.dom.exo.base.ItemType;
import nts.uk.ctx.exio.dom.exo.dataformat.init.NumberDataFmSet;
import nts.uk.ctx.exio.dom.exo.outputitem.ConditionSettingCode;

/**
 * 数値型データ形式設定
 */
@Getter
@Setter
public class NumberDataFmSetting extends NumberDataFmSet {

	/**
	 * 出力項目コード
	 */
	private OutputItemCode outputItemCode;

	/**
	 * 条件設定コード
	 */
	private ConditionSettingCode conditionSettingCode;

	public NumberDataFmSetting(String cid, int nullValueReplace, String valueOfNullValueReplace,
			int outputMinusAsZero, int fixedValue, String valueOfFixedValue, int fixedValueOperation,
			BigDecimal fixedCalculationValue, int fixedValueOperationSymbol, int fixedLengthOutput,
			Integer fixedLengthIntegerDigit, int fixedLengthEditingMethod, Integer decimalDigit,
			int decimalPointClassification, int decimalFraction, int formatSelection, String conditionSettingCode,
			String outputItemCode) {
		super(ItemType.NUMERIC.value, cid, nullValueReplace, valueOfNullValueReplace, outputMinusAsZero, fixedValue,
				valueOfFixedValue, fixedValueOperation, fixedCalculationValue, fixedValueOperationSymbol,
				fixedLengthOutput, fixedLengthIntegerDigit, fixedLengthEditingMethod, decimalDigit,
				decimalPointClassification, decimalFraction, formatSelection);
		this.outputItemCode = new OutputItemCode(outputItemCode);
		this.conditionSettingCode = new ConditionSettingCode(conditionSettingCode);
	}

}
