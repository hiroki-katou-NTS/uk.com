/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.budget.external.actualresult;

/**
 * The Enum CompletionState.
 */
public enum Encoding {
    
    /** The Shift JIS. */
    Shift_JIS(1, "Shift JIS", "Shift JIS");

    /** The value. */
    public int value;

    /** The name id. */
    public String nameId;

    /** The description. */
    public String description;

    /** The Constant values. */
    private final static Encoding[] values = Encoding.values();

    /**
     * Instantiates a new nursing category.
     *
     * @param value the value
     * @param nameId the name id
     * @param description the description
     */
    private Encoding(int value, String nameId, String description) {
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
    public static Encoding valueOf(Integer value) {
        // Invalid object.
        if (value == null) {
            return null;
        }

        // Find value.
        for (Encoding val : Encoding.values) {
            if (val.value == value) {
                return val;
            }
        }

        // Not found.
        return null;
    }
}
