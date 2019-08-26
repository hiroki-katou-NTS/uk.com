package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo;


/**
* 健康保険資格喪失原因
*/
public enum ReasonsForLossHealthyIns {

    BLANK(0, "Enum_ReasonsForLossHealthyIns_BLANK"),
    RETIREMENT(4, "Enum_ReasonsForLossHealthyIns_RETIREMENT"),
    DEATH(5, "Enum_ReasonsForLossHealthyIns_DEATH"),
    ONLY_HEALTHY_INSURANCE(7, "Enum_ReasonsForLossHealthyIns_ONLY_HEALTHY_INSURANCE"),
    DISABILITY_AUTHORIZATION(9, "Enum_ReasonsForLossHealthyIns_DISABILITY_AUTHORIZATION");

    /** The value. */
    public final int value;

    /** The name id. */
    public final String nameId;
    private ReasonsForLossHealthyIns(int value, String nameId) {
        this.value = value;
        this.nameId = nameId;
    }
}
