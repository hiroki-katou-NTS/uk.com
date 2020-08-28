package nts.uk.ctx.at.schedule.dom.shift.WeeklyWorkDay;

/**
 * The Enum DayOfWeek.
 */
// 曜日
public enum DayOfWeek {

    /** The monday. */
    // 月曜日
    MONDAY(1, "Enum_DayOfWeek_Monday", "月曜日"),

    /** The tuesday. */
    // 火曜日
    TUESDAY(2, "Enum_DayOfWeek_Tuesday", "火曜日"),

    /** The wednesday. */
    // 水曜日
    WEDNESDAY(3, "Enum_DayOfWeek_Wednesday", "水曜日"),

    /** The thursday. */
    // 木曜日
    THURSDAY(4, "Enum_DayOfWeek_Thursday", "木曜日"),

    /** The friday. */
    // 金曜日
    FRIDAY(5, "Enum_DayOfWeek_Friday", "金曜日"),

    /** The saturday. */
    // 土曜日
    SATURDAY(6, "Enum_DayOfWeek_Saturday", "土曜日"),

    /** The sunday. */
    // 日曜日
    SUNDAY(7, "Enum_DayOfWeek_Sunday", "日曜日");


    /** The value. */
    public final int value;

    /** The name id. */
    public final String nameId;

    /** The description. */
    public final String description;

    /** The Constant values. */
    private final static DayOfWeek[] values = DayOfWeek.values();

    /**
     * Instantiates a new day of week.
     *
     * @param value the value
     * @param nameId the name id
     * @param description the description
     */
    private DayOfWeek(int value, String nameId, String description) {
        this.value = value;
        this.nameId = nameId;
        this.description = description;
    }

    /**
     * Value of.
     *
     * @param value the value
     * @return the day of week
     */
    public static DayOfWeek valueOf(Integer value) {
        // Invalid object.
        if (value == null) {
            return null;
        }

        // Find value.
        for (DayOfWeek val : DayOfWeek.values) {
            if (val.value == value) {
                return val;
            }
        }
        // Not found.
        return null;
    }
}
