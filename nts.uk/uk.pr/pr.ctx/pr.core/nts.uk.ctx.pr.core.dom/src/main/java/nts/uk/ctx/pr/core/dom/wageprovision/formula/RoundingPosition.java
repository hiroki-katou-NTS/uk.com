package nts.uk.ctx.pr.core.dom.wageprovision.formula;

import nts.arc.i18n.I18NText;

/**
 * 端数処理位置
 */
public enum RoundingPosition {

    ONE_YEN(0, I18NText.getText("Enum_RoundingPosition_ONE_YEN")),
    TEN_YEN(1, I18NText.getText("Enum_RoundingPosition_TEN_YEN")),
    ONE_HUNDRED_YEN(2, I18NText.getText("Enum_RoundingPosition_ONE_HUNDRED_YEN")),
    ONE_THOUSAND_YEN(3, I18NText.getText("Enum_RoundingPosition_ONE_THOUSAND_YEN"));


    /** The value. */
    public final int value;

    /** The name id. */
    public final String nameId;
    private RoundingPosition(int value, String nameId)
    {
        this.value = value;
        this.nameId = nameId;
    }
}
