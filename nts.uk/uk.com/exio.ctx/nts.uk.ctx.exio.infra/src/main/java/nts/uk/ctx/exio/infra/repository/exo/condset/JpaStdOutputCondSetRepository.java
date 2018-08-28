package nts.uk.ctx.exio.infra.repository.exo.condset;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSet;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSetRepository;
import nts.uk.ctx.exio.infra.entity.exo.condset.OiomtStdOutputCondSet;
import nts.uk.ctx.exio.infra.entity.exo.condset.OiomtStdOutputCondSetPk;

@Stateless
public class JpaStdOutputCondSetRepository extends JpaRepository implements StdOutputCondSetRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM OiomtStdOutputCondSet f";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.stdOutputCondSetPk.cid =:cid AND  f.stdOutputCondSetPk.conditionSetCd =:conditionSetCd ";

	private static final String SELECT_BY_CID = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.stdOutputCondSetPk.cid =:cid ORDER BY f.stdOutputCondSetPk.conditionSetCd ASC";
	

	@Override
	public List<StdOutputCondSet> getAllStdOutputCondSet() {
		return this.queryProxy().query(SELECT_ALL_QUERY_STRING, OiomtStdOutputCondSet.class)
				.getList(item -> toDomain(item));
	}
	
	@Override
	 public Optional<StdOutputCondSet> getStdOutputCondSetByCid(String cid) {
	  return this.queryProxy().query(SELECT_BY_CID, OiomtStdOutputCondSet.class).setParameter("cid", cid)
	    .getSingle(c -> toDomain(c));
	 }
	
	@Override
	public List<StdOutputCondSet> getStdOutCondSetByCid(String cid) {
		return this.queryProxy().query(SELECT_BY_CID, OiomtStdOutputCondSet.class).setParameter("cid", cid)
				.getList(c -> toDomain(c));
	}

	@Override
	public Optional<StdOutputCondSet> getStdOutputCondSetById(String cid, String conditionSetCd) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING, OiomtStdOutputCondSet.class).setParameter("cid", cid)
				.setParameter("conditionSetCd", conditionSetCd).getSingle(c -> toDomain(c));
	}
	
	@Override
	public List<StdOutputCondSet> getStdOutputCondSetById(String cid, Optional<String> conditionSetCd) {
		if (conditionSetCd.isPresent()) {
			return this.queryProxy().query(SELECT_BY_KEY_STRING, OiomtStdOutputCondSet.class).setParameter("cid", cid)
					.setParameter("conditionSetCd", conditionSetCd.get()).getList(c -> toDomain(c));
		}
		return this.getStdOutCondSetByCid(cid);
	}

	@Override
	public void add(StdOutputCondSet domain) {
	    this.commandProxy().insert(toEntity(domain));
	}

	@Override
	public void update(StdOutputCondSet domain) {
		OiomtStdOutputCondSet newStdOutputCondSet = toEntity(domain);
		OiomtStdOutputCondSet updateStdOutputCondSet = this.queryProxy()
				.find(newStdOutputCondSet.stdOutputCondSetPk, OiomtStdOutputCondSet.class).get();
		if (null == updateStdOutputCondSet) {
			return;
		}
		updateStdOutputCondSet.categoryId = newStdOutputCondSet.categoryId;
		updateStdOutputCondSet.delimiter = newStdOutputCondSet.delimiter;
		updateStdOutputCondSet.itemOutputName = newStdOutputCondSet.itemOutputName;
		updateStdOutputCondSet.autoExecution = newStdOutputCondSet.autoExecution;
		updateStdOutputCondSet.conditionSetName = newStdOutputCondSet.conditionSetName;
		updateStdOutputCondSet.conditionOutputName = newStdOutputCondSet.conditionOutputName;
		updateStdOutputCondSet.stringFormat = newStdOutputCondSet.stringFormat;
		this.commandProxy().update(updateStdOutputCondSet);
	}

	@Override
	public void remove(String cid, String conditionSetCd) {
		this.commandProxy().remove(OiomtStdOutputCondSet.class, new OiomtStdOutputCondSetPk(cid, conditionSetCd));
		this.getEntityManager().flush();
	}

	private static StdOutputCondSet toDomain(OiomtStdOutputCondSet entity) {
		return new StdOutputCondSet(entity.stdOutputCondSetPk.cid, entity.stdOutputCondSetPk.conditionSetCd,
				entity.categoryId, entity.delimiter, entity.itemOutputName, entity.autoExecution,
				entity.conditionSetName, entity.conditionOutputName, entity.stringFormat);
	}

	private OiomtStdOutputCondSet toEntity(StdOutputCondSet domain) {
		return new OiomtStdOutputCondSet(new OiomtStdOutputCondSetPk(domain.getCid(), domain.getConditionSetCode().v()),
				domain.getCategoryId().v(), domain.getDelimiter().value, domain.getItemOutputName().value,
				domain.getAutoExecution().value, domain.getConditionSetName().v(),
				domain.getConditionOutputName().value, domain.getStringFormat().value);
	}
	
}
