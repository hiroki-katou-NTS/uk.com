package nts.uk.ctx.sys.auth.dom.anniversary;

/**
 * 通知の日数
 */
public enum NoticeDay {

    BEFORE_ZERO_DAY(0),
    BEFORE_ONE_DAY(1),
    BEFORE_TWO_DAY(2),
    BEFORE_THREE_DAY(3),
    BEFORE_FOUR_DAY(4),
    BEFORE_FIVE_DAY(5),
    BEFORE_SIX_DAY(6),
    BEFORE_SEVEN_DAY(7);

    public int value;

    private NoticeDay(int day) {
        this.value = day;
    }
}
