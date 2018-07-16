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

	private static final String SELECT_ALL = "SELECT c FROM OiomtStdAcceptCondSet c WHERE c.stdAcceptCondSetPk.cid = :companyId AND c.stdAcceptCondSetPk.systemType = :systemType ORDER BY c.stdAcceptCondSetPk.conditionSetCd";

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
		Optional<OiomtStdAcceptCondSet> entityOpt = this.queryProxy().find(new OiomtStdAcceptCondSetPk(domain.getCid(),
				domain.getSystemType().value, domain.getConditionSetCd().v()), OiomtStdAcceptCondSet.class);
		if (entityOpt.isPresent()) {
			OiomtStdAcceptCondSet entity = entityOpt.get();
			entity.conditionSetName = domain.getConditionSetName().v();
			entity.acceptMode = domain.getAcceptMode().isPresent() ? domain.getAcceptMode().get().value : null;
			entity.deleteExistData = domain.getDeleteExistData().value;
			entity.deleteExtDataMethod = domain.getDeleteExtDataMethod().isPresent()
					? domain.getDeleteExtDataMethod().get().value : null;
			entity.characterCode = domain.getCharacterCode().isPresent() ? domain.getCharacterCode().get().value : null;
			entity.csvDataLineNumber = domain.getCsvDataLineNumber().isPresent() ? domain.getCsvDataLineNumber().get().v() : null;
			entity.csvDataStartLine = domain.getCsvDataStartLine().isPresent() ? domain.getCsvDataStartLine().get().v() : null;
			entity.checkCompleted = domain.getCheckCompleted().isPresent() ? domain.getCheckCompleted().get().value : null;
			this.commandProxy().update(entity);
		}
	}

	@Override
	public void remove(String cid, int sysType, String conditionSetCd) {
		this.commandProxy().remove(OiomtStdAcceptCondSet.class,
				new OiomtStdAcceptCondSetPk(cid, sysType, conditionSetCd));
	}

	@Override
	public boolean isSettingCodeExist(String cid, int sysType, String conditionSetCd) {
		Optional<OiomtStdAcceptCondSet> entity = this.queryProxy()
				.find(new OiomtStdAcceptCondSetPk(cid, sysType, conditionSetCd), OiomtStdAcceptCondSet.class);
		return entity.isPresent();
	}

	@Override
	public void updateFromD(StdAcceptCondSet domain) {
		Optional<OiomtStdAcceptCondSet> entityOpt = this.queryProxy().find(new OiomtStdAcceptCondSetPk(domain.getCid(),
				domain.getSystemType().value, domain.getConditionSetCd().v()), OiomtStdAcceptCondSet.class);
		if (entityOpt.isPresent()) {
			OiomtStdAcceptCondSet entity = entityOpt.get();
			entity.categoryId = domain.getCategoryId().isPresent() ? domain.getCategoryId().get() : null;
			entity.checkCompleted = domain.getCheckCompleted().isPresent() 
					? domain.getCheckCompleted().get().value : null;
			entity.csvDataLineNumber = domain.getCsvDataLineNumber().isPresent()
					? domain.getCsvDataLineNumber().get().v() : null;
			entity.csvDataStartLine = domain.getCsvDataStartLine().isPresent() 
					? domain.getCsvDataStartLine().get().v() : null;
			entity.characterCode = domain.getCharacterCode().isPresent() 
					? domain.getCharacterCode().get().value : null;
			this.commandProxy().update(entity);
		}
	}

}
