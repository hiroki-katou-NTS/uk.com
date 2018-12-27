package nts.uk.ctx.at.function.dom.alarm.alarmlist.monthly;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TypeMonCheckItemImport {

    /*所定公休日数 */
    CERTAIN_DAY_OFF(0, "Enum_TypeMonCheckItem_CertainDayOff"),
//    /* 36協定エラー時間*/
//    _36_AGR_ERROR_TIME(1, "Enum_TypeMonCheckItem_36AgrErrorTime"),
    /* 36協定アラーム時間 */
//    _36_AGR_ALARM_TIME(2, "Enum_TypeMonCheckItem_36AgrAlarmTime"),
    /* 残数チェック*/
    CHECK_REMAIN_NUMBER(3, "Enum_TypeMonCheckItem_CheckRemainNumber"),
    /* 時間 */
    TIME(4, "Enum_TypeMonCheckItem_Time"),
    /* 日数*/
    DAYS(5, "Enum_TypeMonCheckItem_Days"),
    /* 回数 */
    TIMES(6, "Enum_TypeMonCheckItem_Times"),
    /* 金額*/
    AMOUNT_OF_MONEY(7, "Enum_TypeMonCheckItem_AmountOfMoney"),
    /* 複合条件*/
    COMPOUND_CON(8, "Enum_TypeMonCheckItem_CompoundCon");
    
    public final int value;

    public final String nameId;
}
