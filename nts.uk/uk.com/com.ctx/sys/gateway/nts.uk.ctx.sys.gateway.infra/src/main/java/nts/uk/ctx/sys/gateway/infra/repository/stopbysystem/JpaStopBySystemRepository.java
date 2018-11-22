package nts.uk.ctx.sys.gateway.infra.repository.stopbysystem;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.gateway.dom.stopbysystem.StopBySystem;
import nts.uk.ctx.sys.gateway.dom.stopbysystem.StopBySystemRepository;
import nts.uk.ctx.sys.gateway.infra.entity.stopbysystem.SgwdtStopBySystem;

@Stateless
public class JpaStopBySystemRepository extends JpaRepository implements StopBySystemRepository {

	private static final String FIND_BY_KEY = "SELECT s FROM SgwdtStopBySystem s WHERE s.contractCd=:contractCd";

	@Override
	public void insert(StopBySystem domain) {
		this.commandProxy().insert(toEntity(domain));

	}

	@Override
	public void update(StopBySystem domain) {
		this.commandProxy().update(toEntity(domain));

	}

	private SgwdtStopBySystem toEntity(StopBySystem domain) {
		return new SgwdtStopBySystem(domain.getContractCd(), domain.getUsageStopMode().value,
				domain.getUsageStopMessage(), domain.getSystemStatus().value, domain.getStopMessage());
	}

	@Override
	public Optional<StopBySystem> findByKey(String contractCd) {
		return this.queryProxy().query(FIND_BY_KEY, SgwdtStopBySystem.class).setParameter("contractCd", contractCd)
				.getSingle(x -> toDomain(x));
	}

	private StopBySystem toDomain(SgwdtStopBySystem entity) {
		return StopBySystem.createFromJavaType(entity.contractCd, entity.usageStopMode, entity.usageStopMessage, entity.systemStatus,
				entity.stopMessage);
	}

}
