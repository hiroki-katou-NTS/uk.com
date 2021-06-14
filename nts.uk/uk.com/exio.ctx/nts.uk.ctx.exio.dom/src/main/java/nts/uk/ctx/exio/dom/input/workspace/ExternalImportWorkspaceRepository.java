package nts.uk.ctx.exio.dom.input.workspace;

import java.util.Optional;

import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalizedDataRecord;
import nts.uk.ctx.exio.dom.input.importableitem.group.ImportingGroupId;
import nts.uk.ctx.exio.dom.input.revise.reviseddata.RevisedDataRecord;

public interface ExternalImportWorkspaceRepository {
	
	Optional<GroupWorkspace> findGroupWorkspace(ImportingGroupId groupId);
	
	void createWorkspaceReviced(ExecutionContext context);

	void save(ExecutionContext context, RevisedDataRecord record);
	
	void createWorkspaceCanonicalized(ExecutionContext context);
	
	void save(ExecutionContext context, CanonicalizedDataRecord record);
}
