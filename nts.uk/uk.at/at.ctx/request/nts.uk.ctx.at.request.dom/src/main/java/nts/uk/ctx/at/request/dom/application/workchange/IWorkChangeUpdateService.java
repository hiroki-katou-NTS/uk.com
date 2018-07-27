package nts.uk.ctx.at.request.dom.application.workchange;

import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;

public interface IWorkChangeUpdateService {
	
	public ProcessResult UpdateWorkChange(Application_New app, AppWorkChange workChange);
}
