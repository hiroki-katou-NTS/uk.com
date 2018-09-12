package nts.uk.ctx.exio.infra.repository.exo.condset;

import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.exio.infra.entity.exo.condset.OiomtStdOutputCondSet;
import nts.uk.ctx.exio.infra.entity.exo.condset.OiomtStdOutputCondSetPk;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSetRepository;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSet;
import nts.arc.layer.infra.data.JpaRepository;

@Stateless
public class JpaStdOutputCondSetRepository extends JpaRepository implements StdOutputCondSetRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM OiomtStdOutputCondSet f";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.stdOutputCondSetPk.cid =:cid AND  f.stdOutputCondSetPk.conditionSetCd =:conditionSetCd ";

	@Override
	public List<StdOutputCondSet> getAllStdOutputCondSet() {
		return this.queryProxy().query(SELECT_ALL_QUERY_STRING, OiomtStdOutputCondSet.class)
				.getList(item -> toDomain(item));
	}

	@Override
	public Optional<StdOutputCondSet> getStdOutputCondSetById(String cid, String conditionSetCd) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING, OiomtStdOutputCondSet.class).setParameter("cid", cid)
				.setParameter("conditionSetCd", conditionSetCd).getSingle(c -> toDomain(c));
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
	}

	private static StdOutputCondSet toDomain(OiomtStdOutputCondSet entity) {
		return StdOutputCondSet.toDomain(entity.stdOutputCondSetPk.cid,
				entity.stdOutputCondSetPk.conditionSetCd, entity.categoryId, entity.delimiter, entity.itemOutputName,
				entity.autoExecution, entity.conditionSetName, entity.conditionOutputName, entity.stringFormat);
	}

	private OiomtStdOutputCondSet toEntity(StdOutputCondSet domain) {
		return new OiomtStdOutputCondSet(new OiomtStdOutputCondSetPk(domain.getCid(), domain.getConditionSetCd()),
				domain.getCategoryId(), domain.getDelimiter(), domain.getItemOutputName(), domain.getAutoExecution(),
				domain.getConditionSetName(), domain.getConditionOutputName(), domain.getStringFormat());
	}

}
