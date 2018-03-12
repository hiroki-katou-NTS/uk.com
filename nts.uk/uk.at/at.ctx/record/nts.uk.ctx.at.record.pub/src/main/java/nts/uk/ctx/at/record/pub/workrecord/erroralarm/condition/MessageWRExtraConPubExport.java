package nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MessageWRExtraConPubExport {


	private String errorAlarmCheckID;
	
	private String displayMessage;
	
	public MessageWRExtraConPubExport(String errorAlarmCheckID, String displayMessage) {
		super();
		this.errorAlarmCheckID = errorAlarmCheckID;
		this.displayMessage = displayMessage;
	}
}
