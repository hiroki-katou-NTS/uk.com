package nts.uk.ctx.exio.dom.exo.dataformat.dataformatsetting;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.exio.dom.exo.base.ItemType;
import nts.uk.ctx.exio.dom.exo.dataformat.init.ChacDataFmSet;
import nts.uk.ctx.exio.dom.exo.outputitem.ConditionSettingCode;

/**
 * 文字型データ形式設定
 */
@Getter
@Setter
public class CharacterDataFmSetting extends ChacDataFmSet {

	/**
	 * 出力項目コード
	 */
	private OutputItemCode outputItemCode;

	/**
	 * 条件設定コード
	 */
	private ConditionSettingCode conditionSettingCode;

	public CharacterDataFmSetting(String cid, int nullValueReplace, String valueOfNullValueReplace,
			int cdEditting, int fixedValue, int cdEdittingMethod, int cdEditDigit, String convertCode,
			int spaceEditting, int effectDigitLength, int startDigit, int endDigit, String valueOfFixedValue,
			String conditionSettingCode, String outputItemCode) {
		super(ItemType.CHARACTER.value, cid, nullValueReplace, valueOfNullValueReplace, cdEditting, fixedValue,
				cdEdittingMethod, cdEditDigit, convertCode, spaceEditting, effectDigitLength, startDigit, endDigit,
				valueOfFixedValue);
		this.outputItemCode = new OutputItemCode(outputItemCode);
		this.conditionSettingCode = new ConditionSettingCode(conditionSettingCode);
	}

}
