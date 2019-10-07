package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

/**
 * 社会保険資格取得区分
 */
public enum SocialInsurQuaAcquiClass {

    //新1
    NEW(1, "ENUM_SocialInsurQuaAcquiClass_NEW"),
    //再2
    RE(2, "ENUM_SocialInsurQuaAcquiClass_RE"),
    //共3
    COMMON(3, "ENUM_SocialInsurQuaAcquiClass_COMMON"),
    //船4
    SHIP(4, "ENUM_SocialInsurQuaAcquiClass_SHIP");

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
