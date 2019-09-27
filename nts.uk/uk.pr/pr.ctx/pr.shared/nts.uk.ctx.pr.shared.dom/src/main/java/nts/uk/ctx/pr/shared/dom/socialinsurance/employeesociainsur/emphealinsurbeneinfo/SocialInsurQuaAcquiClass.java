package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

/**
 * 社会保険資格取得区分
 */
public enum SocialInsurQuaAcquiClass {

    //新1
    NEW(0, "ENUM_SocialInsurQuaAcquiClass_NEW"),
    //再2
    RE(1, "ENUM_SocialInsurQuaAcquiClass_RE"),
    //共3
    COMMON(1, "ENUM_SocialInsurQuaAcquiClass_COMMON"),
    //船4
    SHIP(1, "ENUM_SocialInsurQuaAcquiClass_SHIP");

    /** The value. */
    public final int value;

    /** The name id. */
    public final String nameId;
    private SocialInsurQuaAcquiClass(int value, String nameId)
    {
        this.value = value;
        this.nameId = nameId;
    }
}
