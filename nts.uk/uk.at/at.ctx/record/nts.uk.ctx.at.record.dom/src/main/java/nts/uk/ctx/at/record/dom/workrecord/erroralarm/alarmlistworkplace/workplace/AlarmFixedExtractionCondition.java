package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.workplace;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.DisplayMessage;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.勤務実績のエラーアラーム設定.アラームリスト（職場）.マスタチェック（職場）.アラームリスト（職場）固定抽出条件
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AlarmFixedExtractionCondition extends AggregateRoot {

    // ID
    private String id;
    // No
    private FixedCheckItem no;
    // 使用区分
    private boolean useAtr;
    // 最初表示するメッセージ
    private DisplayMessage displayMessage;

}
