package nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily;

import lombok.RequiredArgsConstructor;
import nts.arc.enums.EnumAdaptor;

/**
 * アラームチェック区分
 *
 * @author Thanh.LNP
 */
@RequiredArgsConstructor
public enum AlarmCheckClassification {
    /* エラー */
    ERROR(0, "エラー"),
    /* アラーム */
    ALARM(1, "アラーム"),
    /* 抽出条件 */
    EXTRACTION_CONDITION(2, "抽出条件");

    public final int value;
    public final String nameId;

    public static AlarmCheckClassification of(int value) {
        return EnumAdaptor.valueOf(value, AlarmCheckClassification.class);
    }
}
