package nts.uk.ctx.pr.proto.dom.itemmaster;

import java.util.List;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.proto.dom.enums.CategoryAtr;
import nts.uk.ctx.pr.proto.dom.enums.DisplayAtr;
import nts.uk.ctx.pr.proto.dom.layout.detail.RangeChecker;

/**
 * 
 * 項目マスタ
 *
 */
public class ItemMaster extends AggregateRoot{
	/** 会社コード */
	@Getter
	private CompanyCode companyCode;	
	/** カテゴリ区分 */
	@Getter
	private CategoryAtr categoryAtr;
	/** 項目コード */
	@Getter
	private ItemCode itemCode;
	/** 項目名称 */
	@Getter
	private ItemName itemName;
	/** 略名 */
	@Getter
	private ItemName itemAbName;
	/** 表示区分 */
	@Getter
	private DisplayAtr displayAtr;
	/** 平均賃金対象区分 */
	@Getter
	private WageClassificationAtr avgPaidAtr;
	@Getter
	private List<RangeChecker> alarm;
	@Getter
	private List<RangeChecker> error;
	
	/** 控除種類 */
	@Getter
	private DeductionAtr deductAttribute;
	/** 固定的賃金対象区分 */
	@Getter
	private WageClassificationAtr fixedPaidAtr;
	/** 統合項目コード */
	@Getter
	private IntegratedItemCode integratedItemCode;
	
	/** 項目属性 */
	@Getter
	private ItemAtr itemAttributeAtr;	
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
	private TaxAtr taxAtr;
	/** ゼロ表示区分 */
	@Getter
	private DisplayAtr zeroDisplayAtr;
	
	
	public ItemMaster(CompanyCode companyCode, ItemCode itemCode, CategoryAtr categoryAtr, ItemName itemName, TaxAtr taxAtr) {
		super();
		this.itemName = itemName;
		this.companyCode = companyCode;
		this.itemCode = itemCode;
		this.categoryAtr = categoryAtr;
		this.taxAtr = taxAtr;
	}
	/**
	 * Validate
	 */
	@Override
	public void validate() {
		super.validate();
	}	
	
	public static ItemMaster createSimpleFromJavaType(
			String companyCode, 
			String itemCode, 
			int categoryAtr, 
			String itemName,
			int taxAtr)
	{
		return new ItemMaster(new CompanyCode(companyCode),
				new ItemCode(itemCode),
				EnumAdaptor.valueOf(categoryAtr, CategoryAtr.class),
				new ItemName(itemName), EnumAdaptor.valueOf(taxAtr, TaxAtr.class));
		
	}
	
	/**
	 * additional Info 
	 * @param limitMoney
	 * @param fixedPaidAtr
	 * @param laborInsuranceAtr
	 * @param socialInsuranceAtr
	 * @return
	 */
	public ItemMaster additionalInfo(int limitMoney, int fixedPaidAtr, int laborInsuranceAtr, int socialInsuranceAtr, int avgPaidAtr, int deductAttribute) {
		this.limitMoney = new LimitMoney(limitMoney);
		this.fixedPaidAtr = EnumAdaptor.valueOf(fixedPaidAtr, WageClassificationAtr.class);
		this.laborInsuranceAtr = EnumAdaptor.valueOf(laborInsuranceAtr, WageClassificationAtr.class);
		this.socialInsuranceAtr = EnumAdaptor.valueOf(socialInsuranceAtr, WageClassificationAtr.class);
		this.avgPaidAtr = EnumAdaptor.valueOf(avgPaidAtr, WageClassificationAtr.class);
		this.deductAttribute = EnumAdaptor.valueOf(deductAttribute, DeductionAtr.class);
		return this;
	}
	
	/**
	 * Check tax = COMMUTING_COST || COMMUTING_EXPENSE
	 * (using for calculate payment create data)
	 * @return
	 */
	public boolean isTaxCommutingoCostOrCommutingExpense() {
		return this.taxAtr == TaxAtr.COMMUTING_COST || this.taxAtr == TaxAtr.COMMUTING_EXPENSE;
	}
	
	/**
	 * Check tax = TAXATION || TAX_FREE_LIMIT || TAX_FREE_UN_LIMIT
	 * (using for calculate payment create data)
	 * @return
	 */
	public boolean isTaxTaxationOrTaxFreeLimitOrTaxFreeUnLimit() {
		return this.taxAtr ==  TaxAtr.TAXATION || this.taxAtr == TaxAtr.TAX_FREE_LIMIT || this.taxAtr == TaxAtr.TAX_FREE_UN_LIMIT;
	}
}
