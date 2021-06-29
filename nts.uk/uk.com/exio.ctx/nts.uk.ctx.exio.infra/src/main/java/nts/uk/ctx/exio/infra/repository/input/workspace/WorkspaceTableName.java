package nts.uk.ctx.exio.infra.repository.input.workspace;

import lombok.RequiredArgsConstructor;
import nts.uk.ctx.exio.dom.input.ExecutionContext;

@RequiredArgsConstructor
public class WorkspaceTableName {
	
	private final ExecutionContext context;
	private final String workspaceName;

	public String asRevised() {
		return create() + "_REVI";
	}
	
	public String asCanonicalized() {
		return create() + "_CANO";
	}
	
	private String create() {
		return workspaceName + "_" + context.getCompanyId().replace("-", "");
	}
}
