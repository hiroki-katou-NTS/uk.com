package nts.uk.ctx.sys.env.dom.mailnoticeset.company;

/**
 * メール分類
 *
 * @author admin
 *
 */
public enum EmailClassification {
    /**
     * 会社メールアドレス
     */
    COMPANY_EMAIL_ADDRESS(0),

    /**
     * 会社携帯メールアドレス
     */
    COMPANY_MOBILE_EMAIL_ADDRESS(1),

    /**
     * 個人メールアドレス
     */
    PERSONAL_EMAIL_ADDRESS(2),

    /**
     * 個人携帯メールアドレス
     */
    PERSONAL_MOBILE_EMAIL_ADDRESS(3);

    public final int code;

    /**
     *
     * @param code
     */
    private EmailClassification(int code) {
        this.code = code;
    }

    /**
     *
     * @param value
     * @return
     */
    public static EmailClassification valueOf(int value) {
        // Find value.
        for (EmailClassification val : EmailClassification.values()) {
            if (val.code == value) {
                return val;
            }
        }

        // Not found.
        return null;
    }
}
