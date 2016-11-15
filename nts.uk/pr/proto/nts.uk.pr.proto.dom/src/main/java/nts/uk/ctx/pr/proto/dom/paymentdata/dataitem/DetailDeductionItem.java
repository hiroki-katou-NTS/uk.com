package nts.uk.ctx.pr.proto.dom.paymentdata.dataitem;

import lombok.Getter;
import nts.uk.ctx.pr.proto.dom.itemmaster.DeductionAtr;
import nts.uk.ctx.pr.proto.dom.layout.detail.ItemCode;

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
			InsuranceAtr laborInsuranceAtr, DeductionAtr deductAttribute ) {
		super(itemCode, value, correctFlag, socialInsuranceAtr, laborInsuranceAtr);
		this.deductAttribute = deductAttribute;
	}

	
}
