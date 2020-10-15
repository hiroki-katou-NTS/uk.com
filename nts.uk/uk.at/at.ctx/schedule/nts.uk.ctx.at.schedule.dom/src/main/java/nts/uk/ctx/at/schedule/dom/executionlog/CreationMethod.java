package nts.uk.ctx.at.schedule.dom.executionlog;

//作成方法
public enum CreationMethod {
    /** The personal information. */
    // 個人情報
    PERSONAL_INFO(0),

    /** The specify the creation. */
    // 作成方法指定
    SPECIFY_CREATION(1),

    /** The schedule copy. */
    // 過去スケジュールコピー
    SCHEDULE_COPY(2);

    /** The value. */
    public final int value;

    /** The Constant values. */
    private final static CreationMethod[] values = CreationMethod.values();

    /**
     * Instantiates a new Creation Method.
     *
     * @param value the value
     */
    private CreationMethod(int value) {
        this.value = value;
    }

    /**
     * Value of.
     *
     * @param value the value
     * @return the Creation Method
     */
    public static CreationMethod valueOf(Integer value) {
        // Invalid object.
        if (value == null) {
            return null;
        }

        // Find value.
        for (CreationMethod val : CreationMethod.values) {
            if (val.value == value) {
                return val;
            }
        }

        // Not found.
        return null;
    }

}
