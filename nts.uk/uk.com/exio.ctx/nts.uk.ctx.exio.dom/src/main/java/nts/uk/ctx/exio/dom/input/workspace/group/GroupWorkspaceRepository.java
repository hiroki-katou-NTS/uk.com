package nts.uk.ctx.exio.dom.input.workspace.group;

import nts.uk.ctx.exio.dom.input.group.ImportingGroupId;

public interface GroupWorkspaceRepository {

	GroupWorkspace get(ImportingGroupId groupId);
}
