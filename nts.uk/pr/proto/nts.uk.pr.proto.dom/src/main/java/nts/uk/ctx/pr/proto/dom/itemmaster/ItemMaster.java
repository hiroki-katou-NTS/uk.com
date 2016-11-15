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
	/** 略名 */
	@Getter
	private ItemABName itemAbName;
	/** 項目属性 */
	@Getter
	private ItemAtr itemAttributeAtr;
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
	private TaxAtr taxAtr;
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
	
	public ItemMaster(CompanyCode companyCode, ItemCode itemCode, CategoryAtr categoryAtr, ItemName itemName) {
		super();
		this.itemName = itemName;
		this.companyCode = companyCode;
		this.itemCode = itemCode;
		this.categoryAtr = categoryAtr;
	}
	/**
	 * Validate
	 */
	@Override
	public void validate() {
		super.validate();
	}	
	
	public static ItemMaster createSimpleFromJavaType(String companyCode, String itemCode, int categoryAtr, String itemName)
	{
		return new ItemMaster(new CompanyCode(companyCode),
				new ItemCode(itemCode),
				EnumAdaptor.valueOf(categoryAtr, CategoryAtr.class),
				new ItemName(itemName));
		
	}
}
