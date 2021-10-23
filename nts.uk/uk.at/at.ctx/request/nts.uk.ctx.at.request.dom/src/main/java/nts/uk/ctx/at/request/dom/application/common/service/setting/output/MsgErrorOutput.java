package nts.uk.ctx.at.request.dom.application.common.service.setting.output;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class MsgErrorOutput {
	
	private String msgID;
	
	private List<String> msgParam;
}
