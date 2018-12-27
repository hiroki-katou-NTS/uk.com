package nts.uk.ctx.exio.dom.exo.dataformat.dataformatsetting;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.exio.dom.exo.base.ItemType;
import nts.uk.ctx.exio.dom.exo.dataformat.init.DateFormatSet;
import nts.uk.ctx.exio.dom.exo.outputitem.ConditionSettingCode;

/**
 * 日付型データ形式設定
 */
@Getter
@Setter
public class DateFormatSetting extends DateFormatSet {

	/**
	 * 出力項目コード
	 */
	private OutputItemCode outputItemCode;

	/**
	 * 条件設定コード
	 */
	private ConditionSettingCode conditionSettingCode;

	public DateFormatSetting(String cid, int nullValueSubstitution, int fixedValue,
			String valueOfFixedValue, String valueOfNullValueSubs, int formatSelection, String conditionSettingCode,
			String outputItemCode) {
		super(ItemType.DATE.value, cid, nullValueSubstitution, fixedValue, valueOfFixedValue, valueOfNullValueSubs,
				formatSelection);
		this.outputItemCode = new OutputItemCode(outputItemCode);
		this.conditionSettingCode = new ConditionSettingCode(conditionSettingCode);
	}
}
