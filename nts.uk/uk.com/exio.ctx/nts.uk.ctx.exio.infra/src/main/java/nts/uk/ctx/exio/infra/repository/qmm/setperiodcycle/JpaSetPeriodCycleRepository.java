package nts.uk.ctx.exio.infra.repository.qmm.setperiodcycle;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.qmm.setperiodcycle.SetPeriodCycle;
import nts.uk.ctx.exio.dom.qmm.setperiodcycle.SetPeriodCycleRepository;
import nts.uk.ctx.exio.infra.entity.qmm.setperiodcycle.QpbmtSetPeriodCycle;
import nts.uk.ctx.exio.infra.entity.qmm.setperiodcycle.QpbmtSetPeriodCyclePk;

@Stateless
public class JpaSetPeriodCycleRepository extends JpaRepository implements SetPeriodCycleRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtSetPeriodCycle f";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.setPeriodCyclePk.salaryItemId =:salaryItemId ";

	@Override
	public List<SetPeriodCycle> getAllSetPeriodCycle() {
		return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtSetPeriodCycle.class)
				.getList(item -> item.toDomain());
	}

	@Override
	public Optional<SetPeriodCycle> getSetPeriodCycleById(String salaryItemId) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtSetPeriodCycle.class)
				.setParameter("salaryItemId", salaryItemId).getSingle(c -> c.toDomain());
	}

	@Override
	public void add(SetPeriodCycle domain) {
		this.commandProxy().insert(QpbmtSetPeriodCycle.toEntity(domain));
	}

	@Override
	public void update(SetPeriodCycle domain) {
		this.commandProxy().update(QpbmtSetPeriodCycle.toEntity(domain));
	}

	@Override
	public void remove(String salaryItemId) {
		this.commandProxy().remove(QpbmtSetPeriodCycle.class, new QpbmtSetPeriodCyclePk(salaryItemId));
	}
}
