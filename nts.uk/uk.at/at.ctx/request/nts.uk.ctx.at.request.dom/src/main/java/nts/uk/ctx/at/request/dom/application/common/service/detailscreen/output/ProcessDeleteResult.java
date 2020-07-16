package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ProcessDeleteResult {
	
	private ProcessResult processResult;
	
	private ApplicationType appType;
	
}
