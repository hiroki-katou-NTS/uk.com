package nts.uk.ctx.exio.dom.exo.outputitem;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 出力項目(定型)
 */
@AllArgsConstructor
@Getter
public class StandardOutputItem extends AggregateRoot {

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * 出力項目コード
	 */
	private OutputItemCode outItemCd;

	/**
	 * 条件設定コード
	 */
	private ConditionSettingCode condSetCd;

	/**
	 * 出力項目名
	 */
	private OutItemName outItemName;

	/**
	 * 項目型
	 */
	private ItemType itemType;

	/**
	 * カテゴリ項目
	 */
	private List<CategoryItem> categoryItems = new ArrayList<>();

	public StandardOutputItem(String cid, String outItemCd, String condSetCd, String outItemName, int itemType, List<CategoryItem> categoryItems) {
		this.cid           = cid;
		this.outItemCd     = new OutputItemCode(outItemCd);
		this.condSetCd     = new ConditionSettingCode(condSetCd);
		this.outItemName   = new OutItemName(outItemName);
		this.itemType      = EnumAdaptor.valueOf(itemType, ItemType.class);
		this.categoryItems = categoryItems;
	}
}
