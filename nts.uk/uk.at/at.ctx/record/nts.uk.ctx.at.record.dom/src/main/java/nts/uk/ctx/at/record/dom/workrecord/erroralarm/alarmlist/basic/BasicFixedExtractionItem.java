package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.basic;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ColorCode;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.DisplayMessage;

import java.util.Optional;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.勤務実績のエラーアラーム設定.アラームリスト（職場）.マスタチェック(基本).アラームリスト（職場）基本の固定抽出項目
 */

@Getter
@Setter
@NoArgsConstructor
public class BasicFixedExtractionItem extends AggregateRoot {

    // No
    private BasicFixedCheckItem no;

    // アラームチェック区分
    private AlarmCheckClassification checkCls;

    // メッセージを太字にする
    private Boolean bold;

    // 基本チェック名称
    private BasicCheckName name;

    // 最初表示するメッセージ
    private DisplayMessage displayMessage;

    // カラーコード
    private Optional<ColorCode> colorCode;


    public BasicFixedExtractionItem(int no, int checkCls, Boolean bold, String name, String displayMessage, String colorCode) {
        this.no = EnumAdaptor.valueOf(no, BasicFixedCheckItem.class);
        this.checkCls = EnumAdaptor.valueOf(checkCls, AlarmCheckClassification.class);
        this.bold = bold;
        this.name = new BasicCheckName(name);
        this.displayMessage = new DisplayMessage(displayMessage);
        this.colorCode = colorCode == null ? Optional.empty() : Optional.of(new ColorCode(colorCode));
    }

}
