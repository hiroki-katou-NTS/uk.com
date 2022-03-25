package nts.uk.ctx.exio.infra.repository.exo.condset;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSet;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSetRepository;
import nts.uk.ctx.exio.infra.entity.exo.condset.OiomtExOutCond;
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

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM OiomtExOutCond f";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.stdOutputCondSetPk.cid =:cid AND  f.stdOutputCondSetPk.conditionSetCd =:conditionSetCd ";

	private static final String SELECT_BY_CID = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.stdOutputCondSetPk.cid =:cid ORDER BY f.stdOutputCondSetPk.conditionSetCd ASC";

	@Override
	public List<StdOutputCondSet> getAllStdOutputCondSet() {
		return this.queryProxy().query(SELECT_ALL_QUERY_STRING, OiomtExOutCond.class)
				.getList((OiomtExOutCond entity) -> StdOutputCondSet.createFromMemento(entity.getCompanyId(), entity));
	}

	@Override
	public Optional<StdOutputCondSet> getStdOutputCondSetByCid(String cid) {
		return this.queryProxy().query(SELECT_BY_CID, OiomtExOutCond.class)
				.setParameter("cid", cid)
				.getSingle((OiomtExOutCond entity) -> StdOutputCondSet.createFromMemento(entity.getCompanyId(), entity));
	}

	@Override
	public List<StdOutputCondSet> getStdOutCondSetByCid(String cid) {
		return this.queryProxy().query(SELECT_BY_CID, OiomtExOutCond.class)
				.setParameter("cid", cid)
				.getList((OiomtExOutCond entity) -> StdOutputCondSet.createFromMemento(entity.getCompanyId(), entity));
	}

	@Override
	public Optional<StdOutputCondSet> getStdOutputCondSetById(String cid, String conditionSetCd) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING, OiomtExOutCond.class)
				.setParameter("cid", cid)
				.setParameter("conditionSetCd", conditionSetCd)
				.getSingle((OiomtExOutCond entity) -> StdOutputCondSet.createFromMemento(entity.getCompanyId(), entity));
	}

	@Override
	public List<StdOutputCondSet> getStdOutputCondSetById(String cid, Optional<String> conditionSetCd) {
		if (conditionSetCd.isPresent()) {
			return this.queryProxy().query(SELECT_BY_KEY_STRING, OiomtExOutCond.class)
					.setParameter("cid", cid)
					.setParameter("conditionSetCd", conditionSetCd.get())
					.getList((OiomtExOutCond entity) -> StdOutputCondSet.createFromMemento(entity.getCompanyId(), entity));
		}
		return this.getStdOutCondSetByCid(cid);
	}

	@Override
	public void add(StdOutputCondSet domain) {
		this.commandProxy().insert(new OiomtExOutCond(domain));
	}

	@Override
	public void update(StdOutputCondSet domain) {
		OiomtExOutCond newStdOutputCondSet = new OiomtExOutCond(domain);
		Optional<OiomtExOutCond> updateStdOutputCondSet = this.queryProxy().find(newStdOutputCondSet.getStdOutputCondSetPk(),
																						OiomtExOutCond.class);
		if (updateStdOutputCondSet.isPresent()) {
			OiomtExOutCond entity = updateStdOutputCondSet.get();
			entity.setCategoryId(newStdOutputCondSet.getCategoryId());
			entity.setDelimiter(newStdOutputCondSet.getDelimiter());
			entity.setItemOutputName(newStdOutputCondSet.getItemOutputName());
			entity.setAutoExecution(newStdOutputCondSet.getAutoExecution());
			entity.setConditionSetName(newStdOutputCondSet.getConditionSetName());
			entity.setConditionOutputName(newStdOutputCondSet.getConditionOutputName());
			entity.setStringFormat(newStdOutputCondSet.getStringFormat());
			entity.setEncodeType(newStdOutputCondSet.getEncodeType());
			entity.setFileName(newStdOutputCondSet.getFileName());
			this.commandProxy().update(entity);
		}
	}

	@Override
	public void remove(String cid, String conditionSetCd) {
		this.commandProxy().remove(OiomtExOutCond.class, new OiomtStdOutputCondSetPk(cid, conditionSetCd));
		this.getEntityManager().flush();
	}

}
