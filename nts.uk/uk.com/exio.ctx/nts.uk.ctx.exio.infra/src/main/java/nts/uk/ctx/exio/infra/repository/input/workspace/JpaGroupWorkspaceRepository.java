package nts.uk.ctx.exio.infra.repository.input.workspace;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.input.importableitem.group.ImportingGroupId;
import nts.uk.ctx.exio.dom.input.workspace.GroupWorkspace;
import nts.uk.ctx.exio.dom.input.workspace.GroupWorkspaceRepository;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaGroupWorkspaceRepository extends JpaRepository implements GroupWorkspaceRepository {

	@Override
	public Optional<GroupWorkspace> find(ImportingGroupId groupId) {
		// TODO Auto-generated method stub
		return null;
	}

}
