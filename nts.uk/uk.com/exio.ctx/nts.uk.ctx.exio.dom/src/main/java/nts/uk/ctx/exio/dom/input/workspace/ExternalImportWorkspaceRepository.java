package nts.uk.ctx.exio.dom.input.workspace;

import java.util.List;

import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalizedDataRecord;
import nts.uk.ctx.exio.dom.input.group.ImportingGroup;
import nts.uk.ctx.exio.dom.input.group.ImportingGroupId;
import nts.uk.ctx.exio.dom.input.workspace.group.GroupWorkspace;
import nts.uk.ctx.exio.dom.input.workspace.item.WorkspaceItem;

/**
 * 外部受入のワークスペースに対する入出力を担当するRepository
 */
public interface ExternalImportWorkspaceRepository {
	
	void setup(Require require, ExecutionContext context);
	
	public static interface Require extends WorkspaceItem.RequireConfigureDataType {
		
		ImportingGroup getImportingGroup(ImportingGroupId groupId);
		
		GroupWorkspace getGroupWorkspace(ImportingGroupId groupId);
	}
}
