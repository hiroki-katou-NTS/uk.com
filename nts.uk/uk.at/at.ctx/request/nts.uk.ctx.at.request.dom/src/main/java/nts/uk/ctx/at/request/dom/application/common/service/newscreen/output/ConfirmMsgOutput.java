package nts.uk.ctx.at.request.dom.application.common.service.newscreen.output;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ConfirmMsgOutput {
	
	private String msgID;
	
	private List<String> paramLst;
	
}
