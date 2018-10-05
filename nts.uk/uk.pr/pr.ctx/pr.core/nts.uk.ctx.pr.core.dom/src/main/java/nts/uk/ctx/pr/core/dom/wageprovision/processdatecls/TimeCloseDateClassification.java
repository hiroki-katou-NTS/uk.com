package nts.uk.ctx.pr.core.dom.wageprovision.processdatecls;

/**
 * 勤怠締め日区分
 */
public enum TimeCloseDateClassification {
    /**
     * 社員抽出基準日と同じ
     */
    SAME_DATE(0,"Enum_TimeCloseDateClassification_SAME_DATE"),
    /**
     * 社員抽出基準日とは別
     */
    APART_FROMDATE(1,"Enum_TimeCloseDateClassification_APART_FROMDATE");

    public final int value;

    public final String nameId;

    private TimeCloseDateClassification(int value, String nameId) {
        this.value = value;
        this.nameId = nameId;
    }

}
