package nts.uk.ctx.pr.core.dom.wageprovision.statementitem;

import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 明細書項目の表示設定
 */
@Getter
public class StatementItemDisplaySet extends AggregateRoot {

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * 給与項目ID
	 */
	private String salaryItemId;

	/**
	 * ゼロ表示区分
	 */
	private ZeroDisplayEnum zeroDisplayAtr;

	/**
	 * 項目名表示
	 */
	private Optional<ItemNameDisplayEnum> itemNameDisplay;

	public StatementItemDisplaySet(String cid, String salaryItemId, int zeroDisplayAtr, Integer itemNameDisplay) {
		this.cid = cid;
		this.salaryItemId = salaryItemId;
		this.zeroDisplayAtr = EnumAdaptor.valueOf(zeroDisplayAtr, ZeroDisplayEnum.class);
		this.itemNameDisplay = itemNameDisplay == null ? Optional.empty()
				: Optional.of(EnumAdaptor.valueOf(itemNameDisplay, ItemNameDisplayEnum.class));
	}

}
