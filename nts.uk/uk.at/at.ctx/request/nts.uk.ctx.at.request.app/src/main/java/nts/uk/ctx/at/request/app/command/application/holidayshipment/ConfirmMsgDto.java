package nts.uk.ctx.at.request.app.command.application.holidayshipment;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ConfirmMsgDto {
	
	private String msgID;
	
	private List<String> paramLst;
	
}
