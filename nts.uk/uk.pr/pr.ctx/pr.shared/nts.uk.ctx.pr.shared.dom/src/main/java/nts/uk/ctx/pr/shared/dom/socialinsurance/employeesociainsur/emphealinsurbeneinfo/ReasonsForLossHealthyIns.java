package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo;


/**
* 健康保険資格喪失原因
*/
public enum ReasonsForLossHealthyIns {

    BLANK(0, "Enum_ReasonsForLossHealthyIns_BLANK"),
    //退職等
    RETIREMENT(4, "Enum_ReasonsForLossHealthyIns_RETIREMENT"),
    //死亡
    DEATH(5, "Enum_ReasonsForLossHealthyIns_DEATH"),
    //75歳到達（健康保険のみ喪失）
    ONLY_HEALTHY_INSURANCE(7, "Enum_ReasonsForLossHealthyIns_ONLY_HEALTHY_INSURANCE"),
    //障害認定（健康保険のみ喪失）
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
