package nts.uk.ctx.at.function.dom.adapter.eralworkrecorddto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageWRExtraConAdapterDto {
	private String errorAlarmCheckID;
	
	private String displayMessage;

	public MessageWRExtraConAdapterDto(String errorAlarmCheckID, String displayMessage) {
		super();
		this.errorAlarmCheckID = errorAlarmCheckID;
		this.displayMessage = displayMessage;
	}
	
}
