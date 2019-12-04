package nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting;


/**
* 印字区分
*/
public enum PrinfCtg {

    //印字する
    PRINT(1, "Enum_PrinfCtg_PRINT"),

    //印字しない
    DO_NOT_PRINT(0, "Enum_prinfCtg_DO_NOT_PRINT");
    
    /** The value. */
    public final int value;
    
    /** The name id. */
    public final String nameId;
    private PrinfCtg(int value, String nameId) {
        this.value = value;
        this.nameId = nameId;
    }
}
