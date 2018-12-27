package nts.uk.ctx.exio.dom.exo.dataformat.dataformatsetting;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.exio.dom.exo.base.ItemType;
import nts.uk.ctx.exio.dom.exo.dataformat.init.TimeDataFmSet;
import nts.uk.ctx.exio.dom.exo.outputitem.ConditionSettingCode;

/**
 * 時間型データ形式設定
 */
@Getter
@Setter
public class TimeDataFmSetting extends TimeDataFmSet {

	/**
	 * 出力項目コード
	 */
	private OutputItemCode outputItemCode;

	/**
	 * 条件設定コード
	 */
	private ConditionSettingCode conditionSettingCode;

	public TimeDataFmSetting(String cid, int nullValueSubs, int outputMinusAsZero, int fixedValue,
			String valueOfFixedValue, int fixedLengthOutput, int fixedLongIntegerDigit, int fixedLengthEditingMethod,
			int delimiterSetting, int selectHourMinute, int minuteFractionDigit, int decimalSelection,
			int fixedValueOperationSymbol, int fixedValueOperation, BigDecimal fixedCalculationValue,
			String valueOfNullValueSubs, int minuteFractionDigitProcessCls, String conditionSettingCode,
			String outputItemCode) {
		super(ItemType.TIME.value, cid, nullValueSubs, outputMinusAsZero, fixedValue, valueOfFixedValue,
				fixedLengthOutput, fixedLongIntegerDigit, fixedLengthEditingMethod, delimiterSetting, selectHourMinute,
				minuteFractionDigit, decimalSelection, fixedValueOperationSymbol, fixedValueOperation,
				fixedCalculationValue, valueOfNullValueSubs, minuteFractionDigitProcessCls);
		this.outputItemCode = new OutputItemCode(outputItemCode);
		this.conditionSettingCode = new ConditionSettingCode(conditionSettingCode);
	}
}
