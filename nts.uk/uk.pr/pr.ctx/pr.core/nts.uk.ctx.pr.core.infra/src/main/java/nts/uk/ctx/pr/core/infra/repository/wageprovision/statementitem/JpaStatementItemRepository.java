package nts.uk.ctx.pr.core.infra.repository.wageprovision.statementitem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.StatementItem;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.StatementItemCustom;
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
			+ " f.statementItemPk.itemNameCd =:itemNameCd";
	private static final String SELECT_CUSTOM =
			"SELECT f.statementItemPk.categoryAtr, f.statementItemPk.itemNameCd, n.name, f.deprecatedAtr "
					+ " FROM QpbmtStatementItem f INNER JOIN QpbmtStatementItemName n "
					+ " ON f.statementItemPk.cid = n.statementItemNamePk.cid "
					+ " AND f.statementItemPk.categoryAtr = n.statementItemNamePk.categoryAtr "
					+ " AND f.statementItemPk.itemNameCd = n.statementItemNamePk.itemNameCd "
					+ " WHERE  f.statementItemPk.cid =:cid ";
	private static final String SELECT_CUSTOM_BY_CATE_AND_DEP = SELECT_CUSTOM
					+ " AND f.statementItemPk.categoryAtr =:categoryAtr "
					+ " AND f.deprecatedAtr = 0 ";
	private static final String SELECT_CUSTOM_BY_CATE = SELECT_CUSTOM
					+ " AND f.statementItemPk.categoryAtr =:categoryAtr ";
	private static final String SELECT_CUSTOM_BY_DEP = SELECT_CUSTOM
					+ " AND f.deprecatedAtr = 0 ";

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
	public Optional<StatementItem> getStatementItemById(String cid, int categoryAtr, String itemNameCd) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtStatementItem.class).setParameter("cid", cid)
				.setParameter("categoryAtr", categoryAtr).setParameter("itemNameCd", itemNameCd)
				.getSingle(c -> c.toDomain());
	}

	@Override
	public List<StatementItemCustom> getItemCustomByCategoryAndDeprecated(String cid, int categoryAtr, boolean isIncludeDeprecated) {
		String query = isIncludeDeprecated ? SELECT_CUSTOM_BY_CATE : SELECT_CUSTOM_BY_CATE_AND_DEP;

		return this.queryProxy().query(query, Object[].class).setParameter("cid", cid)
				.setParameter("categoryAtr", categoryAtr)
				.getList(item -> new StatementItemCustom(item[0] != null ? String.valueOf(item[0]) : "", item[1] != null ? String.valueOf(item[1]) : "",
						item[2] != null ? String.valueOf(item[2]) : "", item[3] != null ? String.valueOf(item[3]) : ""));
	}

	@Override
	public List<StatementItemCustom> getItemCustomByDeprecated(String cid, boolean isIncludeDeprecated) {
		String query = isIncludeDeprecated ? SELECT_CUSTOM : SELECT_CUSTOM_BY_DEP;

		List<StatementItemCustom> result = this.queryProxy().query(query, Object[].class).setParameter("cid", cid)
				.getList(item -> new StatementItemCustom(item[0] != null ? String.valueOf(item[0]) : "", item[1] != null ? String.valueOf(item[1]) : "",
						item[2] != null ? String.valueOf(item[2]) : "", item[3] != null ? String.valueOf(item[3]) : ""));

		return result;
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
	public void remove(String cid, int categoryAtr, String itemNameCd) {
		if (this.getStatementItemById(cid, categoryAtr, itemNameCd).isPresent()) {
			this.commandProxy().remove(QpbmtStatementItem.class,
					new QpbmtStatementItemPk(cid, categoryAtr, itemNameCd));
		}
	}

}
