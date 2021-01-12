package nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums;

/**
 * Enumeration: 帳票共通の属性
 * @author chinh.hm
 */
public enum CommonAttributesOfForms {
    //1	勤務種類
    WORK_TYPE(1),
    //2	就業時間帯
    WORKING_HOURS(2),
    //3	時刻
    TIME(3),
    //4	時間
    HOURS(4),
    //5	回数
    TIMES(5),
    //6	日数
    DAYS(6),
    //7	金額
    AMOUNT(7);

    public final int value;

    private CommonAttributesOfForms(int value){
        this.value = value;
    }
}
