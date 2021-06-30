package nts.uk.ctx.exio.dom.input.workspace;

import nts.uk.ctx.exio.dom.input.importableitem.group.ImportingGroupId;

public interface GroupWorkspaceRepository {

	GroupWorkspace get(ImportingGroupId groupId);
}
