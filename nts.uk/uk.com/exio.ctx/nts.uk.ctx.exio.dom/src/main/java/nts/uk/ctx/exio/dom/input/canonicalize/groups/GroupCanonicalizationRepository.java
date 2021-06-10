package nts.uk.ctx.exio.dom.input.canonicalize.groups;

import nts.uk.ctx.exio.dom.input.importableitem.group.ImportingGroupId;

public interface GroupCanonicalizationRepository {

	GroupCanonicalization find(ImportingGroupId groupId);
}
