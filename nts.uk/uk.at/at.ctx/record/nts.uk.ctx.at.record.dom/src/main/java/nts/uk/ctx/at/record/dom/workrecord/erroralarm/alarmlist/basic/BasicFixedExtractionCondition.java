package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.basic;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.DisplayMessage;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.勤務実績のエラーアラーム設定.アラームリスト（職場）.マスタチェック(基本).アラームリスト（職場）基本の固定抽出条件
 */

@Getter
@Setter
@NoArgsConstructor
public class BasicFixedExtractionCondition extends AggregateRoot {

    // ID
    private String ID;

    // No
    private BasicFixedCheckItem no;

    // 使用区分
    private Boolean useAtr;

    // 表示するメッセージ
    private DisplayMessage displayMessage;

    public BasicFixedExtractionCondition(String ID, int no, Boolean useAtr, String displayMessage) {
        this.ID = ID;
        this.no = EnumAdaptor.valueOf(no, BasicFixedCheckItem.class);
        this.useAtr = useAtr;
        this.displayMessage = new DisplayMessage(displayMessage);
    }
}
