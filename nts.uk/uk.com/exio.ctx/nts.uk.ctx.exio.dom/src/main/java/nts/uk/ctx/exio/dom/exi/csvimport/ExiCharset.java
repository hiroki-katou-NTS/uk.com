package nts.uk.ctx.exio.dom.exi.csvimport;

public enum ExiCharset {
    
    /** The Shift JIS. */
    Shift_JIS(3, "Shift JIS", "Shift JIS");

    /** The value. */
    public Integer value;

    /** The name id. */
    public String nameId;

    /** The description. */
    public String description;

    /** The Constant values. */
    private final static ExiCharset[] values = ExiCharset.values();

    private ExiCharset(Integer value, String nameId, String description) {
        this.value = value;
        this.nameId = nameId;
        this.description = description;
    }

    public static ExiCharset valueOf(Integer value) {
        // Invalid object.
        if (value == null) {
            return null;
        }

        // Find value.
        for (ExiCharset val : ExiCharset.values) {
            if (val.value == value) {
                return val;
            }
        }

        // Not found.
        return null;
    }
}
