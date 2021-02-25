package nts.uk.ctx.at.schedule.dom.executionlog;

//マスタ参照先
public enum ReferenceMaster {
    /** The company calendar. */
    // 会社カレンダー
    COMPANY_CALENDAR(0),

    /** The workplace calendar. */
    // 職場カレンダー
    WORKPLACE_CALENDAR(1),

    /** The classification calendar. */
    // 分類カレンダー
    CLASS_CALENDAR(2),

    /** The month pattern. */
    // 月間パターン
    MONTH_PATTERN(3);

    /** The value. */
    public final int value;

    /** The Constant values. */
    private final static ReferenceMaster[] values = ReferenceMaster.values();

    /**
     * Instantiates a new Master Reference.
     *
     * @param value the value
     */
    private ReferenceMaster(int value) {
        this.value = value;
    }

    /**
     * Value of.
     *
     * @param value the value
     * @return the implement atr
     */
    public static ReferenceMaster valueOf(Integer value) {
        // Invalid object.
        if (value == null) {
            return null;
        }

        // Find value.
        for (ReferenceMaster val : ReferenceMaster.values) {
            if (val.value == value) {
                return val;
            }
        }

        // Not found.
        return null;
    }
}
