package nts.uk.ctx.pr.proto.dom.paymentdata.dataitem;

import lombok.Getter;
import nts.uk.ctx.pr.proto.dom.enums.CategoryAtr;
import nts.uk.ctx.pr.proto.dom.itemmaster.DeductionAtr;
import nts.uk.ctx.pr.proto.dom.itemmaster.ItemCode;

public class DetailDeductionItem extends DetailItem {
	
	/*
	 * 控除種類
	 */
	@Getter
	private DeductionAtr deductAttribute;
	
	/**
	 * Constructor
	 * 
	 * @param value
	 * @param correctFlag
	 * @param socialInsuranceAtr
	 * @param laborInsuranceAtr
	 * @param deductAttribute
	 */
	public DetailDeductionItem(ItemCode itemCode, Double value, CorrectFlag correctFlag, InsuranceAtr socialInsuranceAtr,
			InsuranceAtr laborInsuranceAtr, DeductionAtr deductAttribute, CategoryAtr categoryAttribute ) {
		super(itemCode, value, correctFlag, socialInsuranceAtr, laborInsuranceAtr, categoryAttribute);
		this.deductAttribute = deductAttribute;
	}

	
}
