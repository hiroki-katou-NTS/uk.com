package nts.uk.ctx.at.record.dom.workrecord.alarmlist.errorsetting;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ColorCode;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.DisplayMessage;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.勤務実績のエラーアラーム設定.アラームリスト（職場）.マスタチェック（職場）.アラームリスト（職場）固定抽出項目
 */
@Getter
@Setter
@NoArgsConstructor
public class AlarmFixedExtractionItem {

    // No
    private FixedCheckItem no;
    // アラームチェック区分
    private AlarmCheckCls alarmCheckCls;
    // メッセージを太字にする
    private Boolean msgBold;
    // 最初表示するメッセージ
    private DisplayMessage displayMessage;
    // WorkplaceCheckName
    private WorkplaceCheckName workplaceCheckName;
    // カラーコード
    private ColorCode colorCode;

    public AlarmFixedExtractionItem(int no, int checkCls, boolean bold, String message, String workplaceCheckName, String colorCode) {
        this.no = EnumAdaptor.valueOf(no, FixedCheckItem.class);
        this.alarmCheckCls = EnumAdaptor.valueOf(checkCls, AlarmCheckCls.class);
        this.msgBold = bold;
        this.displayMessage = new DisplayMessage(message);
        this.workplaceCheckName = new WorkplaceCheckName(workplaceCheckName);
        this.colorCode = new ColorCode(colorCode);
    }
}
