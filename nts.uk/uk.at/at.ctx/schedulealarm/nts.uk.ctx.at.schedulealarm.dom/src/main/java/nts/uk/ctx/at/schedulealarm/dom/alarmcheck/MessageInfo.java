package nts.uk.ctx.at.schedulealarm.dom.alarmcheck;

import lombok.Value;

@Value
public class MessageInfo {
    /**
     * サブコード
     */
    private SubCode subCode;

    /**
     * メッセージ
     */
    private AlarmCheckMessage message;

}
