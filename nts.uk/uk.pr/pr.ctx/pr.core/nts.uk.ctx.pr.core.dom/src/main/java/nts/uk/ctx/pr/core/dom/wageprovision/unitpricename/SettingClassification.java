package nts.uk.ctx.pr.core.dom.wageprovision.unitpricename;


/**
* 固定的賃金の設定区分
*/
public enum SettingClassification
{
    
    DESIGNATE_BY_ALL_MEMBERS(1, "全員一律で指定する"),
    DESIGNATE_FOR_EACH_TYPE(0, "給与契約形態ごとに指定する");
    
    /** The value. */
    public final int value;
    
    /** The name id. */
    public final String nameId;
    private SettingClassification(int value, String nameId) 
    {
        this.value = value;
        this.nameId = nameId;
    }
}
