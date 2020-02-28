package nts.uk.ctx.workflow.infra.repository.hrapproverstatemana;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.workflow.dom.hrapproverstatemana.ApprovalRootStateHr;
import nts.uk.ctx.workflow.dom.hrapproverstatemana.ApprovalRootStateHrRepository;
import nts.uk.ctx.workflow.infra.entity.hrapproverstatemana.WwfdtHrApprovalRootState;
import nts.uk.ctx.workflow.infra.entity.hrapproverstatemana.WwfdtHrApprovalRootStatePK;
@Stateless
public class JpaHrApprovalRootStateRepository extends JpaRepository implements ApprovalRootStateHrRepository{

	@Override
	public void insert(ApprovalRootStateHr root) {
		this.commandProxy().insert(WwfdtHrApprovalRootState.fromDomain(root));
	}

	@Override
	public Optional<ApprovalRootStateHr> getById(String rootStateId) {
		WwfdtHrApprovalRootStatePK pk = new WwfdtHrApprovalRootStatePK(rootStateId);
		return this.queryProxy().find(pk, WwfdtHrApprovalRootState.class).map(c -> c.toDomain());
	}

	@Override
	public void update(ApprovalRootStateHr root) {
		WwfdtHrApprovalRootState entity = WwfdtHrApprovalRootState.fromDomain(root);
		this.commandProxy().update(entity);
	}

}
