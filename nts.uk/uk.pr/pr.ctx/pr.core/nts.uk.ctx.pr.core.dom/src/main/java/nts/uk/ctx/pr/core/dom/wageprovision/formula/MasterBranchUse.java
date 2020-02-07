package nts.uk.ctx.pr.core.dom.wageprovision.formula;


import nts.arc.i18n.I18NText;

/**
* マスタ分岐利用
*/
public enum MasterBranchUse {
    
    NOT_USE(0, I18NText.getText("Enum_NestedUseCls_NOT_USE")),
    USE(1, I18NText.getText("Enum_NestedUseCls_USE"));
    
    /** The value. */
    public final int value;
    
    /** The name id. */
    public final String nameId;
    private MasterBranchUse(int value, String nameId) 
    {
        this.value = value;
        this.nameId = nameId;
    }
}
