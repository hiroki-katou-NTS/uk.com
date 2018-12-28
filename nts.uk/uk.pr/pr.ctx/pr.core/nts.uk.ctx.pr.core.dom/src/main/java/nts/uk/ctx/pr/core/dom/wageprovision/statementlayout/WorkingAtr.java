package nts.uk.ctx.pr.core.dom.wageprovision.statementlayout;

/**
 * 通勤区分
 */
public enum WorkingAtr {
	// 交通機関
    TRANSPORT_FACILITIES(0, "Enum_WorkingAtr_TRANSPORT_FACILITIES"),
    // 交通用具
    TRANSPORT_EQUIPMENT(1, "Enum_WorkingAtr_TRANSPORT_EQUIPMENT");

    /** The value. */
    public final int value;

    /** The name id. */
   	public final String nameId;

    private WorkingAtr(int value, String nameId)
    {
        this.value = value;
        this.nameId = nameId;
    }
}
