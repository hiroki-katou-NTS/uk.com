package nts.uk.ctx.exio.dom.exo.dataformat.dataformatsetting;

import lombok.Getter;
import nts.uk.ctx.exio.dom.exo.dataformat.init.DateFormatSet;

/**
 * 日付型データ形式設定
 */
@Getter
public class DateFormatSetting extends DateFormatSet {

	/**
	 * 出力項目コード
	 */
	private OutputItemCode outputItemCode;

	/**
	 * 条件設定コード
	 */
	private ConditionSettingCode conditionSettingCode;

	public DateFormatSetting(int itemType, String cid, int nullValueSubstitution, int fixedValue,
			String valueOfFixedValue, String valueOfNullValueSubs, int formatSelection, String conditionSettingCode,
			String outputItemCode) {
		super(itemType, cid, nullValueSubstitution, fixedValue, valueOfFixedValue, valueOfNullValueSubs,
				formatSelection);
		this.outputItemCode = new OutputItemCode(outputItemCode);
		this.conditionSettingCode = new ConditionSettingCode(conditionSettingCode);
	}
}
