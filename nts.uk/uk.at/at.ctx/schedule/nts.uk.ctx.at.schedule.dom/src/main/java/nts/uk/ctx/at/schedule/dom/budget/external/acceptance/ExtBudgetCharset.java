/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.budget.external.acceptance;

/**
 * The Enum ExtBudgetCharset.
 */
public enum ExtBudgetCharset {
    
    /** The Shift JIS. */
    Shift_JIS(3, "Shift JIS", "Shift JIS");

    /** The value. */
    public int value;

    /** The name id. */
    public String nameId;

    /** The description. */
    public String description;

    /** The Constant values. */
    private final static ExtBudgetCharset[] values = ExtBudgetCharset.values();

    /**
     * Instantiates a new ext budget charset.
     *
     * @param value the value
     * @param nameId the name id
     * @param description the description
     */
    private ExtBudgetCharset(int value, String nameId, String description) {
        this.value = value;
        this.nameId = nameId;
        this.description = description;
    }

    /**
     * Value of.
     *
     * @param value the value
     * @return the ext budget charset
     */
    public static ExtBudgetCharset valueOf(Integer value) {
        // Invalid object.
        if (value == null) {
            return null;
        }

        // Find value.
        for (ExtBudgetCharset val : ExtBudgetCharset.values) {
            if (val.value == value) {
                return val;
            }
        }

        // Not found.
        return null;
    }
}
