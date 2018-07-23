package nts.uk.ctx.exio.dom.exo.dataformat.dataformatsetting;

import java.math.BigDecimal;

import lombok.Getter;
import nts.uk.ctx.exio.dom.exo.dataformat.init.TimeDataFmSet;

/**
 * 時間型データ形式設定
 */
@Getter
public class TimeDataFmSetting extends TimeDataFmSet {

	/**
	 * 出力項目コード
	 */
	private OutputItemCode outputItemCode;

	/**
	 * 条件設定コード
	 */
	private ConditionSettingCode conditionSettingCode;

	public TimeDataFmSetting(int itemType, String cid, int nullValueSubs, int outputMinusAsZero, int fixedValue,
			String valueOfFixedValue, int fixedLengthOutput, int fixedLongIntegerDigit, int fixedLengthEditingMothod,
			int delimiterSetting, int selectHourMinute, int minuteFractionDigit, int decimalSelection,
			int fixedValueOperationSymbol, int fixedValueOperation, BigDecimal fixedCalculationValue,
			String valueOfNullValueSubs, int minuteFractionDigitProcessCls, String conditionSettingCode,
			String outputItemCode) {
		super(itemType, cid, nullValueSubs, outputMinusAsZero, fixedValue, valueOfFixedValue, fixedLengthOutput,
				fixedLongIntegerDigit, fixedLengthEditingMothod, delimiterSetting, selectHourMinute,
				minuteFractionDigit, decimalSelection, fixedValueOperationSymbol, fixedValueOperation,
				fixedCalculationValue, valueOfNullValueSubs, minuteFractionDigitProcessCls);
		this.outputItemCode = new OutputItemCode(outputItemCode);
		this.conditionSettingCode = new ConditionSettingCode(conditionSettingCode);
	}
}
