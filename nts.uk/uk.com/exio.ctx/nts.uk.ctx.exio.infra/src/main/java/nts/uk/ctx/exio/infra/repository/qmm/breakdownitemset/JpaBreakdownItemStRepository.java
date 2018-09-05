package nts.uk.ctx.exio.infra.repository.qmm.breakdownitemset;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.qmm.breakdownitemset.BreakdownItemSt;
import nts.uk.ctx.exio.dom.qmm.breakdownitemset.BreakdownItemStRepository;
import nts.uk.ctx.exio.infra.entity.qmm.breakdownitemset.QpbmtBreakdownItemSt;
import nts.uk.ctx.exio.infra.entity.qmm.breakdownitemset.QpbmtBreakdownItemStPk;

@Stateless
public class JpaBreakdownItemStRepository extends JpaRepository implements BreakdownItemStRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtBreakdownItemSt f";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.breakdownItemStPk.salaryItemId =:salaryItemId AND  f.breakdownItemStPk.breakdownItemCode =:breakdownItemCode ";

	@Override
	public List<BreakdownItemSt> getAllBreakdownItemSt() {
		return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtBreakdownItemSt.class)
				.getList(item -> item.toDomain());
	}

	@Override
	public Optional<BreakdownItemSt> getBreakdownItemStById(String salaryItemId, int breakdownItemCode) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtBreakdownItemSt.class)
				.setParameter("salaryItemId", salaryItemId).setParameter("breakdownItemCode", breakdownItemCode)
				.getSingle(c -> c.toDomain());
	}

	@Override
	public void add(BreakdownItemSt domain) {
		this.commandProxy().insert(QpbmtBreakdownItemSt.toEntity(domain));
	}

	@Override
	public void update(BreakdownItemSt domain) {
		this.commandProxy().update(QpbmtBreakdownItemSt.toEntity(domain));
	}

	@Override
	public void remove(String salaryItemId, int breakdownItemCode) {
		this.commandProxy().remove(QpbmtBreakdownItemSt.class,
				new QpbmtBreakdownItemStPk(salaryItemId, breakdownItemCode));
	}
}
