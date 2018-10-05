package nts.uk.ctx.pr.core.infra.repository.wageprovision.statementitem.breakdownitemset;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.breakdownitemset.BreakdownItemSet;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.breakdownitemset.BreakdownItemSetRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementitem.breakdownitemset.QpbmtBreakdownItemSt;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementitem.breakdownitemset.QpbmtBreakdownItemStPk;

@Stateless
public class JpaBreakdownItemStRepository extends JpaRepository implements BreakdownItemSetRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtBreakdownItemSt f";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.breakdownItemStPk.salaryItemId =:salaryItemId AND  f.breakdownItemStPk.breakdownItemCode =:breakdownItemCode ";
	private static final String SELECT_BY_SALARYID = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.breakdownItemStPk.salaryItemId =:salaryItemId";

	@Override
	public List<BreakdownItemSet> getAllBreakdownItemSt() {
		return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtBreakdownItemSt.class)
				.getList(item -> item.toDomain());
	}

	@Override
	public List<BreakdownItemSet> getBreakdownItemStBySalaryId(String salaryItemId) {
		return this.queryProxy().query(SELECT_BY_SALARYID, QpbmtBreakdownItemSt.class)
				.setParameter("salaryItemId", salaryItemId).getList(i -> i.toDomain());
	}

	@Override
	public Optional<BreakdownItemSet> getBreakdownItemStById(String salaryItemId, String breakdownItemCode) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtBreakdownItemSt.class)
				.setParameter("salaryItemId", salaryItemId).setParameter("breakdownItemCode", breakdownItemCode)
				.getSingle(c -> c.toDomain());
	}

	@Override
	public void add(BreakdownItemSet domain) {
		this.commandProxy().insert(QpbmtBreakdownItemSt.toEntity(domain));
	}

	@Override
	public void update(BreakdownItemSet domain) {
		this.commandProxy().update(QpbmtBreakdownItemSt.toEntity(domain));
	}

	@Override
	public void remove(String salaryItemId, String breakdownItemCode) {
		if (this.getBreakdownItemStById(salaryItemId, breakdownItemCode).isPresent()) {
			this.commandProxy().remove(QpbmtBreakdownItemSt.class,
					new QpbmtBreakdownItemStPk(salaryItemId, breakdownItemCode));
		}
	}

	@Override
	public void removeAll(String salaryItemId) {
		List<BreakdownItemSet> entities = this.getBreakdownItemStBySalaryId(salaryItemId);
		entities.forEach(entity -> this.remove(entity.getSalaryItemId(), entity.getBreakdownItemCode().v()));

	}

}
