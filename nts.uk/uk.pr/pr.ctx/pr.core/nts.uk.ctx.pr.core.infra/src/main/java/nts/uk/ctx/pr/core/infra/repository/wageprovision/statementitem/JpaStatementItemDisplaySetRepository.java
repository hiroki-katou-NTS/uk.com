package nts.uk.ctx.pr.core.infra.repository.wageprovision.statementitem;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.StatementItemDisplaySet;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.StatementItemDisplaySetRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementitem.QpbmtStatementItemDisp;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementitem.QpbmtStatementItemDispPk;

@Stateless
public class JpaStatementItemDisplaySetRepository extends JpaRepository implements StatementItemDisplaySetRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtStatementItemDisp f";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.statementItemDispPk.cid =:cid AND  f.statementItemDispPk.categoryAtr =:categoryAtr "
			+ " AND f.statementItemDispPk.itemNameCd =:itemNameCd";

	@Override
	public List<StatementItemDisplaySet> getAllSpecItemDispSet() {
		return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtStatementItemDisp.class)
				.getList(item -> item.toDomain());
	}

	@Override
	public Optional<StatementItemDisplaySet> getSpecItemDispSetById(String cid, int categoryAtr, String itemNameCd) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtStatementItemDisp.class).setParameter("cid", cid)
				.setParameter("categoryAtr", categoryAtr).setParameter("itemNameCd", itemNameCd).getSingle(c -> c.toDomain());
	}

	@Override
	public void add(StatementItemDisplaySet domain) {
		this.commandProxy().insert(QpbmtStatementItemDisp.toEntity(domain));
	}

	@Override
	public void update(StatementItemDisplaySet domain) {
		this.commandProxy().update(QpbmtStatementItemDisp.toEntity(domain));
	}

	@Override
	public void remove(String cid, int categoryAtr, String itemNameCd) {
		if (this.getSpecItemDispSetById(cid, categoryAtr, itemNameCd).isPresent()) {
			this.commandProxy().remove(QpbmtStatementItemDisp.class, new QpbmtStatementItemDispPk(cid, categoryAtr, itemNameCd));
		}
	}
}
