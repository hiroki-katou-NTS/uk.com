package nts.uk.ctx.pr.core.dom.wageprovision.statebindingset;


/**
* 設定利用区分
*/
public enum SettingUseCls
{
    
    USE(1, "利用する"),
    NOTUSE(0, "利用しない");
    
    /** The value. */
    public final int value;
    
    /** The name id. */
    public final String nameId;
    private SettingUseCls(int value, String nameId)
    {
        this.value = value;
        this.nameId = nameId;
    }
}
