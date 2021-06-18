package nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums;

/**
 * Enumeration: 帳票共通の属性
 * @author chinh.hm
 */
public enum CommonAttributesOfForms {

    // 1	勤務種類
    WORK_TYPE(1),

    // 2	就業時間帯
    WORKING_HOURS(2),

    // 3	時刻
    TIME_OF_DAY(3),//TIME(3),

    // 4	時間
    TIME(4),// HOURS(4),

    // 5	回数
    NUMBER_OF_TIMES(5),//  TIMES(5),

    // 6	日数
    DAYS(6),

    // 7	金額
    AMOUNT_OF_MONEY(7), //AMOUNT(7);

    // 11   その他_文字
    OTHER_CHARACTERS(11),

    // 12   その他_数値
    OTHER_NUMERICAL_VALUE(12),

    // 13   その他_文字数値
    OTHER_CHARACTER_NUMBER(13);


    public final int value;

    private CommonAttributesOfForms(int value){
        this.value = value;
    }


}
