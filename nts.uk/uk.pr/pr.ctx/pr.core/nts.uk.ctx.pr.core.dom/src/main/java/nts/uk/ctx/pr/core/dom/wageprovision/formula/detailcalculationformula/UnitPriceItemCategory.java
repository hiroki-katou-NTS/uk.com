package nts.uk.ctx.pr.core.dom.wageprovision.formula.detailcalculationformula;

import nts.arc.i18n.I18NText;

/**
* 単価項目区分
*/
public enum UnitPriceItemCategory {
    COMPANY_UNIT_PRICE_ITEM(0, I18NText.getText("Enum_UnitPriceItemCategory_COMPANY_UNIT_PRICE_ITEM")),
    INDIVIDUAL_UNIT_PRICE_IEM(1, I18NText.getText("Enum_UnitPriceItemCategory_INDIVIDUAL_UNIT_PRICE_ITEM"));

    /** The value. */
    public final int value;

    /** The name id. */
    public final String nameId;
    private UnitPriceItemCategory(int value, String nameId)
    {
        this.value = value;
        this.nameId = nameId;
    }
}
