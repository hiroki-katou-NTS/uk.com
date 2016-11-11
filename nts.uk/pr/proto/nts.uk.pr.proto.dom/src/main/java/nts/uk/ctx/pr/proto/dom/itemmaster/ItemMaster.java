package nts.uk.ctx.pr.proto.dom.itemmaster;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.proto.dom.enums.CategoryAtr;
import nts.uk.ctx.pr.proto.dom.enums.DeductionAtr;
import nts.uk.ctx.pr.proto.dom.enums.DisplayAtr;
import nts.uk.ctx.pr.proto.dom.enums.ItemAttributeAtr;
import nts.uk.ctx.pr.proto.dom.enums.ItemNameDisplayAtr;
import nts.uk.ctx.pr.proto.dom.enums.TaxAttribute;
import nts.uk.ctx.pr.proto.dom.enums.WageClassificationAtr;
import nts.uk.ctx.pr.proto.dom.layout.detail.ItemCode;

/**
 * 
 * 項目マスタ
 *
 */
public class ItemMaster extends AggregateRoot{
	/** 表示区分 */
	@Getter
	private DisplayAtr displayAtr;
	/** 平均賃金対象区分 */
	@Getter
	private WageClassificationAtr avgPaidAtr;

	/** チェック上限値 */
	@Getter
	private CheckMaxValue checkMaxValue;
	/** チェック下限値 */
	@Getter
	private CheckMinValue checkMinValue;
	/** 控除種類 */
	@Getter
	private DeductionAtr deductAttribute;
	/** 固定的賃金対象区分 */
	@Getter
	private WageClassificationAtr fixedPaidAtr;
	/** 統合項目コード */
	@Getter
	private IntegratedItemCode integratedItemCode;
	/** 略名 */
	@Getter
	private ItemABName itemAbName;
	/** 項目属性 */
	@Getter
	private ItemAttributeAtr itemAttributeAtr;
	/** 項目名称 */
	@Getter
	private ItemName itemName;
	/** 項目名表示区分 */
	@Getter
	private ItemNameDisplayAtr itemNameDisplayAtr;
	/** 労働保険対象区分 */
	@Getter
	private WageClassificationAtr laborInsuranceAtr;
	/** 限度金額 */
	@Getter
	private LimitMoney limitMoney;
	/** メモ */
	@Getter
	private Memo memo;
	/** 社会保険対象区分 */
	@Getter
	private WageClassificationAtr socialInsuranceAtr;
	/** 課税区分 */
	@Getter
	private TaxAttribute taxAtr;
	/** ゼロ表示区分 */
	@Getter
	private DisplayAtr zeroDisplayAtr;
	/** 会社コード */
	@Getter
	private CompanyCode companyCode;
	/** 項目コード */
	@Getter
	private ItemCode itemCode;
	/** カテゴリ区分 */
	@Getter
	private CategoryAtr categoryAtr;
	
	public ItemMaster(DisplayAtr displayAtr, WageClassificationAtr avgPaidAtr,
			CheckMaxValue checkMaxValue, CheckMinValue checkMinValue, DeductionAtr deductAttribute,
			WageClassificationAtr fixedPaidAtr, IntegratedItemCode integratedItemCode, ItemABName itemAbName,
			ItemAttributeAtr itemAttributeAtr, ItemName itemName, ItemNameDisplayAtr itemNameDisplayAtr,
			WageClassificationAtr laborInsuranceAtr, LimitMoney limitMoney, Memo memo,
			WageClassificationAtr socialInsuranceAtr, TaxAttribute taxAtr, DisplayAtr zeroDisplayAtr,
			CompanyCode companyCode, ItemCode itemCode, CategoryAtr categoryAtr) {
		super();
		this.displayAtr = displayAtr;
		this.avgPaidAtr = avgPaidAtr;
		this.checkMaxValue = checkMaxValue;
		this.checkMinValue = checkMinValue;
		this.deductAttribute = deductAttribute;
		this.fixedPaidAtr = fixedPaidAtr;
		this.integratedItemCode = integratedItemCode;
		this.itemAbName = itemAbName;
		this.itemAttributeAtr = itemAttributeAtr;
		this.itemName = itemName;
		this.itemNameDisplayAtr = itemNameDisplayAtr;
		this.laborInsuranceAtr = laborInsuranceAtr;
		this.limitMoney = limitMoney;
		this.memo = memo;
		this.socialInsuranceAtr = socialInsuranceAtr;
		this.taxAtr = taxAtr;
		this.zeroDisplayAtr = zeroDisplayAtr;
		this.companyCode = companyCode;
		this.itemCode = itemCode;
		this.categoryAtr = categoryAtr;
	}
	
	
}
