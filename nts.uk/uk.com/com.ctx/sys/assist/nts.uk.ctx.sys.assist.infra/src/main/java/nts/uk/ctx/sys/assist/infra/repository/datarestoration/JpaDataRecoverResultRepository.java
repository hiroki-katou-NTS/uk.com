package nts.uk.ctx.sys.assist.infra.repository.datarestoration;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.assist.dom.datarestoration.DataRecoveryResult;
import nts.uk.ctx.sys.assist.dom.datarestoration.DataRecoveryResultRepository;
import nts.uk.ctx.sys.assist.infra.entity.datarestoration.SspmtDataRecoverResult;

@Stateless
public class JpaDataRecoverResultRepository extends JpaRepository implements DataRecoveryResultRepository {

	@Override
	public Optional<DataRecoveryResult> getDataRecoverResultById(String dataRecoveryProcessId) {
		return Optional.ofNullable(
				this.getEntityManager().find(SspmtDataRecoverResult.class, dataRecoveryProcessId).toDomain());
	}

	@Override
	public void add(DataRecoveryResult domain) {
		this.commandProxy().insert(SspmtDataRecoverResult.toEntity(domain));
	}

	@Override
	public void update(DataRecoveryResult domain) {
		this.commandProxy().update(SspmtDataRecoverResult.toEntity(domain));
	}

	@Override
	public void remove(String dataRecoveryProcessId) {
		this.commandProxy().remove(SspmtDataRecoverResult.class, dataRecoveryProcessId);
	}
}
