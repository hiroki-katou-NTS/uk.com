package nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset;

//事業所整理記号区分
public enum BussEsimateClass {
    //健保整理記号
    HEAL_INSUR_OFF_ARR_SYMBOL(0, "Enum_BussEsimateClass_HEAL_INSUR_OFF_ARR_SYMBOL"),
    //厚生整理記号
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
