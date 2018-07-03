package nts.uk.ctx.exio.dom.exo.outitem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 出力項目(定型)
 */
@AllArgsConstructor
@Getter
public class StdOutItem extends AggregateRoot {

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * 出力項目コード
	 */
	private OutItemCd outItemCd;

	/**
	 * 条件設定コード
	 */
	private CondSetCd condSetCd;

	/**
	 * 出力項目名
	 */
	private OutItemName outItemName;

	/**
	 * 項目型
	 */
	private ItemType itemType;

	public static StdOutItem createFromJavaType(String cid, OutItemCd outItemCd, CondSetCd condSetCd,
			OutItemName outItemName, int itemType) {
		StdOutItem stdOutItem = new StdOutItem(cid, outItemCd, condSetCd, outItemName,
				EnumAdaptor.valueOf(itemType, ItemType.class));
		return stdOutItem;
	}
}
