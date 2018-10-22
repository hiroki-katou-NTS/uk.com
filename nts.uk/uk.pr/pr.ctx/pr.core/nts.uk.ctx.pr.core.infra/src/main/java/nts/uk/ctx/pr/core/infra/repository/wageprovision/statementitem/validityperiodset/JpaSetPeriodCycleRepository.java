package nts.uk.ctx.pr.core.infra.repository.wageprovision.statementitem.validityperiodset;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.validityperiodset.SetPeriodCycleRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.validityperiodset.SetValidityPeriodCycle;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementitem.validityperiodset.QpbmtSetPeriodCycle;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementitem.validityperiodset.QpbmtSetPeriodCyclePk;

@Stateless
public class JpaSetPeriodCycleRepository extends JpaRepository implements SetPeriodCycleRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtSetPeriodCycle f";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.setPeriodCyclePk.cid =:cid "
			+ " AND  f.setPeriodCyclePk.categoryAtr =:categoryAtr "
			+ " AND  f.setPeriodCyclePk.itemNameCd =:itemNameCd ";

	@Override
	public List<SetValidityPeriodCycle> getAllSetPeriodCycle() {
		return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtSetPeriodCycle.class)
				.getList(item -> item.toDomain());
	}

	@Override
	public Optional<SetValidityPeriodCycle> getSetPeriodCycleById(String cid, int categoryAtr, String itemNameCd) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtSetPeriodCycle.class)
				.setParameter("cid", cid).setParameter("categoryAtr", categoryAtr)
				.setParameter("itemNameCd", itemNameCd).getSingle(c -> c.toDomain());
	}

	@Override
	public void register(SetValidityPeriodCycle domain) {
		this.remove(domain.getCid(), domain.getCategoryAtr().value, domain.getItemNameCd().v());
		this.getEntityManager().flush();
		this.commandProxy().insert(QpbmtSetPeriodCycle.toEntity(domain));
	}

	@Override
	public void remove(String cid, int categoryAtr, String itemNameCd) {
		if (this.getSetPeriodCycleById(cid, categoryAtr, itemNameCd).isPresent()) {
			this.commandProxy().remove(QpbmtSetPeriodCycle.class, new QpbmtSetPeriodCyclePk(cid, categoryAtr, itemNameCd));
		}
	}
}
