package nts.uk.ctx.sys.assist.infra.repository.datarestoration;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareMng;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareMngRepository;
import nts.uk.ctx.sys.assist.infra.entity.datarestoration.SspmtServerPrepareMng;

@Stateless
public class JpaServerPrepareMngRepository extends JpaRepository implements ServerPrepareMngRepository {

	@Override
	public Optional<ServerPrepareMng> getServerPrepareMngById(String dataRecoveryProcessId) {
		return Optional.ofNullable(
				this.getEntityManager().find(SspmtServerPrepareMng.class, dataRecoveryProcessId).toDomain());
	}

	@Override
	public void add(ServerPrepareMng domain) {
		this.commandProxy().insert(SspmtServerPrepareMng.toEntity(domain));
	}

	@Override
	public void update(ServerPrepareMng domain) {
		this.commandProxy().update(SspmtServerPrepareMng.toEntity(domain));
	}

	@Override
	public void remove(String dataRecoveryProcessId) {
		this.commandProxy().remove(SspmtServerPrepareMng.class, dataRecoveryProcessId);
	}
}
