package nts.uk.ctx.exio.dom.qmm.specificationitem;

import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;

/**
 * 明細書項目の表示設定
 */
@Getter
public class SpecificationItemDisplaySetting {

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

	public SpecificationItemDisplaySetting(String cid, String salaryItemId, int zeroDisplayAtr,
			Integer itemNameDisplay) {
		this.cid = cid;
		this.salaryItemId = salaryItemId;
		this.zeroDisplayAtr = EnumAdaptor.valueOf(zeroDisplayAtr, ZeroDisplayEnum.class);
		this.itemNameDisplay = itemNameDisplay == null ? Optional.empty()
				: Optional.of(EnumAdaptor.valueOf(itemNameDisplay, ItemNameDisplayEnum.class));
	}

}
