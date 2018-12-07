package nts.uk.ctx.pr.core.dom.wageprovision.formula;


/**
* 参照月
*/
public enum ReferenceMonth {
    
    CURRENT_MONTH(0, "当月"),
    TWO_MONTH_AGO(2, "２ヶ月前"),
    THREE_MONTH_AGO(3, "３ヶ月前"),
    FOUR_MONTH_AGO(4, "４ヶ月前"),
    FIVE_MONTH_AGO(5, "５ヶ月前"),
    SIX_MONTH_AGO(6, "６ヶ月前"),
    SEVEN_MONTH_AGO(7, "７ヶ月前"),
    EIGHT_MONTH_AGO(8, "８ヶ月前"),
    NINE_MONTH_AGO(9, "９ヶ月前"),
    ONE_MONTH_AGO(1, "１ヶ月前"),
    TEN_MONTH_AGO(10, "１０ヶ月前"),
    ELEVEN_MONTH_AGO(11, "１１ヶ月前"),
    TWELVE_MONTH_AGO(12, "１２ヶ月前");
    
    /** The value. */
    public final int value;
    
    /** The name id. */
    public final String nameId;
    private ReferenceMonth(int value, String nameId) 
    {
        this.value = value;
        this.nameId = nameId;
    }
}
