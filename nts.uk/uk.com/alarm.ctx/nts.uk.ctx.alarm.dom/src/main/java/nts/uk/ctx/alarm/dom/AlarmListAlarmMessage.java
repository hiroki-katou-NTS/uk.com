package nts.uk.ctx.alarm.dom;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.ErrorAlarmMessage;

/**
 * アラームリストのアラームメッセージ
 */
@StringMaxLength(200)
public class AlarmListAlarmMessage extends StringPrimitiveValue<AlarmListAlarmMessage> {

    public AlarmListAlarmMessage(String rawValue) {
        super(rawValue);
    }

    public static AlarmListAlarmMessage of(ErrorAlarmMessage m) {
        return new AlarmListAlarmMessage(m.v());
    }

    public static AlarmListAlarmMessage empty() {
        return new AlarmListAlarmMessage("");
    }
}
