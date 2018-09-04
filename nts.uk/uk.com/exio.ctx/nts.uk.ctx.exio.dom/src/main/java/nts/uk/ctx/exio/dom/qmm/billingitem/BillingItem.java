package nts.uk.ctx.exio.dom.qmm.billingitem;

import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 明細書項目
 */
@Getter
public class BillingItem extends AggregateRoot {

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * カテゴリ区分
	 */
	private CategoryAtr categoryAtr;

	/**
	 * 項目名コード
	 */
	private ItemNameCd itemNameCd;

	/**
	 * 給与項目ID
	 */
	private String salaryItemId;

	/**
	 * 既定区分
	 */
	private DefaultAtr defaultAtr;

	/**
	 * 値の属性
	 */
	private ValueAtr valueAtr;

	/**
	 * 廃止区分
	 */
	private DeprecatedAtr deprecatedAtr;

	/**
	 * 社会保険対象変更区分
	 */
	private Optional<SocialInsuaEditableAtr> socialInsuaEditableAtr;

	/**
	 * 統合コード
	 */
	private Optional<IntergrateCd> intergrateCd;

	public BillingItem(String cid, int categoryAtr, int itemNameCd, String salaryItemId, int defaultAtr, int valueAtr,
			int deprecatedAtr, int socialInsuaEditableAtr, int intergrateCd) {
		super();
		this.cid = cid;
		this.categoryAtr = EnumAdaptor.valueOf(categoryAtr, CategoryAtr.class);
		this.itemNameCd = new ItemNameCd(itemNameCd);
		this.salaryItemId = salaryItemId;
		this.defaultAtr = EnumAdaptor.valueOf(defaultAtr, DefaultAtr.class);
		this.valueAtr = EnumAdaptor.valueOf(valueAtr, ValueAtr.class);
		this.deprecatedAtr = EnumAdaptor.valueOf(deprecatedAtr, DeprecatedAtr.class);
		this.socialInsuaEditableAtr = Optional
				.ofNullable(EnumAdaptor.valueOf(socialInsuaEditableAtr, SocialInsuaEditableAtr.class));
		this.intergrateCd = Optional.ofNullable(new IntergrateCd(intergrateCd));
	}

}
