package nts.uk.ctx.pr.core.dom.retirement.payitem;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.shr.com.primitive.Memo;

/**
 * 退職金項目マスタ
 * @author Doan Duy Hung
 *
 */
public class RetirementPayItem extends AggregateRoot{
	@Getter
	private CompanyCode	companyCode;
	
	@Getter
	private IndicatorCategory category;
	
	@Getter
	private RetirementPayItemCode itemCode;
	
	@Getter
	private RetirementPayItemName itemName;
	
	@Getter
	private RetirementPayItemPrintName printName;
	
	@Getter
	private RetirementPayItemEnglishName englishName;
	
	@Getter
	private RetirementPayItemFullName fullName;

	@Getter
	private Memo memo;

	public RetirementPayItem(CompanyCode companyCode, IndicatorCategory category, RetirementPayItemCode itemCode,
			RetirementPayItemName itemName, RetirementPayItemPrintName printName, RetirementPayItemEnglishName englishName,
			RetirementPayItemFullName fullName, Memo memo) {
		super();
		this.companyCode = companyCode;
		this.category = category;
		this.itemCode = itemCode;
		this.itemName = itemName;
		this.printName = printName;
		this.englishName = englishName;
		this.fullName = fullName;
		this.memo = memo;
	}
	
	public static RetirementPayItem createFromJavaType(String companyCode, int category, String itemCode,
			String itemName, String printName, String englishName, String fullName, String memo) {
		return new RetirementPayItem(
				new CompanyCode(companyCode),
				EnumAdaptor.valueOf(category, IndicatorCategory.class),
				new RetirementPayItemCode(itemCode),
				new RetirementPayItemName(itemName),
				new RetirementPayItemPrintName(printName),
				new RetirementPayItemEnglishName(englishName),
				new RetirementPayItemFullName(fullName),
				new Memo(memo));
	
	}
}
