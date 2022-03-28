package nts.uk.ctx.exio.dom.exo.condset;

/**
 * 文字コード
 */
public enum EncodeType {
    /**
     * UTF-8 BOMなし
     */
    UTF8WITHOUTBOM(1,"UTF-8 BOMなし"),
    /**
     * UTF-8 BOM付き
     */
    UTF8WITHBOM(2,"UTF-8 BOM付き"),

    /**
     * Shift-JIS
     */
    SHIFTJIS(3,"Shift-JIS");

    /** The value. */
    public final int value;
    public final String name;
    private EncodeType(int value, String name) {
        this.value = value;
        this.name = name;
    }
}
