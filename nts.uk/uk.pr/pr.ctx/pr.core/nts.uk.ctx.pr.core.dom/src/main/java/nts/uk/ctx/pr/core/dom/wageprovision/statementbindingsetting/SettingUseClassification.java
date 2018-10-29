package nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting;


/**
* 設定利用区分
*/
public enum SettingUseClassification
{
    
    USE(1, "利用する"),
    NOTUSE(0, "利用しない");
    
    /** The value. */
    public final int value;
    
    /** The name id. */
    public final String nameId;
    private SettingUseClassification(int value, String nameId) 
    {
        this.value = value;
        this.nameId = nameId;
    }
}
