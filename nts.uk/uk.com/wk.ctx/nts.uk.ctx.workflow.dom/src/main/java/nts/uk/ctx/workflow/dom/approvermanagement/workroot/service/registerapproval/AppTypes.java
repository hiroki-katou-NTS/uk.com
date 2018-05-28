package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.registerapproval;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.workflow.dom.service.output.ErrorFlag;

@Getter
@AllArgsConstructor
public class AppTypes {
	private Integer code;
	private int empRoot;
	@Setter
	private ErrorFlag err;
}
