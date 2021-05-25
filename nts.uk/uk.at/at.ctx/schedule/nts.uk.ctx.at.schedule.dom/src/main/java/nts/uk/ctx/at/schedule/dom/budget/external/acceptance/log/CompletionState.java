/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.budget.external.acceptance.log;

/**
 * The Enum CompletionState.
 */
public enum CompletionState {
    
    /** The incomplete. */
    INCOMPLETE(0, "未完了", "未完了"),

    /** The done. */
    DONE(1, "完了", "完了"),

    /** The interruption. */
    INTERRUPTION(2, "中断", "中断");

    /** The value. */
    public int value;

    /** The name id. */
    public String nameId;

    /** The description. */
    public String description;

    /** The Constant values. */
    private final static CompletionState[] values = CompletionState.values();

    /**
     * Instantiates a new nursing category.
     *
     * @param value the value
     * @param nameId the name id
     * @param description the description
     */
    private CompletionState(int value, String nameId, String description) {
        this.value = value;
        this.nameId = nameId;
        this.description = description;
    }

    /**
     * Value of.
     *
     * @param value the value
     * @return the nursing category
     */
    public static CompletionState valueOf(Integer value) {
        // Invalid object.
        if (value == null) {
            return null;
        }

        // Find value.
        for (CompletionState val : CompletionState.values) {
            if (val.value == value) {
                return val;
            }
        }

        // Not found.
        return null;
    }
}
