package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.registerapproval;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.workflow.dom.service.output.ErrorFlag;

@Getter
@AllArgsConstructor
public class AppTypes {
	private String code;
	private String id;
	private int empRoot;
	@Setter
	private ErrorFlag err;
	
	private String name;
	
	private Optional<Boolean> lowerApprove;
}
