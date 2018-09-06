package nts.uk.ctx.core.dom.socialinsurance;


/**
 * 自動計算実施区分
 */
public enum AutoCalculationExecutionCls {

    AUTO(1, "Enum_AutoCalculationExecutionCls_AUTO"),
    NOT_AUTO(0, "Enum_AutoCalculationExecutionCls_NOT_AUTO");

    /**
     * The value.
     */
    public final int value;

    /**
     * The name id.
     */
    public final String nameId;

    private AutoCalculationExecutionCls(int value, String nameId) {
        this.value = value;
        this.nameId = nameId;
    }
}
