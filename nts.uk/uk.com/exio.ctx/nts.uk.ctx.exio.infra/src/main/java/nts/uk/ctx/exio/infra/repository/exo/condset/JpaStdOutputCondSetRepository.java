package nts.uk.ctx.exio.infra.repository.exo.condset;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSet;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSetRepository;
import nts.uk.ctx.exio.infra.entity.exo.condset.OiomtStdOutputCondSet;
import nts.uk.ctx.exio.infra.entity.exo.condset.OiomtStdOutputCondSetPk;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

/**
 * The class Jpa standard output condition setting repository.<br>
 * Repository 出力条件設定（定型）
 *
 * @author nws-minhnb
 */
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
				.getList((OiomtStdOutputCondSet entity) -> StdOutputCondSet.createFromMemento(entity.getCompanyId(), entity));
	}

	@Override
	public Optional<StdOutputCondSet> getStdOutputCondSetByCid(String cid) {
		return this.queryProxy().query(SELECT_BY_CID, OiomtStdOutputCondSet.class)
				.setParameter("cid", cid)
				.getSingle((OiomtStdOutputCondSet entity) -> StdOutputCondSet.createFromMemento(entity.getCompanyId(), entity));
	}

	@Override
	public List<StdOutputCondSet> getStdOutCondSetByCid(String cid) {
		return this.queryProxy().query(SELECT_BY_CID, OiomtStdOutputCondSet.class)
				.setParameter("cid", cid)
				.getList((OiomtStdOutputCondSet entity) -> StdOutputCondSet.createFromMemento(entity.getCompanyId(), entity));
	}

	@Override
	public Optional<StdOutputCondSet> getStdOutputCondSetById(String cid, String conditionSetCd) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING, OiomtStdOutputCondSet.class)
				.setParameter("cid", cid)
				.setParameter("conditionSetCd", conditionSetCd)
				.getSingle((OiomtStdOutputCondSet entity) -> StdOutputCondSet.createFromMemento(entity.getCompanyId(), entity));
	}

	@Override
	public List<StdOutputCondSet> getStdOutputCondSetById(String cid, Optional<String> conditionSetCd) {
		if (conditionSetCd.isPresent()) {
			return this.queryProxy().query(SELECT_BY_KEY_STRING, OiomtStdOutputCondSet.class)
					.setParameter("cid", cid)
					.setParameter("conditionSetCd", conditionSetCd.get())
					.getList((OiomtStdOutputCondSet entity) -> StdOutputCondSet.createFromMemento(entity.getCompanyId(), entity));
		}
		return this.getStdOutCondSetByCid(cid);
	}

	@Override
	public void add(StdOutputCondSet domain) {
		this.commandProxy().insert(new OiomtStdOutputCondSet(domain));
	}

	@Override
	public void update(StdOutputCondSet domain) {
		OiomtStdOutputCondSet newStdOutputCondSet = new OiomtStdOutputCondSet(domain);
		Optional<OiomtStdOutputCondSet> updateStdOutputCondSet = this.queryProxy().find(newStdOutputCondSet.getStdOutputCondSetPk(),
																						OiomtStdOutputCondSet.class);
		if (updateStdOutputCondSet.isPresent()) {
			OiomtStdOutputCondSet entity = updateStdOutputCondSet.get();
			entity.setCategoryId(newStdOutputCondSet.getCategoryId());
			entity.setDelimiter(newStdOutputCondSet.getDelimiter());
			entity.setItemOutputName(newStdOutputCondSet.getItemOutputName());
			entity.setAutoExecution(newStdOutputCondSet.getAutoExecution());
			entity.setConditionSetName(newStdOutputCondSet.getConditionSetName());
			entity.setConditionOutputName(newStdOutputCondSet.getConditionOutputName());
			entity.setStringFormat(newStdOutputCondSet.getStringFormat());
			this.commandProxy().update(entity);
		}
	}

	@Override
	public void remove(String cid, String conditionSetCd) {
		this.commandProxy().remove(OiomtStdOutputCondSet.class, new OiomtStdOutputCondSetPk(cid, conditionSetCd));
		this.getEntityManager().flush();
	}

}
