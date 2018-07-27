package nts.uk.ctx.exio.dom.exo.outputitem;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.exio.dom.exo.base.ItemType;

/**
 * 出力項目(定型)
 */
@Getter
public class StandardOutputItem extends AggregateRoot {

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
	 * 出力項目名
	 */
	private OutputItemName outputItemName;

	/**
	 * 項目型
	 */
	private ItemType itemType;

	/**
	 * カテゴリ項目
	 */
	@Setter
	private List<CategoryItem> categoryItems;

	public StandardOutputItem(String cid, String outputItemCode, String conditionSettingCode, String outputItemName,
			int itemType, List<CategoryItem> categoryItems) {
		this.cid = cid;
		this.outputItemCode = new OutputItemCode(outputItemCode);
		this.conditionSettingCode = new ConditionSettingCode(conditionSettingCode);
		this.outputItemName = new OutputItemName(outputItemName);
		this.itemType = EnumAdaptor.valueOf(itemType, ItemType.class);
		this.categoryItems = categoryItems;
	}
	
	public StandardOutputItem(String cid, String outputItemCode, String conditionSettingCode, String outputItemName,
			int itemType) {
		this.cid = cid;
		this.outputItemCode = new OutputItemCode(outputItemCode);
		this.conditionSettingCode = new ConditionSettingCode(conditionSettingCode);
		this.outputItemName = new OutputItemName(outputItemName);
		this.itemType = EnumAdaptor.valueOf(itemType, ItemType.class);
	}
}
