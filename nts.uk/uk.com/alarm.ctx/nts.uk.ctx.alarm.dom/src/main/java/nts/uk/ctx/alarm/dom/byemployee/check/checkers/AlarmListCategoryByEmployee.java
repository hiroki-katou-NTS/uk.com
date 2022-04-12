package nts.uk.ctx.alarm.dom.byemployee.check.checkers;

import lombok.RequiredArgsConstructor;
import nts.arc.enums.EnumAdaptor;

/**
 * アラームリストのカテゴリ
 */
@RequiredArgsConstructor
public enum AlarmListCategoryByEmployee {

    /** スケジュール日次 */
    SCHEDULE_DAILY(1),

    /** スケジュール複数日 */
    SCHEDULE_MULTI_DAY(2),

    /** 見込み月次 */
    PROSPECT_MONTHLY(3),

    /** 見込み年次 */
    PROSPECT_YEARLY(4),

    /** 日次 */
    RECORD_DAILY(5),

    /** 複数日 */
    RECORD_MULTI_DAY(6),

    /** 週次 */
    RECORD_WEEKLY(7),

    /** 複数週 */
    RECORD_MULTI_WEEK(8),

    /** 月次 */
    RECORD_MONTHLY(9),

    /** 複数月 */
    RECORD_MULTI_MONTH(10),

    /** 任意期間 */
    RECORD_ANY_PERIOD(11),

    /** 休日 */
    DAY_OFF(20),

    /** 36協定月次 */
    AGREE36_MONTHLY(21),

    /** 36協定年次 */
    AGREE36_YEARLY(22),

    /** 申請承認 */
    APPLICATION_APPROVAL(30),

    /** マスタ */
    MASTER(90),


    ;

    public final int value;

    public static AlarmListCategoryByEmployee valueOf(int value) {
        return EnumAdaptor.valueOf(value, AlarmListCategoryByEmployee.class);
    }
    public String categoryName() {
        return "";
    }
}
