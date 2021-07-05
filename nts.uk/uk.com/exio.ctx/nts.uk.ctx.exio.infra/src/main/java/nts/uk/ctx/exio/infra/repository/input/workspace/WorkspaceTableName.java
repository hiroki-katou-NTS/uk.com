package nts.uk.ctx.exio.infra.repository.input.workspace;

import lombok.RequiredArgsConstructor;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.infra.repository.input.TemporaryTable;

@RequiredArgsConstructor
public class WorkspaceTableName {
	
	private final ExecutionContext context;
	private final String workspaceName;

	public String asRevised() {
		return TemporaryTable.PREFIX + "REVI_" + create();
	}
	
	public String asCanonicalized() {
		return TemporaryTable.PREFIX + "CANO_" + create();
	}
	
	private String create() {
		return workspaceName + "_" + context.getCompanyId().replace("-", "");
	}
}
