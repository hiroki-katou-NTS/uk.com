package nts.uk.ctx.at.request.dom.application.common.service.other.output;
import java.util.List;
import lombok.Getter;

import lombok.AllArgsConstructor;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ProcessResult {
	private boolean isProcessDone;
	private boolean isAutoSendMail;
	private List<String> autoSuccessMail;
	private List<String> autoFailMail;
	private String appID;
}
