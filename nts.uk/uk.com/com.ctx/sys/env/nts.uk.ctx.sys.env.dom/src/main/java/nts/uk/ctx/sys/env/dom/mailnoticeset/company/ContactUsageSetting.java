package nts.uk.ctx.sys.env.dom.mailnoticeset.company;

public enum ContactUsageSetting {
    // 利用しない
    DO_NOT_USE(0),

    // 利用する
    USE(1),

    // 個人選択可
    INDIVIDUAL_SELECT(2);

    public final int code;

    /**
     *
     * @param code
     */
    private ContactUsageSetting(int code) {
        this.code = code;
    }

    /**
     *
     * @param value
     * @return
     */
    public static ContactUsageSetting valueOf(int value) {
        // Find value.
        for (ContactUsageSetting val : ContactUsageSetting.values()) {
            if (val.code == value) {
                return val;
            }
        }

        // Not found.
        return null;
    }
}
