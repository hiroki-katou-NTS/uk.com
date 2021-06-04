package nts.uk.ctx.at.shared.dom.alarmList.persistenceextractresult;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.alarmList.AlarmPatternCode;
import nts.uk.ctx.at.shared.dom.alarmList.AlarmPatternName;

import java.util.List;

/**
 * 永続化のアラームリスト抽出結果
 * @author viet.tx
 */
@AllArgsConstructor
@Getter
public class PersistenceAlarmListExtractResult extends AggregateRoot {
    /** アラームリストパターンコード */
    private AlarmPatternCode alarmPatternCode;

    /** アラームリストパターン名称 */
    private AlarmPatternName alarmPatternName;

    /** アラームリスト抽出結果 */
    private List<AlarmEmployeeList> alarmListExtractResults;

    /** 会社ID */
    private String companyID;

    /** 自動実行コード  (手動実行の場合自動実行コード＝　’Z’) */
    private String autoRunCode;
}
