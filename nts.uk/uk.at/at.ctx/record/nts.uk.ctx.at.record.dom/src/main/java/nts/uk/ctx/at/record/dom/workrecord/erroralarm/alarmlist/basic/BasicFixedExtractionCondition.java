package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.basic;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.DisplayMessage;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.勤務実績のエラーアラーム設定.アラームリスト（職場）.マスタチェック(基本).アラームリスト（職場）基本の固定抽出条件
 */

@Getter
@NoArgsConstructor
public class BasicFixedExtractionCondition extends AggregateRoot {

    // ID
    private String id;

    // No
    private BasicFixedCheckItem no;

    // 使用区分
    private boolean useAtr;

    // 表示するメッセージ
    private DisplayMessage displayMessage;

    public BasicFixedExtractionCondition(String id, int no, boolean useAtr, String displayMessage) {
        this.id = id;
        this.no = EnumAdaptor.valueOf(no, BasicFixedCheckItem.class);
        this.useAtr = useAtr;
        this.displayMessage = new DisplayMessage(displayMessage);
    }
}
