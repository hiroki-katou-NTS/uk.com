package nts.uk.ctx.exio.dom.exo.dataformat.dataformatsetting;

import lombok.Getter;
import nts.uk.ctx.exio.dom.exo.base.ItemType;
import nts.uk.ctx.exio.dom.exo.dataformat.init.AwDataFormatSet;

/**
 * 在職区分型データ形式設定
 */
@Getter
public class AwDataFormatSetting extends AwDataFormatSet {

	/**
	 * 出力項目コード
	 */
	private OutputItemCode outputItemCode;

	/**
	 * 条件設定コード
	 */
	private ConditionSettingCode conditionSettingCode;

	public AwDataFormatSetting(String cid, String closedOutput, String absenceOutput, int fixedValue,
			String valueOfFixedValue, String atWorkOutput, String retirementOutput,
			String conditionSettingCode, String outputItemCode) {
		super(ItemType.AT_WORK_CLS.value, cid, closedOutput, absenceOutput, fixedValue, valueOfFixedValue, atWorkOutput,
				retirementOutput);
		this.outputItemCode = new OutputItemCode(outputItemCode);
		this.conditionSettingCode = new ConditionSettingCode(conditionSettingCode);
	}
}
