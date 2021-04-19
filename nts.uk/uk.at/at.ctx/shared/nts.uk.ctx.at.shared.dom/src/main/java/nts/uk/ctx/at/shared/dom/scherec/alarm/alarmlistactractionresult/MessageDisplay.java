package nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 表示するメッセージ
 *
 * @author Le Huu Dat
 */
@StringMaxLength(200)
public class MessageDisplay extends StringPrimitiveValue<MessageDisplay> {
    private static final long serialVersionUID = 1L;

    public MessageDisplay(String rawValue) {
        super(rawValue);
    }
}

