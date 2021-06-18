package nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums;

/**
 *Enumeration:  帳票共通の日次月次区分
 * @author chinh.hm
 */
public enum DailyMonthlyClassification {
    //1	日次
    DAILY(1),

    //2 月次
    MONTHLY(2);

    public final int value;

    private DailyMonthlyClassification(int value){
        this.value = value;
    }
}
