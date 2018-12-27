package nts.uk.ctx.exio.dom.exo.outputitemorder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.exio.dom.exo.outputitem.ConditionSettingCode;

/**
 * 出力項目並び順(定型)
 */
@AllArgsConstructor
@Getter
public class StandardOutputItemOrder extends AggregateRoot {

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * 出力項目コード
	 */
	private OutputItemCode outputItemCode;

	/**
	 * 条件設定コード
	 */
	@Setter
	private ConditionSettingCode conditionSettingCode;

	/**
	 * 順序
	 */
	private int displayOrder;

	public StandardOutputItemOrder(String cid, String outputItemCode, String conditionSettingCode, int displayOrder) {
		this.cid = cid;
		this.outputItemCode = new OutputItemCode(outputItemCode);
		this.conditionSettingCode = new ConditionSettingCode(conditionSettingCode);
		this.displayOrder = displayOrder;
	}
}
