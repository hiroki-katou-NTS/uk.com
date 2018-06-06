package nts.uk.ctx.sys.assist.infra.repository.datarestoration;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.assist.dom.datarestoration.PerformDataRecovery;
import nts.uk.ctx.sys.assist.dom.datarestoration.PerformDataRecoveryRepository;
import nts.uk.ctx.sys.assist.infra.entity.datarestoration.SspmtPerformDataRecovery;

@Stateless
public class JpaPerformDataRecoveryRepository extends JpaRepository implements PerformDataRecoveryRepository {

	@Override
	public Optional<PerformDataRecovery> getPerformDatRecoverById(String dataRecoveryProcessId) {
		return Optional.ofNullable(
				this.getEntityManager().find(SspmtPerformDataRecovery.class, dataRecoveryProcessId).toDomain());
	}

	@Override
	public void add(PerformDataRecovery domain) {
		this.commandProxy().insert(SspmtPerformDataRecovery.toEntity(domain));
	}

	@Override
	public void update(PerformDataRecovery domain) {
		this.commandProxy().update(SspmtPerformDataRecovery.toEntity(domain));
	}

	@Override
	public void remove(String dataRecoveryProcessId) {
		this.commandProxy().remove(SspmtPerformDataRecovery.class, dataRecoveryProcessId);
	}
}
