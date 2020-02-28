package nts.uk.ctx.pr.core.dom.wageprovision.formula;


import nts.arc.i18n.I18NText;

/**
 * 入れ子利用区分
 */
public enum NestedUseCls {

    NOT_USE(0, I18NText.getText("Enum_NestedUseCls_NOT_USE")),
    USE(1, I18NText.getText("Enum_NestedUseCls_USE"));

    /**
     * The value.
     */
    public final int value;

    /**
     * The name id.
     */
    public final String nameId;

    private NestedUseCls(int value, String nameId) {
        this.value = value;
        this.nameId = nameId;
    }
}
