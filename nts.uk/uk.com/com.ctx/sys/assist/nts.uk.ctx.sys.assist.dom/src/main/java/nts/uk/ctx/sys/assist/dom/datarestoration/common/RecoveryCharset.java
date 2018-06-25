package nts.uk.ctx.sys.assist.dom.datarestoration.common;

public enum RecoveryCharset {
    
	/** The Shift JIS. */
    Shift_JIS(3, "Shift JIS", "Shift JIS");

    /** The value. */
    public Integer value;

    /** The name id. */
    public String nameId;

    /** The description. */
    public String description;

    /** The Constant values. */
    private final static RecoveryCharset[] values = RecoveryCharset.values();

    private RecoveryCharset(Integer value, String nameId, String description) {
        this.value = value;
        this.nameId = nameId;
        this.description = description;
    }

    public static RecoveryCharset valueOf(Integer value) {
        // Invalid object.
        if (value == null) {
            return null;
        }

        // Find value.
        for (RecoveryCharset val : RecoveryCharset.values) {
            if (val.value == value) {
                return val;
            }
        }

        // Not found.
        return null;
    }
}
