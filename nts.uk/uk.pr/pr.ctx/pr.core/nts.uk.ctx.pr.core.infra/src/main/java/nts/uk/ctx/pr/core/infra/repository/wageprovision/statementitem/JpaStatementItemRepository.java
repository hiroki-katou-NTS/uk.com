package nts.uk.ctx.pr.core.infra.repository.wageprovision.statementitem;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.query.TypedQueryWrapper;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.CategoryAtr;
import nts.uk.ctx.pr.core.dom.wageprovision.averagewagecalculationset.StatementCustom;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.StatementItem;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.StatementItemCustom;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.StatementItemRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementitem.QpbmtStatementItem;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementitem.QpbmtStatementItemPk;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaStatementItemRepository extends JpaRepository implements StatementItemRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtStatementItem f";
	private static final String ORDER_BY_ITEM_NAME_CD_ASC = " ORDER BY f.statementItemPk.itemNameCd ASC";
	private static final String SELECT_BY_COMPANY_ID = SELECT_ALL_QUERY_STRING + " WHERE  f.statementItemPk.cid =:cid"
			+ ORDER_BY_ITEM_NAME_CD_ASC;
	private static final String SELECT_BY_CATEGORY = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.statementItemPk.cid =:cid AND " + " f.statementItemPk.categoryAtr =:categoryAtr"
			+ ORDER_BY_ITEM_NAME_CD_ASC;
	private static final String SELECT_BY_ITEM_NAME_CD = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.statementItemPk.cid =:cid AND " + " f.statementItemPk.categoryAtr =:categoryAtr AND "
			+ " f.statementItemPk.itemNameCd =:itemNameCd "
			+ ORDER_BY_ITEM_NAME_CD_ASC;
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.statementItemPk.cid =:cid AND " + " f.statementItemPk.categoryAtr =:categoryAtr AND "
			+ " f.statementItemPk.itemNameCd =:itemNameCd"
			+ ORDER_BY_ITEM_NAME_CD_ASC;
	private static final String SELECT_CUSTOM =
			"SELECT f.statementItemPk.categoryAtr, f.statementItemPk.itemNameCd, n.name, f.deprecatedAtr, f.defaultAtr, n.shortName "
					+ " FROM QpbmtStatementItem f INNER JOIN QpbmtStatementItemName n "
					+ " ON f.statementItemPk.cid = n.statementItemNamePk.cid "
					+ " AND f.statementItemPk.categoryAtr = n.statementItemNamePk.categoryAtr "
					+ " AND f.statementItemPk.itemNameCd = n.statementItemNamePk.itemNameCd "
					+ " WHERE  f.statementItemPk.cid =:cid ";
	private static final String SELECT_CUSTOM_BY_CATE_AND_DEP = SELECT_CUSTOM
					+ " AND f.statementItemPk.categoryAtr =:categoryAtr "
					+ " AND f.deprecatedAtr = 0 "
					+ ORDER_BY_ITEM_NAME_CD_ASC;
	private static final String SELECT_CUSTOM_BY_CATE = SELECT_CUSTOM
					+ " AND f.statementItemPk.categoryAtr =:categoryAtr "
					+ ORDER_BY_ITEM_NAME_CD_ASC;
	private static final String SELECT_CUSTOM_BY_DEP = SELECT_CUSTOM
					+ " AND f.deprecatedAtr = 0 "
					+ ORDER_BY_ITEM_NAME_CD_ASC;
	private static final String SELECT_CUSTOM_BY_CTG = SELECT_CUSTOM
					+ " AND f.statementItemPk.categoryAtr =:categoryAtr "
                    + " AND f.deprecatedAtr = :deprecatedAtr ";

    private static final String SELECT_CUSTOM_BY_STATEMENT_ITEMST =
            "SELECT a.statementItemPk.categoryAtr, a.statementItemPk.itemNameCd, c.name "
                    + " FROM QpbmtStatementItem a INNER JOIN QpbmtPaymentItemSt b "
                    + " ON a.statementItemPk.cid = b.paymentItemStPk.cid "
                    + " AND a.statementItemPk.categoryAtr = b.paymentItemStPk.categoryAtr "
                    + " AND a.statementItemPk.itemNameCd = b.paymentItemStPk.itemNameCd "
                    + " INNER JOIN QpbmtStatementItemName c"
                    + " ON a.statementItemPk.cid = c.statementItemNamePk.cid "
                    + " AND a.statementItemPk.categoryAtr = c.statementItemNamePk.categoryAtr "
                    + " AND a.statementItemPk.itemNameCd = c.statementItemNamePk.itemNameCd "
                    + " WHERE  a.statementItemPk.cid =:cid "
                    + " AND (a.statementItemPk.categoryAtr = 0 OR a.statementItemPk.categoryAtr = 1) "
                    + " AND a.defaultAtr = 0"
                    + " AND a.deprecatedAtr = 0"
                    + " AND b.breakdownItemUseAtr = 1";

    private static final String SELECT_CUSTOM_BY_STATEMENT_ITEMDEDUCTION =
            "SELECT a.statementItemPk.categoryAtr, a.statementItemPk.itemNameCd, c.name "
                    + " FROM QpbmtStatementItem a INNER JOIN QpbmtStatementItemName c"
                    + " ON a.statementItemPk.cid = c.statementItemNamePk.cid "
                    + " AND a.statementItemPk.categoryAtr = c.statementItemNamePk.categoryAtr "
                    + " AND a.statementItemPk.itemNameCd = c.statementItemNamePk.itemNameCd "
                    + " INNER JOIN QpbmtDeductionItemSt d"
                    + " ON a.statementItemPk.cid = d.deductionItemStPk.cid "
                    + " AND a.statementItemPk.categoryAtr = d.deductionItemStPk.categoryAtr "
                    + " AND a.statementItemPk.itemNameCd = d.deductionItemStPk.itemNameCd "
                    + " WHERE  a.statementItemPk.cid =:cid "
                    + " AND (a.statementItemPk.categoryAtr = 0 OR a.statementItemPk.categoryAtr = 1) "
                    + " AND a.defaultAtr = 0"
                    + " AND a.deprecatedAtr = 0"
                    + " AND d.breakdownItemUseAtr = 1";

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
						item[2] != null ? String.valueOf(item[2]) : "", item[3] != null ? String.valueOf(item[3]) : "",
						item[4] != null ? String.valueOf(item[4]) : "", item[5] != null ? String.valueOf(item[5]) : ""));
	}

	@Override
	public List<StatementItemCustom> getItemCustomByDeprecated(String cid, boolean isIncludeDeprecated) {
		String query = isIncludeDeprecated ? SELECT_CUSTOM : SELECT_CUSTOM_BY_DEP;

		List<StatementItemCustom> result = this.queryProxy().query(query, Object[].class).setParameter("cid", cid)
				.getList(item -> new StatementItemCustom(item[0] != null ? String.valueOf(item[0]) : "", item[1] != null ? String.valueOf(item[1]) : "",
						item[2] != null ? String.valueOf(item[2]) : "", item[3] != null ? String.valueOf(item[3]) : "",
						item[4] != null ? String.valueOf(item[4]) : "", item[5] != null ? String.valueOf(item[5]) : ""));

		return result;
	}

	@Override
	public List<StatementItemCustom> getItemCustomByCtgAndExcludeCodes(String cid, int categoryAtr, int deprecatedAtr,
                                                                       List<String> itemNameCdFixedList,
																	   String itemNameCdSelected,
																	   List<String> itemNameCdExList) {
	    boolean hasItemNameCdFixedList = itemNameCdFixedList != null && !itemNameCdFixedList.isEmpty();
        boolean hasItemNameCdExList = itemNameCdExList != null && !itemNameCdExList.isEmpty();
        boolean hasItemNameCdSelected = itemNameCdSelected != null;
		TypedQueryWrapper<Object[]> typeQuery;
        StringBuilder builder = new StringBuilder();
        builder.append(SELECT_CUSTOM_BY_CTG);
		builder.append(" AND ( ");
		if(hasItemNameCdFixedList){
            builder.append(" f.statementItemPk.itemNameCd NOT IN :itemNameCdFixedList ");
        }else{
			builder.append(" f.statementItemPk.itemNameCd IS NOT NULL ");
		}
        if(hasItemNameCdExList){
            builder.append(" AND f.statementItemPk.itemNameCd NOT IN :itemNameCdExList ");
        }else{
			builder.append(" AND f.statementItemPk.itemNameCd IS NOT NULL ");
		}
		if(hasItemNameCdSelected){
            builder.append(" OR f.statementItemPk.itemNameCd = :itemNameCdSelected ");
        }
		builder.append(" ) ");
        builder.append(ORDER_BY_ITEM_NAME_CD_ASC);
        typeQuery = this.queryProxy().query(builder.toString(), Object[].class)
                .setParameter("cid", cid)
                .setParameter("categoryAtr", categoryAtr)
				.setParameter("deprecatedAtr", deprecatedAtr);
		if (hasItemNameCdFixedList) {
			typeQuery.setParameter("itemNameCdFixedList", itemNameCdFixedList);
		}
		if (hasItemNameCdExList) {
			typeQuery.setParameter("itemNameCdExList", itemNameCdExList);
		}
        if(hasItemNameCdSelected){
            typeQuery.setParameter("itemNameCdSelected", itemNameCdSelected);
        }
		List<StatementItemCustom> result = typeQuery
				.getList(item -> new StatementItemCustom(item[0] != null ? String.valueOf(item[0]) : "", item[1] != null ? String.valueOf(item[1]) : "",
						item[2] != null ? String.valueOf(item[2]) : "", item[3] != null ? String.valueOf(item[3]) : "",
						item[4] != null ? String.valueOf(item[4]) : "", item[5] != null ? String.valueOf(item[5]) : ""));
		return result;
	}

    @Override
    public List<StatementCustom> getItemCustomByDeprecated(String cid) {
        return this.queryProxy().query(SELECT_CUSTOM_BY_STATEMENT_ITEMST, Object[].class).setParameter("cid", cid).getList(
                item -> new StatementCustom(item[0] != null ? String.valueOf(item[0]) : "", item[1] != null ? String.valueOf(item[1]) : "",
                        item[2] != null ? String.valueOf(item[2]) : "")
        );
    }

    @Override
    public List<StatementCustom> getItemCustomByDeprecated2(String cid) {
        return this.queryProxy().query(SELECT_CUSTOM_BY_STATEMENT_ITEMDEDUCTION, Object[].class).setParameter("cid", cid).getList(
                item -> new StatementCustom(item[0] != null ? String.valueOf(item[0]) : "", item[1] != null ? String.valueOf(item[1]) : "",
                        item[2] != null ? String.valueOf(item[2]) : "")
        );
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
