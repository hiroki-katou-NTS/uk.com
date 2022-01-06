package nts.uk.ctx.at.function.dom.supportworklist.aggregationsetting;

/**
 * 職場応援判断方法
 */
public enum WorkplaceSupportJudgmentMethod {
    SUPPORT_ALL_BUT_WORKPLACE(0, "KHA002_74"), // 所属職場以外全て応援扱う
    SUPPORT_JUDGMENT_AT_WORKPLACE_LEVEL(1, "KHA002_75"); // 指定職場階層で応援判断

    public final int value;
    public final String nameId;

    WorkplaceSupportJudgmentMethod(int value, String nameId) {
        this.value = value;
        this.nameId = nameId;
    }
}
