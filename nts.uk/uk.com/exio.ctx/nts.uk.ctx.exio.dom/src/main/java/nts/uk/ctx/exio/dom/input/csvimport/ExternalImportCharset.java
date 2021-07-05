package nts.uk.ctx.exio.dom.input.csvimport;

/**
 * 
 * 受入CSV文字コード
 *
 */
public enum ExternalImportCharset {
    // 現状　Shift JIS　にのみ対応
	
    /** Shift JIS. */
    Shift_JIS(3, "Shift JIS", "Shift JIS");

    /** The value. */
    public Integer value;

    /** The name id. */
    public String nameId;

    /** The description. */
    public String description;

    /** The Constant values. */
    private final static ExternalImportCharset[] values = ExternalImportCharset.values();

    private ExternalImportCharset(Integer value, String nameId, String description) {
        this.value = value;
        this.nameId = nameId;
        this.description = description;
    }

    public static ExternalImportCharset valueOf(Integer value) {
        // Invalid object.
        if (value == null) {
            return null;
        }

        // Find value.
        for (ExternalImportCharset val : ExternalImportCharset.values) {
            if (val.value == value) {
                return val;
            }
        }

        // Not found.
        return null;
    }
}
