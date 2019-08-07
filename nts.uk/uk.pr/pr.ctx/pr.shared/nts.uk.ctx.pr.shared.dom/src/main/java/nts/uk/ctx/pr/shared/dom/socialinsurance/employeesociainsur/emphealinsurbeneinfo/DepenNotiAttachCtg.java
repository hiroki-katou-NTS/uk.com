package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo;


/**
* 被扶養者届添付区分
*/
public enum DepenNotiAttachCtg
{
    
    NOTATTACH(0, "ENUM_DepenNotiAttachCtg_NOTATTACH"),
    ATTACH(1, "ENUM_DepenNotiAttachCtg_ATTACH");

    /** The value. */
    public final int value;
    
    /** The name id. */
    public final String nameId;
    private DepenNotiAttachCtg(int value, String nameId) 
    {
        this.value = value;
        this.nameId = nameId;
    }
}
