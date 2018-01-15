/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave;

/**
 * The Enum 介護看護区分.
 */
public enum NursingCategory {
    
    /** The Nursing. */
    Nursing(0, "介護", "介護"),

    /** The Child nursing. */
    ChildNursing(1, "子の看護", "子の看護");

    /** The value. */
    public int value;

    /** The name id. */
    public String nameId;

    /** The description. */
    public String description;

    /** The Constant values. */
    private final static NursingCategory[] values = NursingCategory.values();

    /**
     * Instantiates a new nursing category.
     *
     * @param value the value
     * @param nameId the name id
     * @param description the description
     */
    private NursingCategory(int value, String nameId, String description) {
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
    public static NursingCategory valueOf(Integer value) {
        // Invalid object.
        if (value == null) {
            return null;
        }

        // Find value.
        for (NursingCategory val : NursingCategory.values) {
            if (val.value == value) {
                return val;
            }
        }

        // Not found.
        return null;
    }
}
