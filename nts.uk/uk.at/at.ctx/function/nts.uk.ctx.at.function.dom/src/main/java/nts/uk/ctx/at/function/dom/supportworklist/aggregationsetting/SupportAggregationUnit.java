package nts.uk.ctx.at.function.dom.supportworklist.aggregationsetting;

/**
 * 応援集計単位
 */
public enum SupportAggregationUnit {
    WORKPLACE(0, "KHA002_71"), // 職場
    WORK_LOCATION(1, "KHA002_72"); // 勤務場所

    public final int value;
    public final String nameId;

    SupportAggregationUnit(int value, String nameId) {
        this.value = value;
        this.nameId = nameId;
    }
}
