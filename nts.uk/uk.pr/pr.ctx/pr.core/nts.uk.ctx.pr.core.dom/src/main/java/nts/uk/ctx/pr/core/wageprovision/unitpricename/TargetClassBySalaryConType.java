package nts.uk.ctx.pr.core.wageprovision.unitpricename;


/**
* 給与契約形態ごとの対象区分
*/
public enum TargetClassBySalaryConType
{
    /*
    * 対象
    * */
    OBJECT(1),
    /*
    * 対象外
    * */
    NOT_COVERED(0);
    
    /** The value. */
    public final int value;
    private TargetClassBySalaryConType(int value)
    {
        this.value = value;
    }
}
