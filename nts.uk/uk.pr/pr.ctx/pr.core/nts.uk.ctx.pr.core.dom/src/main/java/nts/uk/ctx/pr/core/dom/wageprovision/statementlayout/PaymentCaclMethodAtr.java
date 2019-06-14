package nts.uk.ctx.pr.core.dom.wageprovision.statementlayout;


/**
* 支給計算方法区分
*/
public enum PaymentCaclMethodAtr
{
    // 手入力
    MANUAL_INPUT(0, "Enum_PaymentCaclMethodAtr_MANUAL_INPUT"),
    // 個人情報参照
    PERSON_INFO_REF(1, "Enum_PaymentCaclMethodAtr_PERSON_INFO_REF"),
    // 計算式
    CACL_FOMULA(2, "Enum_PaymentCaclMethodAtr_CACL_FOMULA"),
    // 賃金テーブル
    WAGE_TABLE(3, "Enum_PaymentCaclMethodAtr_WAGE_TABLE"),
    // 共通金額
    COMMON_AMOUNT(4, "Enum_PaymentCaclMethodAtr_COMMON_AMOUNT"),
    // 内訳項目
    BREAKDOWN_ITEM(5, "Enum_PaymentCaclMethodAtr_BREAKDOWN_ITEM");
    
    /** The value. */
    public final int value;

    /** The name id. */
   	public final String nameId;
    
    private PaymentCaclMethodAtr(int value, String nameId)
    {
        this.value = value;
        this.nameId = nameId;
    }
}
