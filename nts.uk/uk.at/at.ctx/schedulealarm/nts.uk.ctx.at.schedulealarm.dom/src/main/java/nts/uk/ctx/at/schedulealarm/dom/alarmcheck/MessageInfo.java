package nts.uk.ctx.at.schedulealarm.dom.alarmcheck;

import lombok.Value;

@Value
public class MessageInfo {
	private SubCode subCode;
	
	private AlarmCheckMessage message;

}
