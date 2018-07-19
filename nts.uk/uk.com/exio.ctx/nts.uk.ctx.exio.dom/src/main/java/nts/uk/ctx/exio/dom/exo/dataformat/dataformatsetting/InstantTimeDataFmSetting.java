package nts.uk.ctx.exio.dom.exo.dataformat.dataformatsetting;

import lombok.Getter;
import nts.uk.ctx.exio.dom.exo.dataformat.init.InTimeDataFmSet;

/**
 * 時刻型データ形式設定
 */
@Getter
public class InstantTimeDataFmSetting extends InTimeDataFmSet {

	/**
	 * 出力項目コード
	 */
	private OutputItemCode outputItemCode;

	/**
	 * 条件設定コード
	 */
	private ConditionSettingCode conditionSettingCode;

	public InstantTimeDataFmSetting(int itemType, String cid, int nullValueSubs, String valueOfNullValueSubs,
			int outputMinusAsZero, int fixedValue, String valueOfFixedValue, int timeSeletion, int fixedLengthOutput,
			Integer fixedLongIntegerDigit, int fixedLengthEditingMothod, int delimiterSetting,
			int previousDayOutputMethod, int nextDayOutputMethod, int minuteFractionDigit, int decimalSelection,
			int minuteFractionDigitProcessCla, String conditionSettingCode, String outputItemCode) {
		super(itemType, cid, nullValueSubs, valueOfNullValueSubs, outputMinusAsZero, fixedValue, valueOfFixedValue,
				timeSeletion, fixedLengthOutput, fixedLongIntegerDigit, fixedLengthEditingMothod, delimiterSetting,
				previousDayOutputMethod, nextDayOutputMethod, minuteFractionDigit, decimalSelection,
				minuteFractionDigitProcessCla);
		this.outputItemCode = new OutputItemCode(outputItemCode);
		this.conditionSettingCode = new ConditionSettingCode(conditionSettingCode);
	}

}
