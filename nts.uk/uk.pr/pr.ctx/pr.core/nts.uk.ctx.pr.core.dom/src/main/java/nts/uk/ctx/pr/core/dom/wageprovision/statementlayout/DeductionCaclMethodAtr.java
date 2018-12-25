package nts.uk.ctx.pr.core.dom.wageprovision.statementlayout;


/**
* 控除計算方法区分
*/
public enum DeductionCaclMethodAtr
{
    // 手入力
    MANUAL_INPUT(0, "Enum_DeductionCaclMethodAtr_MANUAL_INPUT"),
    // 個人情報参照
    PERSON_INFO_REF(1, "Enum_DeductionCaclMethodAtr_PERSON_INFO_REF"),
    // 計算式
    CACL_FOMULA(2, "Enum_DeductionCaclMethodAtr_CACL_FOMULA"),
    // 賃金テーブル
    WAGE_TABLE(3, "Enum_DeductionCaclMethodAtr_WAGE_TABLE"),
    // 共通金額
    COMMON_AMOUNT(4, "Enum_DeductionCaclMethodAtr_COMMON_AMOUNT"),
    // 支給相殺
    SUPPLY_OFFSET(5, "Enum_DeductionCaclMethodAtr_SUPPLY_OFFSET"),
    // 内訳項目
    BREAKDOWN_ITEM(6, "Enum_DeductionCaclMethodAtr_BREAKDOWN_ITEM");
    
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
