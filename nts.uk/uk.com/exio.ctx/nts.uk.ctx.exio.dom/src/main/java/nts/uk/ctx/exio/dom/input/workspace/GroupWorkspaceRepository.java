package nts.uk.ctx.exio.dom.input.workspace;

import java.util.Optional;

import nts.uk.ctx.exio.dom.input.importableitem.group.ImportingGroupId;

public interface GroupWorkspaceRepository {

	Optional<GroupWorkspace> find(ImportingGroupId groupId);
}
