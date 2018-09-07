package nts.uk.ctx.pr.core.infra.repository.wageprovision.statementitem;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.StatementItem;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.StatementItemRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementitem.QpbmtBillingItem;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementitem.QpbmtBillingItemPk;

@Stateless
public class JpaStatementItemRepository extends JpaRepository implements StatementItemRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtBillingItem f";
	private static final String SELECT_BY_COMPANY_ID = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.billingItemPk.cid =:cid";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.billingItemPk.cid =:cid AND " + " f.billingItemPk.categoryAtr =:categoryAtr AND "
			+ " f.billingItemPk.itemNameCd =:itemNameCd AND" + " f.billingItemPk.salaryItemId =:salaryItemId";

	@Override
	public List<StatementItem> getAllStatementItem() {
		return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtBillingItem.class)
				.getList(item -> item.toDomain());
	}
	
	@Override
	public List<StatementItem> getAllItemByCid(String cid) {
		return this.queryProxy().query(SELECT_BY_COMPANY_ID, QpbmtBillingItem.class).setParameter("cid", cid)
				.getList(item -> item.toDomain());
	}

	@Override
	public Optional<StatementItem> getStatementItemById(String cid, int categoryAtr, int itemNameCd, String salaryItemId) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtBillingItem.class).setParameter("cid", cid)
				.setParameter("categoryAtr", categoryAtr).setParameter("itemNameCd", itemNameCd)
				.setParameter("salaryItemId", salaryItemId).getSingle(c -> c.toDomain());
	}

	@Override
	public void add(StatementItem domain) {
		this.commandProxy().insert(QpbmtBillingItem.toEntity(domain));
	}

	@Override
	public void update(StatementItem domain) {
		this.commandProxy().update(QpbmtBillingItem.toEntity(domain));
	}

	@Override
	public void remove(String cid, int categoryAtr, int itemNameCd, String salaryItemId) {
		this.commandProxy().remove(QpbmtBillingItem.class,
				new QpbmtBillingItemPk(cid, categoryAtr, itemNameCd, salaryItemId));
	}
}
