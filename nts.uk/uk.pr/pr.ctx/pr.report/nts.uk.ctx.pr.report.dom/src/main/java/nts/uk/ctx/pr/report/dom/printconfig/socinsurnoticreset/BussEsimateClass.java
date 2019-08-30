package nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset;

public enum BussEsimateClass {
    HEAL_INSUR_OFF_ARR_SYMBOL(0, "Enum_BussEsimateClass_HEAL_INSUR_OFF_ARR_SYMBOL"),
    EMPEN_ESTAB_REARSIGN(1, "Enum_BussEsimateClass_EMPEN_ESTAB_REARSIGN");

    /** The value. */
    public final int value;

    /** The name id. */
    public final String nameId;
    private BussEsimateClass(int value, String nameId)
    {
        this.value = value;
        this.nameId = nameId;
    }
}
