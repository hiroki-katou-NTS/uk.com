package nts.uk.ctx.sys.assist.infra.repository.datarestoration;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.assist.dom.datarestoration.DataRecoveryMng;
import nts.uk.ctx.sys.assist.dom.datarestoration.DataRecoveryMngRepository;
import nts.uk.ctx.sys.assist.infra.entity.datarestoration.SspmtDataRecoveryMng;

@Stateless
public class JpaDataRecoveryMngRepository extends JpaRepository implements DataRecoveryMngRepository {

	@Override
	public Optional<DataRecoveryMng> getDataRecoveryMngById(String dataRecoveryProcessId) {
		return Optional
				.ofNullable(this.getEntityManager().find(SspmtDataRecoveryMng.class, dataRecoveryProcessId).toDomain());
	}

	@Override
	public void add(DataRecoveryMng domain) {
		this.commandProxy().insert(SspmtDataRecoveryMng.toEntity(domain));
	}

	@Override
	public void update(DataRecoveryMng domain) {
		this.commandProxy().update(SspmtDataRecoveryMng.toEntity(domain));
	}

	@Override
	public void remove(String dataRecoveryProcessId) {
		this.commandProxy().remove(SspmtDataRecoveryMng.class, dataRecoveryProcessId);
	}
}
