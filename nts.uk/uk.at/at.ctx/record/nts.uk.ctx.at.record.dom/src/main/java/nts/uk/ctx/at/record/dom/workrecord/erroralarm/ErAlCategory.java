package nts.uk.ctx.at.record.dom.workrecord.erroralarm;

/**
 * エラーアラームカテゴリ
 */
public enum ErAlCategory {
    WEEKLY(1),
    MOTHLY(2),
    ANY_PERIOD(3);

    public int value;

    ErAlCategory(int v){
        this.value = v;
    }
}
