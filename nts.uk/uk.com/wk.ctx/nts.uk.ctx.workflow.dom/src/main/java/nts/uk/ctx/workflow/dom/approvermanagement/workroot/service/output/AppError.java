package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.registerapproval.AppTypes;
import nts.uk.ctx.workflow.dom.service.output.ErrorFlag;
@Getter
@Setter
public class AppError {
	private AppTypes app; 
	private ErrorFlag err;
}
