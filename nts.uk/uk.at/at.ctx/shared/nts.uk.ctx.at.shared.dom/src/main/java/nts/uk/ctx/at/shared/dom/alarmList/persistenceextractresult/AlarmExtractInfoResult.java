package nts.uk.ctx.at.shared.dom.alarmList.persistenceextractresult;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.alarmList.AlarmCategory;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.AlarmListCheckType;

import java.util.List;

/**
 * アラーム抽出情報結果
 * @author viet.tx
 */
@AllArgsConstructor
@Getter
public class AlarmExtractInfoResult {
    /** アラームチェック条件No */
    private String alarmCheckConditionNo;

    /** アラームチェック条件コード */
    private AlarmCheckConditionCode alarmCheckConditionCode;

    /** アラームリストのカテゴリ */
    private AlarmCategory alarmCategory;

    /** チェック種類 */
    private AlarmListCheckType alarmListCheckType;

    /** 抽出結果 */
    private List<ExtractResultDetail> extractionResultDetails;
}
