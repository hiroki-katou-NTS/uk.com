package nts.uk.ctx.exio.dom.input.workspace;

import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalizedDataRecord;
import nts.uk.ctx.exio.dom.input.importableitem.group.ImportingGroup;
import nts.uk.ctx.exio.dom.input.importableitem.group.ImportingGroupId;
import nts.uk.ctx.exio.dom.input.revise.reviseddata.RevisedDataRecord;

public interface ExternalImportWorkspaceRepository {
	
	void createWorkspaceReviced(Require require, ExecutionContext context);

	void save(Require require, ExecutionContext context, RevisedDataRecord record);
	
	void createWorkspaceCanonicalized(Require require, ExecutionContext context);
	
	void save(Require require, ExecutionContext context, CanonicalizedDataRecord record);
	
	public static interface Require extends WorkspaceItem.RequireConfigureDataType {
		
		ImportingGroup getImportingGroup(ImportingGroupId groupId);
		
		GroupWorkspace getGroupWorkspace(ImportingGroupId groupId);
	}
}
