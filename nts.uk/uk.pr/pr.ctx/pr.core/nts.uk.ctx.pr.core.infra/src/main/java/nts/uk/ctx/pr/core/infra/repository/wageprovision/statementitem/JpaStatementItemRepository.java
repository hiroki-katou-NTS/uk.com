package nts.uk.ctx.pr.core.infra.repository.wageprovision.statementitem;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.StatementItem;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.StatementItemRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementitem.QpbmtStatementItem;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementitem.QpbmtStatementItemPk;

@Stateless
public class JpaStatementItemRepository extends JpaRepository implements StatementItemRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtStatementItem f";
	private static final String SELECT_BY_COMPANY_ID = SELECT_ALL_QUERY_STRING + " WHERE  f.statementItemPk.cid =:cid";
	private static final String SELECT_BY_CATEGORY = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.statementItemPk.cid =:cid AND " + " f.statementItemPk.categoryAtr =:categoryAtr";
	private static final String SELECT_BY_ITEM_NAME_CD = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.statementItemPk.cid =:cid AND " + " f.statementItemPk.categoryAtr =:categoryAtr AND "
			+ " f.statementItemPk.itemNameCd =:itemNameCd ";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.statementItemPk.cid =:cid AND " + " f.statementItemPk.categoryAtr =:categoryAtr AND "
			+ " f.statementItemPk.itemNameCd =:itemNameCd AND" + " f.statementItemPk.salaryItemId =:salaryItemId";

	@Override
	public List<StatementItem> getAllStatementItem() {
		return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtStatementItem.class)
				.getList(item -> item.toDomain());
	}

	@Override
	public List<StatementItem> getAllItemByCid(String cid) {
		return this.queryProxy().query(SELECT_BY_COMPANY_ID, QpbmtStatementItem.class).setParameter("cid", cid)
				.getList(item -> item.toDomain());
	}

	@Override
	public List<StatementItem> getByCategory(String cid, int categoryAtr) {
		return this.queryProxy().query(SELECT_BY_CATEGORY, QpbmtStatementItem.class).setParameter("cid", cid)
				.setParameter("categoryAtr", categoryAtr).getList(item -> item.toDomain());
	}

	@Override
	public List<StatementItem> getByCategoryAndCode(String cid, int categoryAtr, String itemNameCd) {
		return this.queryProxy().query(SELECT_BY_ITEM_NAME_CD, QpbmtStatementItem.class).setParameter("cid", cid)
				.setParameter("categoryAtr", categoryAtr).setParameter("itemNameCd", itemNameCd)
				.getList(item -> item.toDomain());
	}

	@Override
	public Optional<StatementItem> getStatementItemById(String cid, int categoryAtr, String itemNameCd,
			String salaryItemId) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtStatementItem.class).setParameter("cid", cid)
				.setParameter("categoryAtr", categoryAtr).setParameter("itemNameCd", itemNameCd)
				.setParameter("salaryItemId", salaryItemId).getSingle(c -> c.toDomain());
	}

	@Override
	public void add(StatementItem domain) {
		this.commandProxy().insert(QpbmtStatementItem.toEntity(domain));
	}

	@Override
	public void update(StatementItem domain) {
		this.commandProxy().update(QpbmtStatementItem.toEntity(domain));
	}

	@Override
	public void remove(String cid, int categoryAtr, String itemNameCd, String salaryItemId) {
		if (this.getStatementItemById(cid, categoryAtr, itemNameCd, salaryItemId).isPresent()) {
			this.commandProxy().remove(QpbmtStatementItem.class,
					new QpbmtStatementItemPk(cid, categoryAtr, itemNameCd, salaryItemId));
		}
	}

}
