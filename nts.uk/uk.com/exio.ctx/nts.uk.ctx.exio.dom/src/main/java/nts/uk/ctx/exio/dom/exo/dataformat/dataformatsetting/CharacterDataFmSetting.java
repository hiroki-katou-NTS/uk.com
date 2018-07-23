package nts.uk.ctx.exio.dom.exo.dataformat.dataformatsetting;

import lombok.Getter;
import nts.uk.ctx.exio.dom.exo.dataformat.init.ChacDataFmSet;

/**
 * 文字型データ形式設定
 */
@Getter
public class CharacterDataFmSetting extends ChacDataFmSet {

	/**
	 * 出力項目コード
	 */
	private OutputItemCode outputItemCode;

	/**
	 * 条件設定コード
	 */
	private ConditionSettingCode conditionSettingCode;

	public CharacterDataFmSetting(int itemType, String cid, int nullValueReplace, String valueOfNullValueReplace,
			int cdEditting, int fixedValue, int cdEdittingMethod, int cdEditDigit, String convertCode,
			int spaceEditting, int effectDigitLength, int startDigit, int endDigit, String valueOfFixedValue,
			String conditionSettingCode, String outputItemCode) {
		super(itemType, cid, nullValueReplace, valueOfNullValueReplace, cdEditting, fixedValue, cdEdittingMethod,
				cdEditDigit, convertCode, spaceEditting, effectDigitLength, startDigit, endDigit, valueOfFixedValue);
		this.outputItemCode = new OutputItemCode(outputItemCode);
		this.conditionSettingCode = new ConditionSettingCode(conditionSettingCode);
	}

}
