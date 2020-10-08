package nts.uk.ctx.sys.auth.dom.anniversary;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 *お知らせのメッセージ
 */
@StringMaxLength(600)
public class NotificationMessage extends StringPrimitiveValue<NotificationMessage> {
    public NotificationMessage(String rawValue) {
        super(rawValue);
    }
}
