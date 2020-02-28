package nts.uk.ctx.pr.core.dom.wageprovision.formula;


import nts.arc.i18n.I18NText;

/**
* 参照月
*/
public enum ReferenceMonth {
    
    CURRENT_MONTH(0, I18NText.getText("Enum_ReferenceMonth_CURRENT_MONTH")),
    ONE_MONTH_AGO(1, I18NText.getText("Enum_ReferenceMonth_ONE_MONTH_AGO")),
    TWO_MONTH_AGO(2, I18NText.getText("Enum_ReferenceMonth_TWO_MONTH_AGO")),
    THREE_MONTH_AGO(3, I18NText.getText("Enum_ReferenceMonth_THREE_MONTH_AGO")),
    FOUR_MONTH_AGO(4, I18NText.getText("Enum_ReferenceMonth_FOUR_MONTH_AGO")),
    FIVE_MONTH_AGO(5, I18NText.getText("Enum_ReferenceMonth_FIVE_MONTH_AGO")),
    SIX_MONTH_AGO(6, I18NText.getText("Enum_ReferenceMonth_SIX_MONTH_AGO")),
    SEVEN_MONTH_AGO(7, I18NText.getText("Enum_ReferenceMonth_SEVEN_MONTH_AGO")),
    EIGHT_MONTH_AGO(8, I18NText.getText("Enum_ReferenceMonth_EIGHT_MONTH_AGO")),
    NINE_MONTH_AGO(9, I18NText.getText("Enum_ReferenceMonth_NINE_MONTH_AGO")),
    TEN_MONTH_AGO(10, I18NText.getText("Enum_ReferenceMonth_TEN_MONTH_AGO")),
    ELEVEN_MONTH_AGO(11, I18NText.getText("Enum_ReferenceMonth_ELEVEN_MONTH_AGO")),
    TWELVE_MONTH_AGO(12, I18NText.getText("Enum_ReferenceMonth_TWELVE_MONTH_AGO"));
    
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
