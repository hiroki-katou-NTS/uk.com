package nts.uk.ctx.exio.infra.repository.exo.condset;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSet;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSetRepository;
import nts.uk.ctx.exio.infra.entity.exo.condset.OiomtExOutCond;
import nts.uk.ctx.exio.infra.entity.exo.condset.OiomtExOutCondPk;

@Stateless
public class JpaStdOutputCondSetRepository extends JpaRepository implements StdOutputCondSetRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM OiomtExOutCond f";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.stdOutputCondSetPk.cid =:cid AND  f.stdOutputCondSetPk.conditionSetCd =:conditionSetCd ";

	private static final String SELECT_BY_CID = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.stdOutputCondSetPk.cid =:cid ORDER BY f.stdOutputCondSetPk.conditionSetCd ASC";
	

	@Override
	public List<StdOutputCondSet> getAllStdOutputCondSet() {
		return this.queryProxy().query(SELECT_ALL_QUERY_STRING, OiomtExOutCond.class)
				.getList(item -> toDomain(item));
	}
	
	@Override
	 public Optional<StdOutputCondSet> getStdOutputCondSetByCid(String cid) {
	  return this.queryProxy().query(SELECT_BY_CID, OiomtExOutCond.class).setParameter("cid", cid)
	    .getSingle(c -> toDomain(c));
	 }
	
	@Override
	public List<StdOutputCondSet> getStdOutCondSetByCid(String cid) {
		return this.queryProxy().query(SELECT_BY_CID, OiomtExOutCond.class).setParameter("cid", cid)
				.getList(c -> toDomain(c));
	}

	@Override
	public Optional<StdOutputCondSet> getStdOutputCondSetById(String cid, String conditionSetCd) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING, OiomtExOutCond.class).setParameter("cid", cid)
				.setParameter("conditionSetCd", conditionSetCd).getSingle(c -> toDomain(c));
	}
	
	@Override
	public List<StdOutputCondSet> getStdOutputCondSetById(String cid, Optional<String> conditionSetCd) {
		if (conditionSetCd.isPresent()) {
			return this.queryProxy().query(SELECT_BY_KEY_STRING, OiomtExOutCond.class).setParameter("cid", cid)
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
		OiomtExOutCond newStdOutputCondSet = toEntity(domain);
		OiomtExOutCond updateStdOutputCondSet = this.queryProxy()
				.find(newStdOutputCondSet.stdOutputCondSetPk, OiomtExOutCond.class).get();
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
		this.commandProxy().remove(OiomtExOutCond.class, new OiomtExOutCondPk(cid, conditionSetCd));
		this.getEntityManager().flush();
	}

	private static StdOutputCondSet toDomain(OiomtExOutCond entity) {
		return new StdOutputCondSet(entity.stdOutputCondSetPk.cid, entity.stdOutputCondSetPk.conditionSetCd,
				entity.categoryId, entity.delimiter, entity.itemOutputName, entity.autoExecution,
				entity.conditionSetName, entity.conditionOutputName, entity.stringFormat);
	}

	private OiomtExOutCond toEntity(StdOutputCondSet domain) {
		return new OiomtExOutCond(
				new OiomtExOutCondPk(
					domain.getCid(), 
					domain.getConditionSetCode().v()),
				domain.getConditionSetName().v(),
				domain.getCategoryId().v(), 
				domain.getConditionOutputName().value, 
				domain.getItemOutputName().value,
				domain.getDelimiter().value,  
				domain.getStringFormat().value,
				domain.getAutoExecution().value);
	}
	
}
