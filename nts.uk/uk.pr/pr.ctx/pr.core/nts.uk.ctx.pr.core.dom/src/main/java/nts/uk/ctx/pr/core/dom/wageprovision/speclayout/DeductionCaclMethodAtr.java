package nts.uk.ctx.pr.core.dom.wageprovision.speclayout;


/**
* 控除計算方法区分
*/
public enum DeductionCaclMethodAtr
{
    
    MANUAL_INPUT(0, "手入力"),
    PERSON_INFO_REF(1, "個人情報参照"),
    CACL_FOMULA(2, "計算式"),
    WAGE_TABLE(3, "賃金テーブル"),
    COMMON_AMOUNT(4, "共通金額"),
    SUPPLY_OFFSET(5, "支給相殺"),
    BREAKDOWN_ITEM(6, "内訳項目");
    
    /** The value. */
    public final int value;
    
    /** The name id. */
    public final String nameId;
    private DeductionCaclMethodAtr(int value, String nameId) 
    {
        this.value = value;
        this.nameId = nameId;
    }
}
