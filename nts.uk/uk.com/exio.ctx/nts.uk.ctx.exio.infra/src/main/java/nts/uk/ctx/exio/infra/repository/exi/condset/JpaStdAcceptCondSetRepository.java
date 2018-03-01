package nts.uk.ctx.exio.infra.repository.exi.condset;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.exi.condset.StdAcceptCondSet;
import nts.uk.ctx.exio.dom.exi.condset.StdAcceptCondSetRepository;
import nts.uk.ctx.exio.infra.entity.exi.condset.OiomtStdAcceptCondSet;
import nts.uk.ctx.exio.infra.entity.exi.condset.OiomtStdAcceptCondSetPk;

@Stateless
public class JpaStdAcceptCondSetRepository extends JpaRepository implements StdAcceptCondSetRepository {

	private static final String SELECT_ALL = "SELECT c FROM OiomtStdAcceptCondSet c WHERE c.stdAcceptCondSetPk.cid = :companyId AND c.stdAcceptCondSetPk.systemType = :systemType";

	@Override
	public List<StdAcceptCondSet> getStdAcceptCondSetBySysType(String cid, int sysType) {
		return this.queryProxy().query(SELECT_ALL, OiomtStdAcceptCondSet.class).setParameter("companyId", cid)
				.setParameter("systemType", sysType).getList(c -> OiomtStdAcceptCondSet.entityToDomain(c));
	}

	@Override
	public Optional<StdAcceptCondSet> getStdAcceptCondSetById(String cid, int sysType, String conditionSetCd) {
		Optional<OiomtStdAcceptCondSet> entity = this.queryProxy()
				.find(new OiomtStdAcceptCondSetPk(cid, sysType, conditionSetCd), OiomtStdAcceptCondSet.class);
		if (entity.isPresent()) {
			return Optional.of(OiomtStdAcceptCondSet.entityToDomain(entity.get()));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void add(StdAcceptCondSet domain) {
		this.commandProxy().insert(OiomtStdAcceptCondSet.domainToEntity(domain));
	}

	@Override
	public void update(StdAcceptCondSet domain) {
		Optional<OiomtStdAcceptCondSet> entityOpt = this.queryProxy().find(
				new OiomtStdAcceptCondSetPk(domain.getCid(), domain.getSystemType(), domain.getConditionSetCd().v()),
				OiomtStdAcceptCondSet.class);
		if (entityOpt.isPresent()) {
			OiomtStdAcceptCondSet entity = entityOpt.get();
			this.commandProxy().update(entity);
		}
	}

	@Override
	public void remove(String cid, int sysType, String conditionSetCd) {
		this.commandProxy().remove(OiomtStdAcceptCondSet.class,
				new OiomtStdAcceptCondSetPk(cid, sysType, conditionSetCd));
	}

}
