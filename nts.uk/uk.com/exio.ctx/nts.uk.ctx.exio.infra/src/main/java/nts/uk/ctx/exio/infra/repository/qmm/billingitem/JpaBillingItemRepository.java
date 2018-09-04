package nts.uk.ctx.exio.infra.repository.qmm.billingitem;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.qmm.billingitem.BillingItem;
import nts.uk.ctx.exio.dom.qmm.billingitem.BillingItemRepository;
import nts.uk.ctx.exio.infra.entity.qmm.billingitem.QpbmtBillingItem;
import nts.uk.ctx.exio.infra.entity.qmm.billingitem.QpbmtBillingItemPk;

@Stateless
public class JpaBillingItemRepository extends JpaRepository implements BillingItemRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtBillingItem f";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.billingItemPk.cid =:cid AND " + " f.billingItemPk.categoryAtr =:categoryAtr AND "
			+ " f.billingItemPk.itemNameCd =:itemNameCd AND" + " f.billingItemPk.salaryItemId =:salaryItemId";

	@Override
	public List<BillingItem> getAllBillingItem() {
		return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtBillingItem.class)
				.getList(item -> item.toDomain());
	}

	@Override
	public Optional<BillingItem> getBillingItemById(String cid, int categoryAtr, int itemNameCd, String salaryItemId) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtBillingItem.class).setParameter("cid", cid)
				.setParameter("categoryAtr", categoryAtr).setParameter("itemNameCd", itemNameCd)
				.setParameter("salaryItemId", salaryItemId).getSingle(c -> c.toDomain());
	}

	@Override
	public void add(BillingItem domain) {
		this.commandProxy().insert(QpbmtBillingItem.toEntity(domain));
	}

	@Override
	public void update(BillingItem domain) {
		this.commandProxy().update(QpbmtBillingItem.toEntity(domain));
	}

	@Override
	public void remove(String cid, int categoryAtr, int itemNameCd, String salaryItemId) {
		this.commandProxy().remove(QpbmtBillingItem.class,
				new QpbmtBillingItemPk(cid, categoryAtr, itemNameCd, salaryItemId));
	}
}
