package nts.uk.ctx.pr.core.dom.wageprovision.unitpricename;


/**
* 個人単価種類
*/
public enum PerUnitPriceType
{
    
    HOUR(0, "時間単価"),
    DAILY_AMOUNT(1, "日額"),
    OTHER(2, "その他");
    
    /** The value. */
    public final int value;
    
    /** The name id. */
    public final String nameId;
    private PerUnitPriceType(int value, String nameId) 
    {
        this.value = value;
        this.nameId = nameId;
    }
}
