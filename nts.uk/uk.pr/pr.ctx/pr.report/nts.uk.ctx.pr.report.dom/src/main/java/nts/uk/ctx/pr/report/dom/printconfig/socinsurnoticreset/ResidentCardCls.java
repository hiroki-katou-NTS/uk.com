package nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset;


/**
* 住民票有無区分
*/
public enum ResidentCardCls {
    
    NO(1, "Enum_ResidentCardCls_YES"),
    YES(2, "Enum_ResidentCardCls_YES_NO");
    
    /** The value. */
    public final int value;
    
    /** The name id. */
    public final String nameId;
    private ResidentCardCls(int value, String nameId)
    {
        this.value = value;
        this.nameId = nameId;
    }
}
