package nts.uk.ctx.pr.core.dom.wageprovision.formula;


import nts.arc.i18n.I18NText;

/**
* 基底項目区分
*/
public enum BaseItemClassification {
    
    FIXED_VALUE(0, I18NText.getText("Enum_BaseItemCls_FIXED_VALUE")),
    STANDARD_DAY(1, I18NText.getText("Enum_BaseItemCls_STANDARD_DAY")),
    WORKDAY(2, I18NText.getText("Enum_BaseItemCls_WORKDAY")),
    ATTENDANCE_DAY(3, I18NText.getText("Enum_BaseItemCls_ATTENDANCE_DAY")),
    ATTENDANCE_DAY_AND_HOLIDAY(4, I18NText.getText("Enum_BaseItemCls_ATTENDANCE_DAY_AND_HOLIDAY")),
    REFERENCE_DATE_TIME(5, I18NText.getText("Enum_BaseItemCls_REFERENCE_TIME")),
    SERVICE_DAY_MUL_REFER_TIME(6, I18NText.getText("Enum_BaseItemCls_SERVICE_DAY_MUL_REFER_TIME")),
    WORKDAY_MUL_REFER_TIME(7, I18NText.getText("Enum_BaseItemCls_WORKDAY_MUL_REFER_TIME")),
    WORKDAY_AND_HOLIDAY_MUL_REFER_TIME(8, I18NText.getText("Enum_BaseItemCls_WORKDAY_AND_HOLIDAY_MUL_REFER_TIME")),
    ATTENDANCE_TIME(9, I18NText.getText("Enum_BaseItemCls_ATTENDANCE_TIME"));
    
    /** The value. */
    public final int value;
    
    /** The name id. */
    public final String nameId;
    private BaseItemClassification(int value, String nameId) 
    {
        this.value = value;
        this.nameId = nameId;
    }
}
