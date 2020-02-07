package nts.uk.ctx.pr.core.dom.wageprovision.formula;


/**
* 基準金額区分
*/
public enum StandardAmountClassification
{
    
    FIXED_AMOUNT(0, "固定金額"),
    PAYMENT_ITEM(1, "支給項目"),
    DEDUCTION_ITEM(2, "控除項目"),
    COMPANY_UNIT_PRICE_ITEM(3, "会社単価項目"),
    INDIVIDUAL_UNIT_PRICE_ITEM(4, "個人単価項目");
    
    /** The value. */
    public final int value;
    
    /** The name id. */
    public final String nameId;
    private StandardAmountClassification(int value, String nameId)
    {
        this.value = value;
        this.nameId = nameId;
    }
}
