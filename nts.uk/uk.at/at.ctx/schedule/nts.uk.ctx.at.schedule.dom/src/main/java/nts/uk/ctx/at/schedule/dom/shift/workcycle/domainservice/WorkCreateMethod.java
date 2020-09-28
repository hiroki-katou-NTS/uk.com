package nts.uk.ctx.at.schedule.dom.shift.workcycle.domainservice;

/**
 * 勤務作成方法
 */
public enum WorkCreateMethod {

    /** The pre determined. */
    // 勤務サイクル
    WORK_CYCLE(0, "勤務サイクル"),

    /** The total working hours. */
    // 週間勤務
    WEEKLY_WORK(1, "週間勤務"),

    /** The per cost time. */
    // 人件費時間
    PUB_HOLIDAY(2, "祝日");

    /** The value. */
    public int value;

    /** The description. */
    public String description;

    /** The Constant values. */
    private static final WorkCreateMethod[] values =  WorkCreateMethod.values();

    /**
     * Instantiates a new est comparison atr.
     *
     * @param value the value
     * @param description the description
     */
    private WorkCreateMethod (int value, String description) {
        this.value = value;
        this.description = description;
    }
}
