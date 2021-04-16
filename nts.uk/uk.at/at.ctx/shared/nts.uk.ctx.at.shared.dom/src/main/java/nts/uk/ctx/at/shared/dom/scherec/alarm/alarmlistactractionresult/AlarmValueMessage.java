package nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.shared(勤務予定、勤務実績).アラーム.アラームリスト抽出結果
 * アラーム値メッセージ
 *
 * @author Le Huu Dat
 */
@StringMaxLength(1000)
public class AlarmValueMessage extends StringPrimitiveValue<AlarmValueMessage> {
    private static final long serialVersionUID = 1L;

    public AlarmValueMessage(String rawValue) {
        super(rawValue);
    }
}
