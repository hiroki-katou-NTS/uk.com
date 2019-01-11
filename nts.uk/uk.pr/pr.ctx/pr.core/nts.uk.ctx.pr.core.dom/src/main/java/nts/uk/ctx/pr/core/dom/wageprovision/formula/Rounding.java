package nts.uk.ctx.pr.core.dom.wageprovision.formula;

import nts.arc.i18n.I18NText;

/**
 * 端数処理
 */
public enum Rounding {

    ROUND_UP(0, I18NText.getText("Enum_Rounding_ROUND_UP")),
    TRUNCATION(1, I18NText.getText("Enum_Rounding_TRUNCATION")),
    DOWN_1_UP_2(2, I18NText.getText("Enum_Rounding_DOWN_1_UP_2")),
    DOWN_2_UP_3(3, I18NText.getText("Enum_Rounding_DOWN_2_UP_3")),
    DOWN_3_UP_4(4, I18NText.getText("Enum_Rounding_DOWN_3_UP_4")),
    DOWN_4_UP_5(5, I18NText.getText("Enum_Rounding_DOWN_4_UP_5")),
    DOWN_5_UP_6(6, I18NText.getText("Enum_Rounding_DOWN_5_UP_6")),
    DOWN_6_UP_7(7, I18NText.getText("Enum_Rounding_DOWN_6_UP_7")),
    DOWN_7_UP_8(8, I18NText.getText("Enum_Rounding_DOWN_7_UP_8")),
    DOWN_8_UP_9(9, I18NText.getText("Enum_Rounding_DOWN_8_UP_9"));


    /** The value. */
    public final int value;

    /** The name id. */
    public final String nameId;
    private Rounding(int value, String nameId)
    {
        this.value = value;
        this.nameId = nameId;
    }
}
